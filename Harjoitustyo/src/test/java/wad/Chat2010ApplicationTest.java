package wad;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import wad.service.MessageService;
import wad.domain.Message;
import wad.domain.Person;
import wad.repository.PersonRepository;
import wad.service.PersonService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Chat2010ApplicationTest {

    @Autowired
    private MessageService messageService;
    
    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }
    
    @Test
    public void statusOk() throws Exception {
        /*
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/signin"))
                .andExpect(status().isOk());
*/
    }
    
    @Test
    public void loginOk() throws Exception {
        //mockMvc.perform(post("/chat").param("name", "testi").param("channel", "testiChannel")).andExpect(status().isOk());
    }
    
    @Test
    public void messageOk() throws Exception {
        Person person = new Person();
        person.setChatname("testi");
        person.setUsername("t");
        person.setPassword("salainen");
        
        Message message = new Message();
        message.setAuthor(person);
        message.setChannel("testiChannel");
        message.setContent("testi... 123");
        
        messageService.addMessage(message);
        
        List<Message> messages = messageService.getAllMessages();
           
        Assert.assertTrue(messages.contains(message));
    }
    
    @Test
    public void personOk() throws Exception {
        Person person = new Person();
        person.setChatname("testi");
        person.setUsername("t");
        person.setPassword("salainen");
        
        personRepository.save(person);
        
        List<Person> persons = personRepository.findAll();
           
        Assert.assertTrue(persons.contains(person));
    }
}
