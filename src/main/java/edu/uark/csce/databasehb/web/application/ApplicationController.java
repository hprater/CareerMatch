package edu.uark.csce.databasehb.web.application;

import edu.uark.csce.databasehb.model.ToastMessage;
import edu.uark.csce.databasehb.model.major.Major;
import edu.uark.csce.databasehb.model.student.Student;
import edu.uark.csce.databasehb.service.ApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
public class ApplicationController {
    private final ApplicationService service;

    public ApplicationController(ApplicationService service) {
        this.service = service;
    }

    @GetMapping("/addApplication")
    public String addApplication(Model model) {
        AddApplicationForm form = populateForm(new AddApplicationForm());
        model.addAttribute("form", form);
        model.addAttribute("viewName", "add_application");
        return "add_application";
    }

    @PostMapping("/addApplication")
    public String addApplication(@ModelAttribute AddApplicationForm form, Model model) {
        ToastMessage toast = new ToastMessage();
        log.info("FORM : {}", form);
        if (form.getSelectedStudent() < 1 || form.getSelectedJob() < 1) {
            toast.setMessage("Please make a selection from both dropdowns");
            toast.setCssClass("alert-warning", "fa-question-circle");
        } else {
            try {
                service.addApplication(form);
                toast.setMessage("Application added/updated in database");
            } catch (Exception e) {

                toast.setCssClass("alert-danger", "fa-exclamation-triangle");
                if (e.getMessage().contains("Duplicate")) {
                    toast.setMessage("Application already exists");
                    toast.setSymbol("fa-question-circle");
                    toast.setCssClass("alert-warning");
                }
                else
                    toast.setMessage(e.getMessage());
            }
        }
        model.addAttribute("toast", toast);
        model.addAttribute("form", populateForm(form));
        model.addAttribute("viewName", "add_application");
        return "add_application";
    }

    private AddApplicationForm populateForm(AddApplicationForm form) {
        form.setStudentList(service.getAllStudents());
        form.setJobList(service.getAllJobs());
        return form;
    }

    @GetMapping("/viewApplication")
    public String viewApplication(Model model) {
        ViewApplicationForm form = new ViewApplicationForm();
        model.addAttribute("form", form);
        model.addAttribute("viewName", "view_applications");
        return "view_applications";
    }

    @PostMapping("/viewApplication")
    public String viewApplication(@ModelAttribute ViewApplicationForm form, Model model) {
        log.info("***** viewApplication *****");
        log.info("Form: {}", form);
        form.getApplicationTableList().clear();
        switch (form.getSearchMethod()) {
            case 1 -> { // View all applications
                form.setApplicationTableList(service.getAllApplications());
            }
            case 2 -> { // View all applications by specified majorId
                List<Major> majors = service.getAllMajors();
//                majors.add(0, new Major(-1, "Select", "Majors"));
                form.setMajorList(majors);
                if (form.getSelectedMajor() > 0) {
                    form.setApplicationTableList(service.getApplicationByMajorId(form.getSelectedMajor()));
                    if (form.getApplicationTableList().isEmpty())
                        model.addAttribute("toast", getEmptyToast());
                }
            }
            case 3 -> { // View all applications by specified studentId
                List<Student> studentList = service.getAllStudents();
                //studentList.add(0, new Student(0, "Select...", ""));
                form.setStudentList(studentList);
                if (form.getSelectedStudent() > 0) {
                    form.setApplicationTableList(service.getApplicationByStudentId(form.getSelectedStudent()));
                    if (form.getApplicationTableList().isEmpty())
                        model.addAttribute("toast", getEmptyToast());

                }
            }
            case 4 -> { // View all applications by specified jobId
                form.setJobList(service.getAllJobs());
                if (form.getSelectedJob() > 0) {
                    form.setApplicationTableList(service.getApplicationByJobId(form.getSelectedJob()));
                    if (form.getApplicationTableList().isEmpty())
                        model.addAttribute("toast", getEmptyToast());

                }
            }
        }
        model.addAttribute("form", form);
        model.addAttribute("viewName", "view_applications");
        return "view_applications";
    }

    public static ToastMessage getEmptyToast() {
        return new ToastMessage("alert-warning", "No results returned", "fa-exclamation-triangle");
    }
}
