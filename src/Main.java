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
        ArrayList tasksofjob= new ArrayList();
        int startlinenumber=0;
        int endlinenumber=0;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            while ((line2 = bufferedReader.readLine()) != null) {
                String[] words = line2.split("\s+");
                for(int i=0;i<words.length;i++){
                    if(words[i].equals("(JOBTYPES")){
                        startlinenumber=i;
                    }
                }
                for(int j=0;j<words.length;j++){
                    if(words[j].equals("(STATIONS")){
                        endlinenumber=j;
                    }
                }
                for(int k=startlinenumber;k<endlinenumber;k++){
                    if(words[k].startsWith("T"));
                    tasksofjob.add(words[k]);
                }
                ArrayList taskofstation= new ArrayList();
                for(int a=endlinenumber;a<words.length;a++){
                    if(words[a].startsWith("T"));
                    taskofstation.add(words[a]);
                }
                int b=taskofstation.size();
                int c=tasksofjob.size();
                for(int i=0;i<b;i++){
                    for(int j=0;j<c;j++){
                        if(taskofstation.get(i)!=tasksofjob.get(j)){
                            throw new Exception("task"+i +"is not executed at any station");
                        }
                    }
                }
            }

        }catch(Exception e){
            System.out.println(e);
        }
        System.out.println();
        try {
            JobErrors parser = new JobErrors();
            List<Job> jobs = parser.parseJobFile("jobfile.txt");
            //  JobTracker tracker = new JobTracker(jobs);

        /*    for (int time = 0; time <= 50; time += 1) {
                System.out.println("Tracking jobs at time: " + time);
                tracker.trackJobStates(time);
                System.out.println();
            } */


        } catch (IOException e) {
            e.printStackTrace();
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