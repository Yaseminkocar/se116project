
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
public class WorkFlowParser {
    public static void main(String[] args) {
        String filePath = "WrongFile.txt";

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
            }
        }
    }
}

