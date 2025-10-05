package net.jobcompare.backend.repositories;

// import 'List' data structure from Java's utility class
import java.util.List;

// Spring DATA JPA API repository provides many default CRUD operations 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// import 'Job.java' '@Entity' class
import net.jobcompare.backend.entities.*;

// 'JobRepository.java' class inherits the CRUD operations and attributes from 'JpaRepository' (which takes in 'Job' entity class & 'jobId' data type as args)
public interface JobRepository extends JpaRepository<Job, Integer> {

    /* OMITTED: employed built-in .findlAll() method from 'JPARepository' for these CRUD functionalities */
    //     public List<Job> getAllJobs();
    //     public Job findByJobId(Integer jobId);
    //     public Job updateByJobId(Integer jobId, Job job);
    //     public Job deleteByJobId(Integer jobId);
    //     // '@Query' annotation from JPA to retrieve all job openings posted by one particular recruiter
    //     // Aside: 'modId' is the PRIMARY_KEY in its own table while also being a FOREIGN_KEY to 'jobs' DB table, connecting the two
    //     @Query("SELECT opening FROM jobs WHERE opening.modId = :modId")
    //     public List<Job> findAllJobsByTeamId(Integer modId);

    public List<Job> findByModId(Integer modId);
}
