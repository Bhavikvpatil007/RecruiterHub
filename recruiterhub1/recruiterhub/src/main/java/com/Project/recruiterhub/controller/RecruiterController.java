package com.Project.recruiterhub.controller;

import com.Project.recruiterhub.model.Recruiter;
import com.Project.recruiterhub.model.Recruiterlogin;
import com.Project.recruiterhub.repository.RecruiterRepo;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Objects;

@Controller
public class RecruiterController {
    @Autowired
    RecruiterRepo repo;
    @RequestMapping("/emp")
    public String EmpCred(){
        return "EmpCred";
    }
    @PostMapping("/RecRegister")
    public String EmpRegister(@ModelAttribute Recruiter recruiter, Model model, HttpSession session){
        session.setAttribute("CurrentUser",recruiter);
        repo.save(recruiter);
        return "Recruiter Dashboard";
    }
    @PostMapping("/RecLogin")
    public String EmpLogin(@ModelAttribute Recruiterlogin recruiterlogin,Model model,HttpSession session){
        List<Recruiter> allRecruiters=repo.findAll();
        for(Recruiter recruiter : allRecruiters){
            if(Objects.equals(recruiter.getEmail()  , recruiterlogin.getEmail()) && Objects.equals(recruiter.getPassword(), recruiterlogin.getPassword())){
                session.setAttribute("CurrentUser",recruiter);
                return "Recruiter Dashboard";
            }
        }
        return "WrongPassRecruiter";
    }
}
