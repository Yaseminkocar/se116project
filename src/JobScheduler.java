/*import java.util.*;
public class JobScheduler {
    public static void main(String[] args) {
        // Örnek iş ve istasyon verileri oluşturma
        List<Job> jobs = createJobs();
        List<Station> stations = createStations();

        Scheduler scheduler = new Scheduler(jobs, stations);
        scheduler.run();
    }

    private static List<Job> createJobs() {
        // Örnek işler oluşturma
        List<Job> jobs = new ArrayList<>();

        // Örnek job ekleme
        Job job1 = new Job("Job1", 0, 100, Arrays.asList(
                new Task("T1", "Task1", 10),
                new Task("T2", "Task2", 20),
                new Task("T3", "Task3", 30)
        ));
        jobs.add(job1);

        return jobs;
    }

    private static List<Station> createStations() {
        // Örnek istasyonlar oluşturma
        List<Station> stations = new ArrayList<>();

        // Örnek station ekleme
        Station station1 = new Station("Station1", Arrays.asList("T1", "T2"));
        stations.add(station1);

        return stations;
    }
}*/
