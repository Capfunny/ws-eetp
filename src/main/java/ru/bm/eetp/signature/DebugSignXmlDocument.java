package ru.bm.eetp.signature;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bm.eetp.service.IncomingDocumentExtractor;


import java.util.Optional;
import java.util.Base64;

import static ru.bm.eetp.config.Utils.decodeBase64;

@RestController("/debug/sign")
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

        String documentBase64 = extract.get();

        String documentXML = decodeBase64(documentBase64);

        return calculateSignGost(signatureIdentification, documentXML)+" document-length => " + documentBase64.length()
                + " start => '" + documentBase64.substring(0, 5) + "'"
                +" ... end => '" + documentBase64.substring(documentBase64.length() - 10) + "'";
    }

    private String calculateSignGost(String signatureIdentification, String stringDocumentBody) {
        SignCalculator signCalculator = signer.calculator(signatureIdentification, stringDocumentBody);
        signCalculator.calculate();
        String signature = new String(Base64.getEncoder().encode(signCalculator.signature()));

        return "Sign for '"+ signatureIdentification + "' "
                + "\n=>" + signature+"<=\n";
    }

}
