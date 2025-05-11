package log.monitor.service;

import java.time.LocalTime;
import java.util.Map;

/**
 * The interface can be implemented by multiple log readers in different formats: CSV,JSON,XML and others.
*/
public interface LogReaderServiceInterface {
    Map<String, LocalTime> readLogFile(String filePath);
    Map<String, LocalTime[]> getJobDurations(Map<String, LocalTime> startTimes);
}
