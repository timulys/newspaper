package com.news.rss.service;

import com.news.rss.domain.PapersArticle;
import com.news.rss.domain.PapersRss;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class RssService {
    private Logger log = LoggerFactory.getLogger(RssService.class);
    private final String APPLICATION = "application.properties";

    public boolean registerRssUrl(String url, String encoding) { return false; }

    public boolean modifyRssUrl(String url, String encoding) { return false; }

    public boolean removeRssUrl(String url, String encoding) { return false; }

    public List<PapersArticle> reciveRssData(String newspaper) {
        List<PapersArticle> articles = new ArrayList<>();
        return callRss(newspaper);
    }

    public boolean testConnection() { return false; }

    /**
     * private methods
     */
    private List<PapersArticle> callRss(String newspaper) {
        if (newspaper == null) newspaper="cho";
        try {
            PapersRss paper = getRssAddress(newspaper);
            InputStream is = new URL(paper.getRssUrl()).openStream();
            return parsingArticle(readData(is, paper.getRssEncoding()));
        } catch (IOException e) {
            log.error(e.toString());
        }
        return null;
    }

    private String readData(InputStream is, String encoding) throws IOException {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName(encoding)));
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = br.read()) != -1) {
                sb.append((char) cp);
            }
            return sb.toString();
        } finally {
            is.close();
        }
    }

    private PapersRss getRssAddress(String newspaper) throws IOException {
        PapersRss papersRss = new PapersRss();
        InputStream is = getClass().getClassLoader().getResourceAsStream(APPLICATION);
        try {
            Properties prop = new Properties();
            prop.load(is);
            prop.stringPropertyNames().stream().allMatch(key -> {
                if (!StringUtils.isEmpty(newspaper) && key.contains(newspaper)) {
                    if (key.contains("rssUrl")) papersRss.setRssUrl(prop.getProperty(key));
                    if (key.contains("rssEncoding")) papersRss.setRssEncoding(prop.getProperty(key));
                }
                return true;
            });
        } finally {
            is.close();
        }
        return papersRss;
    }

    public static void main(String[] args) {
        try {
            InputStream is = new URL("http://api.seibro.or.kr/openapi/service/StockSvc?_wadl&type=xml").openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<PapersArticle> parsingArticle(String xml) throws IOException {
        List<PapersArticle> articles = new ArrayList<>();

        JSONObject jsonObj = XML.toJSONObject(xml);
        JSONArray jsonArr = jsonObj.getJSONObject("rss").getJSONObject("channel").getJSONArray("item");
        for (int i = 0; i < jsonArr.length(); i++) {
            JSONObject obj = jsonArr.getJSONObject(i);
            PapersArticle article = new PapersArticle();
            article.setTitle(obj.getString("title"));
            article.setDescription(obj.getString("description"));
            article.setLink(obj.getString("link"));
            if (!obj.isNull("pubDate")) {
                article.setPubDate(obj.getString("pubDate"));
            } else if(!obj.isNull("dc:date")) {
                article.setPubDate(obj.getString("dc:date"));
            }
            articles.add(article);
        }
        return articles;
    }
}

