package com.Project.recruiterhub.controller;

import com.Project.recruiterhub.model.JobProfiles;
import com.Project.recruiterhub.model.Recruiter;
import com.Project.recruiterhub.model.jobs;
import com.Project.recruiterhub.repository.JobProfilesRepo;
import com.Project.recruiterhub.repository.RecruiterRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class CreateJobProfileController {
    @Autowired
    JobProfilesRepo repo;
    @Autowired
    RecruiterRepo recruiterRepo;
    @PostMapping("JobProfile")
    public String CreateJob(@ModelAttribute JobProfiles Jobs, Model model, HttpSession session){
        Recruiter recruiter= (Recruiter) session.getAttribute("CurrentUser");
        Jobs.setRecruiter(recruiter.getObjectId());
        JobProfiles savedJob = repo.save(Jobs);
        jobs jobEntry = new jobs();
        jobEntry.setJobId(savedJob.getObjectId());
        jobEntry.setApplicantId(new ArrayList<>());
        recruiter.addjobs(jobEntry);
        recruiterRepo.save(recruiter);
        return "PostJobConf";
    }
}
