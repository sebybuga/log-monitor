package log.monitor.service;

import java.time.LocalTime;
import java.util.Map;

public interface LogReaderServiceInterface {
    Map<String, LocalTime> readLogFile(String filePath);
    Map<String, LocalTime[]> getJobDurations(Map<String, LocalTime> startTimes);
}
