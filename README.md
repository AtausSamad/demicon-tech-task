## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Running the application

`Application.properties` file defines the application specific and h2 databse related configs. Adapt it according to your needs.
```shell
spring.h2.console.enabled=true
spring.datasource.generate-unique-name=false
spring.datasource.name=random_users

# application specific configs
# defines the end point for fetching random users
application.url=https://randomuser.me/api

# defines max number of records to fetch (max value: 5000)
application.userSize=10

# defines automatic periodic random user fetching task interval in sec
application.period=300
```

Use the following commands to rune the application:

```shell
# builds both the back-end and front-end
mvn clean install

# runs the application on localhost port 8080
mvn spring-boot:run
```

Once up and running application can be accessed http://localhost:8080