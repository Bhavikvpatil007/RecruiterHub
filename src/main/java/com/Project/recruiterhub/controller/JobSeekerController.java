package com.Project.recruiterhub.controller;
import com.Project.recruiterhub.model.JobProfiles;
import com.Project.recruiterhub.model.Jobseekers;
import com.Project.recruiterhub.model.Jobseekerslogin;
import com.Project.recruiterhub.model.WorkExperience;
import com.Project.recruiterhub.repository.JobProfilesRepo;
import com.Project.recruiterhub.repository.PostRepo;
import com.Project.recruiterhub.services.DocxReader;
import com.Project.recruiterhub.services.ExperienceExtractor;
import com.Project.recruiterhub.services.NLPoperations;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.Project.recruiterhub.services.ExperienceExtractor.extractTextFromPDF;

@Controller
public class JobSeekerController {
    @Autowired
    PostRepo repo;
    @Autowired
    NLPoperations nlPoperations;

    @Autowired
    JobProfilesRepo jobProfilesRepo;
    @Autowired
    ExperienceExtractor extractor;
    @PostMapping("/register")
    public String ApplicantRegister(@ModelAttribute Jobseekers users, Model model, @RequestParam("file") MultipartFile file, HttpSession
                                    session) throws IOException, ClassNotFoundException {
        String modelPath = "resume_experience_ner_model.ser.gz";
        CRFClassifier<CoreLabel> classifier = CRFClassifier.getClassifier(modelPath);
        session.setAttribute("CurrentUser",users);
        String text = DocxReader.readDocument(file);
        List<WorkExperience> workExperience=extractor.processResumeText(text, classifier);
        System.out.println(users.toString());
        try {
            if(workExperience ==null){
                users.setHasExperience(false);
                users.setWorkExperienceList(null);
            }
            else{
                users.setHasExperience(true);
                users.setWorkExperienceList(workExperience);
            }
            users.setResumefile(file.getBytes());
            users.setFileContentType(file.getContentType());
            users.setFilename(file.getOriginalFilename());
            List<String> skills = nlPoperations.extractSkills(text);
            users.setSkills(skills);
            repo.save(users);
            List<JobProfiles> jobProfiles=jobProfilesRepo.findAll();
            model.addAttribute("jobProfiles",jobProfiles);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ApplDash";
    }

    @PostMapping("/login")
    public String ApplicantLogin(@ModelAttribute Jobseekerslogin users, Model model,HttpSession session) {
        List<Jobseekers> allusers;
        List<JobProfiles> jobProfiles=jobProfilesRepo.findAll();
        model.addAttribute("jobProfiles",jobProfiles);
        allusers = repo.findAll();
        for (Jobseekers jobseekers : allusers) {
            if (Objects.equals(jobseekers.getEmail(), users.getEmail()) && Objects.equals(jobseekers.getPassword(), users.getPassword())) {
                session.setAttribute("CurrentUser",jobseekers);
                return "ApplDash";

            }
        }
        return "WrongPassSeeker";
    }


}
