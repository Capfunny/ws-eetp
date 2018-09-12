package ru.bm.eetp.signature.keystore.storage;

public class SignStorageProperties {

    private String storePath;
    private String storetype;
    private String storePass;
    private String keyAlias;
    private String keyPass;

    public String getStorePath() {
        return storePath;
    }

    public void setStorePath(String storePath) {
        this.storePath = storePath;
    }

    public String getStoreType() {
        return storetype;
    }

    public void setStoreType(String storeType) {
        this.storetype = storeType;
    }

    public String getStorePass() {
        return storePass;
    }

    public void setStorePass(String storePass) {
        this.storePass = storePass;
    }

    public String getKeyAlias() {
        return keyAlias;
    }

    public void setKeyAlias(String keyAlias) {
        this.keyAlias = keyAlias;
    }

    public String getKeyPass() {
        return keyPass;
    }

    public void setKeyPass(String keyPass) {
        this.keyPass = keyPass;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()+" {" +
                "storePath='" + storePath + '\'' +
                ", storetype='" + storetype + '\'' +
                ", storePass='" + storePass + '\'' +
                ", keyAlias='" + keyAlias + '\'' +
                ", keyPass='" + keyPass + '\'' +
                '}';
    }
}
