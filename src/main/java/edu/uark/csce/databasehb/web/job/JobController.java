package edu.uark.csce.databasehb.web.job;

import edu.uark.csce.databasehb.model.ToastMessage;
import edu.uark.csce.databasehb.model.major.Major;
import edu.uark.csce.databasehb.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import static edu.uark.csce.databasehb.web.application.ApplicationController.getEmptyToast;

@Slf4j
@Controller
public class JobController {
    private final JobService service;

    public JobController(JobService service) {
        this.service = service;
    }

    @GetMapping("/addJob")
    public String addJob(Model model) {
        List<Major> majors = service.getAllMajors();
        model.addAttribute("majors", majors);
        model.addAttribute("viewName", "add_job");
        return "add_job";
    }

    @PostMapping("/addJob")
    public String addJob(@ModelAttribute JobForm form, Model model) {
        ToastMessage toast = new ToastMessage();
        if (form.isValid()) {
            try {
                service.addJob(form);
                toast.setMessage(form.getCompanyName() + " has been added to the database");
            } catch (DataIntegrityViolationException dive) {
                if (dive.getMessage().contains("Duplicate")) {
                    toast.setCssClass("alert alert-warning");
                    toast.setMessage("Job already exists");
                    toast.setSymbol("fa-question-circle");
                } else
                    toast.setMessage(dive.getMessage());
            } catch (Exception e) {
                toast.setCssClass("alert alert-danger");
                toast.setMessage(e.getMessage());
                toast.setSymbol("fa-exclamation-triangle");
            }
        } else {
            toast.setCssClass("alert alert-warning");
            toast.setMessage("Invalid Value(s) in form");
            toast.setSymbol("fa-question-circle");
        }
        log.info("Toast: {}", toast);
        model.addAttribute("toast", toast);
        List<Major> majors = service.getAllMajors();
        model.addAttribute("majors", majors);
        model.addAttribute("viewName", "add_job");
        return "add_job";
    }

    @GetMapping("/viewJob")
    public String viewJob(Model model) {
        ViewJobForm form = new ViewJobForm();
        form.setMajorList(service.getAllMajors());
        model.addAttribute("form", form);
        model.addAttribute("viewName", "view_jobs");
        return "view_jobs";
    }

    @PostMapping("/viewJob")
    public String viewJob(@ModelAttribute ViewJobForm form, Model model) {
        form.getJobList().clear();
        form.setMajorList(service.getAllMajors());
        form.setJobList(service.getJobByMajor(form.getSelectedMajor()));
        if (form.getJobList().isEmpty())
            model.addAttribute("toast", getEmptyToast());
        model.addAttribute("form", form);
        model.addAttribute("viewName", "view_jobs");
        return "view_jobs";
    }
}
