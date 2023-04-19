package edu.uark.csce.databasehb.web.student;

import edu.uark.csce.databasehb.model.ToastMessage;
import edu.uark.csce.databasehb.model.major.Major;
import edu.uark.csce.databasehb.model.student.Student;
import edu.uark.csce.databasehb.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/addStudent")
    public String addStudent(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        List<Major> majors = service.getAllMajors();
        model.addAttribute("majors", majors);
        model.addAttribute("viewName", "add_student");
        return "add_student";
    }

    @GetMapping("/viewStudent")
    public String viewStudent(Model model) {
        log.info("ViewStudents GET");
        List<Major> majors = service.getAllMajors();
        Boolean initialLoad = true;
        model.addAttribute("majors", majors);
        model.addAttribute("initialLoad", initialLoad);
        model.addAttribute("viewName", "view_students");
        return "view_students";
    }

    @PostMapping("/viewStudent")
    public String viewStudents(@RequestParam("major") Integer major, Model model) {
        log.info("ViewStudents POST");
        List<Major> majors = service.getAllMajors();
        majors.add(0, majors.stream().filter(s -> s.getMajorId() == major).toList().get(0));
        log.info("MajorList: {}", majors);
        model.addAttribute("majors", majors);
        List<Student> studentList = service.getStudentsByMajor(major);
        Boolean initialLoad = false;
        model.addAttribute("students", studentList);
        model.addAttribute("initialLoad", initialLoad);
        model.addAttribute("viewName", "view_students");
        return "view_students";
    }

    @PostMapping("/addStudent")
    public String addStudent(@ModelAttribute StudentForm form, Model model) {
        ToastMessage toast = new ToastMessage();
        if (form.isValid()) {
            try {
                boolean duplicateValue = service.addStudent(form);
                if (duplicateValue) {
                    toast.setCssClass("alert-warning");
                    toast.setSymbol("fa-question-circle");
                    toast.setMessage("Student already exists in the database, updating values instead");
                } else
                    toast.setMessage(form.getStudentName() + " has been added to the database");
            } catch (Exception e) {
                toast.setCssClass("alert-danger");
                toast.setSymbol("fa-exclamation-triangle");
                toast.setMessage(e.getMessage());
            }
        } else {
            toast.setCssClass("alert-warning");
            toast.setSymbol("fa-question-circle");
            toast.setMessage("Invalid Value(s) in form");
        }
        log.info("Toast: {}", toast);
        model.addAttribute("toast", toast);
        List<Major> majors = service.getAllMajors();
        model.addAttribute("majors", majors);
        model.addAttribute("viewName", "add_student");
        return "add_student";
    }

}
