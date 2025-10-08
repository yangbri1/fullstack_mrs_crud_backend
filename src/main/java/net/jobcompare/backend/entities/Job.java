// file path to 'entities.java' class (THIS)
package net.jobcompare.backend.entities;

// '@CreationTimestamp' annotation, Hibernate will use current timestamp of JVM as insert value (in milliseconds) since 1-1-1970
import org.hibernate.annotations.CreationTimestamp;
// import 'Instant' class from Java's 'Time' library to take snapshots in time 
import java.time.Instant;

import jakarta.persistence.Column;
// Notice: during earlier project used 'javax' library for persistence b/c Java Spring Boot version was below 3 ...
// ... here using 'jakarta' library as this version of Spring Boot is 3.5.6
// ... also could continue using 'javax' library if decided to use JDBC (low-level, lengthy) over Spring JPA + Spring Web
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
// import lombok dependencies to cut down on excess lines of code for getters, setters, constructors
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
// these lombok shorten annotations will provide each private attributes w/ their getter & setter methods
// normally after initializing a private attribute ...
/* public Integer getJobId(){
 * }
 * public void setMessageId(Integer jobId){
 *      this.jobId = jobId;
 * }
} */

@Getter     // '@Data' includes both '@Getter', '@Setter', 
@Setter
// lombok annotations to generate the constructors ...
/* Aside: what if I want a custom constructor for posting jobs? */
// ... and initialize all the fields (order is important!)
@AllArgsConstructor
// ... default constructor w/o any args necessary for entity instantiation
@NoArgsConstructor
// '@Entity' annotation from Java Persistence API (JPA) to indicate 'Entity.java' class as a persistent entity ...
// ... aka this class will be mapped to a DB table where its instance is stored & retrieved from DB table
@Entity
// '@Table' annotation to customize table name otw by default table name === class name ('Job')
@Table(name = "jobs") 
public class Job {
    // use 'Enum' from 'java.lang.Enum' library to define constant choices for 'workArrangement' field
    public enum WorkArrangement{
        REMOTE,
        ONSITE,
        HYBRID;
    }

    @Column(name = "jobId")
    // '@Id' annotation indicates this field as the PRIMARY_KEY --- '@Entity' requires a PRIMARY_KEY
    @Id 
    // provides auto-generation of key set explicitly to 'IDENTITY' type s.t. the PK is generated upon 'INSERT' record operation by DB
    /* Aside: default behavior was 'GenerationType.AUTO' in which JPA picks strategy depending on selected DB dialect (MySQL, Oracle, etc.)   */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer jobId;
    @Column(name = "title", unique = true)
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "company")
    private String company;
    @Column(name = "location")
    private String location; 
    @Column(name = "year_of_experience")    // method 1: 1) explicitly change column's name to underscores as w/ camelCase H2 will UPPERCASE all (cause troubles as H2 is sensitive as it is ...) --- not quotes needed in `data.sql`
    private Integer yearOfExperience;
    
    // '@Enumerated' annotation w/ enum type of 'STRING' instructs JPA provider to convert the enum to its 'String' value (other option is .ORDINAL -- show index)
    @Enumerated(EnumType.STRING)            // when persisting/storing 'job' entity in DB, JPA will use 'Enum.name()' value  --- since using 'EnumType.STRING'
    @Column(name = "work_arrangement")      // method 2: 1) update column mappings as "\workArrangement\" so H2 DB will display this at it is & 2) double quotes respective camelCase column fields in `data.sql`
    private WorkArrangement workArrangement;
    
    @Column(name = "yearly_salary")
    private Float yearlySalary;
    // '@Column' annotation maps 'hiringTeamEmail' field to 'email' column where it must NOT be of null value & it's unique (no duplicates)
    // '@Column' annotation by default aka w/o this annotation would map its field name to a column of DB table using exact same name ...
    // ... from previous project example '@Column' was still used despite it being redundant ... may remove for DRY-er
    /* Some sense of data validation? */
    @Column(name = "email", nullable = false, unique = true)
    private String hiringTeamEmail;
    // 'teamId' field in this 'Job' schema must be non-falsy (not empty)
    /* Aside: including 'unique = true' property disallows moderator from creating multiple jobs so EXCLUDED here */
    @Column(name = "mod_id", nullable = false)
    private Integer modId;

    @Column(name = "createdOn")
    // '@CreationTimestamp' allows Hibernate to automatically populate 'createdOn' field w/ current moment in time (in JVM) -- NOT manual insertion
    /* Note: DO NOT include 'createdOn' field in constructor as 'createdOn' field will automatically be handled by Hibernate when entity 1st persists */
    @CreationTimestamp
    private Instant createdOn;

    // private String locationCity;
    // private String locationState;
    // private Float yearlyBonus;
    // private Integer match401k;  // nearest whole percentage
    // private Float internetStipend;
    // private Float accidentInsurance;
    // private Float tuitionReimbursement;

    // custom constructor w/o 'jobId' s.t. the when POST-ing a new 'job', the id could be generated by the DB
    /* OMITTED 'createdOn' field too as it will automatically be created via Hibernate '@CreationTimestamp' w/ 'Instant' type */
    public Job(String title, String description, String company, String location, Integer yearOfExperience, WorkArrangement workArrangement, Float yearlySalary, 
    String hiringTeamEmail, Integer modId){
        this.title = title;
        this.company = company;
        this.location = location;
        this.description = description;
        // this.locationCity = locationCity;
        // this.locationState = locationState;
        this.yearOfExperience = yearOfExperience;
        this.workArrangement = workArrangement;
        this.yearlySalary = yearlySalary;
        this.hiringTeamEmail = hiringTeamEmail;
        // this.createdOn = createdOn;
        this.modId = modId;
    }

}
