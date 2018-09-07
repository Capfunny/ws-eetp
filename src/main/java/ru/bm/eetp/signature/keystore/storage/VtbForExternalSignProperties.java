package ru.bm.eetp.signature.keystore.storage;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("signature.vtb-for-external-sign")
public class VtbForExternalSignProperties {

    private String storetype;
    private String storePass;
    private String keyAlias;
    private String keyPass;

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
}
