package edu.uark.csce.databasehb.web;

import edu.uark.csce.databasehb.data.JobRepository;
import edu.uark.csce.databasehb.data.Student;
import edu.uark.csce.databasehb.data.StudentRepository;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {

    private final StudentRepository studentRepo;
    private final JobRepository jobRepo;

    public WebController(StudentRepository studentRepo, JobRepository jobRepo) {
        this.studentRepo = studentRepo;
        this.jobRepo = jobRepo;
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
    public String addStudent(@ModelAttribute StudentForm form, Model model) {
        if(form.isValid()){
            studentRepo.addStudent(form);
            model.addAttribute("formResult", form.getStudentName() + " inserted to database.");
        }else{
            model.addAttribute("formResult", "Invalid input.");
        }
        System.out.println(form);
        return "add_student";
    }
}
