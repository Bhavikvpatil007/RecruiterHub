package com.Project.recruiterhub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class AboutUsController {
    @GetMapping("About")
    public String AboutUs(){
        return "AboutUs";
    }
}
