package log.monitor.service;

import java.util.logging.Level;

public interface AlertServiceInterface {
    Level logDuration(String jobId, long duration);
}
