package ru.bm.eetp.signature;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.bm.eetp.signature.keystore.PrivateKeySupplierJvmKeyStore;
import ru.bm.eetp.signature.keystore.PublicKeySupplierJvmKeyStore;
import ru.bm.eetp.signature.keystore.PrivateKeySupplier;
import ru.bm.eetp.signature.keystore.storage.VtbForExternalSignProperties;

import java.security.PrivateKey;

@Component
public class PrivateKeySupplierProducerImpl implements PrivateKeySupplierProducer {

    private ApplicationContext applicationContext;

    public PrivateKeySupplierProducerImpl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
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
                    VtbForExternalSignProperties properties = applicationContext.getBean(VtbForExternalSignProperties.class);
                    privateKey = new PrivateKeySupplierJvmKeyStore(
                                   properties.getStoreType(), properties.getStorePass(),
                                   properties.getKeyAlias(), properties.getKeyPass()
                           ).privateKey();
                }
                return privateKey;
            }
        };
    }
}
