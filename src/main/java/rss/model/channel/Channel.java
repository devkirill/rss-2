package rss.model.channel;

import rss.model.Post;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "channel")
@SequenceGenerator(name = Channel.GENERATOR, sequenceName = Channel.GENERATOR, allocationSize = 1)
public class Channel {
    public final static String GENERATOR = "channel_seq";

    @Id
    @Column
    @GeneratedValue(generator = GENERATOR)
    private int id;

    @Column
    private boolean make;

    @Column
    private String url;

    @Embedded
    private Template template;

    @OneToMany(mappedBy = "channel")
    private List<Post> posts = new ArrayList<>();

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

    //endregion
}
