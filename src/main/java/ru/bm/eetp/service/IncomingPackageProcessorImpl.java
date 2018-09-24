package ru.bm.eetp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bm.eetp.dto.ProcessResult;
import ru.bm.eetp.signature.SignatureValidator;
import ru.bm.eetp.signature.SignatureValidatorProducer;
import ru.bm.eetp.signature.keystore.storage.SignatureCheckExcluder;

import java.util.Optional;

import static ru.bm.eetp.config.Utils.*;

@Component
public class IncomingPackageProcessorImpl implements IncomingPackageProcessor {

    private SignatureValidatorProducer signatureValidatorProducer;
    private IncomingPackageValidator incomingPackageValidator;
    private IncomingDocumentValidator incomingDocumentValidator;
    private IncomingDocumentExtractor incomingDocumentExtractor;
    private IncomingSignatureExtractor incomingSignatureExtractor;
    private SignatureValidator signatureValidator;
    private TagExtractor tagExtractor;
    @Autowired  private SignatureCheckExcluder signatureCheckExcluder;

    public IncomingPackageProcessorImpl(SignatureValidatorProducer signatureValidatorProducer,
                                        IncomingPackageValidator incomingPackageValidator,
                                        IncomingDocumentValidator incomingDocumentValidator,
                                        IncomingDocumentExtractor incomingDocumentExtractor,
                                        IncomingSignatureExtractor incomingSignatureExtractor,
                                        SignatureValidator signatureValidator,
                                        TagExtractor tagExtractor) {
        this.signatureValidatorProducer = signatureValidatorProducer;
        this.incomingPackageValidator = incomingPackageValidator;
        this.incomingDocumentValidator = incomingDocumentValidator;
        this.incomingDocumentExtractor = incomingDocumentExtractor;
        this.incomingSignatureExtractor = incomingSignatureExtractor;
        this.signatureValidator = signatureValidator;
        this.tagExtractor = tagExtractor;
    }

    @Override
    public ProcessResult processing(String content) {
        return doValidatePackage(content);
    }

    private ProcessResult doValidatePackage(String content) {
        // проверить по XSD первое сообщение
        incomingPackageValidator.validate(content);
        if( ! incomingPackageValidator.validateResult()) {
            return ProcessResult.PACKAGE_VALIDATION_ERROR;
        }
        //вытащиить и раскодировать документ
        String documentXML;
        try {
            // вытащить из первого сообщения атрибут с документом
            String documentBase64 = extractDocument(content);
            // раскодировать base64
            documentXML = decodeBase64(documentBase64);
        } catch (Exception e) {
            return ProcessResult.DOCUMENT_VALIDATION_ERROR;
        }
        // проверить раскодированный документ по XSD
        incomingDocumentValidator.validate(documentXML);
        if( ! incomingDocumentValidator.validateResult()) {
            return ProcessResult.DOCUMENT_VALIDATION_ERROR;
        }
        // получаем имя торговой площадки и определяем, проверять ли подпись
        boolean checkSignature = true;
        Optional<String> operName = tagExtractor.extract("OperatorName",documentXML);
        if (operName.isPresent()) {
            checkSignature = (! signatureCheckExcluder.geteetpNames().contains(operName.get()));
        }
        // определяем, проверять ли подпись
        if (checkSignature) {
            // вытащить подпись
            String signature = extractSignature(content);
            //

            //пытаемся валидировать подпись
            if (! signatureValidator.validateSignature(documentXML, signature, null)) {
                return ProcessResult.SIGNATURE_VALIDATION_ERROR;
            }
        }
        //если всё ок, возвращает ОК )))
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
