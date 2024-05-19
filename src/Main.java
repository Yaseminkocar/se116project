import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                    //System.out.println(word);
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
        //d3
        Control();

        try {
            if (sumOpen < sumClose) {
                throw new Exception("Error: Missing '('");

            } else if (sumClose < sumOpen) {
                throw new Exception("Error: Missing ')'");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
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

        WorkFlow();

    }
    //d3
    private static void Control() {
        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader("WrongFile.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (stringBuilder.length() > 0) {
            stringBuilder.setLength(stringBuilder.length() - 1);
        }

        String element = stringBuilder.toString();

        String[] lines = element.split("\n");
        List<String> inputList = new ArrayList<>();

        for (String line : lines) {
            String[] words = line.trim().split("\\s+");
            for (String word : words) {
                inputList.add(word.replaceAll("[()]", ""));
            }
        }

        String[] wordsArray = inputList.toArray(new String[0]);
        for (int i=0; i< wordsArray.length; i++){
            System.out.println(wordsArray[i]);
        }
        int jobTypesIndex = 0;
        int stationsIndex = 0;

        for (int j =0; j < wordsArray.length; j++) {
            if (wordsArray[j].equals("JOBTYPES")) {
                jobTypesIndex = j;
            }
        }
        for (int k = 0; k < wordsArray.length; k++) {
            if (wordsArray[k].equals("STATIONS")) {
                stationsIndex = k;
            }
        }

        System.out.println("Line number of where 'JobTypes' is "+jobTypesIndex);
        System.out.println("Line number of where 'Station' is "+stationsIndex);

        List<String> taskTypes = new ArrayList<>();
        List<Double> defaultSizes = new ArrayList<>();
        List<String> declaredTaskTypes = new ArrayList<>();
        List<String> duplicateTaskTypes = new ArrayList<>();
        List<String> invalidTaskTypes = new ArrayList<>();
        List<String> negativeSizeTasks = new ArrayList<>();

        // Extracting T elements and default sizes
        for (int i = 0; i < jobTypesIndex; i++) {
            String word = wordsArray[i];
            if (word.startsWith("T")) {
                if (declaredTaskTypes.contains(word)) {
                    duplicateTaskTypes.add(word);
                } else {
                    declaredTaskTypes.add(word);
                    taskTypes.add(word);
                    try {
                        double size = Double.parseDouble(wordsArray[i + 1]);
                        if (size < 0) {
                            negativeSizeTasks.add(word);
                        }
                        defaultSizes.add(size);
                        i++;
                    } catch (NumberFormatException e) {
                        defaultSizes.add(null); // No default size
                    }
                }
            } else if (!word.matches("[0-9]+") && !word.equals("TASKTYPES")) {
                invalidTaskTypes.add(word);
            }
        }

        List<String> declaredJobTypes = new ArrayList<>();
        List<String> jobsWithNegativeSize = new ArrayList<>();
        List<String> undeclaredTaskTypes = new ArrayList<>();
        System.out.println();

        for (int i = jobTypesIndex + 1; i < stationsIndex; i++) {
            String word = wordsArray[i];
            if (word.startsWith("J")) {
                if (declaredJobTypes.contains(word)) {
                    System.out.println("Error: Job type " + word + " already declared.");
                } else {
                    declaredJobTypes.add(word);
                }
            } else if (word.startsWith("T")) {
                if (!taskTypes.contains(word)) {
                    undeclaredTaskTypes.add(word);
                    System.out.println("Error: Task type " + word + " not declared in TASKTYPES.");
                }
                try {
                    double size = Double.parseDouble(wordsArray[i + 1]);
                    if (size < 0) {
                        System.out.println("Error: Negative size for task type " + word + " in job type " + wordsArray[i - 1]);
                        jobsWithNegativeSize.add(wordsArray[i - 1]);
                    }
                    i++;
                } catch (NumberFormatException e) {
                    int tIndex = taskTypes.indexOf(word);
                    if (tIndex >= 0 && defaultSizes.get(tIndex) == null) {
                        System.out.println("Error: Task type " + word + " has no default size.");
                    }
                }
            }
        }

        List<String> stationTasks = new ArrayList<>();
        for (int i = stationsIndex + 1; i < wordsArray.length; i++) {
            String word = wordsArray[i];
            if (word.startsWith("T")) {
                stationTasks.add(word);
            }
        }
        for (int i =0; i< taskTypes.size(); i++){
            if (!stationTasks.contains(taskTypes.get(i))) {
                System.out.println("Error: Task type " + taskTypes.get(i) + " is not executed in any station.");
            }
        }
        int openParenthesisCount = 0;
        int closeParenthesisCount = 0;
        for (int i =0; i<wordsArray.length; i++){
            if(wordsArray[i].contains("(")){
                openParenthesisCount++;
            } if (wordsArray[i].contains(")")) {
                closeParenthesisCount++;
            }
        }
        if (openParenthesisCount != closeParenthesisCount) {
            System.out.println("Error: Unmatched parentheses found.");
        }

        for (int m =0; m<duplicateTaskTypes.size(); m++) {
            System.out.println("Error: Duplicate task type " + duplicateTaskTypes.get(m) + " found.");
        }

        for (int k =0; k<invalidTaskTypes.size(); k++) {
            System.out.println("Error: Invalid task type ID " + invalidTaskTypes.get(k));
        }

        for (int l =0; l<negativeSizeTasks.size(); l++){
            System.out.println("Error: Negative default size for task type " + negativeSizeTasks.get(l));
        }
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
    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    private static void WorkFlow() {
        StringBuilder contentBuilder = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader("InputFile.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (contentBuilder.length() > 0) {
            contentBuilder.setLength(contentBuilder.length() - 1);
        }

        String elementString = contentBuilder.toString();
        String[] lines = elementString.split("\n");
        List<String> inputList = new ArrayList<>();

        for (String print : lines) {
            String[] words = print.trim().split("\\s+");
            for (String word : words) {
                // Remove parentheses
                inputList.add(word.replaceAll("[()]", ""));
            }
        }

        String[] wordsArray = inputList.toArray(new String[0]);
        for (String word : wordsArray) {
           // System.out.println(word);
        }

        int startLineNumber = 0;
        int endLineNumber = 0;

        for (int i = 0; i < wordsArray.length; i++) {
            if (wordsArray[i].equals("JOBTYPES")) {
                startLineNumber = i;
            }
        }
        for (int j = 0; j < wordsArray.length; j++) {
            if (wordsArray[j].equals("STATIONS")) {
                endLineNumber = j;
            }
        }



        Map<String, Double> defaultTaskSizes = new HashMap<>();


        for (int i = 1; i < startLineNumber; i++) {
            if (wordsArray[i].startsWith("T")) {
                try {
                    double size = Double.parseDouble(wordsArray[i + 1]);
                    defaultTaskSizes.put(wordsArray[i], size);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {

                    defaultTaskSizes.put(wordsArray[i], 0.0);
                }
            }
        }

        List<String> taskArrayList = new ArrayList<>();
        List<Double> sizeArray = new ArrayList<>();
        List<Integer> taskIndices = new ArrayList<>();
        List<Integer> sizeIndices = new ArrayList<>();


        Map<String, List<Task>> jobTasksMap = new HashMap<>();
        String currentJob = null;


        for (int i = startLineNumber + 1; i < endLineNumber; i++) {
            if (wordsArray[i].startsWith("J")) {
                currentJob = wordsArray[i];
                jobTasksMap.putIfAbsent(currentJob, new ArrayList<>());
            }

            if (wordsArray[i].startsWith("T") && currentJob != null) {
                taskArrayList.add(wordsArray[i]);
                taskIndices.add(i);
                double taskSize = defaultTaskSizes.getOrDefault(wordsArray[i], 0.0);
                if (i + 1 < wordsArray.length && isNumericWorkFile(wordsArray[i + 1])) {
                    taskSize = Double.parseDouble(wordsArray[i + 1]);
                    sizeIndices.add(i + 1);
                    i++; // Skip the next element as it is the size
                }
                Task task = new Task(wordsArray[i], taskSize);
                jobTasksMap.get(currentJob).add(task);
            } else {
                try {
                    double size = Double.parseDouble(wordsArray[i]);
                    sizeArray.add(size);
                } catch (NumberFormatException e) {

                }
            }
        }


        for (String job : jobTasksMap.keySet()) {
            System.out.println(job + " Task Array List:");
            double totalDuration = 0.0;
            for (Task task : jobTasksMap.get(job)) {
                double speed = getStationSpeed(task.getTaskType(), wordsArray, endLineNumber);
                double duration = task.getTaskSize() / speed;
                totalDuration += duration;
                System.out.println("Task Type: " + task.getTaskType() + ", Task Size: " + task.getTaskSize() + ", Duration: " + duration);
            }

            System.out.println("Total Duration for " + job + ": " + totalDuration);
        }

        System.out.println("Size Array with Indices:");
        for (int i = 0; i < sizeArray.size(); i++) {
            System.out.println("Index: " + sizeIndices.get(i) + ", Size: " + sizeArray.get(i) + ", Task: " + wordsArray[sizeIndices.get(i) - 1]);
        }

        System.out.println("Task Indices:");
        for (int index : taskIndices) {
            System.out.println(index);
        }

        System.out.println("Size Indices:");
        for (int index : sizeIndices) {
            System.out.println(index);
        }

        Station stationNN = new Station();
        Station stationNY = new Station();
        Station stationYN = new Station();
        Station stationYY = new Station();
    }

    private static boolean isNumericWorkFile(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static double getStationSpeed(String taskType, String[] wordsArray, int endLineNumber) {
        for (int i = endLineNumber + 1; i < wordsArray.length; i++) {
            if (wordsArray[i].startsWith("S")) {
                for (int j = i + 1; j < wordsArray.length && !wordsArray[j].startsWith("S"); j++) {
                    if (wordsArray[j].equals(taskType)) {
                        try {
                            return Double.parseDouble(wordsArray[j + 1]);
                        } catch (NumberFormatException e) {
                        }
                    }
                }
            }
        }
        return 1.0; // Default speed if not found
    }
}
