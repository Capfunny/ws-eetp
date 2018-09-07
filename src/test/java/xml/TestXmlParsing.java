package xml;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;


/**
 * Created by Capfunny on 08.08.2018.
 */
public class TestXmlParsing {

    private static String getXMLstring() throws IOException {
        String XMLStr = "";
        try {
            System.out.println(new File(".").getAbsolutePath());
            FileInputStream inputStream = new FileInputStream("src\\test\\java\\xml\\TestXmlParsing.xml");
            try {
                XMLStr = IOUtils.toString(inputStream, "UTF-8");
            } finally {
                inputStream.close();
            }
        }
         catch (IOException ex)
        {
            System.out.println("При чтении XML-файла произошла Хуйня!");
        }
        return XMLStr;
    }

    //https://stackoverflow.com/questions/3904270/parse-xml-and-convert-to-a-collection
    static Map<String,List<String>> parse(String xml) throws Exception
    {
        Map<String,List<String>> map = new HashMap<String,List<String>>();

        SAXReader reader = new SAXReader();
        Document doc = reader.read(new StringReader(xml));

        /*for (Iterator i = doc.getRootElement().elements().iterator(); i.hasNext();)
        {
            Element element = (Element)i.next();

            List<String> list = map.get(element.getName());
            if (list == null)
            {
                list = new ArrayList<String>();
                map.put(element.getName(), list);
            }
            list.add(element.getText());
        }*/
        Element rt = doc.getRootElement();
        // Element el = rt.element("ClientForName");
        XPath xp = rt.createXPath("/ABS_GOZ_ACCOUNT_REQUEST/ClientForName");
        printValueByXpath(rt, xp);
        //printValueByXpath(rt, rt.createXPath("/ABS_GOZ_ACCOUNT_REQUEST/ClientInfo/ClientForName"));

        // NodeList nodes = (NodeList)xPath.evaluate("/html/body/p/div[3]/a", doc, XPathConstants.NODESET);
      //  for (int i = 0; i < nodes.getLength(); ++i) {
      //      Element e = (Element) nodes.item(i);
      //  }

         return map;
    }


    private static void printValueByXpath(Element rt, XPath xp) {
        if (xp == null){
            System.out.println("Не нашли :(");
        }
        else {
            System.out.println("Нашли !!!");
            System.out.println(xp);
            List items = xp.selectNodes(rt);
            System.out.println(items);
            for (Iterator iter = items.iterator(); iter.hasNext(); ) {
                Element item = (Element) iter.next();
                System.out.println(item.getData());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String xml = getXMLstring();

        SAXReader reader = new SAXReader();
        Document doc = reader.read(new StringReader(xml));
        Element rt = doc.getRootElement();
        printValueByXpath(rt, rt.createXPath("/ClientForName"));
        printValueByXpath(rt, rt.createXPath("/ClientInfo/ClientForName"));

        //Map mp = parse(xml);
        //System.out.println(parse(xml));

        //System.out.println(xml);
    }
}
