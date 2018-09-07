package ru.bm.eetp.signature.keystore;

import java.security.PublicKey;

public final class PublicKeySupplierJvmKeyStore implements PublicKeySupplier {

    private PublicKey publicKey;

    private String storeType;
    private String storePass;
    private String keyAlias;
    private String keyPass;

    public PublicKeySupplierJvmKeyStore(String storeType, String storePass, String keyAlias, String keyPass) {
        this.storeType = storeType;
        this.storePass = storePass;
        this.keyAlias = keyAlias;
        this.keyPass = keyPass;
    }

    public PublicKey publicKey() {
        if (publicKey == null) {
            KeySupplierJvmKeyStore keySupplierJvmKeyStore
                    = new KeySupplierJvmKeyStore(storeType, storePass, keyAlias, keyPass);

            publicKey = keySupplierJvmKeyStore.publicKey();
        }

        return publicKey;
    }
}
