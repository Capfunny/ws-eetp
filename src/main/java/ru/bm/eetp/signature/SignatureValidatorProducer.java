package ru.bm.eetp.signature;

import java.util.Optional;

public interface SignatureValidatorProducer {

    Optional<SignatureValidator> signatureValidator(String documentXML);
}
