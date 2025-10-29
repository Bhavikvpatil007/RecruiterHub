package com.Project.recruiterhub.services;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NLPoperations {
    private static final Set<String> SKILLS_SET = new HashSet<>(Arrays.asList(
            "java", "python", "machine learning", "mysql", "docker", "javascript",
            "spring boot", "aws", "react", "c++", "data analysis", "project management", "dbms", "reactjs",
            "c","rust","angular","php","hibernate","mongodb","html","css","ml"
    ));
    @Autowired
    private StanfordCoreNLP stanfordCoreNLP;
    public List<String> extractSkills(String resume) {
        if (stanfordCoreNLP == null) {
            throw new IllegalStateException("StanfordCoreNLP bean was not injected!");
        }
        CoreDocument document = new CoreDocument(resume);
        stanfordCoreNLP.annotate(document);
        Set<String> extractedSkills = new HashSet<>();

        List<CoreLabel> tokens = document.tokens();
        int tokenSize = tokens.size();
        for (int start = 0; start < tokenSize; start++) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int end = start; end < tokenSize; end++) {
                String lemma = tokens.get(end).lemma().toLowerCase();
                if (!stringBuilder.isEmpty()) {
                    stringBuilder.append(" ");
                }
                stringBuilder.append(lemma);
                if (SKILLS_SET.contains(stringBuilder.toString())) {
                    extractedSkills.add(stringBuilder.toString());
                }
            }
        }
        return new ArrayList<>(extractedSkills);
    }
}

