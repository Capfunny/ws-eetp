package ru.bm.eetp.signature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.bm.eetp.dto.ProcessResult;
import ru.bm.eetp.exception.SimpleError;
import ru.bm.eetp.service.TagExtractorImpl;
import ru.bm.eetp.signature.keystore.PublicKeySupplier;
import ru.bm.eetp.signature.keystore.storage.SignStorageProperties;
import ru.bm.eetp.signature.validator.SignatureValidatorDebugForVtbKeys;

import java.util.Optional;

@Component
public class SignatureValidatorProducerImpl implements SignatureValidatorProducer {

    @Autowired  private TagExtractorImpl tagExtractor;
    private PublicKeyKeySupplierProducer publicKeyKeySupplierProducer;
    private SignStorageProperties signStorageProperties;

    public SignatureValidatorProducerImpl(
            PublicKeyKeySupplierProducer publicKeyKeySupplierProducer,
            @Qualifier("publicKeyStorageProperties")
            SignStorageProperties signStorageProperties) {

        this.publicKeyKeySupplierProducer = publicKeyKeySupplierProducer;
        this.signStorageProperties = signStorageProperties;
    }

    @Override
    public Optional<SignatureValidator> signatureValidator(String documentXML) {
        String correspondentCode = correspondentCode(documentXML);
        if(Correspondents.DEBUG_VTB.equals(correspondentCode)) {

            PublicKeySupplier publicKeySupplier
                    = publicKeyKeySupplierProducer.publicKeySupplier(
                            SignatureIdentificationConstants.VTB_FOR_EETP,
                            signStorageProperties
                    );

            return Optional.of(new SignatureValidatorDebugForVtbKeys(publicKeySupplier.publicKey()));

        } else {

            PublicKeySupplier publicKeySupplier
                    = publicKeyKeySupplierProducer.publicKeySupplier(
                    correspondentCode,
                    signStorageProperties
            );

            return Optional.of(new SignatureValidatorDebugForVtbKeys(publicKeySupplier.publicKey()));
        }

    }

    private String correspondentCode(String documentXML) {
        Optional<String> operName = tagExtractor.extract("OperatorName",documentXML);
        if (! operName.isPresent()){
            throw new SimpleError(ProcessResult.SIGNATURE_VALIDATION_ERROR);
        }
        return operName.get(); //"ETP_SBAST"
    }
}
