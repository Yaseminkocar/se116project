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


            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static String[] combineTaskTypes(String[] elements) {
        ArrayList combinedElements = new ArrayList<>();
        for (int i = 0; i < elements.length; i++) {
            if (i + 1 < elements.length && elements[i + 1].matches("-?\\d+(\\.\\d+)?")) { combinedElements.add(elements[i] + " " + elements[i + 1]); i++; }
            else { combinedElements.add(elements[i]);
            }
        }
        return combinedElements.toArray(new String[0]);
    }


    private static void checkTaskTypes(String[] taskTypes) {
        ArrayList<String> temptasktype = new ArrayList<>();
        for (int i = 0; i < taskTypes.length; i += 2) {
            String taskType = taskTypes[i];
            if (!taskType.startsWith("[a-zA-Z_]+")) {
                System.out.println("Line 1: " + taskType + " is an invalid tasktypeID.");
            }
            if (temptasktype.contains(taskType)) {
                System.out.println("Line 1: " + taskType + " is listed twice.");
            }
            else {
                temptasktype.add(taskType);
            }
            try {
                double size = Double.parseDouble(taskTypes[i + 1]);
                if (size < 0) {
                    System.out.println("Line" + (i+1) + " : " + taskType + " has a negative task size.");
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

}

