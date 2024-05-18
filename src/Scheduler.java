/*import java.util.*;
public class Scheduler {
    private List<Job> jobs;
    private List<Station> stations;

    public Scheduler(List<Job> jobs, List<Station> stations) {
        this.jobs = jobs;
        this.stations = stations;
    }

    public void run() {
        for (Job job : jobs) {
            for (Task task : job.getTasks()) {
                Station station = findSuitableStation(task);
                if (station != null) {
                    station.assignTask(task);
                    job.setTaskState(task, "In Progress");
                    System.out.println("Assigned " + task.getName() + " to " + station.getName());
                }
            }
        }
    }

    private Station findSuitableStation(Task task) {
        for (Station station : stations) {
            if (station.canExecuteTask(task)) {
                return station;
            }
        }
        return null;
    }
}*/
