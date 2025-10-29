package com.Project.recruiterhub.controller;

import com.Project.recruiterhub.model.JobProfiles;
import com.Project.recruiterhub.model.Jobseekers;
import com.Project.recruiterhub.model.Recruiter;
import com.Project.recruiterhub.model.jobs;
import com.Project.recruiterhub.repository.JobProfilesRepo;
import com.Project.recruiterhub.repository.PostRepo;
import com.Project.recruiterhub.repository.RecruiterRepo;
import jakarta.servlet.http.HttpSession;
import org.apache.xpath.operations.Mod;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class RecruiterDashboardController {
    @Autowired
    RecruiterRepo recruiterRepo;
    @Autowired
    PostRepo repo;
    @Autowired
    JobProfilesRepo jobProfilesRepo;

@PostMapping("/PostJob")
    public String jobpost(){
    return "PostJob";
}

@GetMapping("/PostJob")
public  String PostJob(){
    return "PostJob";
}

@PostMapping("/SearchByKeyword")
    public String Search( Model model){

    List<Jobseekers> applicants =repo.findAll();
    for(Jobseekers applicant:applicants){
        applicant.setRatings(0);
    }
    model.addAttribute("applicants",applicants);
    model.addAttribute("isSearchResult", false);
    return "SrchByKw";
}
    @GetMapping("/SearchByKeyword")
    public String Candiates( Model model){
        List<Jobseekers> applicants =repo.findAll();
        for(Jobseekers applicant:applicants){
            applicant.setRatings(0);
        }
        model.addAttribute("applicants",applicants);
        model.addAttribute("isSearchResult", false);
        return "SrchByKw";
    }
    @GetMapping("/downloadResume/{id}")
    public ResponseEntity<byte[]> downloadResume(@PathVariable String id) {
        ObjectId objectId = new ObjectId(id);

        Optional<Jobseekers> applicants = repo.findById(objectId);

        if (applicants.isPresent()) {
            Jobseekers user = applicants.get();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(user.getFileContentType()));

            ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                    .filename(user.getFilename())
                    .build();
            headers.setContentDisposition(contentDisposition);

            return new ResponseEntity<>(user.getResumefile(), headers, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/ManageJobs")
    public String ManageJobs(HttpSession session, Model model){
        Recruiter recruiter = (Recruiter) session.getAttribute("CurrentUser");
        if (recruiter == null) {
            return "redirect:/EmpCred";
        }
        List<JobProfiles> jobs = jobProfilesRepo.findByRecruiter(recruiter.getObjectId());
        Optional<Recruiter> updatedRecruiter = recruiterRepo.findById(recruiter.getObjectId());
        if (updatedRecruiter.isPresent()) {
            model.addAttribute("recruiter", updatedRecruiter.get());
            model.addAttribute("jobs", jobs);
            session.setAttribute("CurrentUser", updatedRecruiter.get());
        }
        return "MyJobs";
    }
    @GetMapping("/RecDashBoard")
    public String RecDash(){
        return "Recruiter Dashboard";
    }
}

