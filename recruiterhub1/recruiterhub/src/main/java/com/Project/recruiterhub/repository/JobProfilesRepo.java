package com.Project.recruiterhub.repository;

import com.Project.recruiterhub.model.JobProfiles;
import com.Project.recruiterhub.model.MeetingSlot;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.stream.Collectors;

public interface JobProfilesRepo extends MongoRepository<JobProfiles, ObjectId> {


    List<JobProfiles> findByRecruiter(ObjectId recruiterId);
    default List<JobProfiles> findByRecruiterAndMeetingSlotsBooked(ObjectId recruiterId) {
        return findByRecruiter(recruiterId).stream()
                .filter(job -> job.getMeetingSlots() != null &&
                        job.getMeetingSlots().stream().anyMatch(MeetingSlot::isBooked))
                .collect(Collectors.toList());
    }
}