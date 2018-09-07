package ru.bm.eetp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bm.eetp.dto.ProcessResult;
import ru.bm.eetp.signature.SignatureProcessor;

import java.util.Optional;

import static ru.bm.eetp.config.Utils.*;

@Component
public class IncomingPackageProcessorImpl implements IncomingPackageProcessor {

    @Autowired private SignatureProcessor signatureProcessor;
    @Autowired  private IncomingPackageValidator incomingPackageValidator;
    @Autowired  private IncomingDocumentValidator incomingDocumentValidator;
    @Autowired  private IncomingDocumentExtractor incomingDocumentExtractor;
    @Autowired  private IncomingSignatureExtractor incomingSignatureExtractor;

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
            debugg("documentBase64Content => ", documentBase64);
            // раскодировать base64
            documentXML = decodeBase64(documentBase64);
            debugg("documentXmlContent => ", documentXML);
        } catch (Exception e) {
            documentXML = "";
        }
        // проверить раскодированный документ по XSD
        incomingDocumentValidator.validate(documentXML);
        if( ! incomingDocumentValidator.validateResult()) {
            return validateResult = ProcessResult.DOCUMENT_VALIDATION_ERROR;
        }
        // вытащить и раскодировать подпись
        String signature;
        try{
           String signatureBase64 = extractSignature(content);
           debugg("signaturetBase64Content => ", signatureBase64);
            // раскодировать base64
           signature = decodeBase64(signatureBase64);
           debugg("signature => ", signature);
        }
        catch (Exception e){
            signature = "";
        }
        //валидировать раскодированную подпись
        signatureProcessor.validate(signature);
        if( ! signatureProcessor.getValidateResult()) {
            return validateResult = ProcessResult.SIGNATURE_VALIDATION_ERROR;
        }
        //вернуть результат валидации
        return validateResult = ProcessResult.OK;
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

}
