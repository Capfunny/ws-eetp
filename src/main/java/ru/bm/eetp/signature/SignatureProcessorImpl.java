package ru.bm.eetp.signature;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.bm.eetp.config.Utils;

import java.util.Base64;

@Component("vtbToEetpSignatureProcessor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SignatureProcessorImpl implements SignatureProcessor {

    private boolean validateResult = false;

    private EetpSigner signer;
    private Base64Encoder base64Encoder;

    public SignatureProcessorImpl(EetpSigner signer, Base64Encoder base64Encoder) {
        this.signer = signer;
        this.base64Encoder = base64Encoder;
    }

    public void validate(String signature){
        validateResult =  (signature.length() > 0); //TO-DO!
    }

    public boolean getValidateResult(){
        return validateResult;
    }

    public String getSignature(String content){
        return calculateSignGost(SignatureIdentificationConstants.VTB_FOR_EETP, content);
    }

    private String calculateSignGost(String signatureIdentification, String stringDocumentBody) {
        SignCalculator signCalculator = signer.calculator(signatureIdentification, stringDocumentBody);
        signCalculator.calculate();
        return new String(base64Encoder.encode(signCalculator.signature()));
    }



}
