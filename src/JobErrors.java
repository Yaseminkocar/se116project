import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JobErrors {

    public List<Job> parseJobFile(String filename) throws IOException {
        List<Job> jobs = new ArrayList<>();
        List<String> jobIDs = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        int lineNumber = 1;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.trim().split("\\s+");
            if (parts.length != 4) {
                System.out.println("Syntax error at line " + lineNumber + ": " + line);
                lineNumber++;
                continue;
            }

            String jobID = parts[0];
            String jobTypeID = parts[1];
            int startTime;
            int duration;

            if (!jobTypeID.matches("[A-Za-z]\\d+")) {
                System.out.println("Semantic error at line " + lineNumber + ": jobTypeID must start with a letter followed by a number.");
                lineNumber++;
                continue;
            }

            try {
                if (jobIDs.contains(jobID)) {
                    throw new IllegalArgumentException("Duplicate jobID " + jobID);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Semantic error at line " + lineNumber + ": Duplicate jobID " + jobID);
            }

            try {
                startTime = Integer.parseInt(parts[2]);
                duration = Integer.parseInt(parts[3]);

                if (startTime < 0) {
                    System.out.println("Semantic error at line " + lineNumber + ": startTime must be non-negative.");
                    lineNumber++;
                    continue;
                }
                if (duration <= 0) {
                    System.out.println("Semantic error at line " + lineNumber + ": duration must be greater than zero.");
                    lineNumber++;
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Semantic error at line " + lineNumber + ": Non-numeric value in startTime or duration.");
                lineNumber++;
                continue;
            }

        }

        reader.close();
        return jobs;
    }
}


