package rss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import rss.model.channel.Channel;
import rss.service.channel.ChannelService;

@Controller
@RequestMapping(path = "/channel")
public class ChannelController {
    @Autowired
    private ChannelService channelService;

    /**
     * @return Получение всех существующих каналов
     */
    @GetMapping(path = "/")
    public ModelAndView index() {
        return new ModelAndView("channel", "model", channelService.getChannels());
    }

    /**
     * Модель для создания и изменения каналов
     * @param id - id модели с настройками канала. Получение id изменяет уже существующий канал
     */
    @GetMapping(path = "/edit/{id}")
    public ModelAndView edit(@PathVariable Integer id) {
        return new ModelAndView("edit", "channel", channelService.getChannelById(id));
    }

    @GetMapping(path = "/edit")
    public ModelAndView edit() {
        return edit(null);
    }

    @PostMapping(path = "/edit/{id}")
    public String update(@PathVariable Integer id, @ModelAttribute("channel") Channel channel) {
        channelService.update(id, channel);

        return "redirect:/edit/";
    }

    @PostMapping(path = "/edit")
    public String update(@ModelAttribute("channel") Channel channel) {
        return update(null, channel);
    }
}
