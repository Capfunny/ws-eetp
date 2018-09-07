package ru.bm.eetp.signature;

public interface SignatureProcessor {

    void validate(String signature);
    boolean getValidateResult();
    String getSignature(String content);
}
