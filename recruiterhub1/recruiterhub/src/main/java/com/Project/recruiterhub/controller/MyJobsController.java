package com.Project.recruiterhub.controller;

import com.Project.recruiterhub.model.*;
import com.Project.recruiterhub.repository.JobProfilesRepo;
import com.Project.recruiterhub.repository.PostRepo;
import com.Project.recruiterhub.repository.RecruiterRepo;
import com.Project.recruiterhub.repository.SearchbyKeyword;
import jakarta.servlet.http.HttpSession;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
@Controller
public class MyJobsController {
    @Autowired
    JobProfilesRepo jobProfilesRepo;
    @Autowired
    PostRepo repo;
    @Autowired
    SearchbyKeyword searchbyKeyword;
    @Autowired
    RecruiterRepo recruiterRepo;
    @GetMapping("/deletejob/{jobId}")
    public  String DeleteJob(@PathVariable String jobId, Model model, HttpSession session){
        Recruiter recruiter = (Recruiter) session.getAttribute("CurrentUser");
        ObjectId jobObjectId = new ObjectId(jobId);
        jobProfilesRepo.deleteById(jobObjectId);
        recruiter.deleteJobfromRecruiter(jobObjectId);
        recruiterRepo.save(recruiter);
        List<JobProfiles> jobs = jobProfilesRepo.findByRecruiter(recruiter.getObjectId());
        Optional<Recruiter> updatedRecruiter = recruiterRepo.findById(recruiter.getObjectId());
        if (updatedRecruiter.isPresent()) {
            model.addAttribute("recruiter", updatedRecruiter.get());
            model.addAttribute("jobs", jobs);
            session.setAttribute("CurrentUser", updatedRecruiter.get());
        }
        return "MyJobs";
    }
    @GetMapping("/viewApplicants/{jobId}")
    public String viewApplicants(@PathVariable String jobId, Model model, HttpSession session) {
        Recruiter recruiter = (Recruiter) session.getAttribute("CurrentUser");
        if (recruiter == null) return "redirect:/EmpCred";

        try {
            ObjectId jobObjId = new ObjectId(jobId);
            JobProfiles job = jobProfilesRepo.findById(jobObjId).orElse(null);

            if (job != null) {
                List<Jobseekers> applicants = new ArrayList<>();
                for (ObjectId applicantId : job.getApplicantIds()) {
                    Optional<Jobseekers> applicant = repo.findById(applicantId);
                    applicant.ifPresent(applicants::add);
                }
                for(Jobseekers jobseekers : applicants){
                    jobseekers.setRatings(searchbyKeyword.ratings(jobseekers,job.getSkills()));
                }
                model.addAttribute("job", job);
                model.addAttribute("applicants", applicants);
                return "ViewApplNew";
            }
        } catch (IllegalArgumentException e) {

        }

        model.addAttribute("job", new JobProfiles());
        model.addAttribute("applicants", Collections.emptyList());
        return "ViewApplNew";
    }

    @GetMapping("/ScheduledMeeting/{jobId}")
    public String ScheduledMeetings(@PathVariable String jobId, HttpSession session, Model model) {
        Recruiter recruiter = (Recruiter) session.getAttribute("CurrentUser");
        if (recruiter == null) return "redirect:/EmpCred";

        try {
            ObjectId ObjectJobId = new ObjectId(jobId);
            Optional<JobProfiles> job = jobProfilesRepo.findById(ObjectJobId);
            if (job.isPresent()) {
                JobProfiles jobProfiles = job.get();
                List<MeetingSlot> meetingSlots = jobProfiles.FindMeeting(true);
                List<MeetingofApplicant> applicants = new ArrayList<>();

                System.out.println("Found " + meetingSlots.size() + " booked meeting slots"); // Debug

                for (MeetingSlot slot : meetingSlots) {
                    System.out.println("Slot booked by: " + slot.getBookedBy()); // Debug
                    System.out.println("Start time: " + slot.getStartTime()); // Debug

                    MeetingofApplicant meeting = new MeetingofApplicant();
                    Optional<Jobseekers> jobseeker = repo.findById(slot.getBookedBy());

                    if (jobseeker.isPresent()) {
                        Jobseekers applicant = jobseeker.get();
                        meeting.setApplicantName(applicant.getFirstname() + " " + applicant.getLastname());
                        meeting.setDate(slot.getStartTime().toLocalDate());
                        meeting.setTime(slot.getStartTime().toLocalTime());
                        applicants.add(meeting);

                        System.out.println("Created meeting for: " + meeting.getApplicantName()); // Debug
                    }
                }

                model.addAttribute("Applicants", applicants);

                return "EmpSchldMeet";
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid job ID format: " + e.getMessage()); // Debug
        }

        model.addAttribute("Applicants", Collections.emptyList());
        return "EmpSchldMeet";
    }

}