package com.Project.recruiterhub.repository;

import com.Project.recruiterhub.model.JobProfiles;
import com.Project.recruiterhub.model.Jobseekers;

import java.util.List;

public interface SearchbyKeywordsJobs {
    public List<JobProfiles> JobSearch(String text);
}
