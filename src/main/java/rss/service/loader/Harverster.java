package rss.service.loader;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import rss.model.db.Channel;
import rss.model.db.Feed;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@Transactional
@EnableScheduling
public class Harverster {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PostLoader parser;

    public void update(Channel channel) {
        Feed feed = parser.getFeed(channel);

        populateNullFields(feed);

        if (!feed.getPosts().isEmpty()) {
            feed.setChannel(channel);
            feed.getPosts().forEach(post -> {
                post.setChannel(channel);
                post.setFeed(feed);
            });

            entityManager.persist(feed);
            Session session = entityManager.unwrap(Session.class);
            feed.getPosts().forEach(session::saveOrUpdate);
        }

        entityManager.flush();
    }

    @Scheduled(initialDelay = 0, fixedRate = 60 * 1000)
    public void update() {
        List<Channel> channels = entityManager
                .createQuery("FROM Channel " +
                        "WHERE " +
                        "   make = true", Channel.class)
                .getResultList();

        channels.forEach(this::update);

        entityManager.flush();
    }

    public void populateNullFields(Feed feed) {
        if (feed.getPubDate() == null)
            feed.setPubDate(ZonedDateTime.now());
    }
}
