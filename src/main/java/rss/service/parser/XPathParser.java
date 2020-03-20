package rss.service.parser;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import rss.model.Feed;
import rss.model.Post;
import rss.model.channel.Template;
import rss.utils.DateUtil;

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
public class XPathParser {
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

    public String getValue(Node node, String path) {
        try {
            if (path == null || path.trim().isEmpty())
                return "";

            XPath xPath = XPathFactory.newInstance().newXPath();

            return xPath.compile(path).evaluate(node, XPathConstants.STRING).toString();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public Post parsePost(Template template, Node node) {
        Post post = new Post();

        post.setTitle(getValue(node, template.getHeadline()));
        post.setDescription(getValue(node, template.getSummary()));
        post.setAuthor(getValue(node, template.getAuthor()));
        String rawDate = getValue(node, template.getPosted());
        post.setRawPubDate(rawDate);
        post.setPubDate(DateUtil.parse(rawDate));

        return post;
    }

    public Feed parseFeed(InputStream stream, Template template) {
        try {
            Document xmlDocument = builder.parse(stream);
            XPath xPath = XPathFactory.newInstance().newXPath();
            String expression = template.getMain();
            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);

            List<Post> posts = new ArrayList<>();

            for (int n = 0, len = nodeList.getLength(); n < len; n++) {
                Node child = nodeList.item(n);

                posts.add(parsePost(template, child));
            }

            Feed feed = new Feed();
            feed.setPosts(posts);

            return feed;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
