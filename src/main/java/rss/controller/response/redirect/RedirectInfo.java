package rss.controller.response.redirect;

import rss.controller.response.ResponseInfo;
import rss.controller.response.ResponseType;

public class RedirectInfo implements ResponseInfo
{
    private final String url;

    public RedirectInfo(String url)
    {
        this.url = url;
    }

    //region getters

    public String getUrl()
    {
        return url;
    }

    @Override
    public ResponseType getResponseType()
    {
        return ResponseType.REDIRECT;
    }

    //endregion
}
