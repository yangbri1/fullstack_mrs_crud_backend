/* establish RESTful API endpoints as a way to expose the backend to frontend */
package net.jobcompare.backend.controllers;

// import 'Job.java' & ... classes from 'entities' directory
import net.jobcompare.backend.entities.*;
import net.jobcompare.backend.entities.Job.WorkArrangement;
import net.jobcompare.backend.repositories.*;
import net.jobcompare.backend.services.*;

// import relevant annotations from Spring framework to help create RESTful API endpoints
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;    // for retrieval of work assignment (filter way)
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// import built-in Java 'List' data structure for later retrieval of many job offerings
import java.util.List;
// import Java 'Operational' class to deal w/ null exceptions
// import java.util.Optional;

@RestController     /* Aside: '@RestController' annotation introduced in v4 is a combination of '@RequestBody' & '@Controller' (@RequestBody by default) */
@CrossOrigin(origins = "http://localhost:5173")
public class JobController {
    
    /* practice separation of concerns by strictly keeping the business logic to 'JobService' service layer */
    // @Autowired
    // private JobRepository jobRepository;    // to be able to use built-in basic CRUD operations from JpaRepository class
    @Autowired
    private JobService jobService;          // declare instance variable 'jobRepository' to use custom methods, fields, etc.

    @Autowired
    private ModeratorService moderatorService;

    // mapping to 'http://localhost:8080/jobs' endpoint to retrieve all job offerings
    @GetMapping("/jobs")
    /* generics <> used here as ResponseEntity is a raw type to reference to generic type */
    public ResponseEntity<List<Job>> getAllJobs(){
        // List<Job> allJobs = jobRepository.findAll();
        /* OMITTED above as it calls on 'JobRepository' (it works but we want to practice SOC), instead we call on 'JobService' service layer */
        List<Job> allJobs = jobService.getAllJobs();
        // return a ResponseEntity w/ custom response status code 200 & response body containing 'Job' obj
        // return ResponseEntity.status(200).body(job);    // .body("Extraction Complete");
        return ResponseEntity.ok(allJobs); // .ok() == HTTP status code 200
    }

    // retrieve a job offering by its particular ID
    @GetMapping("/jobs/{jobId}")
    public ResponseEntity<Job> getJobById(@PathVariable Integer jobId){
        Job position = jobService.findByJobId(jobId);
        // if attempt to fetch a 'job' fails ...
        if(position == null){
            // return HTTP status of 404 (NOT FOUND) & w/ a null response body
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        // otw if attempt was successful, return HTTP status code of 200 w/ found job
        return ResponseEntity.ok(position);
    }

    // output all job offerings published under a certain moderator (modId)
    // @GetMapping("/moderators/{modId}/jobs")
    // public ResponseEntity<List<Job>> findAllJobsByModId(@PathVariable Integer modId){
    //     List<Job> allJobsByMod = moderatorService.findByModeratorId(modId);
    //     return ResponseEntity.ok(allJobsByMod);
    // }

    // // dedicated API endpoint for only 'ONSITE' work arrangement
    // @GetMapping("/jobs/work_arrangement/onsite")
    // public ResponseEntity<List<Job>> findAllOnsite(){
    //     List<Job> onsitePositions = jobService.getAllOnsite();
    //     return ResponseEntity.ok(onsitePositions);
    // }

    // // dedicated API endpoint for only 'REMOTE' work arrangement
    // @GetMapping("/jobs/work_arrangement/remote")
    // public ResponseEntity<List<Job>> findAllRemote(){
    //     List<Job> remotePositions = jobService.getAllRemote();
    //     return ResponseEntity.ok(remotePositions);
    // }

    // // dedicated API endpoint for only 'HYBRID' work arrangement
    // @GetMapping("/jobs/work_arrangement/hybrid")
    // public ResponseEntity<List<Job>> findAllHybrid(){
    //     List<Job> hybridPositions = jobService.getAllHybrid();
    //     return ResponseEntity.ok(hybridPositions);
    // }

    // dyanmic API endpoint approach --- ex. 'http://localhost:8080/jobs/work_arrangement/hybrid'
    // this essentially the above 3 dedicated API endpoint consolidated into 1
    @GetMapping("/jobs/work_arrangement/{workArrangement}")
    public ResponseEntity<List<Job>> findByWorkArrangement(@PathVariable WorkArrangement workArrangement) {
        List<Job> filteredPositions = jobService.findByWorkArrangement(workArrangement);
        return ResponseEntity.ok(filteredPositions);
    }

    // alternative filter way to retrieve work arrangement type
    // @GetMapping("/jobs/filter")
    // public ResponseEntity<List<Job>> findByWorkArrangement(@RequestParam("work_arrangement") WorkArrangement workArrangement){
    //     // ex. HTTP GET Request: 'http://localhost:8080/jobs/filter?work_arrangement=HYBRID' -> returns 'HYBRID' job offerings
    //     try {
    //         // call 'findWorkArrangement()' from 'JobService' service layer to retrieve a List of job offerings
    //         List<Job> filteredPositions = jobService.findByWorkArrangement(workArrangement);
    //         return new ResponseEntity<>(filteredPositions, HttpStatus.CREATED);
    //     } catch (Exception e) {
    //         // if any exception arises, return status code 400 w/ null in response body
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    //     }
    // }

    // POST a new job offering
    @PostMapping("/jobs")
    public ResponseEntity<Job> newJob(@RequestBody Job job){    // '@RequestBody' annotation extracts the JSON from HTTP request & conver it to 'Job' obj
        // try-catch block for error handling the unhandled Exceptions (500) 
        try {
            // attempt to add new job offering via .createJob() method in 'JobService.java' class
            Job position = jobService.createJob(job);
            // return newly added job w/ status code 200 (OK)
            // return ResponseEntity.ok(position);
            // return newly added job w/ status code 201 (CREATED) --- above works too
            return new ResponseEntity<>(position, HttpStatus.CREATED);
            // catch any thrown Exceptions ...
        } catch (Exception e) {
            // If any exception occurs, return a 400 HTTP response code (Bad Request)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // API endpoint exposed to front-end allowing update of job offering info identified by its unique 'jobId' (generated by Spring)
    @PatchMapping("/jobs/{jobId}")
    public ResponseEntity<Job> updateJobByJobId(
        @PathVariable Integer jobId,    // '@PathVariable' annotation will bind to simpler datatypes like String, Integer, Long, etc. from Path URL (not custom objects ex. 'Job', 'Account')
        @RequestBody Job job){          // '@RequestBody' annotation allows custom datatypes; data to travel as JSON String, XML, instead of as Path Variable
        // 'Job job' --- aka backend expects entire 'Job' obj
        try{
            Job updatedJob = jobService.updateByJobId(jobId, job);
            // return HTTP status code 200 to be idempotent regardless if an actual entity was removed or not from 'Job' DB table & result from this deletion operation
            return ResponseEntity.ok(updatedJob); 
            // another way would be to split this up into an if-else flow control statement where if condition 'entityNumchanged == 1' run above, otw if else run above w/ .build()[no body for response entity] concatenated afterwards
            // return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(Exception e){
            // e.getMessage();
            // return new ResponseEntity.status(400).body(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    // API endpoint for deleting a job offering by its 'jobId'
    @DeleteMapping("/jobs/{jobId}")
    public ResponseEntity<Integer> deleteJobByJobId(@PathVariable Integer jobId){
        /* Before use: arg need to be of 'Integer' Complex object datatype matching what was initialized in 'Job.java' entity for 'jobId' ('Job' @Id) JpaRepository to <Job, Integer> 
        -- otw different datatypes across the Entity -> Service -> Controller layers will cause extra complications! */
        // Job jobByJobId = jobRepository.findById(jobId); 
        Integer entityNumChanged = jobService.deleteByJobId(jobId);
        // return HTTP status code 200 to be idempotent regardless if an actual entity was removed or not from 'Job' DB table & result from this deletion operation
        return ResponseEntity.ok(entityNumChanged); 
        // another way would be to split this up into an if-else flow control statement where if condition 'entityNumchanged == 1' run above, otw if else run above w/ .build()[no body for response entity] concatenated afterwards
        // return new ResponseEntity<>(HttpStatus.OK);
    }
}
