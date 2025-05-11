package log.monitor.scheduler;

import log.monitor.service.LogParserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;


@Service
public class LogFileScheduler {
    private static final Logger LOGGER = Logger.getLogger(LogFileScheduler.class.getName());
    private final LogParserService logParserService;

    @Value("${log.file.path}")
    private String filePath;

    @Value("${log.scheduler.fixedRate}")
    private long fixedRate;

    public LogFileScheduler(LogParserService logParserService) {
        this.logParserService = logParserService;
    }

    // Schedule task to run periodically
    @Scheduled(fixedRateString = "${log.scheduler.fixedRate}") // Runs every fixedRate seconds
    public void scheduledParseLogFile() {

        // Log separator between runs
        LOGGER.info("------------------------------------------------------");

        logParserService.parseLogFile(filePath);
    }
}
