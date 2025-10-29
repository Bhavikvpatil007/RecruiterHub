package com.Project.recruiterhub.repository;

import com.Project.recruiterhub.model.Jobseekers;

import java.util.List;

public interface SearchbyKeyword {
    public List<Jobseekers> searcbykeyword(String text);
    public int ratings(Jobseekers applicant, String text);
}

