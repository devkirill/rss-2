package rss.utils;

import rss.model.db.template.Template;
import rss.model.db.template.TypeParser;

public class DefaultTemplate {
    public static Template getRssTemplate() {
        Template template = new Template();

        template.setType(TypeParser.XPath);

        template.getFeed().setTitle("//channel/title");
        template.getFeed().setDescription("//channel/description");
        template.getFeed().setLanguage("//channel/language");
        template.getFeed().setImage("//channel/image/url");
        template.getFeed().setPubDate("//channel/pubDate|//channel/lastBuildDate");
        template.getFeed().setLink("//channel/link/text()");

        template.setRoot("//channel/item");

        template.getPost().setTitle("title");
        template.getPost().setDescription("description");
        template.getPost().setPubDate("pubDate");
        template.getPost().setAuthor("creator");
        template.getPost().setLink("link");
        template.getPost().setGuid("guid");

        return template;
    }
}
