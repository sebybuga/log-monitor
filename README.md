# Log Monitor (Homework assignment solution)

This is a Java application for calculating monitoring data from a log file and generating warning and error alert messages

## Requirements

- Java 17
- Maven 3.9.9

## Installation

1. Clone the repository:   
   git clone https://github.com/sebybuga/log-monitor.git
   cd log-monitor 

2. Add your input CSV file in data/logs.log or use the existing one. In case of a new file, the name and structure should be the same with logs.log.   

3. Install Spring dependencies:
   mvn clean install

4. Run the application 
   - from IDE: right click on src/main/java/log/monitor/LogMonitor.java and choose Run
   - form terminal application folder: java -jar target/logmonitor-0.0.1-SNAPSHOT.jar

## Usage
   The application will read the file and generate the alerts (error and warnings) at a specified time interval. 
   This interval can be modified using log.scheduler.fixedRate parameter from src/main/resources/application.properties file.
   
   Thresholds for warnings and errors can be modified using the following parameters from application.properties:
   threshold.warning.seconds
   threshold.error.seconds.

   Only one instance of application must run at the same time.
   

## Running Tests
   mvn clean install  - this command also runs tests from src/test folder
