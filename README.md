# OAuth2 Authorization Server project

This project is now deployed on GCP


# To run the tests locally
```
 .\mvnw test
```
Please note that in order to execute individual tests, we still can do it from the command line like this

```
.\mvnw -Dtest=Oauth2AuthorizationServerApplicationTests#performTokenRequestWhenValidClientCredentialsThenOk test
```

However if we want to execute the test in debug mode, then we have to execute them from the IDE (some configurations might need
to be done depending of you IDE)


# If you are running on localhost

To run this project locally, use following command

```
.\mvnw spring-boot:run -Dspring-boot.run.profiles=localhost
```

Please, see below steps to make approprite configurations to be able to run it locally.

Note: when runnig locally, we're using the "local" profile (declared in the <profiles><profile><id>local</id></profile></profiles>) 
which uses the dependencies it declares, do not remove them.


1.- *Set up your clound config server

Please set up and run your project in localhost and point to your configuration properties file for the oauth2-authorization-server 
project when running on localhost.

2.- *The application.properties

The application-localhost.properties is no longer kept here since we are getting the configuration from the spring.cloud.config.uri 
    for localhost. We still need the application-development.properties, application-stage.properties, application-production.properties
	when deploying the project on GCP

3.- *set the spring.cloud.config.uri


If you face an error like this when runing on localhost

Description:

Failed to bind properties under 'spring.cloud.config.uri' to java.lang.String[]:

    Reason: Circular placeholder reference 'spring.cloud.config.uri' in property definitions

Then update your application's configuration


* Do this on the command line

```
set spring.cloud.config.uri=path-to-server-where-reside-your-git-repository-configuration
```

So let's say you are running you oauth2-authorization-server-localhost.properties on localhost on port 10080, that will be like

```
set spring.cloud.config.uri=http://localhost:10080
```