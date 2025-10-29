package com.Project.recruiterhub.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Collection;

public class MeetingSlot {
    private ObjectId id;
    private LocalDateTime startTime;
    private boolean booked;
    private ObjectId bookedBy;
    private ObjectId bookedto;
    private LocalDateTime endTime;
    private ObjectId candidateId;
    public MeetingSlot() {
        this.id = new ObjectId();
    }
    public boolean isBookedBy(ObjectId applicantId) {
        return booked && bookedBy != null && bookedBy.equals(applicantId);
    }

    public boolean isAvailableFor(ObjectId applicantId) {

        return !booked && (candidateId == null || candidateId.equals(applicantId));
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public ObjectId getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(ObjectId candidateId) {
        this.candidateId = candidateId;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public ObjectId getBookedto() {
        return bookedto;
    }

    public void setBookedto(ObjectId bookedto) {
        this.bookedto = bookedto;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public ObjectId getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(ObjectId bookedBy) {
        this.bookedBy = bookedBy;
    }
}
