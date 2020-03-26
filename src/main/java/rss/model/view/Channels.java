package rss.model.view;

import rss.model.db.Channel;

import java.util.List;

public class Channels {
    private List<Channel> channels;

    public Channels(List<Channel> channels) {
        this.channels = channels;
    }

    //region getters and setters

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

    //endregion
}
