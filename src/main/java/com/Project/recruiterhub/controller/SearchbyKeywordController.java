package com.Project.recruiterhub.controller;

import com.Project.recruiterhub.model.Jobseekers;
import com.Project.recruiterhub.repository.PostRepo;
import com.Project.recruiterhub.repository.SearchbyKeyword;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class SearchbyKeywordController {
    @Autowired
    SearchbyKeyword searchbyKeyword;
    @Autowired
     PostRepo repo;
    @PostMapping("/search")
    public String searchApplicants(@RequestParam("usertext") String usertext, Model model) {
        List<Jobseekers> applicants = searchbyKeyword.searcbykeyword(usertext);
        for(Jobseekers applicant : applicants){
            applicant.setRatings(searchbyKeyword.ratings(applicant,usertext));
            repo.save(applicant);
        }
        model.addAttribute("applicants", applicants);
        model.addAttribute("isSearchResult", true);
        model.addAttribute("searchTerm", usertext);
        return "SrchByKw";
    }
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadResume(@PathVariable String id) {
        ObjectId objectId = new ObjectId(id);
        Optional<Jobseekers> applicants = repo.findById(objectId);

        if (applicants.isPresent()) {
            Jobseekers user = applicants.get();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(user.getFileContentType()));

            ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                    .filename(user.getFilename())
                    .build();
            headers.setContentDisposition(contentDisposition);

            return new ResponseEntity<>(user.getResumefile(), headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}