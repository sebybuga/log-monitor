package log.monitor.service;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;

@Service
public class JobDurationCalculator implements DurationCalculatorInterface {
    public long calculateDuration(LocalTime startTime, LocalTime endTime) {
        return Duration.between(startTime, endTime).getSeconds();
    }
}
