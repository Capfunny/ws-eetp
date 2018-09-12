package ru.bm.eetp.signature;

public interface SignatureValidator {

    void validate(byte[] document, byte[] signature);
    boolean getValidateResult();
}
