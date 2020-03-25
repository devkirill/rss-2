package rss.service.parser;

import rss.model.Feed;
import rss.model.channel.Channel;
import rss.model.channel.TypeParser;

public interface Parser {
    TypeParser getType();

    Feed getFeed(Channel channel);
}
