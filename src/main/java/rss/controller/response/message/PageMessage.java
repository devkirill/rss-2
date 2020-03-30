package rss.controller.response.message;

import org.springframework.web.util.HtmlUtils;

public class PageMessage
{
    private final String message;

    public PageMessage(String message)
    {
        this.message = HtmlUtils.htmlEscape(message);
    }

    public String getMessage()
    {
        return message;
    }
}