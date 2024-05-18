import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String input = null;
        try {
            input = ReadTask("WrongFile.txt");
            String result = Rename(input);
            System.out.println("Modified task statement: " + result);
        } catch (IOException e) {
            System.out.println("Invalid tasktypeID " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        String filePath = "WrongFile.txt";

        try (BufferedReader bufferReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferReader.readLine()) != null) {
                String[] words = line.split("\s+");
                for (String word : words) {
                    System.out.println(word);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Number of parentheses comparison
        int sumOpen = 0;
        int sumClose = 0;
        for (int i = 0; i < input.length(); i++) {

            if (input.charAt(i) == '(') {
                sumOpen++;
            } else if (input.charAt(i) == ')') {
                sumClose++;
            }
        }
        System.out.println();
        // d1'e ait
        String fn = "WrongFile.txt";
        try {
            checkWorkflowFile(fn);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (sumOpen < sumClose) {
                throw new Exception("Missing error '('");

            } else if (sumClose < sumOpen) {
                throw new Exception("Missing error ')'");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println();
        //Checking whether tasks are on the station or not
        String line2;
        ArrayList tasksOfJob= new ArrayList();
        int startLineNumber=0;
        int endLineNumber=0;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            while ((line2 = bufferedReader.readLine()) != null) {
                String[] words = line2.split("\s+");
                for(int i=0;i<words.length;i++){
                    if(words[i].equals("(JOBTYPES")){
                        startLineNumber=i;
                    }
                }
                for(int j=0;j<words.length;j++){
                    if(words[j].equals("(STATIONS")){
                        endLineNumber=j;
                    }
                }
                for(int k=startLineNumber;k<endLineNumber;k++){
                    if(words[k].startsWith("T")) {
                        tasksOfJob.add(words[k]);
                    }
                }
                ArrayList taskOfStation= new ArrayList();
                for(int a=endLineNumber;a<words.length;a++){
                    if(words[a].startsWith("T")) {
                        taskOfStation.add(words[a]);
                    }
                }
                int b=taskOfStation.size();
                int c=tasksOfJob.size();
                for(int i=0;i<b;i++){
                    for(int j=0;j<c;j++){
                        if(taskOfStation.get(i)!=tasksOfJob.get(j)){
                            throw new Exception("Task"+i +"is not executed at any station");
                        }
                    }
                }
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        String fileName = "WrongFile.txt";
        List<Job> jobs = readJobs(fileName);

        // Print all jobs
        for (Job job : jobs) {
            System.out.println(job);
        }

        System.out.println();
        try {
            JobErrors parser = new JobErrors();
            List<Job> job = parser.parseJobFile("jobfile.txt");
            //  JobTracker tracker = new JobTracker(jobs);

        /*    for (int time = 0; time <= 50; time += 1) {
                System.out.println("Tracking jobs at time: " + time);
                tracker.trackJobStates(time);
                System.out.println();
            } */

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
    }
    //d1'e ait
    public static void checkWorkflowFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        int lineNumber = 0;

        List<String> taskTypeIds = new ArrayList<>();
        List<String> jobTypeIds = new ArrayList<>();
        List<String> stationIds = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            lineNumber++;
            line = line.trim();

            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }

            if (line.startsWith("(TASKTYPES")) {
                checkTaskTypes(line, lineNumber, taskTypeIds, errors);
            } else if (line.startsWith("(JOBTYPES")) {
                checkJobTypes(reader, lineNumber, jobTypeIds, taskTypeIds, errors);
            } else if (line.startsWith("(STATIONS")) {
                checkStations(reader, lineNumber, stationIds, taskTypeIds, errors);
            } else {
                errors.add("Syntax error on line " + lineNumber + ": " + line);
            }
        }

        reader.close();

        for (String error : errors) {
            System.err.println(error);
        }
    }
    //d1'e ait
    public static void checkTaskTypes(String line, int lineNumber, List<String> taskTypeIds, List<String> errors) {
        String[] parts = line.substring(line.indexOf(' ') + 1).split("\\s+");
        List<String> seenTaskTypes = new ArrayList<>();

        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (!part.matches("[a-zA-Z]\\w*")) {
                errors.add("Error on line " + lineNumber + ": " + part + " is an invalid tasktypeID.");
                continue;
            }

            if (seenTaskTypes.contains(part)) {
                errors.add("Error on line " + lineNumber + ": " + part + " is listed twice.");
            } else {
                seenTaskTypes.add(part);
            }

            if (i + 1 < parts.length && numeric(parts[i + 1])) {
                double size = Double.parseDouble(parts[i + 1]);
                if (size < 0) {
                    errors.add("Error on line " + lineNumber + ": " + part + " has a negative task size.");
                }
                i++;
            } /*else {
                errors.add("Error on line " + lineNumber + ": Task type " + part + " is missing a size value.");
            } */

        }
    }
    //d1'e ait
    public static void checkJobTypes(BufferedReader reader, int lineNumber, List<String> jobTypeIds, List<String> taskTypeIds, List<String> errors) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            lineNumber++;
            line = line.trim();

            if (line.startsWith(")")) {
                break;
            }

            String[] parts = line.substring(line.indexOf(' ') + 1).split("\\s+");
            String jobId = parts[0];
            if (jobTypeIds.contains(jobId)) {
                errors.add("Error on line " + lineNumber + ": " + jobId + " already declared.");
            } else {
                jobTypeIds.add(jobId);
            }


            for (int i = 0; i < parts.length; i++) {
                String taskType = parts[i];
                if (!taskTypeIds.contains(taskType)) {
                    errors.add("Error on line " + lineNumber + ": " + taskType + " is not one of the declared task types.");
                }
                if (i + 1 < parts.length && numeric(parts[i + 1])) {
                    i++;
                } else {
                    errors.add("Error on line " + lineNumber + ": Task type " + taskType + " is missing a size value.");
                }
            }
        }
    }
    public static void checkStations(BufferedReader reader, int lineNumber, List<String> stationIds, List<String> taskTypeIds, List<String> errors) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            lineNumber++;
            line = line.trim();

            if (line.startsWith(")")) {
                break;
            }

            String[] parts = line.substring(line.indexOf(' ') + 1).split("\\s+");
            String stationId = parts[0];
            if (stationIds.contains(stationId)) {
                errors.add("Error on line " + lineNumber + ": Duplicate station ID " + stationId);
            } else {
                stationIds.add(stationId);
            }

            for (int i = 4; i < parts.length; i++) {
                String taskType = parts[i];
                if (!taskTypeIds.contains(taskType)) {
                    errors.add("Error on line " + lineNumber + ": " + taskType + " is not one of the declared task types.");
                }
                if (i + 1 < parts.length && numeric(parts[i + 1])) {
                    i++;
                } else {
                    errors.add("Error on line " + lineNumber + ": Task type " + taskType + " is missing a speed value.");
                }
            }
        }
    }
    public static boolean numeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    public static String ReadTask(String inputfile) throws IOException {
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
    // Method to read jobs from file
    public static List<Job> readJobs(String fileName) {
        List<Job> jobs = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                parseJobLine(line, jobs);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jobs;
    }

    // Method to parse each line and add to jobs list
    private static void parseJobLine(String line, List<Job> jobs) {
        // Remove parentheses and split by spaces
        line = line.replaceAll("[()]", "").trim();
        String[] parts = line.split("\\s+");

        if (parts.length > 0) {
            String jobName = parts[0];
            ArrayList<JobDetail> details = new ArrayList<>();
            for (int i = 1; i < parts.length; i += 2) {
                String type = parts[i];
                int value = (i + 1 < parts.length && isNumeric(parts[i + 1])) ? Integer.parseInt(parts[i + 1]) : 0;
                details.add(new JobDetail(type, value));
            }
            jobs.add(new Job(jobName, details));
        }
    }

    // Method to check if a string is numeric
    private static boolean isNumeric(String str
    ) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}