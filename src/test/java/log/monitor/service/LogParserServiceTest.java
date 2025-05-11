package log.monitor.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

class LogParserServiceTest {

    @Mock
    private AlertServiceInterface alertService;

    @Mock
    private LogReaderServiceInterface logReaderService;

    @Mock
    private DurationCalculatorInterface durationCalculator;

    @InjectMocks
    private LogParserService logParserService;

    @BeforeEach
    void setUp() {
        // Initialize the mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testParseLogFile() {
        // Sample data
        String filePath = "src/main/resources/logs.log";
        Map<String, LocalTime> startTimes = new HashMap<>();
        startTimes.put("job1", LocalTime.of(10, 30, 0));
        startTimes.put("job2", LocalTime.of(11, 0, 0));

        Map<String, LocalTime[]> jobDurations = new HashMap<>();
        jobDurations.put("job1", new LocalTime[]{LocalTime.of(10, 30, 0), LocalTime.of(10, 35, 0)});
        jobDurations.put("job2", new LocalTime[]{LocalTime.of(11, 0, 0), LocalTime.of(11, 5, 0)});

        // Mock method calls
        when(logReaderService.readLogFile(filePath)).thenReturn(startTimes);
        when(logReaderService.getJobDurations(startTimes)).thenReturn(jobDurations);
        when(durationCalculator.calculateDuration(any(LocalTime.class), any(LocalTime.class))).thenReturn(300L); // 5 minutes = 300 seconds

        // Call the method under test
        logParserService.parseLogFile(filePath);

        // Verify interactions with mocks
        verify(logReaderService, times(1)).readLogFile(filePath);
        verify(logReaderService, times(1)).getJobDurations(startTimes);
        verify(durationCalculator, times(2)).calculateDuration(any(LocalTime.class), any(LocalTime.class));

        // Check if logDuration was called with 300 seconds
        verify(alertService, times(2)).logDuration(anyString(), eq(300L));


    }
}


