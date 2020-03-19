package rss.service.search;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import rss.model.Post;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SearchPostService {
    @PersistenceContext
    private EntityManager entityManager;

    public Date getCurrentDate() {
        return new Date();
    }

    public SearchResult searchString(SearchQuery query) {
        int count = entityManager
                .createQuery("SELECT count(*) " +
                        "FROM Post " +
                        "WHERE " +
                        "   upper(title) LIKE :search OR " +
                        "   upper(description) LIKE :search ", Long.class)
                .setParameter("search", "%" + StringUtils.upperCase(StringUtils.defaultString(query.getSearch())) + "%")
                .getSingleResult()
                .intValue();

        List<Post> posts = entityManager
                .createQuery("FROM Post " +
                        "WHERE " +
                        "   upper(title) LIKE :search OR " +
                        "   upper(description) LIKE :search " +
                        "ORDER BY pubDate desc ", Post.class)
                .setParameter("search", "%" + StringUtils.upperCase(StringUtils.defaultString(query.getSearch())) + "%")
                .setFirstResult((query.getPage() - 1) * query.getSize())
                .setMaxResults(query.getSize())
                .getResultList();

        return new SearchResult(count, posts);
    }
}
