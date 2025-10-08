/* 'ModeratorController' for all moderator-esque API endpoints */
package net.jobcompare.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.jobcompare.backend.entities.Job;
import net.jobcompare.backend.entities.Moderator;
import net.jobcompare.backend.services.ModeratorService;

@RestController
@RequestMapping("/moderators")
public class ModeratorController {

    @Autowired
    private ModeratorService moderatorService;

    // API endpoint to retrieve all moderators
    @GetMapping
    public ResponseEntity<List<Moderator>> getAllModerators() {
        List<Moderator> moderators = moderatorService.getAllModerators();
        return ResponseEntity.ok(moderators);
    }

    // identify 'moderator' by their unique id
    @GetMapping("/{modId}")
    public ResponseEntity<Moderator> getModeratorById(@PathVariable Integer modId) {
        Moderator moderator = moderatorService.findByModeratorId(modId);
        // console out modId & moderator --- error checking
        System.out.println("Requested modId: " + modId);
        System.out.println("Fetched Moderator: " + moderator);

        if (moderator == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(moderator);
    }

    // allow creation of new 'moderator'
    @PostMapping
    public ResponseEntity<Moderator> createModerator(@RequestBody Moderator moderator) {
        Moderator created = moderatorService.createModerator(moderator);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // API endpoint to retrieve all job offerings under a specific moderator
    @GetMapping("/{modId}/jobs")
    public ResponseEntity<List<Job>> getJobsByModerator(@PathVariable Integer modId) {
        Moderator moderator = moderatorService.findByModeratorId(modId);
        // if 'modId' DNE ...
        if(moderator == null){
            // return HTTP status of 404 and a response body of nada
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // otw if 'modId' EXISTS ...
        List<Job> jobs = moderatorService.findAllByModId(modId);
        // some console error checking
        System.out.println("Requested modId: " + modId);
        System.out.println("Fetched Moderator: " + jobs);
        // ... return HTTP status of 200 (OK) w/ 'List' of jobs connected to this one 'modId'
        return ResponseEntity.ok(jobs);
    }
}
