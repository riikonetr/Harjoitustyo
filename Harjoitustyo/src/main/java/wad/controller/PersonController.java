package wad.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.Message;
import wad.domain.Person;
import wad.repository.PersonRepository;
import wad.service.PersonService;

@Controller
public class PersonController {

    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private PersonService personService;
    
    @RequestMapping(value = "/change", method = RequestMethod.POST)
    public String change(Model model, @RequestParam("avatar") String avatar) {
        if(!avatar.equals("NONE")) {
            Person person = personService.getAuthenticatedPerson();
            person.setAvatar(avatar);
            personRepository.save(person);
        }
        return "redirect:/index";
    }

    @RequestMapping(value = "/persons", method = RequestMethod.POST)
    public String create(@ModelAttribute Person person) {
        personRepository.save(person);
        return "redirect:/index";
    }
    
}
