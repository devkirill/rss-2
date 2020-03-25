package rss.service.parser;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import rss.model.Feed;
import rss.model.Post;
import rss.model.channel.Channel;
import rss.model.channel.Template;
import rss.model.channel.TypeParser;
import rss.service.DataReciever;
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
public class XPathParser implements Parser {
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

    public Post parsePost(Node node, Template template) {
        Post post = new Post();

        post.setTitle(getValue(node, template.getTitle()));
        post.setDescription(getValue(node, template.getDescription()));
        post.setAuthor(getValue(node, template.getAuthor()));
        String rawDate = getValue(node, template.getPubDate());
        post.setRawPubDate(rawDate);
        post.setPubDate(DateUtil.parse(rawDate));
        post.setLink(getValue(node, template.getLink()));
        post.setGuid(getValue(node, template.getGuid()));

        return post;
    }

    public Feed parseFeed(InputStream stream, Template template) {
        try {
            Document xmlDocument = builder.parse(stream);
            XPath xPath = XPathFactory.newInstance().newXPath();

            String expression = template.getRoot();
            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);

            List<Post> posts = new ArrayList<>();

            for (int n = 0, len = nodeList.getLength(); n < len; n++) {
                Node child = nodeList.item(n);

                posts.add(parsePost(child, template));
            }

            Feed feed = new Feed();
            feed.setPosts(posts);

            return feed;
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
