package com.Project.recruiterhub.repository;

import com.Project.recruiterhub.model.Recruiter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RecruiterRepo extends MongoRepository<Recruiter, ObjectId> {
}
