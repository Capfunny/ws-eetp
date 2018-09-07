package xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;
import ru.bm.eetp.service.IncomingDocumentExtractor;

import java.io.StringReader;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class IncomingDocumentExtractorDOMEImpl implements IncomingDocumentExtractor {
    @Override
    public Optional<String> extract(String content) {
        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read(new StringReader(content));
            Element rt = doc.getRootElement();
            XPath xp = rt.createXPath("/Package/Document");
            List items = xp.selectNodes(rt);

            Iterator<Element> iter = items.iterator();

            if( ! iter.hasNext() ) {
                throw new IllegalStateException("Not found '/Package/Document' in xml");
            }

            String result = (String) iter.next().getData();

            if( iter.hasNext() ) {
                throw new IllegalStateException("Multiple '/Package/Document' in xml");
            }

            return Optional.of(result);

        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }
}
