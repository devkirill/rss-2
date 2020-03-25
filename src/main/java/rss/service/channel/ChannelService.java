package rss.service.channel;

import org.springframework.stereotype.Service;
import rss.model.channel.Channel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ChannelService {
    @PersistenceContext
    private EntityManager entityManager;

    public Channels getChannels() {
        List<Channel> channels = entityManager
                .createQuery("FROM Channel " +
                        "ORDER BY id asc ", Channel.class)
                .getResultList();

        return new Channels(channels);
    }

    public Channel getChannelById(Integer id) {
        if (id == null)
            return new Channel();

        return entityManager.find(Channel.class, id);
    }

    public void update(Integer id, Channel channel) {
        if (id == null) {
            entityManager.persist(channel);
        } else {
            Channel dbChannel = entityManager.find(Channel.class, id);

            dbChannel.setTemplate(channel.getTemplate());
        }

        entityManager.flush();
    }
}