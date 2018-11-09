package com.news.rss.controller;

import com.news.rss.domain.PapersArticle;
import com.news.rss.service.RssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class RssController {

    @Autowired
    private RssService rssService;

    @GetMapping("/")
    public ModelAndView getNews(String paperCode) {
        List<PapersArticle> articles = rssService.reciveRssData(paperCode);
        ModelAndView mv = new ModelAndView();
        mv.addObject("author", paperCode);
        mv.addObject("articles", articles);
        mv.setViewName("main");
        return mv;
    }
}
