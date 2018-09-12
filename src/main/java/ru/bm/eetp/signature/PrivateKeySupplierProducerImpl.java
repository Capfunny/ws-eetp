package ru.bm.eetp.signature;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.bm.eetp.signature.keystore.PrivateKeySupplierJvmKeyStore;
import ru.bm.eetp.signature.keystore.PrivateKeySupplier;
import ru.bm.eetp.signature.keystore.storage.SignStorageProperties;

import java.security.PrivateKey;

@Component
public class PrivateKeySupplierProducerImpl implements PrivateKeySupplierProducer {

    private SignStorageProperties signStorageProperties;

    public PrivateKeySupplierProducerImpl(
            @Qualifier("vtbPrivateKeySignProperties")
                    SignStorageProperties signStorageProperties) {

        this.signStorageProperties = signStorageProperties;
    }

    @Override
    public PrivateKeySupplier privateKeySupplier(String signatureIdentification) {
        if(SignatureIdentificationConstants.VTB_FOR_EETP.equals(signatureIdentification)) {
            // Поставщик секретного ключа для документов от ВТБ для внешних получателей
            return vtbForEetpPrivateKeySupplier();
        }

        throw new IllegalArgumentException("no PrivateKeySupplier for '" + signatureIdentification +"'");

    }

    // Поставщик секретного ключа для документов от ВТБ для внешних получателей
    private PrivateKeySupplier vtbForEetpPrivateKeySupplier() {
        return new PrivateKeySupplier() {

            private PrivateKey privateKey;

            @Override
            public PrivateKey privateKey() {

                if(privateKey == null) {
                    privateKey = new PrivateKeySupplierJvmKeyStore(
                                   signStorageProperties.getStoreType(), signStorageProperties.getStorePass(),
                                   signStorageProperties.getKeyAlias(), signStorageProperties.getKeyPass()
                           ).privateKey();
                }
                return privateKey;
            }
        };
    }
}
