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
                System.out.println("error at line " + lineNumber + ": jobTypeID must start with a letter followed by a number.");
                lineNumber++;
                continue;
            }

            try {
                startTime = Integer.parseInt(parts[2]);
                duration = Integer.parseInt(parts[3]);

                if (startTime < 0) {
                    System.out.println("error at line " + lineNumber + ": start time has to be greater than 0 .");
                    lineNumber++;
                    continue;
                }
                if (duration <= 0) {
                    System.out.println("error at line " + lineNumber + ": duration has to be greater than 0 .");
                    lineNumber++;
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println(" error at line " + lineNumber + ": start time or duration has to be numeric value .");
                lineNumber++;
                continue;
            }
            try {
                if (jobIDs.contains(jobID)) {
                    throw new IllegalArgumentException("Duplicate jobID " + jobID);
                } else {
                    jobIDs.add(jobID);
                }
            } catch (IllegalArgumentException e) {
                System.out.println(" error at line " + lineNumber + ": " + e.getMessage());
                lineNumber++;
                continue;
            }

        }
        reader.close();
        return jobs;
 }
}