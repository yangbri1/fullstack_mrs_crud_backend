/* 'JobService' service layer contains the business logic  */
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
    public List<Job> findByModId(Integer modId){
        // initialize 'jobList' List variable to gather up all 'Job' records associated to given 'modId'
        List<Job> jobList = jobRepository.findByModId(modId);
        // return List of 'Job' objects 
        return jobList;
    }

    // create a 'Job' after validating the input fields & .save() to DB table
    public Job createJob(Job job) throws Exception{
        // initialization block for all the schema fields ... NOT really needed as helper function 'validateJobFields()' takes whole 'job' as argument
        // String jobTitle = job.getTitle();
        // String jobCompany = job.getCompany();
        // String jobLocation = job.getLocation();
        // String jobDescription = job.getDescription();
        // String jobEmail = job.getHiringTeamEmail();

        // Integer yearOfExperience = job.getYearOfExperience();
        // Integer costOfLiving = job.getCostOfLivingIndex();
        // Integer moderatorId = job.getModId();

        // Long createdOn = job.getCreatedOn();
        // Float yearlySalary = job.getYearlySalary();

        // call 'validateJobFields()' helper method to validate user inputted fields
        validateJobFields(job);
        
        /* OMITTED since 'jobId' key generation will be automatic from Spring (we want this!) */
        // else if 'jobId' DNE ...
        /* EQUIVALENT to 'if(jobOptional.isEmpty()){...}' logic-wise, req. Optional to be declare earlier*/
        // if(!jobRepository.existsById(job.getJobId())){  // job.getModId()
        //     throw new Exception("jobId field DNE");
        // }

        // create an 'Optional' obj
        // call .findById() method to retrieve 'Job' record from DB fitting given parameter 'jobId'
        // Optional<Job> jobOptional = jobRepository.findById(job.getJobId());
        // if value assigned to 'jobOptional' container obj's value is NON-null
        // if(jobOptional.isPresent()){
        //     // grab the value via .get() method from 'java.util.Optional' package
        //     Job position = jobOptional.get();
        //     // persist changes to 'Job' DB table
        //     jobRepository.save(position);
        //     return position;
        // }
        // // otw if value from 'jobOptional' is indeed NULL ... return default falsy value (null)
        // return null;
        // .save() given fields into 'Job' DB table
        return jobRepository.save(job);
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
        Integer moderatorId = job.getModId();

        // Long createdOn = job.getCreatedOn();
        Float yearlySalary = job.getYearlySalary();

        // invoke helper function to validate 'Job' fields
        validateJobFields(job);
        
        // create an 'Optional' obj instance        
        // call .findById() method to retrieve 'Job' record from DB fitting given parameter 'jobId'
        Optional<Job> jobOptional = jobRepository.findById(jobId);
        // if 'jobId' DNE ...
        if(jobOptional.isEmpty()){ // ' logic-wise, req. Optional to be declare earlier 
        // if(!jobRepository.existsById(job.getJobId())){  // job.getModId()
            throw new Exception("jobId(" + jobId + ") field DNE [UPDATE]");
        }
        
        // else if value assigned to 'jobOptional' container obj's value is NON-null
        /* aka 'jobId' does EXIST ... below if-statement could be omitted */
        if(jobOptional.isPresent()){
            // grab the value via .get() method from 'java.util.Optional' package
            Job position = jobOptional.get();
            // extract 'description' field from provided 'Job' arg & use setter function to overwrite old 'description'
            position.setDescription(job.getDescription());
            position.setTitle(jobTitle);
            position.setCompany(jobCompany);
            position.setLocation(jobLocation);
            position.setHiringTeamEmail(jobEmail);
            position.setYearOfExperience(yearOfExperience);
            position.setCostOfLivingIndex(costOfLiving);
            position.setModId(moderatorId);
            // position.setCreatedOn(createdOn);
            position.setYearlySalary(yearlySalary);

            // persist changes to 'Job' DB table
            jobRepository.save(position);
            // tying up loose ends 
            // jobRepository.deleteById(jobId);
            // return updated job offering
            return position;    // return 1
        }
        // otw if value from 'jobOptional' is indeed NULL ... return default falsy value (null)
        return null;
    }

    public Integer deleteByJobId(Integer jobId){    // void
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
        return 0;
    }

    // helper function for 'Job' field validation (presented in both 'createJob()' & 'updateByJobId()' methods)
    public void validateJobFields(Job job) throws Exception{
        // initialzation block
        String jobTitle = job.getTitle();
        String jobCompany = job.getCompany();
        String jobLocation = job.getLocation();
        String jobDescription = job.getDescription();
        String jobEmail = job.getHiringTeamEmail();

        // if job description fields does NOT contain anything ...
        if(jobTitle.isEmpty() || jobCompany.isEmpty() || jobLocation.isEmpty() || jobDescription.isEmpty() || jobEmail.isEmpty()){
            throw new Exception("Schema field is empty ");
        }
        
        // else if any of the core job fields are above the character limit ...
        if(jobTitle.length() > 60 || jobCompany.length() > 20|| jobLocation.length() > 85 || jobDescription.length() > 4000 || jobEmail.length() > 60){
            throw new Exception("Schema field exceeds length ");
        }
        
    }
}
