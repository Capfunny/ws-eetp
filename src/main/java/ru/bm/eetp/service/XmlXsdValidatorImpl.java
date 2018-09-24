package ru.bm.eetp.service;

import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.util.Scanner;

@Component
public class XmlXsdValidatorImpl implements XmlXsdValidator {

    @Override
    public boolean validate(String content, String schemaFile) {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new StreamSource(new StringReader(getResourceContent(schemaFile))));

            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(content)));
            return true;
        }
        catch (SAXException | IOException e) {
            e.printStackTrace();
            System.out.println("schema => " + schemaFile);
            System.out.println("xml => " + content);
            return false;
        }
    }

    private String getResourceContent(String filename) {

        try ( InputStream inputStream = getClass().getClassLoader().getResource(filename).openStream() ) {
            Scanner s = new Scanner(inputStream).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
