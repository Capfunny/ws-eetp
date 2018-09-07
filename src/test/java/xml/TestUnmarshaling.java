package xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringReader;

/**
 * Created by Capfunny on 23.08.2018.
 */
public class TestUnmarshaling {

    public static void main(String[] args) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(XMLObject.class);
        String filePath = new String("src\\test\\java\\xml\\TestXmlParsing.xml");;
        Unmarshaller um = jaxbContext.createUnmarshaller();
        XMLObject obj = (XMLObject)um.unmarshal(new File(filePath));

        System.out.println( obj);
    }
}
