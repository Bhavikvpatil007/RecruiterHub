package com.Project.recruiterhub.controller;

import com.Project.recruiterhub.model.JobProfiles;
import com.Project.recruiterhub.model.Jobseekers;
import com.Project.recruiterhub.model.Recruiter;
import com.Project.recruiterhub.repository.JobProfilesRepo;
import com.Project.recruiterhub.repository.PostRepo;
import com.Project.recruiterhub.repository.SearchbyKeyword;
import jakarta.servlet.http.HttpSession;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ViewApplNewController {
    @Autowired
    PostRepo repo;
    @Autowired
    JobProfilesRepo jobProfilesRepo;
    @Autowired
    SearchbyKeyword searchbyKeyword;
    @PostMapping("/DeleteCandidate/{jobId}/{applicantId}")
    public String deleteCandidate(
            @PathVariable String jobId,
            @PathVariable String applicantId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Recruiter recruiter = (Recruiter) session.getAttribute("CurrentUser");
        if (recruiter == null) return "redirect:/EmpCred";

        try {
            ObjectId jobObjId = new ObjectId(jobId);
            ObjectId applicantObjId = new ObjectId(applicantId);

            JobProfiles job = jobProfilesRepo.findById(jobObjId).orElse(null);
            if (job != null && job.getRecruiter().equals(recruiter.getObjectId())) {
                job.deleteApplicant(applicantObjId);
                jobProfilesRepo.save(job);
                redirectAttributes.addFlashAttribute("successMessage", "Candidate removed successfully");
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid ID format");
        }
        return "redirect:/viewApplicants/" + jobId;
    }
}
