package com.news.rss.domain;

public class PapersRss {
    private String rssUrl;
    private String rssEncoding;

    public PapersRss() {
    }

    public String getRssUrl() {
        return rssUrl;
    }

    public void setRssUrl(String rssUrl) {
        this.rssUrl = rssUrl;
    }

    public String getRssEncoding() {
        return rssEncoding;
    }

    public void setRssEncoding(String rssEncoding) {
        this.rssEncoding = rssEncoding;
    }
}
