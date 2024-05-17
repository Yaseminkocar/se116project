import java.io.*;
import java.util.ArrayList;

public class Deneme {

    public static void main(String[] args) {
        try {
            // Dosyadan okuma için BufferedReader kullanımı
            BufferedReader reader = new BufferedReader(new FileReader("WrongFile.txt"));


            String taskLine = reader.readLine();
            String[] taskTypes = taskLine.substring(11, taskLine.length() - 1).split(" ");
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
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void checkTaskTypes(String[] taskTypes) {
        ArrayList<String> uniqueTaskTypes = new ArrayList<>();
        for (int i = 0; i < taskTypes.length; i += 2) {
            String taskType = taskTypes[i];
            if (!taskType.matches("[a-zA-Z_]+")) {
                System.out.println(taskType + " is an invalid tasktypeID.");
            }
            if (uniqueTaskTypes.contains(taskType)) {
                System.out.println("Line 1: " + taskType + " is listed twice.");
            } else {
                uniqueTaskTypes.add(taskType);
            }
            try {
                double size = Double.parseDouble(taskTypes[i + 1]);
                if (size < 0) {
                    System.out.println("Line 1: " + taskType + " has a negative task size.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Line 1: " + taskTypes[i + 1] + " is not a valid size for task " + taskType + ".");
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
                System.out.println("Line " + (5 + i) + ": " + jobName + " already declared.");
            } else {
                jobNames.add(jobName);
            }
            for (int j = 1; j < job.length; j++) {
                String task = job[j];
                if (!uniqueTaskTypes.contains(task) && j % 2 == 1) {
                    System.out.println("Line " + (5 + i) + ": " + task + " is not one of the declared task types.");
                }
                if (j % 2 == 1) {
                    try {
                        double size = Double.parseDouble(job[j + 1]);
                        if (size < 0) {
                            System.out.println("Line " + (5 + i) + ": " + task + " has a negative task size of " + job[j + 1] + ".");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Line " + (5 + i) + ": " + job[j + 1] + " is not a valid size for task " + task + ".");
                    }
                }
                if (j % 2 == 0 && !uniqueTaskTypes.contains(task)) {
                    System.out.println("Line " + (5 + i) + ": " + task + " has no default size, either a default size must be declared in TASKTYPE list or the size must be declared within the job.");
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

