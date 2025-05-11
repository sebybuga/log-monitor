package log.monitor.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
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

    }


    @Test
    void testReadLogFile() throws IOException {
        String filePath = "some file";

        String logContent = "12:00:00,INFO,START,job1\n12:01:00,INFO,END,job1";
        BufferedReader mockBufferedReader = new BufferedReader(new StringReader(logContent));

        CsvLogReaderService spyService = spy(csvLogReaderService);
        doReturn(mockBufferedReader).when(spyService).createBufferedReader(filePath);

        Map<String, LocalTime> startTimes = spyService.readLogFile(filePath);

        assertEquals(2, startTimes.size());
        assertEquals(LocalTime.of(12, 0, 0), startTimes.get("job1"));
        assertEquals(LocalTime.of(12, 1, 0), startTimes.get("job1_END"));
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
