package rss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import rss.controller.response.ResponseInfo;
import rss.controller.response.message.MessageInfo;
import rss.controller.response.redirect.RedirectInfo;
import rss.model.db.Channel;
import rss.model.db.template.Template;
import rss.service.view.ChannelView;

import javax.validation.Valid;

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

    @GetMapping(path = "/edit")
    public ModelAndView edit() {
        return edit(null);
    }

    /**
     * Модель для создания и изменения каналов
     * @param id - id модели с настройками канала. Получение id изменяет уже существующий канал
     */
    @GetMapping(path = "/edit/{id}")
    public ModelAndView edit(@PathVariable Integer id) {
        return new ModelAndView("edit", "channel", channelView.getChannelById(id));
    }

    @PostMapping(path = "/edit")
    @ResponseBody
    public ResponseInfo update(@Valid @ModelAttribute("channel") Channel channel, Errors errors) {
        return update(null, channel, errors);
    }

    @PostMapping(path = "/edit/{id}")
    @ResponseBody
    public ResponseInfo update(@PathVariable Integer id, @Valid @ModelAttribute("channel") Channel channel, Errors errors) {
        MessageInfo validation = MessageInfo.createFrom(errors);

        if (validation.hasErrors())
        {
            return validation;
        }

        channelView.update(id, channel);

        return new RedirectInfo("/channel/");
    }

    @GetMapping(path = "/delete/{id}")
    public String delete(@PathVariable Integer id) {
        channelView.delete(id);

        return "redirect:/channel/";
    }

    @GetMapping(path = "/defaultTemplate")
    @ResponseBody
    public Template defaultTemplate() {
        return channelView.getRssDefaultTemplate();
    }
}
