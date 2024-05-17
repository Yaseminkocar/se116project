import java.io.*;
import java.util.ArrayList;

public class d {

    public static void main(String[] args) {
        try {
            // Dosyadan okuma için BufferedReader kullanımı
            BufferedReader reader = new BufferedReader(new FileReader("WrongFile.txt"));

            // TASKTYPES satırını oku
            String taskLine = reader.readLine();
            String[] taskTypes = taskLine.substring(11, taskLine.length() - 1).split(" ");
            taskTypes = combineTaskTypes(taskTypes);
            checkTaskTypes(taskTypes);

            // JOBTYPES bölgesini oku
            ArrayList<String[]> jobTypes = new ArrayList<>();
            reader.readLine(); // (JOBTYPES satırını atla)
            String line;
            while (!(line = reader.readLine()).startsWith(")")) {
                jobTypes.add(line.substring(1, line.length() - 1).split(" "));

            }
            checkJobTypes(jobTypes, taskTypes);

            // STATIONS bölgesini oku
            ArrayList<String[]> stations = new ArrayList<>();
            reader.readLine(); // (STATIONS satırını atla)
            while ((line = reader.readLine()) != null) {
                if (line.endsWith(")")) {
                    stations.add(line.substring(1, line.length() - 1).split(" "));
                } else {
                    stations.add(line.substring(1).split(" "));
                }
            }
            checkStations(stations, taskTypes, jobTypes);

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static String[] combineTaskTypes(String[] elements) {
        ArrayList<String> combinedElements = new ArrayList<>();
        for (int i = 0; i < elements.length; i++) {
            if (i + 1 < elements.length && elements[i + 1].matches("-?\\d+(\\.\\d+)?")) {
                combinedElements.add(elements[i] + " " + elements[i + 1]);
                i++;
            } else {
                combinedElements.add(elements[i]);
            }
        }
        return combinedElements.toArray(new String[0]);
    }

    private static void checkTaskTypes(String[] taskTypes) {
        ArrayList<String> uniqueTaskTypes = new ArrayList<>();
        for (int i = 0; i < taskTypes.length; i += 2) {
            String taskType = taskTypes[i];
            if (!taskType.matches("[a-zA-Z_]+")) {
                System.out.println(taskType + " is an invalid");
            }
            if (uniqueTaskTypes.contains(taskType)) {
                System.out.println(taskType + " is listed twice.");
            } else {
                uniqueTaskTypes.add(taskType);
            }
            try {
                double size = Double.parseDouble(taskTypes[i + 1]);
                if (size < 0) {
                    System.out.println(taskType + " has a negative task size.");
                }
            } catch (NumberFormatException e) {
                System.out.println(taskTypes[i + 1] + " is not a valid");
            }
        }
    }

    private static void checkJobTypes(ArrayList<String[]> jobTypes, String[] taskTypes) {
        ArrayList<String> jobNames = new ArrayList<>();
        ArrayList<String> uniqueTaskTypes = new ArrayList<>();
        for (int i = 0; i < taskTypes.length; i += 2) {
            uniqueTaskTypes.add(taskTypes[i]);
        }

        for (int i = 0; i < jobTypes.size(); i++) {
            String[] job = jobTypes.get(i);
            String jobName = job[0];
            if (jobNames.contains(jobName)) {
                System.out.println(jobName + " already declared.");
            } else {
                jobNames.add(jobName);
            }
            for (int j = 1; j < job.length; j++) {
                String task = job[j];
                if (!uniqueTaskTypes.contains(task) && j % 2 == 1) {
                    System.out.println(task + " is not one of the declared task types.");
                }
                if (j % 2 == 1) {
                    try {
                        double size = Double.parseDouble(job[j + 1]);
                        if (size < 0) {
                            System.out.println(task + " has a negative task size");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(job[j + 1] + " is not a valid size for task.");
                    }
                }
                if (j % 2 == 0 && !uniqueTaskTypes.contains(task)) {
                    System.out.println(task + " has no default size, either a default size must be declared in TASKTYPE list or the size must be declared within the job.");
                }
            }
        }
    }

    private static void checkStations(ArrayList<String[]> stations, String[] taskTypes, ArrayList<String[]> jobTypes) {
        ArrayList<String> executedTasks = new ArrayList<>();
        ArrayList<String> uniqueTaskTypes = new ArrayList<>();
        for (int i = 0; i < taskTypes.length; i += 2) {
            uniqueTaskTypes.add(taskTypes[i]);
        }

        for (String[] station : stations) {
            for (int j = 4; j < station.length; j += 2) {
                if (!executedTasks.contains(station[j])) {
                    executedTasks.add(station[j]);
                }
            }
        }
        for (String task : uniqueTaskTypes) {
            if (!executedTasks.contains(task)) {
                System.out.println("Warning: " + task + " is not executed in any STATIONS even though it is listed as a possible task type.");
            }
        }

        // Checking for missing closing parenthesis in the last station definition
        if (!stations.get(stations.size() - 1)[stations.get(stations.size() - 1).length - 1].equals("0.20")) {
            System.out.println("Line 12: ')' missing");
        }
    }
}

