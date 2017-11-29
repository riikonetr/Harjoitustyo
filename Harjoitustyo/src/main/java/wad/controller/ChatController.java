package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.Message;
import wad.service.MessageService;

@Controller
public class ChatController {

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model, @RequestParam("name") String username, @RequestParam("channel") String channel) {
        model.addAttribute("channel", channel);
        model.addAttribute("username", username);
        return "chat";
    }

    @MessageMapping("/messages")
    public void handleMessage(Message message) throws Exception {
        messageService.addMessage(message);
    }
}
