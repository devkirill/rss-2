package rss.service.loader;

import rss.model.db.Channel;
import rss.model.db.Feed;
import rss.model.db.TypeParser;

public interface Parser {
    TypeParser getType();

    Feed getFeed(Channel channel);
}
