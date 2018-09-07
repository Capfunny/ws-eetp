package xml;

import java.io.*;
import java.io.IOException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;

/**
 * Created by Capfunny on 08.08.2018.
 */
public class XMLConverter {
    private static String inputFileName;
    private static String outputFileName;
    private static String typeName;

    public static final String ROOT_NAME = "Package";
    public static final String TYPE_NAME = "TypeDocument";
    public static final String DOCUMENT_NAME = "Document";
    public static final String SIGN_MANE = "Signature";

    public static void main(String[] args) throws Exception {

        inputFileName = new String("src\\test\\java\\xml\\TestXmlParsing.xml");
        outputFileName = new String("src\\test\\java\\xml\\Output.xml");
        typeName = "SOME_TYPE";

        writeOutFile(getResultXml(readInputFile()));

        System.out.println("Создание тестового файла прошло успешно.");
    }

    public static String getSign() {
        return "some signature";
    }

    private static String getResultXml(String text) {
        String data = stringToBase64String(text);
        String result = "";
        Namespace ns = new Namespace("","http://www.sberbank.ru/edo/oep/edo-oep-document");

        try {
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement(ROOT_NAME);
            Element elm_ = root.addElement(TYPE_NAME).addText(typeName);
            elm_ = root.addElement(DOCUMENT_NAME).addText(data);
            elm_ = root.addElement(SIGN_MANE).addText(getSign());
           // root.add(ns);
            result = document.asXML();

        }
        catch (Exception ex)
        {
            System.out.println("При создании результативного XML-файла произошла какая-то хуйня: " + ex.toString());
        }
        return result;
    }

    private static String stringToBase64String(String text) {
        return Base64.encodeBase64String(text.getBytes());
    }

    private static String readInputFile() {
        String xmlStr = "";
        try {
            //System.out.println(new File(".").getAbsolutePath());
            FileInputStream inputStream = new FileInputStream(inputFileName);
            try {
                xmlStr = IOUtils.toString(inputStream, "UTF-8");
            } finally {
                inputStream.close();
            }
        }
        catch (IOException ex)
        {
            System.out.println("При чтении исходного XML-файла("+ inputFileName +") произошла какая-то хуйня: " + ex.toString());
        }
        return xmlStr;
    }

    private static void writeOutFile(String xml) {
        try {
            //System.out.println(new File(".").getAbsolutePath());
            FileOutputStream outputStream = new FileOutputStream(outputFileName);
            try {
                outputStream.write(xml.getBytes());
            } finally {
                outputStream.close();
            }
        }
        catch (IOException ex)
        {
            System.out.println("При чтении результативного XML-файла("+ outputFileName +") произошла какая-то хуйня: " + ex.toString());
        }
    }

}
