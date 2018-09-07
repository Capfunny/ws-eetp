package ru.bm.eetp.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import ru.bm.eetp.config.Constants;

import java.io.StringReader;

import static ru.bm.eetp.config.Utils.encodeBase64;

@JacksonXmlRootElement(localName = "Package", namespace = Constants.PACKAGE_NAMESPACE)
public class XMLPackage {

    @JacksonXmlProperty(localName = "TypeDocument", namespace = Constants.PACKAGE_NAMESPACE)
    private String typedocument;
    @JacksonXmlProperty(localName = "Document", namespace = Constants.PACKAGE_NAMESPACE)
    private String document;
    @JacksonXmlProperty(localName = "Signature", namespace = Constants.PACKAGE_NAMESPACE)
    private String signature;

    public XMLPackage(String document, String signature) {
        this.typedocument = extractTypeDocument(document);
        this.document = encodeBase64(document);
        this.signature = signature;
    }

    private String extractTypeDocument(String document) {
        try {
            SAXReader reader = new SAXReader();
            Document doc = reader.read(new StringReader(document));
            return doc.getRootElement().getName();
        }
        catch (Exception e){
            return "Unknown type";
        }
    }

    public String getTypedocument() {
        return typedocument;
    }

    public String getDocument() {
        return document;
    }

    public String getSignature() {
        return signature;
    }

}
