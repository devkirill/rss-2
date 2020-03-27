package rss.service.loader.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import rss.model.db.Channel;
import rss.model.db.Feed;
import rss.model.db.Template;
import rss.model.db.TypeParser;
import rss.service.loader.Parser;

import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class JsoupParser extends AbstractParser<Element, Document> implements Parser {
    @Override
    public TypeParser getType() {
        return TypeParser.Jsoup;
    }

    @Override
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
                            .collect(Collectors.joining()).trim();

                return selectedElement.text().trim();
            } else
                return selectedElement.attr(attr).trim();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Element> getNodes(Document root, Template template) {
        String expression = template.getRoot();
        return root.select(expression);
    }

    @Override
    public Feed getFeed(Channel channel) {
        try {
            System.setProperty("http.agent", "insomnia/6.6.2");

            URL url = new URL(channel.getUrl());

            Document document = Jsoup.parse(url, 5000);

            return parse(document, channel.getTemplate());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
