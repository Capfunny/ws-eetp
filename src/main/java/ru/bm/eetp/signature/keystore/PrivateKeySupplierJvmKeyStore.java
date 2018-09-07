package ru.bm.eetp.signature.keystore;

import java.security.PrivateKey;

public final class PrivateKeySupplierJvmKeyStore implements PrivateKeySupplier {

    private PrivateKey privateKey;

    private String storeType;
    private String storePass;
    private String keyAlias;
    private String keyPass;

    public PrivateKeySupplierJvmKeyStore(String storeType, String storePass, String keyAlias, String keyPass) {
        this.storeType = storeType;
        this.storePass = storePass;
        this.keyAlias = keyAlias;
        this.keyPass = keyPass;
    }

    public PrivateKey privateKey(){
        if (privateKey == null) {
            KeySupplierJvmKeyStore keySupplierJvmKeyStore
                    = new KeySupplierJvmKeyStore(storeType, storePass, keyAlias, keyPass);

            privateKey = keySupplierJvmKeyStore.privateKey();
        }

        return privateKey;
    }

}
