package ru.bm.eetp.service;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;

//@Component
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TagExtractorSimpleImpl implements TagExtractor {

    @Override
    public Optional<String> extract(String tagName, String content) {
        String openTag = "<" + tagName + ">";
        String closeTag = "</" + tagName + ">";
        int openPos = content.indexOf(openTag);
        int closePos = content.indexOf(closeTag);

        if(openPos >= 0 && closePos > 0) {
            return Optional.of(content.substring(openPos + openTag.length(), closePos));
        }
        else if (closePos >= 0 && openPos == -1) {
            return Optional.of(null);
        }
        else {
            return Optional.empty();
        }
    }
}
