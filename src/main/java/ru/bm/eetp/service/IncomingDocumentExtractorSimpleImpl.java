package ru.bm.eetp.service;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IncomingDocumentExtractorSimpleImpl implements IncomingDocumentExtractor {

    @Override
    public Optional<String> extract(String content) {
        /* <Document> ...</Document>*/
        String startPart = "<Document>";
        String finishPart = "</Document>";
        int pos1 = content.indexOf(startPart);
        int pos2 = content.indexOf(finishPart);

        if(pos1 >= 0 && pos2 >= 0) {
            return Optional.of(content.substring(pos1 + startPart.length(), pos2));
        }
        return Optional.empty();
    }
}
