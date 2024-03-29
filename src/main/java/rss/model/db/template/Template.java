package rss.model.db.template;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Template {
    @NotNull
    private TypeParser type;

    @NotEmpty
    private String root;

    @Valid
    private FeedTemplate feed = new FeedTemplate();

    @Valid
    private PostTemplate post = new PostTemplate();

    //region getters and setters

    public TypeParser getType() {
        return type;
    }

    public void setType(TypeParser type) {
        this.type = type;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public FeedTemplate getFeed() {
        return feed;
    }

    public void setFeed(FeedTemplate feed) {
        this.feed = feed;
    }

    public PostTemplate getPost() {
        return post;
    }

    public void setPost(PostTemplate post) {
        this.post = post;
    }

    //endregion
}
