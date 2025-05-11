# Log Monitor

This is a Java application for monitoring data from a log file and generating warning and error alert messages

## Requirements
- Java 17
- Maven 3.9.9

## Installation

1. Clone the repository:<br>   
   git clone https://github.com/sebybuga/log-monitor.git<br>
   cd log-monitor

2. Add your input CSV file in data/logs.log or use the existing one. In case of a new file, the name and structure should be the same with logs.log.   

3. Install Spring dependencies:<br>
   mvn clean install 

4. Run the application 
   - from IDE: right click on src/main/java/log/monitor/LogMonitor.java and choose Run
   - form terminal application folder: java -jar target/logmonitor-0.0.1-SNAPSHOT.jar

## Usage
   The application acts like a microservice reading a log file and generating alerts (error and warnings) at a specified time interval (milliseconds).<br> 
   This interval can be modified using log.scheduler.fixedRate parameter from src/main/resources/application.properties file.
   Default is 30000 ms ->30 seconds. 
   
   Thresholds for warnings and errors (seconds) can be modified using the following parameters from application.properties:<br>
   threshold.warning.seconds<br>
   threshold.error.seconds.

   Only one instance of application must run at the same time.
   

## Running Tests
   mvn clean install  - also runs tests from src/test folder.

## Security
   The application can run on a virtual machine or inside a container. End users access must be denied to such resources.<br>
   Spring Boot Starter Security, Spring Boot Starter Web, Spring Boot Starter OAuth2 Resource Server and other dependencies can be used if the log file
   is deployed on an environment which needs authentication.  
