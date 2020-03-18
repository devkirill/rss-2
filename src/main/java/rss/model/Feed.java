package rss.model;

import rss.model.channel.Channel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "feed")
@SequenceGenerator(name = Feed.GENERATOR, sequenceName = Feed.GENERATOR, allocationSize = 1)
public class Feed {
    public final static String GENERATOR = "feed_seq";

    @Id
    @Column
    @GeneratedValue(generator = GENERATOR)
    private int id;

    @ManyToOne
    @JoinColumn
    private Channel channel;

    @OneToMany(mappedBy = "feed")
    private List<Post> posts = new ArrayList<>();

    //region getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    //endregion
}
