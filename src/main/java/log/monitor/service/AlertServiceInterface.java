package log.monitor.service;

import java.util.logging.Level;
/**
 * The interface can be implemented by all kind of services used for logging duration of a process.
 */
public interface AlertServiceInterface {
    Level logDuration(String jobId, long duration);
}
