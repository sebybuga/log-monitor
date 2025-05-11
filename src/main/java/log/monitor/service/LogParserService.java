package log.monitor.service;


import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service

public class LogParserService {
    private static final Logger LOGGER = Logger.getLogger(LogParserService.class.getName());
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    //    private static final long WARNING_THRESHOLD = 5 * 60; // 5 minutes
//    private static final long ERROR_THRESHOLD = 10 * 60; // 10 minutes
    private final AlertService alertService;

    public LogParserService(AlertService alertService) {
        this.alertService = alertService;
    }

    public void parseLogFile(String filePath) {
        Map<String, LocalTime> startTimes = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 4) continue;

                LocalTime timestamp = LocalTime.parse(parts[0], TIME_FORMATTER);

                String jobId = parts[3].trim();
                String eventType = parts[2].trim();

                if (eventType.equals("START")) {
                    startTimes.put(jobId, timestamp);
                } else if (eventType.equals("END") && startTimes.containsKey(jobId)) {

                    LocalTime startTime = startTimes.remove(jobId);
                    long duration = Duration.between(startTime, timestamp).getSeconds();
                    alertService.logDuration(jobId, duration);

                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading log file", e);
        }
    }


}
