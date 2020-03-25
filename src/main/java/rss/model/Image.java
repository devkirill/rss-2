package rss.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Image {
    @Column(length = 128, name = "image_title")
    private String title;

    @Column(length = 256, name = "image_url")
    private String url;

    @Column(length = 128, name = "image_link")
    private String link;

    //region getters and setters

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    //endregion
}
