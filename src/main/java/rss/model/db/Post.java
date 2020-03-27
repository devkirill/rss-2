package rss.model.db;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @Column(updatable = false, nullable = false)
    private String guid;

    @ManyToOne
    @JoinColumn
    private Channel channel;

    @ManyToOne
    @JoinColumn
    private Feed feed;

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String img;

    @Column
    private ZonedDateTime pubDate;

    @Column
    private String rawPubDate;

    @Column(columnDefinition = "TEXT")
    private String author;

    @Column(columnDefinition = "TEXT")
    private String link;

    //region getters and setters

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public ZonedDateTime getPubDate() {
        return pubDate;
    }

    public void setPubDate(ZonedDateTime pubDate) {
        this.pubDate = pubDate;
    }

    public String getRawPubDate() {
        return rawPubDate;
    }

    public void setRawPubDate(String rawPubDate) {
        this.rawPubDate = rawPubDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    //endregion
}
