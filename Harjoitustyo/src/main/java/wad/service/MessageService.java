package wad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import wad.domain.Message;


//Lisätty toinen viestien lähettäjä
@Service
public class MessageService {
    
    @Autowired
    private Poliitikko poliitikko;

    @Autowired
    private SimpMessagingTemplate template;

    public void addMessage(Message message) {
        this.template.convertAndSend("/channel/" + message.getChannel(), message);
    }

    // lähettää viestejä 20 sekunnin välein default-kanavalle
    @Scheduled(fixedDelay = 20000)
    public void send() {
        this.template.convertAndSend("/channel/default", poliitikko.getMessage());
    }
}
