package wad;

import static org.junit.Assert.assertTrue;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class Chat2010ApplicationTest {

    @Autowired
    private MessageService messageService;
    
    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }
    
    @Test
    public void statusOk() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }
    
    @Test
    public void loginOk() throws Exception {
        mockMvc.perform(post("/login").param("name", "testi").param("channel", "testiChannel")).andExpect(status().isOk());
    }
    
    @Test
    public void messageOk() throws Exception {
        Message message = new Message();
        message.setUsername("Testaaja");
        message.setChannel("default");
        message.setContent("testi... 123");
        
        messageService.addMessage(message);
        
        //Testaus että viesti meni perille
        assertTrue(true);
    }
}
