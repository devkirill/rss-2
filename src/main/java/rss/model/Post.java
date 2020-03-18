package rss.model;

import org.hibernate.annotations.GenericGenerator;
import rss.model.channel.Channel;

import javax.persistence.*;

@Entity
@Table(name = "post")
@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
public class Post {
    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue(generator = "UUID")
    private String guid;

    @ManyToOne
    @JoinColumn
    private Channel channel;

    @ManyToOne
    @JoinColumn
    private Feed feed;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private String img;

    @Column
    private String pubDate;

    @Column
    private String author;

    @Column
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

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
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