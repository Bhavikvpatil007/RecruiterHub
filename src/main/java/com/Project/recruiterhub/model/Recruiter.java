package com.Project.recruiterhub.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Recruiters")

public class Recruiter {
    @Id
    ObjectId objectId;

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }
    String firstname;
    String lastname;
    String phone;
    String email;
    String Password;
    List<jobs> createdjobs = new ArrayList<>(); // Initialize the list here

    public void addjobs(jobs job) {
        if (job != null) {
            createdjobs.add(job);
        }
    }
    public void  deleteJobfromRecruiter(ObjectId id){
        for(jobs job : createdjobs){
            if(job.getJobId()==id){
                createdjobs.remove(job);
            }
        }
    }
    public List<jobs> getCreatedjobs() {
        return createdjobs;
    }

    public void setCreatedjobs(List<jobs> createdjobs) {
        this.createdjobs = createdjobs;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


}
