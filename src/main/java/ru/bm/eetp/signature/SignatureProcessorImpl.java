package ru.bm.eetp.signature;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static ru.bm.eetp.config.Utils.encodeBase64;

@Component("vtbToEetpSignatureProcessor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SignatureProcessorImpl implements SignatureProcessor {

    private EetpSigner signer;

    public SignatureProcessorImpl(EetpSigner signer) {
        this.signer = signer;
    }

    public String getSignature(String content){
        return calculateSignGost(SignatureIdentificationConstants.VTB_FOR_EETP, content);
    }

    private String calculateSignGost(String signatureIdentification, String stringDocumentBody) {
        SignCalculator signCalculator = signer.calculator(signatureIdentification, stringDocumentBody);
        signCalculator.calculate();
        return new String(encodeBase64(signCalculator.signature()));
    }



}
