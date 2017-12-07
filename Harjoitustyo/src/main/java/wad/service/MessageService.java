package wad.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import wad.domain.Message;
import wad.repository.MessageRepository;


//Lisätty toinen viestien lähettäjä
@Service
public class MessageService {
    
    @Autowired
    private Poliitikko poliitikko;

    @Autowired
    private SimpMessagingTemplate template;
    
    @Autowired
    private MessageRepository messageRepository;
    
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public void addMessage(Message message) {
        messageRepository.save(message);
        this.template.convertAndSend("/channel/" + message.getChannel(), message);
    }

    // lähettää viestejä 20 sekunnin välein default-kanavalle
    @Scheduled(fixedDelay = 20000)
    public void send() {
        Message poliitikkoMessage = poliitikko.getMessage();
        this.template.convertAndSend("/channel/default", poliitikkoMessage);
    }
}
