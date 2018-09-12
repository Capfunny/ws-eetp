package ru.bm.eetp.signature;

import ru.bm.eetp.signature.keystore.PublicKeySupplier;
import ru.bm.eetp.signature.keystore.storage.SignStorageProperties;

public interface PublicKeyKeySupplierProducer {

    PublicKeySupplier publicKeySupplier(String signatureIdentification, SignStorageProperties signStorageProperties);
}
