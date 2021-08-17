# Getting Started

#### Follow the steps below to run this project in your Local Machine


---
### Step 1 : Cassandra Setup (One time Configuration)

If you use a Mac with Homebrew, you can run the following command

`$ brew install cassandra`

LAUNCH CASSANDRA
`$ cassandra -f`

In new tab, Execute `cqlsh` to connect to the cassandra server.

Following are the cassandra queries that should be executed before the start of project.

`create keyspace test with replication={'class':'SimpleStrategy', 'replication_factor':1};`

`use test;`

`CREATE TABLE productPrice(id bigint PRIMARY KEY, price double, currencyCode text);`

`INSERT INTO productPrice(id, price, currencyCode) values (13860428, 12.99,'USD');`


---

### Step : 2 - Run the App


---


### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.3/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.3/gradle-plugin/reference/html/#build-image)
* [Spring Data MongoDB](https://docs.spring.io/spring-boot/docs/2.5.3/reference/htmlsingle/#boot-features-mongodb)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.5.3/reference/htmlsingle/#using-boot-devtools)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.5.3/reference/htmlsingle/#production-ready)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/docs/2.5.3/reference/htmlsingle/#configuration-metadata-annotation-processor)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.5.3/reference/htmlsingle/#boot-features-developing-web-applications)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with MongoDB](https://spring.io/guides/gs/accessing-data-mongodb/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)