package rss.service.loader.parser;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import rss.model.db.Channel;
import rss.model.db.Feed;
import rss.model.db.template.Template;
import rss.model.db.template.TypeParser;
import rss.service.loader.Parser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class XPathParser extends AbstractParser<Node, Document> implements Parser {
    private DocumentBuilderFactory builderFactory;
    private DocumentBuilder builder;

    public XPathParser() {
        try {
            builderFactory = DocumentBuilderFactory.newInstance();
            builder = builderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public TypeParser getType() {
        return TypeParser.XPath;
    }

    @Override
    public String getValue(Node node, String path) {
        try {
            if (path == null || path.trim().isEmpty())
                return "";

            XPath xPath = XPathFactory.newInstance().newXPath();

            return xPath.compile(path).evaluate(node, XPathConstants.STRING).toString().trim();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Node> getNodes(Document root, Template template) {
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();

            String expression = template.getRoot();
            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(root, XPathConstants.NODESET);

            List<Node> result = new ArrayList<>();

            for (int n = 0, len = nodeList.getLength(); n < len; n++) {
                result.add(nodeList.item(n));
            }

            return result;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public Feed parseFeed(InputStream stream, Template template) {
        try {
            Document xmlDocument = builder.parse(stream);

            return parse(xmlDocument, template);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public Feed parseFeed(String uri, Template template) {
        return parseFeed(new DataReciever().getContent(uri), template);
    }

    @Override
    public Feed getFeed(Channel channel) {
        return parseFeed(channel.getUrl(), channel.getTemplate());
    }
}
