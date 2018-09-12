package ru.bm.eetp.signature.keystore;

import java.security.PublicKey;

public final class PublicKeySupplierJksKeyStore implements PublicKeySupplier {

    private PublicKey publicKey;

    private final String storePath;
    private String storeType;
    private String storePass;
    private String keyAlias;
    private String keyPass;

    public PublicKeySupplierJksKeyStore(String storePath, String storeType, String storePass, String keyAlias, String keyPass) {
        this.storePath = storePath;
        this.storeType = storeType;
        this.storePass = storePass;
        this.keyAlias = keyAlias;
        this.keyPass = keyPass;
    }

    public PublicKey publicKey() {
        if (publicKey == null) {
            KeySupplierJksKeyStore keySupplierJvmKeyStore
                    = new KeySupplierJksKeyStore(storePath, storeType, storePass, keyAlias, keyPass);

            publicKey = keySupplierJvmKeyStore.publicKey();
        }

        return publicKey;
    }
}
