package log.monitor.service;

import java.time.LocalTime;
/**
 * The interface can be implemented by multiple duration calculators for different purposes: duration of a job,
 * an activity during working days, a period when transactions are blocked and others.
 */
public interface DurationCalculatorInterface {
    long calculateDuration(LocalTime startTime, LocalTime endTime);
}