package log.monitor.service;

import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JobDurationCalculatorTest {

    private final JobDurationCalculator jobDurationCalculator = new JobDurationCalculator();

    @Test
    void testCalculateDuration() {
        // Given
        LocalTime startTime = LocalTime.of(10, 30, 0); // 10:30:00
        LocalTime endTime = LocalTime.of(10, 45, 30); // 10:45:30

        // When
        long durationInSeconds = jobDurationCalculator.calculateDuration(startTime, endTime);

        // Then
        long expectedDuration = 15 * 60 + 30; // 15 minutes and 30 seconds = 930 seconds
        assertEquals(expectedDuration, durationInSeconds, "Duration should be calculated correctly in seconds.");
    }

    @Test
    void testCalculateDurationWithNegativeTimeDifference() {
        // Given
        LocalTime startTime = LocalTime.of(10, 45, 30); // 10:45:30
        LocalTime endTime = LocalTime.of(10, 30, 0); // 10:30:00

        // When
        long durationInSeconds = jobDurationCalculator.calculateDuration(startTime, endTime);

        // Then
        long expectedDuration = -15 * 60 - 30; // Duration should be negative
        assertEquals(expectedDuration, durationInSeconds, "Duration should be calculated correctly with a negative time difference.");
    }
}
