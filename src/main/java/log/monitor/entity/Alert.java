package log.monitor.entity;

public class Alert {
    private final String jobId;
    private final long duration;
    private final String level;

    public Alert(String jobId, long duration, String level) {
        this.jobId = jobId;
        this.duration = duration;
        this.level = level;
    }


    @Override
    public String toString() {
        if (duration < 60) {
            return String.format("[%s] Job %s took %d seconds", level, jobId, duration);
        } else {
            return String.format("[%s] Job %s took %d minutes", level, jobId, duration / 60);
        }
    }
}
