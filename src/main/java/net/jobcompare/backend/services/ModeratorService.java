package net.jobcompare.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import net.jobcompare.backend.entities.Job;
import net.jobcompare.backend.entities.Moderator;
import net.jobcompare.backend.repositories.JobRepository;
import net.jobcompare.backend.repositories.ModeratorRepository;


@Service
@Transactional
public class ModeratorService {

    private ModeratorRepository moderatorRepository;
    private JobRepository jobRepository;

    @Autowired
    public ModeratorService(ModeratorRepository moderatorRepository, JobRepository jobRepository){
        this.moderatorRepository = moderatorRepository;
        this.jobRepository = jobRepository;
    }

    // API endpoint to retrieve all moderators in the system
    public List<Moderator> getAllModerators(){
        return moderatorRepository.findAll();
    }

    // return one moderator by its unique 'modId'
    public Moderator findByModeratorId(Integer modId){
        Optional<Moderator> modOptional = moderatorRepository.findById(modId);
        if(modOptional.isPresent()){
            Moderator mod = modOptional.get();
            return mod;
        }
        return null;
    }

    // retrieve all job offerings under specific 'modId'
    public List<Job> findAllByModId(Integer modId){
        // initialize 'jobList' List variable to gather up all 'Job' records associated to given 'modId'
        List<Job> jobList = jobRepository.findByModerator_ModId(modId);
        // return List of 'Job' objects 
        return jobList;
    }

    // create a 'Moderator'
    public Moderator createModerator(Moderator moderator){
        return moderatorRepository.save(moderator);
    }
}
