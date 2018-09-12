package ru.bm.eetp.signature.keystore.storage;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration("vtbPrivateKeySignProperties")
@ConfigurationProperties("signature.vtb-for-external-sign")
public class VtbPrivateKeySignProperties extends SignStorageProperties {

}
