package rss.service.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import rss.model.db.Channel;
import rss.model.db.Feed;
import rss.model.db.Post;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@EnableScheduling
public class Harverster {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PostLoader parser;

    @Scheduled(initialDelay = 0, fixedRate = 60 * 1000)
    public void update() {
        List<Channel> channels = entityManager
                .createQuery("FROM Channel " +
                        "WHERE " +
                        "   make = true", Channel.class)
                .getResultList();

        channels.forEach(channel -> {
            Feed feed = parser.getFeed(channel);

            removeExisted(feed);

            if (!feed.getPosts().isEmpty()) {
                feed.setChannel(channel);
                feed.getPosts().forEach(post -> {
                    post.setChannel(channel);
                    post.setFeed(feed);
                });

                entityManager.persist(feed);
                feed.getPosts().forEach(entityManager::persist);
            }
        });

        entityManager.flush();
    }

    public void removeExisted(Feed feed) {
        List<Post> posts = feed.getPosts()
                .stream()
                .filter(post -> entityManager
                        .createQuery("SELECT count(*) " +
                                "FROM Post " +
                                "WHERE " +
                                "   guid = :guid OR " +
                                "   link = :link OR " +
                                "   ( " +
                                "       title = :title AND" +
                                "       description = :description " +
                                "   ) ", Long.class)
                        .setParameter("guid", post.getGuid())
                        .setParameter("link", post.getLink())
                        .setParameter("title", post.getTitle())
                        .setParameter("description", post.getDescription())
                        .getSingleResult() == 0)
                .collect(Collectors.toList());

        feed.setPosts(posts);
    }
}
