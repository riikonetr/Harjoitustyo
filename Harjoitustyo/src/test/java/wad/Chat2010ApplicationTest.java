package wad;

import java.util.List;
import static org.assertj.core.api.Java6Assertions.assertThat;
import org.fluentlenium.adapter.FluentTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Chat2010ApplicationTest extends FluentTest {

    @Autowired
    private MessageService messageService;
    
    @Autowired
    private PersonRepository personRepository;
    
    public WebDriver webDriver = new HtmlUnitDriver();

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

    @LocalServerPort
    private Integer port;
    
   @Test
    public void pageShouldNotBeDirectlyAccessible() {
        goTo("http://localhost:" + port + "/chat");
        assertThat(pageSource()).doesNotContain("kanava");
    }
    
    @Test
    public void shouldSeeLoginPageOnAccessingChat() {
        goTo("http://localhost:" + port + "/chat");
        assertThat(find(By.name("username"))).isNotNull();
        assertThat(find(By.name("password"))).isNotNull();
    }
    
    @Test
    public void authSuccessful() {
        goTo("http://localhost:" + port + "/signup");

        fill(find(By.name("username"))).with("test");
        fill(find(By.name("chatname"))).with("testaaja");
        fill(find(By.name("password"))).with("x");
        find(By.name("submit")).submit();
        
        goTo("http://localhost:" + port + "/chat");

        fill(find(By.name("username"))).with("test");
        fill(find(By.name("password"))).with("x");
        find(By.name("submit")).submit();
        assertThat(pageSource()).contains("testaaja");
    }
    
    @Test
    public void messageOk() throws Exception { 
        Message message = new Message();
        message.setChatname("testaaja");
        message.setAvatar("images/wather-icon.png");
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
        person.setAvatar("images/wather-icon.png");
        
        personRepository.save(person);
        
        List<Person> persons = personRepository.findAll();
           
        Assert.assertTrue(persons.contains(person));
    }
}
