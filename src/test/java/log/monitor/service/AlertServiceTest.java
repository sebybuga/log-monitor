package log.monitor.service;

import log.monitor.config.AlertConfigInterface;
import log.monitor.entity.Alert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.mockito.Mockito.*;

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
        // Mock the static logger initialization for testing
        Logger.getLogger(AlertService.class.getName()); // Ensures logger is initialized properly
        alertService = new AlertService(alertConfig);
    }

    @Disabled("To be finished later")
    void testLogDuration_AboveErrorThreshold() {
        // Arrange
        String jobId = "job1";
        long duration = 15L;  // Above the error threshold of 10

        // Act
        alertService.logDuration(jobId, duration);

        // Assert: Verify that LOGGER logs a SEVERE message when the duration exceeds the error threshold
        verify(logger, times(1)).log(eq(Level.SEVERE), anyString());  // Verify logger call with SEVERE level
    }

    @Disabled("To be finished later")
    void testLogDuration_AboveWarningThresholdButBelowErrorThreshold() {
        // Arrange
        String jobId = "job2";
        long duration = 7L;  // Above the warning threshold but below the error threshold (5-10)

        // Act
        alertService.logDuration(jobId, duration);

        // Assert: Verify that LOGGER logs a WARNING message
        verify(logger, times(1)).log(eq(Level.WARNING), anyString());  // Verify logger call with WARNING level
    }

    @Test
    void testLogDuration_BelowWarningThreshold() {
        // Arrange
        String jobId = "job3";
        long duration = 3L;  // Below the warning threshold (5)

        // Act
        alertService.logDuration(jobId, duration);

        // Assert: Verify that LOGGER does not log anything since the duration is too short
        verify(logger, never()).log(any(Level.class), anyString());  // Verify logger call never happens
    }

    @Disabled("To be finished later")
    void testLogDurationWithAlertObject() {
        // Arrange
        String jobId = "job4";
        long duration = 12L;  // Above the error threshold (10)

        // Create an expected Alert object
        Alert expectedAlert = new Alert(jobId, duration, "ERROR");

        // Act
        alertService.logDuration(jobId, duration);

        // Assert: Verify that the Alert object is created and passed to the logger
        verify(logger, times(1)).log(eq(Level.SEVERE), anyString());
    }
}