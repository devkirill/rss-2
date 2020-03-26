package rss.service.loader.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import rss.model.db.*;
import rss.service.loader.Parser;
import rss.utils.DateUtil;
import us.codecraft.xsoup.Xsoup;

import java.util.ArrayList;
import java.util.List;

@Service
public class XsoupParser implements Parser {
    @Override
    public TypeParser getType() {
        return TypeParser.Xsoup;
    }

    public String getValue(Element element, String path) {
        try {
            if (path == null || path.trim().isEmpty())
                return "";

            return Xsoup.compile(path).evaluate(element).getElements().text();
//
//
//            XPath xPath = XPathFactory.newInstance().newXPath();
//
//            return xPath.compile(path).evaluate(node, XPathConstants.STRING).toString();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public Post parsePost(Element element, Template template) {
        Post post = new Post();

        post.setTitle(getValue(element, template.getTitle()));
        post.setDescription(getValue(element, template.getDescription()));
        post.setAuthor(getValue(element, template.getAuthor()));
        String rawDate = getValue(element, template.getPubDate());
        post.setRawPubDate(rawDate);
        post.setPubDate(DateUtil.parse(rawDate));
        post.setAuthor(getValue(element, template.getLink()));

        return post;
    }

    public Feed getFeed(Document document, Template template) {
        String expression = template.getRoot();
        List<Element> nodes = Xsoup.compile(expression).evaluate(document).getElements();

        List<Post> posts = new ArrayList<>();
        for (Element element : nodes) {
            posts.add(parsePost(element, template));
        }

        Feed feed = new Feed();
        feed.setPosts(posts);

        return feed;
    }

    @Override
    public Feed getFeed(Channel channel) {
//        try {
            Document document = Jsoup.parse(channel.getUrl());

            return getFeed(document, channel.getTemplate());
//            Document document = Jsoup.parse(stream, "UTF-8", "https://google.ru/");

//            String result = Xsoup.compile("//a/@href").evaluate(document).get();
////            Assert.assertEquals("https://github.com", result);
//
//
//            Template template = channel.getTemplate();
//
//            String expression = template.getRoot();
//            List<Element> nodes = Xsoup.compile(expression).evaluate(document).getElements();
//
//            List<Post> posts = new ArrayList<>();
//            for (Element element : nodes) {
//                posts.add(parsePost(element, template));
//            }
////            Assert.assertEquals("a", list.get(0));
////            Assert.assertEquals("b", list.get(1));
//
////            Document xmlDocument = builder.parse(stream);
////            XPath xPath = XPathFactory.newInstance().newXPath();
////            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
//
////            List<Post> posts = new ArrayList<>();
//
////            for (int n = 0, len = nodeList.getLength(); n < len; n++) {
////                Node child = nodeList.item(n);
////
////                posts.add(parsePost(template, child));
////            }
//
//            Feed feed = new Feed();
//            feed.setPosts(posts);
//
//            return feed;
//        } catch (Exception e) {
//            throw new IllegalStateException(e);
//        }


//        return null;
    }



//    public Post parsePost(Template template, Element element) {
//
//    }
//
//    public Feed parseFeed(InputStream stream, Template template) {
//        try {
//            Document document = Jsoup.parse(stream, "UTF-8", "https://google.ru/");
//
////            String result = Xsoup.compile("//a/@href").evaluate(document).get();
//////            Assert.assertEquals("https://github.com", result);
////
//
//            String expression = template.getRoot();
//            List<Element> nodes = Xsoup.compile(expression).evaluate(document).getElements();
//
//            List<Post> posts = new ArrayList<>();
//            for (Element element : nodes) {
//                posts.add(parsePost(template, element));
//            }
////            Assert.assertEquals("a", list.get(0));
////            Assert.assertEquals("b", list.get(1));
//
////            Document xmlDocument = builder.parse(stream);
////            XPath xPath = XPathFactory.newInstance().newXPath();
////            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
//
////            List<Post> posts = new ArrayList<>();
//
////            for (int n = 0, len = nodeList.getLength(); n < len; n++) {
////                Node child = nodeList.item(n);
////
////                posts.add(parsePost(template, child));
////            }
//
//            Feed feed = new Feed();
//            feed.setPosts(posts);
//
//            return feed;
//        } catch (Exception e) {
//            throw new IllegalStateException(e);
//        }
//    }
}
