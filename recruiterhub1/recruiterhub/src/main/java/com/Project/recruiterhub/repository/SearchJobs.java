package com.Project.recruiterhub.repository;

import com.Project.recruiterhub.model.JobProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Arrays;

import com.Project.recruiterhub.model.Jobseekers;
import com.mongodb.client.MongoClient;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.conversions.Bson;
import java.util.concurrent.TimeUnit;
import org.bson.Document;
import com.mongodb.client.AggregateIterable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Service;

@Service
public class SearchJobs implements SearchbyKeywordsJobs{
    @Autowired
    MongoClient mongoClient;
    @Autowired
    MongoConverter converter;
    @Override
    public List<JobProfiles> JobSearch(String text) {
        String[] keywordsArray = text.split(",");
        List<String> keywords = Arrays.asList(keywordsArray);
        final List<JobProfiles> jobProfilesList = new ArrayList<>();
        MongoDatabase database = mongoClient.getDatabase("RecruiterHub");
        MongoCollection<Document> collection = database.getCollection("JobCollections");
        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search",
                new Document("index", "JobSearch")
                        .append("text",
                                new Document("query", keywords)
                                        .append("path", Arrays.asList("location", "jobtitle", "company", "jobtype", "description", "salary", "recruiter", "skills"))))));
        result.forEach(doc -> jobProfilesList.add(converter.read(JobProfiles.class,doc)));
        return jobProfilesList;
    }
}
