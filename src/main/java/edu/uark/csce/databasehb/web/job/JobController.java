package edu.uark.csce.databasehb.web.job;

import edu.uark.csce.databasehb.model.ToastMessage;
import edu.uark.csce.databasehb.model.job.Job;
import edu.uark.csce.databasehb.model.major.Major;
import edu.uark.csce.databasehb.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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

    @GetMapping("/viewJob")
    public String viewJob(Model model) {
        log.info("ViewJobs GET");
        List<Major> majors = service.getAllMajors();
        Boolean initialLoad = true;
        model.addAttribute("majors", majors);
        model.addAttribute("initialLoad", initialLoad);
        model.addAttribute("viewName", "view_jobs");
        return "view_jobs";
    }

    @PostMapping("/viewJob")
    public String viewJob(@RequestParam("major") Integer major, Model model) {
        log.info("ViewJobs POST");
        List<Major> majors = service.getAllMajors();
        majors.add(0, majors.stream().filter(s -> s.getMajorId() == major).toList().get(0));
        log.info("MajorList: {}", majors);
        model.addAttribute("majors", majors);
        List<Job> jobList = service.getJobByMajor(major);
        Boolean initialLoad = false;
        model.addAttribute("jobs", jobList);
        model.addAttribute("initialLoad", initialLoad);
        model.addAttribute("viewName", "view_jobs");
        return "view_jobs";
    }


    @PostMapping("/addJob")
    public String addJob(@ModelAttribute JobForm form, Model model) {
        ToastMessage toast = new ToastMessage();
        if (form.isValid()) {
            try {
                service.addJob(form);
                toast.setMessage(form.getCompanyName() + " has been added to the database");
            } catch (DataIntegrityViolationException dive) {
                toast.setCssClass("alert alert-warning");
                toast.setMessage("Job already exists in the database");
            } catch (Exception e) {
                toast.setCssClass("alert alert-danger");
                toast.setMessage(e.getMessage());
            }
        } else {
            toast.setCssClass("alert alert-warning");
            toast.setMessage("Invalid Value(s) in form");
        }
        log.info("Toast: {}", toast);
        model.addAttribute("toast", toast);
        List<Major> majors = service.getAllMajors();
        model.addAttribute("majors", majors);
        model.addAttribute("viewName", "add_job");
        return "add_job";
    }


}
