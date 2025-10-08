package net.jobcompare.backend.repositories;

// import 'List' data structure from Java's utility class
import java.util.List;

// Spring DATA JPA API repository provides many default CRUD operations 
import org.springframework.data.jpa.repository.JpaRepository;

import net.jobcompare.backend.entities.Job;
// import 'Moderator.java' '@Entity' class
import net.jobcompare.backend.entities.Moderator;
// import net.jobcompare.backend.entities.Job.WorkArrangement;

public interface ModeratorRepository extends JpaRepository<Moderator, Integer>{

    public List<Job> findByModerator_ModId(Integer modId);

}
