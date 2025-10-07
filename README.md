# Project 1 Summary and Requirements
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

Optional Ideas:
- Users who are not logged in can only attempt to log in or register for a new account
- Logging of the service layer
- Test suites for the service layer
- Logging out functionality

- - - 
# References

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


- - -

# Getting Started

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

