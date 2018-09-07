package xml;

//import ru.bm.eetp.dto.XMLIncomingObject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
//import javax.xml.bind.Marshaller;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

public final class XMLService {

    //XML в объект XMLIncomingPackage
   /* public static final XMLIncomingObject xmlToIncomingObject(String xml) throws JAXBException {
        try {
            // создаем объект JAXBContext - точку входа для JAXB
            JAXBContext jaxbContext = JAXBContext.newInstance(XMLIncomingObject.class);
            Unmarshaller um = jaxbContext.createUnmarshaller();
            um.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.sberbank.ru/edo/oep/edo-oep-document");
            StringReader reader = new StringReader(xml);
            Object obj = um.unmarshal(reader);

            return (XMLIncomingObject)obj;
        } catch (JAXBException e) {
            throw e;
            // return null;
        }
    }*/

    /*// сохраняем объект в XML файл
    private static void convertObjectToXml(Student student, String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(Student.class);
            Marshaller marshaller = context.createMarshaller();
            // устанавливаем флаг для читабельного вывода XML в JAXB
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // маршаллинг объекта в файл
            marshaller.marshal(student, new File(filePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }*/
}