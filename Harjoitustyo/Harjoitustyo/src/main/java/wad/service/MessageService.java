package wad.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
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
        message.setTimestamp(new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(new Date()));
        String channel = message.getChannel();
        if(!channel.contains("(private)")) {
            messageRepository.save(message);
        }
        this.template.convertAndSend("/channel/" + channel, message);
    }

    // lähettää viestejä 20 sekunnin välein default-kanavalle
    @Scheduled(fixedDelay = 20000)
    public void send() {
        Message poliitikkoMessage = poliitikko.getMessage();
        poliitikkoMessage.setTimestamp(new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(new Date()));
        this.template.convertAndSend("/channel/default", poliitikkoMessage);
    }
}
