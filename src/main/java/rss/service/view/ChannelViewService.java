package rss.service.view;

import org.springframework.stereotype.Service;
import rss.model.db.Channel;
import rss.model.view.Channels;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ChannelViewService implements ChannelView {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Channels getChannels() {
        List<Channel> channels = entityManager
                .createQuery("FROM Channel " +
                        "ORDER BY id asc ", Channel.class)
                .getResultList();

        return new Channels(channels);
    }

    @Override
    public Channel getChannelById(Integer id) {
        if (id == null)
            return new Channel();

        return entityManager.find(Channel.class, id);
    }

    @Override
    public void update(Integer id, Channel channel) {
        if (id == null) {
            entityManager.persist(channel);
        } else {
            Channel dbChannel = entityManager.find(Channel.class, id);

            dbChannel.setUrl(channel.getUrl());
            dbChannel.setMake(channel.isMake());
            dbChannel.setTemplate(channel.getTemplate());
        }

        entityManager.flush();
    }
}