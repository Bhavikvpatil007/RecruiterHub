package com.Project.recruiterhub.repository;

import com.Project.recruiterhub.model.Jobseekers;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class getApplicantName {
    @Autowired
    PostRepo applicantRepo;
    public String getApplicantName(ObjectId applicantId) {
        Optional<Jobseekers> applicant = applicantRepo.findById(applicantId);
        return applicant.map(a -> a.getFirstname() + " " + a.getLastname()).orElse("Unknown");
    }
}
