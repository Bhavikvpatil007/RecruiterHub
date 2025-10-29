package com.Project.recruiterhub.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomePageController {
    @GetMapping("/")
    public String Landing(){
        return "Landing";
    }
    @GetMapping("/Jobseeker")
    public String ReDirectJobSeeker(){
        return "SeekCred";
    }

    @GetMapping("/Recruiter")
    public String ReDirectRecruiter(){
        return "EmpCred";
    }

    @PostMapping("/Recruiter")
    public String ReLoginRecruiter() { return "EmpCred"; }

    @PostMapping("/Jobseeker")
    public String ReLoginJobseeker() { return "SeekCred"; }
}
