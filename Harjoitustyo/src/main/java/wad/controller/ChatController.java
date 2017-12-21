package wad.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.Message;
import wad.domain.Person;
import wad.service.MessageService;
import wad.service.PersonService;

@Controller
public class ChatController {

    @Autowired
    private MessageService messageService;
    
    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/chat", method = RequestMethod.POST)
    public String login(Model model, @RequestParam("channel") String channel, @RequestParam(value = "isPrivate", required = false) String isPrivate) {
        Person author = personService.getAuthenticatedPerson();
        model.addAttribute("chatname", author.getChatname());
        model.addAttribute("avatar", author.getAvatar());
        if(isPrivate != null) { //Yksi toimiva tyyli käsitellä checkbox
            channel = channel + "(private)";
        } else {
            List<Message> allMessages = messageService.getAllMessages();
            List<Message> channelMessages = new ArrayList<>();
            for(Message message : allMessages) {
                if(message.getChannel().equals(channel)) {
                    channelMessages.add(message);
                }
            }
            model.addAttribute("messages", channelMessages);
        }
        model.addAttribute("channel", channel);
        
        return "chat";
    }

    @MessageMapping("/messages")
    public void handleMessage(Message message) throws Exception {
        messageService.addMessage(message);
    }
    
}
