package rss.service.view;

import rss.model.view.SearchQuery;
import rss.model.view.SearchResult;

public interface PostSearchView {
    SearchResult searchString(SearchQuery query);
}
