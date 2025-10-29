package com.Project.recruiterhub.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "JobSeekers")
public class Jobseekers {
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
    String email;
    String phone;
    String password;
    byte[] resumefile;
    String fileContentType;
    List<String> skills;
    int experience;
    Integer ratings=100;

    Boolean HasExperience;

    List<WorkExperience> workExperienceList = new ArrayList<>();

    public Integer getRatings() {
        return ratings;
    }

    public Boolean getHasExperience() {
        return HasExperience;
    }

    public void setHasExperience(Boolean hasExperience) {
        HasExperience = hasExperience;
    }

    public List<WorkExperience> getWorkExperienceList() {
        return workExperienceList;
    }

    public void setWorkExperienceList(List<WorkExperience> workExperienceList) {
        this.workExperienceList = workExperienceList;
    }

    public void setRatings(Integer ratings) {
        this.ratings = ratings;
    }

    private List<ObjectId> appliedJobIds;

    public List<ObjectId> getAppliedJobIds() {
        return appliedJobIds;
    }

    public void setAppliedJobIds(List<ObjectId> appliedJobIds) {
        this.appliedJobIds = appliedJobIds;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public List<String> getSkills() {
        return skills;
    }
    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
    public byte[] getResumefile() {
        return resumefile;
    }

    public void setResumefile(byte[] resumefile) {
        this.resumefile = resumefile;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    String filename;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public Jobseekers() {
    }

    @Override
    public String toString() {
        return "Users{" +
                " firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
