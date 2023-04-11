package edu.uark.csce.databasehb.web;

import edu.uark.csce.databasehb.data.PersonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    private final PersonRepository repo;

    public GreetingController(PersonRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/")
    public String defaultPath(Model model) {
        String name = repo.getFirstPerson().getFirstName();
        model.addAttribute("name", name);
        return "greeting";
    }
}
