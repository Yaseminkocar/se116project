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

        String fileName = "jobfile.txt";
        List<Job> jobs = readJobsFromFile(fileName);

        // Print all jobs
        for (Job job : jobs) {
            System.out.println(job);
        }
    }



    // Method to read jobs from file
    public static List<Job> readJobsFromFile(String fileName) {
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