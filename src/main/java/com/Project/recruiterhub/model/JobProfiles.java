package com.Project.recruiterhub.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Document(collection = "JobCollections")
public class JobProfiles {
    @Id
    ObjectId objectId;

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    String jobtitle;
    String company;
    String location;
    String jobtype;
    String salary;
    String description;
    ObjectId recruiter;
    String skills;

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    private List<MeetingSlot> meetingSlots = new ArrayList<>();
    public void addMeetingSlot(MeetingSlot slot) {
        this.meetingSlots.add(slot);
    }

    public List<MeetingSlot> getMeetingSlots() {
        return meetingSlots;
    }

    public void setMeetingSlots(List<MeetingSlot> meetingSlots) {
        this.meetingSlots = meetingSlots;
    }

    public ObjectId getRecruiter() {
        return recruiter;
    }

    public void setRecruiter(ObjectId recruiter) {
        this.recruiter = recruiter;
    }
    private List<ObjectId> applicantIds = new ArrayList<>();
    public List<ObjectId> getApplicantIds() {
        return applicantIds;
    }

    public void setApplicantIds(List<ObjectId> applicantIds) {
        this.applicantIds = applicantIds;
    }

    public void addApplicant(ObjectId applicantId) {
        if (!applicantIds.contains(applicantId)) {
            applicantIds.add(applicantId);
        }
    }

    public List<MeetingSlot> FindMeeting(boolean a) {
        return meetingSlots.stream()
                .filter(slot -> slot.isBooked() == a)
                .collect(Collectors.toList());
    }
    public void deleteApplicant(ObjectId applicanId){
        applicantIds.remove(applicanId);
    }

    public String getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getJobtype() {
        return jobtype;
    }

    public void setJobtype(String jobtype) {
        this.jobtype = jobtype;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
