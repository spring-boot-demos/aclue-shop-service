# Abstract Test Setup with H2
Add H2 dependency

    <dependency>
		<groupId>com.h2database</groupId>
		<artifactId>h2</artifactId>
		<scope>test</scope>
	</dependency>

Create src/test/resources/application-integrationtest.properties with

    spring.datasource.url=jdbc:h2:mem:order-service;DB_CLOSE_ON_EXIT=FALSE
    spring.datasource.driver-class-name=org.h2.Driver
