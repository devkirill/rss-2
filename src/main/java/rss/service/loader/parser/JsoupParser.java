package rss.service.loader.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import rss.model.db.*;
import rss.service.loader.Parser;
import rss.utils.DateUtil;

import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class JsoupParser implements Parser {
    @Override
    public TypeParser getType() {
        return TypeParser.Jsoup;
    }

    public String getValue(Element element, String path) {
        try {
            if (path == null || path.trim().isEmpty())
                return "";

            String select = path;
            String attr = "";
            Boolean withHtml = false;

            Pattern pattern = Pattern.compile("^(.*)\\s(?:@([^@]*))\\s*$");
            Matcher matcher = pattern.matcher(path);
            if (matcher.find()) {
                select = matcher.group(1);
                attr = matcher.group(2);
                withHtml = true;
            }

            Elements selectedElement = element.select(select);

            if (attr.isEmpty()) {
                if (withHtml)
                    return selectedElement.stream()
                            .flatMap(el -> el.childNodes().stream())
                        .map(Node::toString)
                        .collect(Collectors.joining());

                return selectedElement.text();
            }else
                return selectedElement.attr(attr);
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
        post.setLink(getValue(element, template.getLink()));
        post.setGuid(getValue(element, template.getGuid()));

        return post;
    }

    public Feed getFeed(Document document, Template template) {
        String expression = template.getRoot();
        Elements elements = document.select(expression);

        List<Post> posts = elements.stream()
                .map(element -> parsePost(element, template))
                .collect(Collectors.toList());

        Feed feed = new Feed();
        feed.setPosts(posts);

        return feed;
    }

    private DataReciever dataReciever = new DataReciever();

    @Override
    public Feed getFeed(Channel channel) {
        try {
            System.setProperty("http.agent", "insomnia/6.6.2");

            URL url = new URL(channel.getUrl());

            Document document = Jsoup.parse(url, 5000);

            return getFeed(document, channel.getTemplate());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
