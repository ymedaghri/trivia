Solution for the trivia kata
======

Yet another solution for the trivia Kata.

JUnit 5, Jacoco, Sonar and Maven are configured so it can be used to present sonar to the attendants.

## Install Sonar on Docker
```bash
docker pull sonarqube
docker run -d --name sonarqube -p 9000:9000 sonarqube
```

## Configure Sonar by connecting as admin into the webapp
Follow the wizard, get the login token and put it into the pom.xml

## Use maven to launch the sonar code analysis

Either using
```bash
mvn sonar:sonar
```
Or
```bash
mvn clean verify
```




