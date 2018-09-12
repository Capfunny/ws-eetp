package ru.bm.eetp.signature;

import org.springframework.stereotype.Component;
import ru.bm.eetp.signature.keystore.PublicKeySupplier;
import ru.bm.eetp.signature.keystore.PublicKeySupplierJksKeyStore;
import ru.bm.eetp.signature.keystore.PublicKeySupplierJvmKeyStore;
import ru.bm.eetp.signature.keystore.storage.SignStorageProperties;

import java.security.PublicKey;

@Component
public class PublicKeyKeySupplierProducerImpl implements PublicKeyKeySupplierProducer {

    @Override
    public PublicKeySupplier publicKeySupplier(String signatureIdentification, SignStorageProperties signStorageProperties) {
        if(SignatureIdentificationConstants.VTB_FOR_EETP.equals(signatureIdentification)) {
            // Поставщик публичного ключа для документов от ВТБ для внешних получателей
            return vtbForEetpPublicKeySupplier(signStorageProperties);
        } else {
            // Поставщик публичного ключа для документов от для внешних источников - источник ЕЕТП
            return clientEetpPublicKeySupplier(signatureIdentification, signStorageProperties);
        }
    }

    private PublicKeySupplier vtbForEetpPublicKeySupplier(final SignStorageProperties signStorageProperties) {

        return new PublicKeySupplier() {

            private PublicKey publicKey;

            @Override
            public PublicKey publicKey() {

                if(publicKey == null) {
                    publicKey = new PublicKeySupplierJvmKeyStore(
                            signStorageProperties.getStoreType(), signStorageProperties.getStorePass(),
                            signStorageProperties.getKeyAlias(), signStorageProperties.getKeyPass()
                    ).publicKey();
                }
                return publicKey;
            }
        };

    }

    private PublicKeySupplier clientEetpPublicKeySupplier(
            final String signatureIdentification,
            final SignStorageProperties signStorageProperties
    ) {

        return new PublicKeySupplier() {

            private PublicKey publicKey;

            @Override
            public PublicKey publicKey() {

                if(publicKey == null) {
                    publicKey = new PublicKeySupplierJksKeyStore (
                            signStorageProperties.getStorePath(),
                            signStorageProperties.getStoreType(), signStorageProperties.getStorePass(),
                            signatureIdentification, signStorageProperties.getKeyPass()
                    ).publicKey();
                }
                return publicKey;
            }
        };

    }

}
