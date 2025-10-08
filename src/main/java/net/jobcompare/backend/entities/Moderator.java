package net.jobcompare.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/* all these lombok annotations help cut down on getter, setter, constructor boilerplate code */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// '@Entity' annotations means 'Moderator.java' is now an entity class
@Entity 
// customize the DB table name to 'moderators' (default == 'Moderator')
@Table(name = "moderators")
public class Moderator {
    /* Aside: convert 'modId' camelCasing to using underscore as H2 database behavior --- using camelCase may cause unforeseen issues */
    @Column(name = "mod_id")
    // assign 'modId' field to be the id via '@Id' annotation
    @Id
    // allow Spring to automatically generate id value (recommended)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer modId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    // authentication later?
    @Column(name = "password", nullable = false, unique = true)
    private String password;
    
}
