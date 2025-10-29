package com.Project.recruiterhub.repository;

import com.Project.recruiterhub.model.Jobseekers;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;


public interface PostRepo extends MongoRepository<Jobseekers, ObjectId>
{

}
