package rss.model.db;

import javax.persistence.*;
import java.time.ZonedDateTime;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "feed")
    private List<Post> posts = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 128)
    private String link;

//    @Column(length = 16)
//    private String language;
//
//    @Column(length = 256)
//    private String url;

    @Embedded
    private Image image;

    @Column
    private ZonedDateTime pubDate;

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
