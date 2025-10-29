package com.Project.recruiterhub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostJobConfigurationController {
    @GetMapping("/ReturnDashboard")
    public String ReturnDashboard(){
        return "Recruiter Dashboard";
    }
    @GetMapping("/ReturnPostJob")
    public String ReturnJobPosting(){
        return "PostJob";
    }
}
