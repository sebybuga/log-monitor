package log.monitor.service;


import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Map;


@Service
public class LogParserService {
    private final AlertServiceInterface alertService;
    private final LogReaderServiceInterface logReaderService;
    private final DurationCalculatorInterface durationCalculator;

    public LogParserService(AlertServiceInterface alertService,
                            LogReaderServiceInterface logReaderService,
                            DurationCalculatorInterface durationCalculator) {
        this.alertService = alertService;
        this.logReaderService = logReaderService;
        this.durationCalculator = durationCalculator;
    }

    public void parseLogFile(String filePath) {
        Map<String, LocalTime> startTimes = logReaderService.readLogFile(filePath);
        for (Map.Entry<String, LocalTime[]> entry : logReaderService.getJobDurations(startTimes).entrySet()) {
            String jobId = entry.getKey();
            long duration = durationCalculator.calculateDuration(entry.getValue()[0], entry.getValue()[1]);
            alertService.logDuration(jobId, duration);
        }

    }


}
