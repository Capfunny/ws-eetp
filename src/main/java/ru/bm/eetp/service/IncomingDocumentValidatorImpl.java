package ru.bm.eetp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IncomingDocumentValidatorImpl implements IncomingDocumentValidator {

    private static final String SCHEMA_FILE = "DocumentTypes.xsd";
    private boolean validateResult;

    @Autowired  private XmlXsdValidator xmlXsdValidator;

    @Override
    public void validate(String document) {
        validateResult = xmlXsdValidator.validate(document, SCHEMA_FILE);
    }

    @Override
    public boolean validateResult() {
        return validateResult;
    }

}
