package ru.bm.eetp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IncomingPackageValidatorImpl implements IncomingPackageValidator {

    private static final String SCHEMA_FILE = "IncomingPkg.xsd";
    private boolean validateResult;

    @Autowired private XmlXsdValidator xmlXsdValidator;

    @Override
    public void validate(String content) {
        validateResult = xmlXsdValidator.validate(content, SCHEMA_FILE);
    }

    @Override
    public boolean validateResult() {
        return validateResult;
    }

}
