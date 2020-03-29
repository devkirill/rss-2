package rss.service.loader;

import org.springframework.stereotype.Service;
import rss.model.db.Channel;
import rss.model.db.Feed;
import rss.model.db.template.TypeParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostLoaderService implements PostLoader {
    private Map<TypeParser, Parser> parserMap;

    public PostLoaderService(List<Parser> parsers) {
        parserMap = new HashMap<>();

        for (Parser parser : parsers) {
            if (parserMap.containsKey(parser.getType()))
                throw new IllegalStateException("Found duplicate of parsers");
            parserMap.put(parser.getType(), parser);
        }
    }

    @Override
    public Feed getFeed(Channel channel) {
        Parser parser = parserMap.get(channel.getTemplate().getType());
        if (parser == null) {
//            System.err.println(channel.getTemplate().getType().toString());
            throw new IllegalStateException("Parser not exists, channel - " + channel.getId());
        }

        return parser.getFeed(channel);
    }
}
