package com.Project.recruiterhub.model;

public class WorkExperience {
    String company;
    String position;
    String duration;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public WorkExperience(String company, String position, String duration) {
        this.company = company;
        this.position = position;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Company: " + (company != null ? company : "") + "\n" +
                "Position: " + (position != null ? position : "") + "\n" +
                "Duration: " + (duration != null ? duration : "") + "\n" +
                "-------------------------------";
    }
}
