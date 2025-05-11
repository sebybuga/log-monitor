package log.monitor.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CsvLogReaderServiceTest {

    @Mock
    private BufferedReader bufferedReader;

    @InjectMocks
    private CsvLogReaderService csvLogReaderService;

    @BeforeEach
    void setUp() {
        // Initialize mocks before each test
        MockitoAnnotations.openMocks(this);
        csvLogReaderService = new CsvLogReaderService(bufferedReader);
    }

    @Test
    void testReadLogFile() throws IOException {
        // Mock the behavior of BufferedReader
        String filePath = "some file";
        String logLine1 = "10:30:00,INFO,START,job1";
        String logLine2 = "10:35:00,INFO,END,job1";
        String logLine3 = "11:00:00,INFO,START,job2";
        String logLine4 = "11:05:00,INFO,END,job2";


        when(bufferedReader.readLine()).thenReturn(logLine1)
                .thenReturn(logLine2)
                .thenReturn(logLine3)
                .thenReturn(logLine4)
                .thenReturn(null);  // End of file

        Map<String, LocalTime> startTimes = csvLogReaderService.readLogFile(filePath);

        // Assert that the map has the correct job start times
        assertNotNull(startTimes);
        assertEquals(4, startTimes.size());
        assertEquals(LocalTime.of(10, 30, 0), startTimes.get("job1"));
        assertEquals(LocalTime.of(11, 0, 0), startTimes.get("job2"));

        // Verify interactions with BufferedReader
        verify(bufferedReader, times(5)).readLine(); // 4 lines of log and 1 null for EOF
    }

    @Test
    void testGetJobDurations() {
        // Simulating the data returned by readLogFile method
        Map<String, LocalTime> startTimes = Map.of(
                "job1", LocalTime.of(10, 30, 0),
                "job1_END", LocalTime.of(10, 35, 0),
                "job2", LocalTime.of(11, 0, 0),
                "job2_END", LocalTime.of(11, 5, 0)
        );

        Map<String, LocalTime[]> jobDurations = csvLogReaderService.getJobDurations(startTimes);

        // Assert the durations map contains the correct job durations
        assertNotNull(jobDurations);
        assertEquals(2, jobDurations.size());

        LocalTime[] job1Duration = jobDurations.get("job1");
        assertNotNull(job1Duration);
        assertEquals(LocalTime.of(10, 30, 0), job1Duration[0]);
        assertEquals(LocalTime.of(10, 35, 0), job1Duration[1]);

        LocalTime[] job2Duration = jobDurations.get("job2");
        assertNotNull(job2Duration);
        assertEquals(LocalTime.of(11, 0, 0), job2Duration[0]);
        assertEquals(LocalTime.of(11, 5, 0), job2Duration[1]);
    }

    @Test
    void testReadLogFileException() throws IOException {
        // Simulate IOException when reading the file
        when(bufferedReader.readLine()).thenThrow(new IOException("Error reading log file"));

        // Test that an exception is thrown when reading the log file
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            csvLogReaderService.readLogFile("invalid/path");
        });

        // Assert that the exception message is correct
        assertEquals("Error reading log file", exception.getMessage());
    }
}
