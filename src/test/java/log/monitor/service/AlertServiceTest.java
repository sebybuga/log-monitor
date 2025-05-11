package log.monitor.service;

import log.monitor.config.AlertConfigInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlertServiceTest {

    @Mock
    private AlertConfigInterface alertConfig;

    @InjectMocks
    private AlertService alertService;

    @Mock
    private Logger logger;

    @BeforeEach
    void setUp() {
        // Initialize the mock objects
        when(alertConfig.getWarningThreshold()).thenReturn(5L);
        when(alertConfig.getErrorThreshold()).thenReturn(10L);

        alertService = new AlertService(alertConfig);
    }

    @Test
    void testLogDuration_AboveErrorThreshold() {
        String jobId = "job1";
        long duration = 15L;  // Above the error threshold of 10

        Level result = alertService.logDuration(jobId, duration);

        assertEquals(Level.SEVERE, result);
    }

    @Test
    void testLogDuration_AboveWarningThresholdButBelowErrorThreshold() {
        // Arrange
        String jobId = "job2";
        long duration = 7L;  // Above the warning threshold but below the error threshold (5-10)

        // Act
        Level result = alertService.logDuration(jobId, duration);

        assertEquals(Level.WARNING, result);
    }

    @Test
    void testLogDuration_BelowWarningThreshold() {
        // Arrange
        String jobId = "job3";
        long duration = 3L;  // Below the warning threshold (5)

        // Act
        Level result = alertService.logDuration(jobId, duration);

        assertNull(result);
    }


}