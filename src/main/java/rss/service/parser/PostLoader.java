package rss.service.parser;

import org.springframework.stereotype.Service;
import rss.model.Feed;
import rss.model.channel.Channel;
import rss.model.channel.TypeParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostLoader {
    private Map<TypeParser, Parser> parserMap;

    public PostLoader(List<Parser> parsers) {
        parserMap = new HashMap<>();

        for (Parser parser : parsers) {
            if (parserMap.containsKey(parser.getType()))
                throw new IllegalStateException("Found duplicate of parsers");
            parserMap.put(parser.getType(), parser);
        }
    }

    public Feed getFeed(Channel channel) {
        Parser parser = parserMap.get(channel.getTemplate().getType());
        if (parser == null) {
            throw new IllegalStateException("Parser not exists");
        }

        return parser.getFeed(channel);
    }
}
