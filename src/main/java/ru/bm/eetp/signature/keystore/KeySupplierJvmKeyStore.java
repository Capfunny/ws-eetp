package ru.bm.eetp.signature.keystore;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public final class KeySupplierJvmKeyStore {

    private String storeType;
    private String storePass;
    private String keyAlias;
    private String keyPass;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public KeySupplierJvmKeyStore(String storeType, String storePass, String keyAlias, String keyPass) {
        this.storeType = storeType;
        this.storePass = storePass;
        this.keyAlias = keyAlias;
        this.keyPass = keyPass;
    }

    public PublicKey publicKey() {
        if(publicKey == null) {
            loadPublicKey();
        }
        return publicKey;
    }

    public PrivateKey privateKey() {
        if(privateKey == null) {
            loadPrivateKey();
        }
        return privateKey;
    }

    private void loadPrivateKey() {

        try {
            KeyStore keyStore = keyStore();

            privateKey = (PrivateKey) keyStore.getKey(keyAlias, keyPass.toCharArray());

        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException e) {
            throw new RuntimeException(e);
        }

        //
    }

    private void loadPublicKey() {

        try {
            KeyStore keyStore = keyStore();

            X509Certificate certificate = (X509Certificate) keyStore.getCertificate(keyAlias);
            publicKey = certificate.getPublicKey();

        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            throw new RuntimeException(e);
        }

        //
    }

    private KeyStore keyStore() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        KeyStore keyStore = KeyStore.getInstance(storeType);
        InputStream storeStream = null;

        keyStore.load(storeStream, getPasswordAsCharArray());
        return keyStore;
    }

    private char[] getPasswordAsCharArray() {
        return storePass != null ? storePass.toCharArray() : null;
    }

}
