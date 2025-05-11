package log.monitor.service;

import java.time.LocalTime;

public interface DurationCalculatorInterface {
    long calculateDuration(LocalTime startTime, LocalTime endTime);
}