package com.Project.recruiterhub.controller;

import com.Project.recruiterhub.model.JobProfiles;
import com.Project.recruiterhub.model.Recruiter;
import com.Project.recruiterhub.repository.JobProfilesRepo;
import jakarta.servlet.http.HttpSession;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SelectCandidateController {
    @Autowired
    private JobProfilesRepo jobProfilesRepo;
    @PostMapping("/selectCandidate/{jobId}/{applicantId}")
    public String selectCandidate(
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
                if (job.getApplicantIds().contains(applicantObjId)) {
                    redirectAttributes.addAttribute("applicantId", applicantId);
                    return "redirect:/meetings/schedule/" + jobId;
                }
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid ID format");
        }
        return "redirect:/viewApplicants/" + jobId;
    }
}