package edu.uark.csce.databasehb.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String defaultPath(Model model) {
        model.addAttribute("viewName", "home");
        return "index";
    }

}
