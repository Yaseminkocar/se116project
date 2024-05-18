import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
        HashSet<String> tasksofjobH = new HashSet<>();

        int startlinenumber = 0;
        int endlinenumber = 0;

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            while ((line2 = bufferedReader.readLine()) != null) {


                String[] words = line2.split("\s+");

               /* for(int y=0;y< words.length;y++){
                    for(int x=0;x< words.length;x++){
                        words[x]=words[y].trim();
                    }
                }

                */

                for(int y=0;y< words.length;y++){
                    System.out.println(words[y]);
                }


                int i = 0;
                for (; i < words.length; i++) {
                    switch (words[i]) {
                        case "(JOBTYPES":
                            startlinenumber = i;
                            System.out.println(startlinenumber);
                            break;
                        case "(STATIONS":
                            endlinenumber = i;
                            System.out.println(endlinenumber);
                            break;
                        //default:
                         //   System.out.println("Unmatched word: " + words[i]);
                    }
                }


                //switch-case ile aynı sonucu vermeli if de
                    /*if(words[i].equals("(JOBTYPES")){
                        startlinenumber=i;
                        System.out.println(startlinenumber);
                    }
                }
                for(int j=0;j<words.length;j++){
                    if(words[j].equals("(STATIONS")){
                        endlinenumber=j;
                        System.out.println(endlinenumber);
                    }
                }
                     */


                    for (int k = startlinenumber; k <= endlinenumber; k++) {
                        if (words[k].startsWith("T")) ;
                        tasksofjobH.add(words[k]);
                    }
                    ArrayList taskOfJobA = new ArrayList<>(tasksofjobH);
                    HashSet taskofstationH = new HashSet();
                    for (int a = endlinenumber; a < words.length; a++) {
                        if (words[a].startsWith("T")) ;
                        taskofstationH.add(words[a]);

                    }
                    int b = taskofstationH.size();
                    int c = tasksofjobH.size();

                    boolean check = true;
                    for (int a = 0; a < b; a++) {
                        for (int j = 0; j < c; j++) {
                            if (taskofstationH.contains(taskOfJobA.get(j))) {
                                check = true;
                            } else {
                                throw new Exception(taskOfJobA.get(j) + " is not processed in Stations");
                            }
                        }
                    }
                }

            }catch(Exception e){
            System.out.println(e);
            }
        }


        /*
        //sadece T4'ün station'da olmadığını söylüyor T21'i de görmesi lazım
        String line2;
        HashMap<String, Integer> tasksOfJob = new HashMap<>();
        int startLineNumber = 0;
        int endLineNumber = 0;

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            while ((line2 = bufferedReader.readLine()) != null) {
                String[] words = line2.split("\\s+"); // Corrected the split pattern
                for (int i = 0; i < words.length; i++) {
                    if (words[i].equals("(JOBTYPES")) {
                        startLineNumber = i;
                    }
                }
                for (int j = 0; j < words.length; j++) {
                    if (words[j].equals("(STATIONS")) {
                        endLineNumber = j;
                    }
                }
                for (int k = startLineNumber; k < endLineNumber; k++) {
                    if (words[k].startsWith("T")) {
                        tasksOfJob.put(words[k], tasksOfJob.getOrDefault(words[k], 0) + 1);
                    }
                }
                HashMap<String, Integer> tasksOfStation = new HashMap<>();
                for (int a = endLineNumber; a < words.length; a++) {
                    if (words[a].startsWith("T")) {
                        tasksOfStation.put(words[a], tasksOfStation.getOrDefault(words[a], 0) + 1);
                    }
                }
                for (Map.Entry<String, Integer> entry : tasksOfStation.entrySet()) {
                    String task = entry.getKey();
                    int countInStation = entry.getValue();
                    int countInJob = tasksOfJob.getOrDefault(task, 0);
                    if (countInJob != countInStation) {
                        throw new Exception("Task " + task + " is not executed at any station");
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }

         */


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



    


