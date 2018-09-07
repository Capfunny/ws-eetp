package ru.bm.eetp.controler;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bm.eetp.service.IncomingDocumentExtractor;
import ru.bm.eetp.signature.EetpSigner;
import ru.bm.eetp.signature.SignCalculator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import javax.xml.bind.DatatypeConverter;
import java.util.Base64;

@RestController("/debug/sign")
@Profile("debug")
public class DebugSignXmlDocument {

    private IncomingDocumentExtractor incomingDocumentExtractor;
    private EetpSigner signer;

    public DebugSignXmlDocument(IncomingDocumentExtractor incomingDocumentExtractor, EetpSigner signer) {
        this.incomingDocumentExtractor = incomingDocumentExtractor;
        this.signer = signer;
    }

    @PostMapping
    public String signDocument(@RequestBody String stringDocumentBody,
                               @RequestParam("signatureIdentification") String signatureIdentification) {

        Optional<String> extract = incomingDocumentExtractor.extract(stringDocumentBody);

        String documentBody = extract.orElse("nonenonenonenone");

        return calculateSignGost(signatureIdentification, documentBody)+" document-length => " + documentBody.length()
                + " start => '" + documentBody.substring(0, 5) + "'"
                +" ... end => '" + documentBody.substring(documentBody.length() - 10) + "'";
    }

    private String calculateSignGost(String signatureIdentification, String stringDocumentBody) {
        SignCalculator signCalculator = signer.calculator(signatureIdentification, stringDocumentBody);
        signCalculator.calculate();
        String signature = new String(Base64.getEncoder().encode(signCalculator.signature()));

        return "Sign for '"+ signatureIdentification + "' "
                + signature;
    }

    private String calculateSign(String keyCode, String stringDocumentBody) {
        try {
            return "Sign for '"+ keyCode + "' "
                    + DatatypeConverter.printHexBinary(MessageDigest.getInstance("MD5").digest(stringDocumentBody.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            return "error calculate signature";
        }
    }
}
