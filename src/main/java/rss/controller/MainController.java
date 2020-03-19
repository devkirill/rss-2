package rss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import rss.service.search.SearchPostService;
import rss.service.search.SearchQuery;
import rss.service.search.SearchResult;

@Controller
@RequestMapping(path = "/")
public class MainController {
    @Autowired
    private SearchPostService postService;

    @GetMapping(path = "/")
    public ModelAndView index(@ModelAttribute("query") SearchQuery query) {
        SearchResult result = postService.searchString(query);

        return new ModelAndView("index", "model", result);
    }
}
