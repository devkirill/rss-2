package rss.service.loader.parser;

import rss.model.db.*;
import rss.utils.DateUtil;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractParser<T, R extends T> {
    public abstract String getValue(T node, String path);

    public Post parsePost(T node, PostTemplate template) {
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

    public Feed parseFeed(T node, FeedTemplate template) {
        Feed feed = new Feed();

        feed.setTitle(getValue(node, template.getTitle()));
        feed.setDescription(getValue(node, template.getDescription()));
        feed.setImage(getValue(node, template.getImage()));
        feed.setLanguage(getValue(node, template.getLanguage()));
        feed.setLink(getValue(node, template.getLink()));
        String rawDate = getValue(node, template.getPubDate());
        feed.setPubDate(DateUtil.parse(rawDate));

        return feed;
    }

    public abstract List<T> getNodes(R root, Template template);

    public Feed parse(R root, Template template) {
        FeedTemplate feedTemplate = template.getFeed();
        PostTemplate postTemplate = template.getPost();

        Feed feed = parseFeed(root, feedTemplate);

        List<T> posts = getNodes(root, template);
        List<Post> parsedPosts = posts.stream().map(node -> parsePost(node, postTemplate)).collect(Collectors.toList());

        feed.setPosts(parsedPosts);

        return feed;
    }
}
