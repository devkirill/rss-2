package rss.service.view;

import rss.model.db.Channel;
import rss.model.db.template.Template;
import rss.model.view.Channels;

public interface ChannelView {
    Channels getChannels();

    Channel getChannelById(Integer id);

    void update(Integer id, Channel channel);

    Template getRssDefaultTemplate();
}
