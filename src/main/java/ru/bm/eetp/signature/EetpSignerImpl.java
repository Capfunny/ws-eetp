package ru.bm.eetp.signature;

import org.springframework.stereotype.Component;
import ru.bm.eetp.signature.keystore.PrivateKeySupplier;

@Component
public class EetpSignerImpl implements EetpSigner {

    private PrivateKeySupplierProducer privateKeySupplierProducer;

    public EetpSignerImpl(PrivateKeySupplierProducer privateKeySupplierProducer) {
        this.privateKeySupplierProducer = privateKeySupplierProducer;
    }

    @Override
    public SignCalculator calculator(String signatureIdentification, String stringDocumentBody) {
        return new CryptoProSignCalculatorImpl(
                documentBodyBytes(stringDocumentBody),
                privateKeySupplier(signatureIdentification));
    }

    private byte[] documentBodyBytes(String stringDocumentBody) {
        return stringDocumentBody.getBytes();
    }

    private PrivateKeySupplier privateKeySupplier(String signatureIdentification) {
        return privateKeySupplierProducer.privateKeySupplier(signatureIdentification);
    }
}
