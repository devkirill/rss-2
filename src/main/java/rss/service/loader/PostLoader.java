package rss.service.loader;

import rss.model.db.Channel;
import rss.model.db.Feed;

public interface PostLoader {
    Feed getFeed(Channel channel);
}
