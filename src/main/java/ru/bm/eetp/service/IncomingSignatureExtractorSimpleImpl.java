package ru.bm.eetp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IncomingSignatureExtractorSimpleImpl implements IncomingSignatureExtractor {

    @Autowired
    private TagExtractorImpl tagExtractor;

    @Override
    public Optional<String> extract(String content) {
        /* <Signature> ...</Signature>*/
        return tagExtractor.extract("Signature", content);
    }
}
