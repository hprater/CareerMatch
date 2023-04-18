package edu.uark.csce.databasehb.web.application;

import edu.uark.csce.databasehb.data.Job;
import edu.uark.csce.databasehb.data.Major;
import edu.uark.csce.databasehb.data.Student;
import edu.uark.csce.databasehb.model.ToastMessage;
import edu.uark.csce.databasehb.service.ApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
public class ApplicationController {
    private final ApplicationService service;

    public ApplicationController(ApplicationService service) {
        this.service = service;
    }

    @PostMapping("/addApplication")
    public String addApplication(@ModelAttribute ApplicationForm form, Model model) {
        ToastMessage toast = new ToastMessage();
        if (form.isValid()) {
            try {
                boolean duplicateValue = service.addApplication(form);
                if (duplicateValue) {
                    toast.setMessage("Application already exists in the database");
                    toast.setCssClass("alert-warning", "fa-question-circle");
                } else
                    toast.setMessage("Application has been added to the database");
            } catch (Exception e) {
                toast.setCssClass("alert-danger", "fa-exclamation-triangle");
                toast.setMessage(e.getMessage());
            }
        } else {
            toast.setCssClass("alert-warning", "fa-question-circle");
            toast.setMessage("Invalid Value(s) in form");
        }
        model.addAttribute("toast", toast);
        model.addAttribute("viewName", "add_application");
        return "add_application";
    }
    @PostMapping("/viewApplication")
    public String viewApplication(@RequestParam("searchMethod") Integer searchMethod, Model model) {
        log.info("***** viewApplication *****");
        boolean noList = true;
        switch (searchMethod) {
            case 1 -> { // View all applications
                List<ViewApplicationForm> applications = service.getAllApplications();
                model.addAttribute("applicationList", applications);
                if (!applications.isEmpty()) noList = false;
            }
            case 2 -> { // View all applications by specified majorId
                List<Major> majors = service.getAllMajors();
                majors.add(0, new Major(-1, "Select", "Majors"));
                model.addAttribute("majorList", majors);
            }
            case 3 -> { // View all applications by specified studentId
                List<Student> students = service.getAllStudents();
                model.addAttribute("studentList", students);
            }
            case 4 -> { // View all applications by specified jobId
                List<Job> jobs = service.getAllJobs();
                model.addAttribute("jobList", jobs);
            }
        }
        model.addAttribute("noList", noList);
        model.addAttribute("searchMethod", searchMethod);
        model.addAttribute("viewName", "view_applications");
        return "view_applications";
    }

    @PostMapping("/viewApplication2")
    public String viewApplication(@RequestParam(value = "searchMethod", required = false, defaultValue = "0") Integer searchMethod
            , @RequestParam(value = "major", required = false, defaultValue = "0") int major
            , @RequestParam(value = "studentId", required = false, defaultValue = "0") long studentId
            , @RequestParam(value = "jobId", required = false, defaultValue = "0") long jobId, Model model) {
        log.info("***** viewApplication2 *****");
        boolean noList = true;
        List<ViewApplicationForm> applicationFormList = null;
        switch (searchMethod) {
            case 2 -> {
                List<Major> majors = service.getAllMajors();
                majors.add(0, majors.stream().filter(s -> s.getMajorId() == major).toList().get(0));
                model.addAttribute("majorList", majors);
                applicationFormList = service.getApplicationByMajorId(major);
            }
            case 3 -> {
                model.addAttribute("studentList", service.getAllStudents());
                applicationFormList = service.getApplicationByStudentId(studentId);
            }
            case 4 -> {
                model.addAttribute("jobList", service.getAllJobs());
                applicationFormList = service.getApplicationByJobId(jobId);
            }
        }
        if(applicationFormList != null) noList = false;
        if(noList) {
            ToastMessage toast = new ToastMessage("alert-warning","No results returned","fa-exclamation-triangle");
            model.addAttribute("toast", toast);
        }
        model.addAttribute("noList", noList);
        model.addAttribute("searchMethod", searchMethod);
        model.addAttribute("viewName", "view_applications");
        model.addAttribute("applicationList", applicationFormList);
        return "view_applications";
    }
}
