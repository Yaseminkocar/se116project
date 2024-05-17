import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        String input = null;
        try {
            input = TaskReading("WrongFile.txt");
            String result = Rename(input);
            System.out.println("Modified task statement: " + result);
        } catch (IOException e) {
            System.out.println("Invalid tasktypeID " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        String filePath = "WrongFile.txt";

        try (BufferedReader bufferreader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferreader.readLine()) != null) {
                String[] words = line.split("\s+");
                for (String word : words) {
                    System.out.println(word);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String filePath2 = "WrongFile.txt";

        try {
            parseWorkflowFile(filePath);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        //parantez sayısı karşılaştırma
        int sumopen = 0;
        int sumclose = 0;
        for (int i = 0; i < input.length(); i++) {

            if (input.charAt(i) == '(') {
                sumopen++;
            } else if (input.charAt(i) == ')') {
                sumclose++;
            }

        }
        try {
            if (sumopen < sumclose) {
                throw new Exception("missing '('");

            } else if (sumclose < sumopen) {
                throw new Exception("missing ')'");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        //tasklerin stationda var yok kontrolü
        String line2;
        ArrayList tasksofjob = new ArrayList();
        int startlinenumber = 0;
        int endlinenumber = 0;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            while ((line2 = bufferedReader.readLine()) != null) {
                String[] words = line2.split("\s+");
                for (int i = 0; i < words.length; i++) {
                    if (words[i].equals("(JOBTYPES")) {
                        startlinenumber = i;
                    }
                }
                for (int j = 0; j < words.length; j++) {
                    if (words[j].equals("(STATIONS")) {
                        endlinenumber = j;
                    }
                }
                for (int k = startlinenumber; k < endlinenumber; k++) {
                    if (words[k].startsWith("T")) ;
                    tasksofjob.add(words[k]);
                }
                ArrayList taskofstation = new ArrayList();
                for (int a = endlinenumber; a < words.length; a++) {
                    if (words[a].startsWith("T")) ;
                    taskofstation.add(words[a]);
                }
                int b = taskofstation.size();
                int c = tasksofjob.size();
                for (int i = 0; i < b; i++) {
                    for (int j = 0; j < c; j++) {
                        if (taskofstation.get(i) != tasksofjob.get(j)) {
                            throw new Exception("task" + i + "is not executed at any station");
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        printWorkflowInfo(input);

        // Çalışma akışı dosyasını oku ve işle
        try {
            parseWorkflowFile("workflow.txt");
        } catch (IOException e) {
            System.err.println("Error reading workflow file: " + e.getMessage());
        }
    }



    public static void printWorkflowInfo(String content) {
        String[] sections = content.split("\\(");
        Map<String, List<String>> taskTypes = new HashMap<>();
        Map<String, List<String>> jobTypes = new HashMap<>();
        Map<String, List<String>> stations = new HashMap<>();

        for (String section : sections) {
            section = section.trim();
            if (section.startsWith("TASKTYPES")) {
                parseTaskTypes(section, taskTypes);
            } else if (section.startsWith("JOBTYPES")) {
                parseJobTypes(section, jobTypes);
            } else if (section.startsWith("STATIONS")) {
                parseStations(section, stations);
            }
        }

        System.out.println("Task Types:");
        taskTypes.forEach((task, details) -> System.out.println("- " + task + ": " + String.join(" ", details)));

        System.out.println("\nJob Types:");
        jobTypes.forEach((job, tasks) -> {
            System.out.println("- " + job + ":");
            tasks.forEach(task -> System.out.println("  - " + task));
        });

        System.out.println("\nStations:");
        stations.forEach((station, details) -> {
            System.out.println("- " + station + ":");
            details.forEach(detail -> System.out.println("  - " + detail));
        });
    }

    private static void parseTaskTypes(String section, Map<String, List<String>> taskTypes) {
        String[] parts = section.split("\\s+");
        for (int i = 1; i < parts.length; i++) {
            if (parts[i].matches("T\\d+")) {
                taskTypes.put(parts[i], new ArrayList<>());
            } else {
                List<String> details = taskTypes.get(parts[i - 1]);
                details.add(parts[i]);
            }
        }
    }

    private static void parseJobTypes(String section, Map<String, List<String>> jobTypes) {
        String[] parts = section.split("\\s+");
        String currentJob = null;
        for (String part : parts) {
            if (part.startsWith("J")) {
                currentJob = part;
                jobTypes.put(currentJob, new ArrayList<>());
            } else if (currentJob != null) {
                jobTypes.get(currentJob).add(part);
            }
        }
    }

    private static void parseStations(String section, Map<String, List<String>> stations) {
        String[] parts = section.split("\\s+");
        String currentStation = null;
        for (String part : parts) {
            if (part.startsWith("S")) {
                currentStation = part;
                stations.put(currentStation, new ArrayList<>());
            } else if (currentStation != null) {
                stations.get(currentStation).add(part);
            }
        }
    }
    public static void parseWorkflowFile(String inputfile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputfile))) {
            String line;
            int lineNumber = 0;
            ArrayList<String> uniqueIds = new ArrayList<>();  // Using ArrayList instead of List

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\s+");
                if (parts.length < 3) {
                    System.out.println("Syntax error on line " + lineNumber + ": Each line must have at least 3 parts.");
                    continue;
                }
                String taskType = parts[0];
                String jobId = parts[1];
                String stationInfo = parts[2];

                if (Character.isDigit(taskType.charAt(0))) {
                    System.out.println("Syntax error on line " + lineNumber + ": Task type '" + taskType + "' cannot start with a number.");
                    continue;
                }
                if (!jobId.matches("\\d+")) {
                    System.out.println("Syntax error on line " + lineNumber + ": Job ID '" + jobId + "' must be a numeric value.");
                    continue;
                }

                if (uniqueIds.contains(jobId)) {
                    System.out.println("Semantic error on line " + lineNumber + ": Job ID '" + jobId + "' must be unique.");
                    continue;
                } else {
                    uniqueIds.add(jobId);
                }

                String[] stationParts = stationInfo.split(",");
                if (stationParts.length != 2 || !stationParts[0].matches("\\d+") || !stationParts[1].matches("\\d+")) {
                    System.out.println("Syntax error on line " + lineNumber + ": Invalid station information '" + stationInfo + "'.");
                    continue;
                }
                int stationNumber = Integer.parseInt(stationParts[0]);
                int stationRange = Integer.parseInt(stationParts[1]);
                if (stationNumber < 1 || stationRange < 1 || stationRange > 100) {
                    System.out.println("Semantic error on line " + lineNumber + ": Station range '" + stationRange + "' must be between 1 and 100.");
                    continue;
                }

                Task task = new Task(taskType, 2);
                System.out.println("Successfully parsed line " + lineNumber + ": Task Type: " + taskType + ", Job ID: " + jobId + ", Station Info: " + stationInfo);
            }
        }
    }

    public static String TaskReading(String inputfile) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputfile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n"); // Append each line with a new line character
            }
        }
        return stringBuilder.toString();
    }

    public static String Rename(String input) {
        if (Character.isDigit(input.charAt(0))) {
            throw new IllegalArgumentException("Task statement cannot start with a number!");
        }
        return input;
    }
}

/* public static String TaskReading(String WrongFile) throws IOException {


    public static String TaskReading(String WrongFile) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(WrongFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }

    public static String Rename(String input) {
        if (Character.isDigit(input.charAt(0))) {
            throw new IllegalArgumentException("Task statement can not start with a number !.");
        }
        return input;
    }



    public static void parseWorkflowFile(String inputfile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputfile))) {
            String line;
            int lineNumber = 0;
            Set<String> uniqueIds = new HashSet<>();

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split("\s+");
                if (parts.length < 3) {
                    System.out.printf("Syntax error on line %d: Each line must have at least 3 parts.%n", lineNumber);
                    continue;
                }
                String taskType = parts[0];
                String jobId = parts[1];
                String stationInfo = parts[2];

                if (Character.isDigit(taskType.charAt(0))) {
                    System.out.printf("Syntax error on line %d: Task type '%s' cannot start with a number.%n", lineNumber, taskType);

                    continue;
                }
                if (!jobId.matches("\\d+")) {
                    System.out.printf("Syntax error on line %d: Job ID '%s' must be a numeric value.%n", lineNumber, jobId);
                    continue;
                }

                if (!uniqueIds.add(jobId)) {
                    System.out.printf("Semantic error on line %d: Job ID '%s' must be unique.%n", lineNumber, jobId);
                    continue;
                }

                String[] stationParts = stationInfo.split(",");
                if (stationParts.length != 2 || !stationParts[0].matches("\\d+") || !stationParts[1].matches("\\d+")) {
                    System.out.printf("Syntax error on line %d: Invalid station information '%s'.%n", lineNumber, stationInfo);
                    continue;
                }
                int stationNumber = Integer.parseInt(stationParts[0]);
                int stationRange = Integer.parseInt(stationParts[1]);
                if (stationNumber < 1 || stationRange < 1 || stationRange > 100) {
                    System.out.printf("Semantic error on line %d: Station range '%d' must be between 1 and 100.%n", lineNumber, stationRange);
                    continue;
                }


                Task task = new Task(taskType ,2);
                System.out.printf("Successfully parsed line %d: Task Type: %s, Job ID: %s, Station Info: %s%n", lineNumber, taskType, jobId, stationInfo);
            }
        }
    }


}

 */



    


