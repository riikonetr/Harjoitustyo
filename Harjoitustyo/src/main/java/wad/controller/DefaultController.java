package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wad.service.PersonService;

@Controller
@RequestMapping("*")
public class DefaultController {
    
    @Autowired
    private PersonService personService;
    
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String viewLogin(Model model) {
        return "login";
    }

    @RequestMapping(value = "signup", method = RequestMethod.GET)
    public String viewSignup(Model model) {
        return "signup";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String view(Model model) {
        model.addAttribute("chatname", personService.getAuthenticatedPerson().getChatname());
        return "index";
    }
}
