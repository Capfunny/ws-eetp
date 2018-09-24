package ru.bm.eetp.service;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.util.Optional;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import ru.bm.eetp.config.Constants;


@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TagExtractorDOMImpl implements TagExtractor {
    @Override
    public Optional<String> extract(String tagName, String content) {
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilderFactory.setNamespaceAware(true);
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new InputSource(new StringReader(content)));

            NodeList nodes = doc.getElementsByTagNameNS(/*Constants.PACKAGE_NAMESPACE*/"*", tagName);

            if (nodes.getLength() == 0){throw new Exception();}

            return Optional.of(nodes.item(0).getTextContent());
        }
        catch (Exception e){
            return Optional.empty();
        }
    }
}
