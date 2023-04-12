package edu.uark.csce.databasehb.web;

import edu.uark.csce.databasehb.data.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {

    private final StudentRepository repo;

    public WebController(StudentRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/addStudent")
    public String addStudent(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "add_student";
    }

    @GetMapping("/addJob")
    public String addJob(Model model) {
        return "add_job";
    }

    @GetMapping("/addApplication")
    public String addApplication(Model model) {
        return "add_application";
    }

    @GetMapping("/viewStudent")
    public String viewStudent(Model model) {
        return "view_students";
    }

    @GetMapping("/viewJob")
    public String viewJob(Model model) {
        return "view_jobs";
    }

    @GetMapping("/viewApplication")
    public String viewApplication(Model model) {
        return "view_applications";
    }

    @GetMapping("/")
    public String defaultPath(Model model) {
//        String name = repo.getStudents().getmajor();
//        model.addAttribute("name", name);
        return "index";
    }

    @PostMapping("/addStudent")
    public String addStudent(@RequestBody StudentForm form) {
        System.out.println(form);
        return "add_student";
    }
}
