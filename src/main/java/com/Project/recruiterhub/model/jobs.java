package com.Project.recruiterhub.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class jobs {
    ObjectId JobId;
    List<ObjectId> ApplicantIds = new ArrayList<>();

    public ObjectId getJobId() {
        return JobId;
    }

    public void setJobId(ObjectId jobId) {
        JobId = jobId;
    }

    public List<ObjectId> getApplicantId() {
        return ApplicantIds;
    }

    public void setApplicantId(List<ObjectId> applicantId) {
        ApplicantIds = applicantId;
    }

    public void addApplicantId(ObjectId applicantId) {
        if (!ApplicantIds.contains(applicantId)) {
            ApplicantIds.add(applicantId);
        }
    }
}
