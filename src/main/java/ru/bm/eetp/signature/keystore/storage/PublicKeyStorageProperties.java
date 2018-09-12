package ru.bm.eetp.signature.keystore.storage;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration("publicKeyStorageProperties")
@ConfigurationProperties("signature.vtb-for-check-external-sign")
public class PublicKeyStorageProperties extends SignStorageProperties {

}
