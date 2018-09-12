package ru.bm.eetp.signature.validator;

import ru.CryptoPro.JCP.Sign.CryptoProSign;
import ru.bm.eetp.signature.SignatureValidator;

import java.security.InvalidKeyException;
import java.security.PublicKey;
import java.security.SignatureException;

public class SignatureValidatorDebugForVtbKeys implements SignatureValidator {

    private final PublicKey publicKey;
    private boolean validateResult;

    public SignatureValidatorDebugForVtbKeys(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public void validate(byte[] document, byte[] signature) {

        CryptoProSign cryptoProSign = new CryptoProSign();
        try {
            cryptoProSign.initVerify(publicKey);
            cryptoProSign.update(document);
            validateResult = cryptoProSign.verify(signature);
        } catch (InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean getValidateResult() {
        return validateResult;
    }
}
