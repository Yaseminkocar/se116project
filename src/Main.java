import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            String input = TaskReading("WrongFile.txt");
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



    


