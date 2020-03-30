package rss.model.db;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.JoinFormula;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import rss.model.db.template.Template;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "channel")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@SequenceGenerator(name = Channel.GENERATOR, sequenceName = Channel.GENERATOR, allocationSize = 1)
public class Channel {
    public final static String GENERATOR = "channel_seq";

    @Id
    @Column
    @GeneratedValue(generator = GENERATOR)
    private int id;

    @Column
    @NotNull
    private boolean make;

    @Column
    @NotEmpty
    private String url;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Valid
    private Template template = new Template();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "channel")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "channel")
    private List<Feed> feeds = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinFormula(value = "(SELECT max(f.id) FROM feed f WHERE f.channel_id = id)")
    private Feed lastFeed;

    //region getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isMake() {
        return make;
    }

    public void setMake(boolean make) {
        this.make = make;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Feed> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<Feed> feeds) {
        this.feeds = feeds;
    }

    public Feed getLastFeed() {
        return lastFeed;
    }

    public void setLastFeed(Feed lastFeed) {
        this.lastFeed = lastFeed;
    }

    //endregion
}
