package net.jobcompare.backend.services;

// import entity classes
import net.jobcompare.backend.entities.Job;

//
import net.jobcompare.backend.repositories.JobRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
// utilize when returning all job offerings
import java.util.List;
// import 'java.util.Optional' class to safely deal w/ (or avoid) 'NullPointerException'
import java.util.Optional;

// '@Service' annotation instructs Spring container to create Spring bean for 'JobService' class
@Service
@Transactional
public class JobService {
    // declare an instance variable 'jobRepository'
    // constructor injection of mandatory dependencies
    private JobRepository jobRepository;

    @Autowired
    // constructor instantiating above instance variable 'JobRepository' by taking in an 'JobRepository' obj
    /* introduce 'JobRepository' obj in 'JobRepository' obj -- enables 'UserService' class to use 'JobRepository' functionalities */
    public JobService(JobRepository jobRepository){
        /* reserved keyword 'this' points to earlier declared instance variable (outside this constructor) ...
        & assign given parameter 'jobRepository' value to it */
        this.jobRepository = jobRepository;
    }

    // API should be able to retrieve all job offerings
    public List<Job> getAllJobs(){
        // employ built-in .findAll() method from CrudRepository (extends from JpaRepository) to find all 'Job' object entities/records in 'Job' DB table
        return jobRepository.findAll();
    }

    // retrieve  one job offering under 'jobId'
    public Job findByJobId(Integer jobId){
        // call .findById() method to retrieve 'Job' entity/record from DB fitting given parameter 'jobId'
        Optional<Job> jobOptional = jobRepository.findById(jobId);
        // if value assigned to 'jobOptional' container obj is of a non-null nature ...
        if(jobOptional.isPresent()){
            // grab the value via .get() method from 'java.util.Optional' package
            Job job = jobOptional.get();
            // return 'Job' entity
            return job;
        }
        // otw if the value from 'jobOptional' is indeed NULL ... return default falsy value (null)
        return null;
    }

    // retrieve all job offerings under specific 'modId'
    public List<Job> getAllJobsByModId(Integer modId){
        // initialize 'jobList' List variable to gather up all 'Job' records associated to given 'modId'
        List<Job> jobList = jobRepository.findByModId(modId);
        // return List of 'Job' objects 
        return jobList;
    }

    // create a 'Job' 
    public Job createJob(Job job) throws Exception{
        // initialization block for all the schema fields
        String jobTitle = job.getTitle();
        String jobCompany = job.getCompany();
        String jobLocation = job.getLocation();
        String jobDescription = job.getDescription();
        String jobEmail = job.getHiringTeamEmail();

        Integer yearOfExperience = job.getYearOfExperience();
        Integer costOfLiving = job.getCostOfLivingIndex();
        // Integer moderatorId = job.getModId();

        // Long timeOfPosting = job.getTimeOfPosting();
        Float yearlySalary = job.getYearlySalary();

        // if job description fields does NOT contain anything ...
        if(jobTitle.isEmpty() || jobCompany.isEmpty() || jobLocation.isEmpty() || jobDescription.isEmpty() || jobEmail.isEmpty()){
            throw new Exception("Schema field is empty");
        }
        
        // else if any of the core job fields are above the character limit ...
        if(jobTitle.length() > 60 || jobCompany.length() > 20|| jobLocation.length() > 85 || jobDescription.length() > 4000 || jobEmail.length() > 60){
            throw new Exception("Schema field exceeds length");
        }

        // else if 'jobId' DNE ...
        /* EQUIVALENT to 'if(messageOptional.isEmpty()){...}' logic-wise, req. Optional to be declare earlier*/
        if(!jobRepository.existsById(job.getJobId())){  // job.getModId()
            throw new Exception("jobId field DNE");
        }

        // create an 'Optional' obj
        // call .findById() method to retrieve 'Job' record from DB fitting given parameter 'jobId'
        Optional<Job> jobOptional = jobRepository.findById(job.getJobId());
        // if value assigned to 'jobOptional' container obj's value is NON-null
        if(jobOptional.isPresent()){
            // grab the value via .get() method from 'java.util.Optional' package
            Job position = jobOptional.get();
            // persist changes to 'Job' DB table
            jobRepository.save(position);
            return position;
        }
        // otw if value from 'jobOptional' is indeed NULL ... return default falsy value (null)
        return null;
    }

    // public Optional<Job> updateJob(Integer jobId, String title, String description, String company, String location, 
    // Integer yearOfExperience, Integer costOfLivingIndex, Float yearlySalary, String hiringTeamEmail, Long timeOfPosting, Integer modId){
    //     // if find the 'Job' obj via 'jobId'
    //     if(jobRepository.findById(jobId).isPresent()){
    //         if()
    //     }
    // }

    public Job updateByJobId(Integer jobId, Job job) throws Exception{
        // initialization block for all the schema fields
        String jobTitle = job.getTitle();
        String jobCompany = job.getCompany();
        String jobLocation = job.getLocation();
        String jobDescription = job.getDescription();
        String jobEmail = job.getHiringTeamEmail();

        Integer yearOfExperience = job.getYearOfExperience();
        Integer costOfLiving = job.getCostOfLivingIndex();
        // Integer moderatorId = job.getModId();

        // Long timeOfPosting = job.getTimeOfPosting();
        Float yearlySalary = job.getYearlySalary();

        // if job description fields does NOT contain anything ...
        if(jobTitle.isEmpty() || jobCompany.isEmpty() || jobLocation.isEmpty() || jobDescription.isEmpty() || jobEmail.isEmpty()){
            throw new Exception("Schema field is empty [UPDATE]");
        }
        
        // else if any of the core job fields are above the character limit ...
        if(jobTitle.length() > 60 || jobCompany.length() > 20|| jobLocation.length() > 85 || jobDescription.length() > 4000 || jobEmail.length() > 60){
            throw new Exception("Schema field exceeds length [UPDATE]");
        }

        // else if 'jobId' DNE ...
        /* EQUIVALENT to 'if(jobOptional.isEmpty()){...}' logic-wise, req. Optional to be declare earlier*/
        if(!jobRepository.existsById(job.getJobId())){  // job.getModId()
            throw new Exception("jobId field DNE [UPDATE]");
        }

        // create an 'Optional' obj
        // call .findById() method to retrieve 'Job' record from DB fitting given parameter 'jobId'
        Optional<Job> jobOptional = jobRepository.findById(job.getJobId());
        // if value assigned to 'jobOptional' container obj's value is NON-null
        if(jobOptional.isPresent()){
            // grab the value via .get() method from 'java.util.Optional' package
            Job position = jobOptional.get();
            // extract 'description' field from provided 'Job' arg & use setter function to overwrite old 'description'
            position.setDescription(job.getDescription());
            // persist changes to 'Job' DB table
            jobRepository.save(position);
            // tying up loose ends 
            jobRepository.deleteById(jobId);
            // return updated job offering
            return position;    // return 1
        }
        // otw if value from 'jobOptional' is indeed NULL ... return default falsy value (null)
        return null;
    }

    public Integer deleteJob(Integer jobId){    // void
        // call .findById() method to retrieve 'Job' entity/record from DB fitting given parameter 'jobId'
        Optional<Job> jobOptional = jobRepository.findById(jobId);
        // if value assigned to 'jobOptional' container obj is of a non-null nature ...
        if(jobOptional.isPresent()){
            // use JpaRepository's .deleteById() method to remove 'Job' entity associated w/ passed in 'jobId'
            jobRepository.deleteById(jobId);
            // return 1 as wished (represents number of entity changed from this deletion process)
            return 1;
        }
        // otw if the value from 'jobOptional' is indeed NULL ... return default falsy value (null)
        return null;
    }
}
