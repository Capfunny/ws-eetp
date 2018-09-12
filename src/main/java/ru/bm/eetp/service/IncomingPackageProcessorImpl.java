package ru.bm.eetp.service;

import org.springframework.stereotype.Component;
import ru.bm.eetp.dto.ProcessResult;
import ru.bm.eetp.signature.SignatureValidator;
import ru.bm.eetp.signature.SignatureValidatorProducer;

import java.util.Optional;

import static ru.bm.eetp.config.Utils.*;

@Component
public class IncomingPackageProcessorImpl implements IncomingPackageProcessor {

    private SignatureValidatorProducer signatureValidatorProducer;
    private IncomingPackageValidator incomingPackageValidator;
    private IncomingDocumentValidator incomingDocumentValidator;
    private IncomingDocumentExtractor incomingDocumentExtractor;
    private IncomingSignatureExtractor incomingSignatureExtractor;

    public IncomingPackageProcessorImpl(SignatureValidatorProducer signatureValidatorProducer,
                                        IncomingPackageValidator incomingPackageValidator,
                                        IncomingDocumentValidator incomingDocumentValidator,
                                        IncomingDocumentExtractor incomingDocumentExtractor,
                                        IncomingSignatureExtractor incomingSignatureExtractor) {

        this.signatureValidatorProducer = signatureValidatorProducer;
        this.incomingPackageValidator = incomingPackageValidator;
        this.incomingDocumentValidator = incomingDocumentValidator;
        this.incomingDocumentExtractor = incomingDocumentExtractor;
        this.incomingSignatureExtractor = incomingSignatureExtractor;
    }

    @Override
    public ProcessResult processing(String content) {
        return doValidatePackage(content);
    }

    private ProcessResult doValidatePackage(String content) {
        ProcessResult validateResult;
        // проверить по XSD первое сообщение
        incomingPackageValidator.validate(content);
        if( ! incomingPackageValidator.validateResult()) {
            return validateResult = ProcessResult.PACKAGE_VALIDATION_ERROR;
        }
        //вытащиить и раскодировать документ
        String documentXML;

        try {
            // вытащить из первого сообщения атрибут с документом
            String documentBase64 = extractDocument(content);
            debugg("documentBase64Content ", "=>"+documentBase64+"<=");
            // раскодировать base64
            documentXML = decodeBase64(documentBase64);

        } catch (Exception e) {
            documentXML = "";
        }
        // проверить раскодированный документ по XSD
        incomingDocumentValidator.validate(documentXML);
        if( ! incomingDocumentValidator.validateResult()) {
            return validateResult = ProcessResult.DOCUMENT_VALIDATION_ERROR;
        }
        // вытащить и раскодировать подпись
        byte[] signature;
        try{
           String signatureBase64 = extractSignature(content);
           debugg("signaturetBase64Content ", "=>"+signatureBase64+"<=");
            // раскодировать base64
           signature = decodeBase64ToByte(signatureBase64);
//           debugg("signature => ", signature);
        }
        catch (Exception e){
            signature = new byte[0];
        }

        Optional<SignatureValidator> signatureValidatorOpt = signatureValidatorProducer.signatureValidator(documentXML);
        if( ! signatureValidatorOpt.isPresent()) {
            return ProcessResult.DOCUMENT_VALIDATION_ERROR;
        } else {
            SignatureValidator signatureValidator = signatureValidatorOpt.get();
            //валидировать раскодированную подпись
            signatureValidator.validate(documentXML.getBytes(), signature);
            if (!signatureValidator.getValidateResult()) {
                return ProcessResult.SIGNATURE_VALIDATION_ERROR;
            }
            //вернуть результат валидации

        }
        return ProcessResult.OK;
    }

    private String extractSignature(String signature) {
        Optional<String> extract = incomingSignatureExtractor.extract(signature);
        if(extract.isPresent()) {
            return extract.get();
        }
        return "";
    }

    private String extractDocument(String document) {
        Optional<String> extract = incomingDocumentExtractor.extract(document);
        if(extract.isPresent()) {
            return extract.get();
        }
        return "";
    }

    private String documentFromTest() {
        return "";
    }

}
