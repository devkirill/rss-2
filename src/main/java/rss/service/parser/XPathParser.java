package rss.service.parser;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import rss.model.Feed;
import rss.model.Post;
import rss.model.channel.Template;

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

    XPathParser() throws ParserConfigurationException {
        builderFactory = DocumentBuilderFactory.newInstance();
        builder = builderFactory.newDocumentBuilder();
    }

    public String getValue(Node node, String path) {
        try {
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
//        post.setAuthor(getValue(node, template.getAuthor()));
//        post.setPubDate(getValue(node, template.getPosted()));

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
