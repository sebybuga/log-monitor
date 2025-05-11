package log.monitor.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
class CsvLogReaderService implements LogReaderServiceInterface {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    protected BufferedReader createBufferedReader(String filePath) throws IOException {
        return new BufferedReader(new FileReader(filePath));
    }

    public Map<String, LocalTime> readLogFile(String filePath) {
        Map<String, LocalTime> startTimes = new HashMap<>();
        try (BufferedReader br = createBufferedReader(filePath)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 4) continue;

                LocalTime timestamp = LocalTime.parse(parts[0], TIME_FORMATTER);
                String jobId = parts[3].trim();
                String eventType = parts[2].trim();

                if (eventType.equals("START")) {
                    startTimes.put(jobId, timestamp);
                } else if (eventType.equals("END")) {
                    startTimes.put(jobId + "_END", timestamp);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading log file", e);
        }
        return startTimes;
    }

    public Map<String, LocalTime[]> getJobDurations(Map<String, LocalTime> startTimes) {
        Map<String, LocalTime[]> jobDurations = new HashMap<>();
        for (String key : startTimes.keySet()) {
            if (key.endsWith("_END")) continue;

            LocalTime start = startTimes.get(key);
            LocalTime end = startTimes.get(key + "_END");
            if (end != null) {
                jobDurations.put(key, new LocalTime[]{start, end});
            }
        }
        return jobDurations;
    }
}