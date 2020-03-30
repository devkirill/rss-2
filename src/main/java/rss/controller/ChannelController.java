package rss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import rss.model.db.Channel;
import rss.model.db.template.Template;
import rss.service.view.ChannelView;

@Controller
@RequestMapping(path = "/channel")
public class ChannelController {
    @Autowired
    private ChannelView channelView;

    /**
     * @return Получение всех существующих каналов
     */
    @GetMapping(path = "/")
    public ModelAndView index() {
        return new ModelAndView("channel", "model", channelView.getChannels());
    }

    /**
     * Модель для создания и изменения каналов
     * @param id - id модели с настройками канала. Получение id изменяет уже существующий канал
     */
    @GetMapping(path = "/edit/{id}")
    public ModelAndView edit(@PathVariable Integer id) {
        return new ModelAndView("edit", "channel", channelView.getChannelById(id));
    }

    @GetMapping(path = "/edit")
    public ModelAndView edit() {
        return edit(null);
    }

    @PostMapping(path = "/edit/{id}")
    public String update(@PathVariable Integer id, @ModelAttribute("channel") Channel channel) {
        channelView.update(id, channel);

        return "redirect:/edit/";
    }

    @PostMapping(path = "/edit")
    public String update(@ModelAttribute("channel") Channel channel) {
        return update(null, channel);
    }

    @GetMapping(path = "/defaultTemplate")
    @ResponseBody
    public Template defaultTemplate() {
        return channelView.getRssDefaultTemplate();
    }
}
