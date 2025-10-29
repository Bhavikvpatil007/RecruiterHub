package com.Project.recruiterhub.controller;

import com.Project.recruiterhub.model.*;
import com.Project.recruiterhub.repository.*;
import jakarta.servlet.http.HttpSession;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/meetings")
public class MeetingController {
    @Autowired
    private JobProfilesRepo jobProfilesRepo;
    @Autowired
    private RecruiterRepo recruiterRepo;
    @Autowired
    private PostRepo applicantRepo;
    @PostMapping("/addSlot/{jobId}")
    public String addSlot(
            @PathVariable String jobId,
            @RequestParam String slotDate,
            @RequestParam String slotTime,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Recruiter recruiter = (Recruiter) session.getAttribute("CurrentUser");
        if (recruiter == null) return "redirect:/EmpCred";

        try {
            ObjectId jobObjId = new ObjectId(jobId); // Validate jobId
            JobProfiles job = jobProfilesRepo.findById(jobObjId).orElse(null);

            if (job != null && job.getRecruiter().equals(recruiter.getObjectId())) {
                LocalDateTime dateTime = LocalDateTime.parse(slotDate + "T" + slotTime);

                MeetingSlot slot = new MeetingSlot();
                slot.setStartTime(dateTime);
                slot.setEndTime(dateTime.plusHours(1));
                job.addMeetingSlot(slot);
                jobProfilesRepo.save(job);
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid job ID format");
            return "redirect:/error";
        }

        return "redirect:/meetings/schedule/" + jobId;
    }

    @GetMapping("/available/{jobId}")
    public String viewAvailableSlots(
            @PathVariable String jobId,
            Model model,
            HttpSession session) {

        Jobseekers applicant = (Jobseekers) session.getAttribute("CurrentUser");
        if (applicant == null) return "redirect:/login";

        JobProfiles job = jobProfilesRepo.findById(new ObjectId(jobId)).orElse(null);
        if (job == null) {
            return "redirect:/error";
        }

        // Filter slots specifically for this applicant
        List<MeetingSlot> availableSlots = job.getMeetingSlots().stream()
                .filter(s -> !s.isBooked() &&
                        (s.getCandidateId() == null || s.getCandidateId().equals(applicant.getObjectId())))
                .collect(Collectors.toList());

        model.addAttribute("job", job);
        model.addAttribute("availableSlots", availableSlots);
        return "ApplMeet";
    }
    @GetMapping("/employer")
    public String employerMeetings(Model model, HttpSession session) {
        Recruiter recruiter = (Recruiter) session.getAttribute("CurrentUser");
        if (recruiter == null) return "redirect:/EmpCred";

        List<JobProfiles> jobsWithMeetings = jobProfilesRepo.findByRecruiterAndMeetingSlotsBooked(recruiter.getObjectId());
        model.addAttribute("meetings", jobsWithMeetings);

        return "EmpSchldMeet";
    }
    @GetMapping("/schedule/{jobId}")
    public String showSchedulePage(
            @PathVariable String jobId,
            @RequestParam(required = false) String applicantId,
            Model model,
            HttpSession session) {

        Recruiter recruiter = (Recruiter) session.getAttribute("CurrentUser");
        if (recruiter == null) return "redirect:/EmpCred";

        JobProfiles job = jobProfilesRepo.findById(new ObjectId(jobId)).orElse(null);
        if (job == null || !job.getRecruiter().equals(recruiter.getObjectId())) {
            return "redirect:/dashboard";
        }

        model.addAttribute("job", job);
        model.addAttribute("applicantId", applicantId);
        return "Meeting";
    }
    @PostMapping("/addSlot/{jobId}/{applicantId}")
    public String addSlotForCandidate(
            @PathVariable String jobId,
            @PathVariable String applicantId,
            @RequestParam String slotDate,
            @RequestParam String slotTime,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Recruiter recruiter = (Recruiter) session.getAttribute("CurrentUser");
        if (recruiter == null) return "redirect:/EmpCred";

        try {
            ObjectId jobObjId = new ObjectId(jobId);
            ObjectId applicantObjId = new ObjectId(applicantId);

            JobProfiles job = jobProfilesRepo.findById(jobObjId).orElse(null);
            if (job != null && job.getRecruiter().equals(recruiter.getObjectId())) {
                LocalDateTime dateTime = LocalDateTime.parse(slotDate + "T" + slotTime);

                MeetingSlot slot = new MeetingSlot();
                slot.setStartTime(dateTime);
                slot.setEndTime(dateTime.plusHours(1));
                slot.setCandidateId(applicantObjId);
                job.addMeetingSlot(slot);
                jobProfilesRepo.save(job);
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid ID format");
            return "redirect:/error";
        }

        return "redirect:/meetings/schedule/" + jobId + "?applicantId=" + applicantId;
    }
    @PostMapping("/book")
    public String bookMeeting(
            @RequestParam String jobId,
            @RequestParam String slotTime,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Jobseekers applicant = (Jobseekers) session.getAttribute("CurrentUser");
        if (applicant == null) return "redirect:/login";

        try {
            JobProfiles job = jobProfilesRepo.findById(new ObjectId(jobId)).orElse(null);
            if (job != null) {
                LocalDateTime dateTime = LocalDateTime.parse(slotTime.replace(" ", "T"));

                for (MeetingSlot slot : job.getMeetingSlots()) {
                    if (slot.getStartTime().equals(dateTime) && slot.isAvailableFor(applicant.getObjectId())) {
                        slot.setBooked(true);
                        slot.setBookedBy(applicant.getObjectId());
                        jobProfilesRepo.save(job);
                        redirectAttributes.addFlashAttribute("successMessage", "Meeting booked successfully!");
                        return "redirect:/scheduledMeetings";
                    }
                }
                redirectAttributes.addFlashAttribute("errorMessage", "Slot not available or already booked");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error booking meeting: " + e.getMessage());
        }
        return "redirect:/scheduledMeetings";
    }
}
