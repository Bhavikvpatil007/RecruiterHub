package com.Project.recruiterhub.model;

public class MeetingInfo {
    private JobProfiles job;
    private MeetingSlot slot;


    public JobProfiles getJob() {
        return job;
    }

    public void setJob(JobProfiles job) {
        this.job = job;
    }

    public MeetingSlot getSlot() {
        return slot;
    }

    public void setSlot(MeetingSlot slot) {
        this.slot = slot;
    }
}
