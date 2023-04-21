package edu.uark.csce.databasehb.web.student;

import edu.uark.csce.databasehb.model.ToastMessage;
import edu.uark.csce.databasehb.model.major.Major;
import edu.uark.csce.databasehb.model.student.Student;
import edu.uark.csce.databasehb.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static edu.uark.csce.databasehb.web.application.ApplicationController.getEmptyToast;

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
        StudentForm form = new StudentForm();
        form.setMajor(0);
        model.addAttribute("form", form);
        model.addAttribute("majors", majors);
        model.addAttribute("viewName", "add_student");
        return "add_student";
    }

    @PostMapping("/addStudent")
    public String addStudent(@ModelAttribute StudentForm form, BindingResult br, Model model) {
        model.addAttribute("form", form);
        ToastMessage toast = new ToastMessage();
        if (br.hasErrors()) {
            log.error("ERROR!! {}", br.getErrorCount());
            toast.setCssClass("alert-warning");
            toast.setSymbol("fa-exclamation-triangle");
            toast.setMessage("Invalid Value(s) in form");
            model.addAttribute("toast", toast);
            List<Major> majors = service.getAllMajors();
            StudentForm formA = new StudentForm();
            form.setMajor(0);
            model.addAttribute("form", formA);
            model.addAttribute("majors", majors);
            model.addAttribute("viewName", "add_student");
            return "add_student";
        }
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

    @GetMapping("/viewStudent")
    public String viewStudent(Model model) {
        ViewStudentForm form = new ViewStudentForm();
        form.setMajorList(service.getAllMajors());
        model.addAttribute("form", form);
        model.addAttribute("viewName", "view_students");
        return "view_students";
    }

    @PostMapping("/viewStudent")
    public String viewStudents(@ModelAttribute ViewStudentForm form, Model model) {
        form.getStudentList().clear();
        form.setMajorList(service.getAllMajors());
        form.setStudentList(service.getStudentsByMajor(form.getSelectedMajor()));
        if(form.getStudentList().isEmpty())
            model.addAttribute("toast", getEmptyToast());
        model.addAttribute("form", form);
        model.addAttribute("viewName", "view_students");
        return "view_students";
    }

    @GetMapping("/viewAllNoAppStudents")
    public String viewAllNoAppStudents(Model model){
        List<Student> students = service.getAllNoAppStudents();
        model.addAttribute("studentList", students);
        model.addAttribute("viewName", "view_no_app");
        return "view_all_no_app_students";
    }

}
