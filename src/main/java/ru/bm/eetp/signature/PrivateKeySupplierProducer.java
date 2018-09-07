package ru.bm.eetp.signature;

import ru.bm.eetp.signature.keystore.PrivateKeySupplier;

public interface PrivateKeySupplierProducer {

    PrivateKeySupplier privateKeySupplier(String signatureIdentification);
}
