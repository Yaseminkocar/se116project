import java.io.*;
import java.util.ArrayList;

public class d1 {

    public static void main(String[] args) {
        try {
            // Dosyadan okuma için BufferedReader kullanımı
            BufferedReader reader = new BufferedReader(new FileReader("WrongFile.txt"));

            // TASKTYPES satırını oku
            String taskLine = reader.readLine();
            String[] taskTypes = taskLine.substring(11, taskLine.length() - 1).split("\\s+");
            checkTaskTypes(taskTypes);

            // JOBTYPES bölgesini oku
            ArrayList<String[]> jobTypes = new ArrayList<>();
            reader.readLine(); // (JOBTYPES satırını atla)
            String line;
            while (!(line = reader.readLine()).startsWith(")")) {
                jobTypes.add(line.substring(1, line.length() - 1).split("\\s+"));
            }
            checkJobTypes(jobTypes, taskTypes);

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void checkTaskTypes(String[] taskTypes) {
        ArrayList<String> uniqueTaskTypes = new ArrayList<>();
        for (int i = 0; i < taskTypes.length; i++) {
            String taskType = taskTypes[i];

            // Görev türü geçerliliğini kontrol et
            if (!taskType.matches("^[a-zA-Z][a-zA-Z0-9_]*$")) {
                System.out.println("Line 1: " + taskType + " is an invalid tasktypeID.");
                continue;
            }

            if (uniqueTaskTypes.contains(taskType)) {
                System.out.println("Line 1: " + taskType + " is listed twice.");
            } else {
                uniqueTaskTypes.add(taskType);
            }

            // Görev boyutunu kontrol et
            if (i + 1 < taskTypes.length) {
                try {
                    double size = Double.parseDouble(taskTypes[i + 1]);
                    if (size < 0) {
                        System.out.println("Line 1: " + taskType + " has a negative task size.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Line 1: " + taskTypes[i + 1] + " is not a valid size for task " + taskType + ".");
                }
                i++; // Bir sonraki öğeyi atla, çünkü bu görev boyutudur
            }
        }
    }

    private static void checkJobTypes(ArrayList<String[]> jobTypes, String[] taskTypes) {
        ArrayList<String> jobNames = new ArrayList<>();
        ArrayList<String> uniqueTaskTypes = new ArrayList<>();
        for (int i = 0; i < taskTypes.length; i++) {
            if (!taskTypes[i].matches("^[a-zA-Z][a-zA-Z0-9_]*$")) {
                uniqueTaskTypes.add(taskTypes[i]);
            }
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
