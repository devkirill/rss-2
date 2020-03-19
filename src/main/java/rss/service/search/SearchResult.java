package rss.service.search;

import rss.model.Post;

import java.util.List;

public class SearchResult {
    private final int totalCount;
    private final List<Post> posts;

    public SearchResult(int totalCount, List<Post> posts) {
        this.totalCount = totalCount;
        this.posts = posts;
    }

    //region getters

    public int getTotalCount() {
        return totalCount;
    }

    public List<Post> getPosts() {
        return posts;
    }

    //endregion
}
