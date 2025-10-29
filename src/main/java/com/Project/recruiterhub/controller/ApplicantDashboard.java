package com.Project.recruiterhub.controller;

import com.Project.recruiterhub.model.*;
import com.Project.recruiterhub.repository.JobProfilesRepo;
import com.Project.recruiterhub.repository.PostRepo;
import com.Project.recruiterhub.repository.RecruiterRepo;
import com.Project.recruiterhub.repository.SearchbyKeywordsJobs;
import jakarta.servlet.http.HttpSession;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class ApplicantDashboard {
    @Autowired
    PostRepo applicantRepo;
    @Autowired
    RecruiterRepo recruiterRepo;

    @Autowired
    JobProfilesRepo jobProfilesRepo;
    @Autowired
    SearchbyKeywordsJobs searchbyKeywordsJobs;

    @PostMapping("/apply/{jobId}")
    public String applyForJob(@PathVariable String jobId, HttpSession session) {
        Jobseekers applicant = (Jobseekers) session.getAttribute("CurrentUser");
        ObjectId jobObjectId = new ObjectId(jobId);
        Optional<JobProfiles> jobOptional = jobProfilesRepo.findById(jobObjectId);
        if (jobOptional.isPresent()) {
            JobProfiles job = jobOptional.get();
            job.addApplicant(applicant.getObjectId());
            jobProfilesRepo.save(job);
            Optional<Recruiter> recruiterOptional = recruiterRepo.findById(job.getRecruiter());
            if (recruiterOptional.isPresent()) {
                Recruiter recruiter = recruiterOptional.get();
                for (jobs recruiterJob : recruiter.getCreatedjobs()) {
                    if (recruiterJob.getJobId().equals(jobObjectId)) {
                        recruiterJob.addApplicantId(applicant.getObjectId());
                        break;
                    }
                }
                recruiterRepo.save(recruiter);
            }
        }
        return "AppliedSuccess";
    }
    @GetMapping("/ApplicantDash")
    public String ApplicantDash(Model model){
        List<Jobseekers> allusers;
        List<JobProfiles> jobProfiles=jobProfilesRepo.findAll();
        model.addAttribute("jobProfiles",jobProfiles);
        return "ApplDash";
    }
    @GetMapping("/scheduledMeetings")
    public String viewScheduledMeetings(Model model, HttpSession session) {
        Jobseekers applicant = (Jobseekers) session.getAttribute("CurrentUser");
        if (applicant == null) return "redirect:/login";

        List<JobProfiles> allJobs = jobProfilesRepo.findAll();

        List<JobProfiles> selectedJobs = allJobs.stream()
                .filter(job -> job.getApplicantIds().contains(applicant.getObjectId()))
                .collect(Collectors.toList());

        List<MeetingInfo> bookedMeetings = new ArrayList<>();
        List<JobProfiles> unbookedJobs = new ArrayList<>();

        for (JobProfiles job : selectedJobs) {

            List<MeetingSlot> allocatedSlots = job.getMeetingSlots().stream()
                    .filter(slot -> slot.getCandidateId() != null && slot.getCandidateId().equals(applicant.getObjectId()))
                    .collect(Collectors.toList());
            boolean hasBookedSlot = allocatedSlots.stream()
                    .anyMatch(MeetingSlot::isBooked);

            if (hasBookedSlot) {

                allocatedSlots.stream()
                        .filter(MeetingSlot::isBooked)
                        .forEach(slot -> {
                            MeetingInfo info = new MeetingInfo();
                            info.setJob(job);
                            info.setSlot(slot);
                            bookedMeetings.add(info);
                        });
            } else if (!allocatedSlots.isEmpty()) {
                unbookedJobs.add(job);
            }
        }
        model.addAttribute("bookedMeetings", bookedMeetings);
        model.addAttribute("unbookedJobs", unbookedJobs);
        model.addAttribute("currentApplicantId", applicant.getObjectId().toString());
        model.addAttribute("view", "meetings");
        return "ApplDash";
    }
    @GetMapping("/AllJobs")
    public String Jobs(Model model){
        List<Jobseekers> allusers;
        List<JobProfiles> jobProfiles=jobProfilesRepo.findAll();
        model.addAttribute("jobProfiles",jobProfiles);
        return "ApplDash";
    }
    @PostMapping("/SearchJobs")
    public String SearchJobs(@RequestParam("usertext") String usertext, Model model) {
        List<JobProfiles> jobProfiles = searchbyKeywordsJobs.JobSearch(usertext);

        if (jobProfiles.isEmpty()) {
            model.addAttribute("searchMessage", "No jobs found matching your search criteria: '" + usertext + "'");
        }
        model.addAttribute("jobProfiles", jobProfiles);
        model.addAttribute("isSearch", true);
        return "ApplDash";
    }
}
