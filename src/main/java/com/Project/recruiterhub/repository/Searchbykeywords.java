package com.Project.recruiterhub.repository;

import com.Project.recruiterhub.model.Jobseekers;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Arrays;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.AggregateIterable;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class Searchbykeywords implements SearchbyKeyword{
    @Autowired
    MongoClient mongoClient;
    @Autowired
    MongoConverter converter;
    @Override
    public List<Jobseekers> searcbykeyword(String text) {
        String[] keywordsArray = text.split(",");
        List<String> keywords = Arrays.asList(keywordsArray);
        final List<Jobseekers> Applicant=new ArrayList<>();
        MongoDatabase database = mongoClient.getDatabase("RecruiterHub");
        MongoCollection<Document> collection = database.getCollection("JobSeekers");
        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search",
                        new Document("index", "default")
                                .append("text",
                                        new Document("query", keywords)
                                                .append("path", "skills"))),
                new Document("$sort",
                        new Document("field1", -1L))));
        result.forEach(doc -> Applicant.add(converter.read(Jobseekers.class,doc)));
        return Applicant;
    }
    @Override
    public int ratings(Jobseekers applicant, String text){
        int ratings;
        int skillsfound=0;
        String[] keywords = Arrays.stream(text.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .toArray(String[]::new);
        List<String> skills =applicant.getSkills();
        for(int i=0;i< keywords.length;i++){
            if(skills.contains(keywords[i])){
                skillsfound++;
            }
        }
        ratings = (int)(((double)skillsfound / keywords.length) * 100);
        return ratings;
    }
}
