package rss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import rss.model.view.SearchQuery;
import rss.model.view.SearchResult;
import rss.service.view.PostSearchView;

@Controller
@RequestMapping(path = "/")
public class MainController {
    @Autowired
    private PostSearchView searchView;

    /**
     * Просмотр карточек с новостями
     * @param query запрос поиска постов (строка запроса, страница)
     * @return найденные посты
     */
    @GetMapping(path = "/")
    public ModelAndView index(@ModelAttribute("query") SearchQuery query) {
        SearchResult result = searchView.searchString(query);

        return new ModelAndView("index", "model", result);
    }
}
