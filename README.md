## Project 1 Summary and Requirements
Build a fullstack application using Spring Boot & React. Back your data with a SQL database, expose with an HTTP API, and a webapp UI.

## Technology Requirements
- Spring Boot
- Spring Web, Spring JPA (or JDBC)
- SQL (H2 embedded, Postgres, etc)
- React
- Maven
- GitHub

## Deadline & Presentation
- First Checkpoint: 9/26
- Final Presentation: 10/10

## Optional Ideas:
- Users who are not logged in can only attempt to log in or register for a new account
- Logging of the service layer
- Test suites for the service layer
- Logging out functionality

- - - 
## $\color{green}{How \: To \: Get \: Started}$
***Instructions for usage:***
1. Clone down github backend repository
    ```
    git clone [repo]
    ```
2. Install all dependencies needed to for this repo
    ```
    npm install 
    ```
## LINK TO FRONT-END:
> https://github.com/yangbri1/fullstack_react_spring_boot_frontend
   
## $\color{orange}{Backend \: Workflow}$
1. Head to https://start.spring.io/ to generate a boilerplate with wanted dependencies. Download & extract the zip file to desired directory.
2. Check `application.properties` file to make sure all wanted dependencies are present, otherwise can always add more.
3. Start by hashing out the **Job.java** & **Moderator.java** `@Entity` classes. In a sense the entity classes provides the schemas for `Job` and `Moderator`.
4. Afterwards create **JobRepository.java** & **ModeratorRepository.java** interfaces  both *implements* from **JPARepostory** which provides basic built-in CRUD methods. Also you could place customized method signatures here as well.  
5. Next, chiseling out the business logic in the service layers `JobService.java` & `ModeratorService.java`. Backend data validation could take place in here.
6. Establish RESTful API endpoints as a way to expose the backend to frontend in `JobController.java` & `ModeratorController.java`.
7. Run these commands in the terminal to build, compile, execute the Java program
    ```
    mvn spring-boot:run
    ```
8. Use POSTMAN for API endpoint testing (POSTMAN coudld be glitchy):
    ```
    http://localhost:8080/jobs
    ```
9. H2 console using SQL statements (if the SQL statements are functional, this indirectly indicates that specific API endpoint is functional)
    ```
    http://localhost:8080/h2-console/
    ```
    (find your customized username & password under `application.properties`)
10. `exceptions` folder is there for future JUnits or any other type of testing
11. `data.sql` was included on a whim as a way to seed the `Job` and `Moderator` database tables with some pre-existing records. Its location being in `resources` inherently invokes a Hibernate feature where Spring Boot will automatically detect and run 'data.sql' after schema is created (depending on '@Entity' classes). Set up to trigger in `application.properties` file.
12. Originally, the extracted Spring initializer package configures H2 to store data in memory.
    ```
    spring.datasource.url=jdbc:h2:mem:testdb 
    ```
13. Unfortunately that means any newly created `Job` or `Moderator` records would not persist in their respective database tables once the program stops running. 
To combat this, configures H2 to store data in a FILE 'data/jobcompare-db' instead of in memory --- this way the DB records would persist despite Spring Boot app closing
    ```
    spring.datasource.url=jdbc:h2:file:./data/jobcompare-db
    ```
    *Note: Sometimes after multiple `mvn spring-boot:run` there may be a need where the created `data` file may need to be deleted --- fix in the future*

## $\color{lightgreen}{API \: Endpoints \:}$
   VERB 		 | 		  PATH 		 |  	 DESCRIPTION
------------ | ------------- | -------------------
`GET` | `/jobs` | Show all job offerings |
`POST` | `/jobs` | Create a new job to be added to the jobs database |
`GET` | `/jobs/:jobId` | Retrieve a specific job by their unique "jobId" |
`GET` | `/jobs/work_arrangement/onsite` | Present only ONSITE job offerings |
`GET` | `/jobs/work_arrangement/remote` | Present only REMOTE job offerings |
`GET` | `/jobs/work_arrangement/hybrid` | Present only HYBRID job offerings |
`PATCH` | `/jobs/:jobId` | Access an existing job verified by its "jobId" and update its info as pleased |
`DELETE` | `/jobs/:jobId` | Access a job by their "jobId" and delete it (moderator) |
`GET` | `/moderators` | Lay out all of the moderators listed in the database |
`POST` | `/moderators` | Assign a new moderator to be saved into the moderators database |
`GET` | `/moderators/:modId` | Pick out one moderator by their unique "modId" |
`GET` | `/moderators/:modId/jobs` | Filter out jobs posted by a particular moderator |

## $\color{purple}{Acknowledgements}$

* Best length of description and field members for optiminal SEO 
https://www.baeldung.com/java-optional
https://theundercoverrecruiter.com/tips-writing-job-descriptions/
https://www.mentalfloss.com/article/646581/world-longest-place-names
https://atdata.com/blog/long-email-addresses/

* `@CreationTimestamp` annotation allows Hibernate to populate field automatically with timestamp of an entity first creation
https://stackoverflow.com/questions/49954812/how-can-you-make-a-created-at-column-generate-the-creation-date-time-automatical
https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#mapping-generated-CreationTimestamp

* Explicitly force Spring Boot to load data to initialzie database from `data.sql`
https://stackoverflow.com/questions/45082574/spring-boot-doesnt-load-data-to-initialize-database-using-data-sql

* Enum for constants for `WorkArrangement` field
https://www.baeldung.com/a-guide-to-java-enums 
https://stackoverflow.com/questions/67233340/how-do-i-validate-or-restrict-enum-type-to-accept-only-specific-values
https://www.baeldung.com/jpa-persisting-enums-in-jpa

* PEP Work - JDBC, Jackson, Bean, SQL, etc.

- - -


### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.6/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.5.6/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.5.6/reference/web/servlet.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/3.5.6/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/3.5.6/reference/using/devtools.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

- - - 

## Example Project - Employee Reimbursement System
A system for employees to submit reimbursement tickets, and for managers to view and approve/deny them.

Employee Users can:
- Create an account
- Create a new Reimbursement
- See their reimbursement tickets
- See only pending reimbursement tickets
- Edit a reimbursement ticket

Manager Users can:
- See all Reimbursements
- See all pending Reimbursements
- Resolve (approve/deny) a reimbursement
- See all Users
- Delete a User

