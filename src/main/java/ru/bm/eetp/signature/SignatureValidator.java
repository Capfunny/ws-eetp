package ru.bm.eetp.signature;

import java.security.cert.Certificate;

public interface SignatureValidator {

    boolean validateSignature(String document, String signature, Certificate[]  sertificates);
    boolean getValidateResult();
}
