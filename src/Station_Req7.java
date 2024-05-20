import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Station_Req7 {

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
            System.out.println(word);
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

        System.out.println(startLineNumber);
        System.out.println(endLineNumber);

        Map<String, Double> defaultTaskSizes = new HashMap<>();

        // Extract default sizes from TASKTYPES
        for (int i = 1; i < startLineNumber; i++) {
            if (wordsArray[i].startsWith("T")) {
                try {
                    double size = Double.parseDouble(wordsArray[i + 1]);
                    defaultTaskSizes.put(wordsArray[i], size);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    // Handle invalid or missing size
                    defaultTaskSizes.put(wordsArray[i], 0.0);
                }
            }
        }

        List<String> taskArrayList = new ArrayList<>();
        List<Double> sizeArray = new ArrayList<>();
        List<Integer> taskIndices = new ArrayList<>();
        List<Integer> sizeIndices = new ArrayList<>();

        // All jobs and their task lists
        Map<String, List<Task>> jobTasksMap = new HashMap<>();
        String currentJob = null;

        // Extracting T elements and other values
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
                    // System.out.println("Invalid number format: " + wordsArray[i]);
                }
            }
        }

        // Printing each job's task array list and their total duration
        for (String job : jobTasksMap.keySet()) {
            System.out.println(job + " Task Array List:");
            double totalDuration = 0.0;
            for (Task task : jobTasksMap.get(job)) {
                double speed = getStationSpeed(task.getTaskType(), wordsArray, endLineNumber);
                double duration = task.getTaskSize() / speed;
                totalDuration += duration;
                System.out.println("Task Type: " + task.getTaskType() + ", Task Size: " + task.getTaskSize() + ", Duration: " + duration);
            }
            // Print the total duration for the job
            System.out.println("Total Duration for " + job + ": " + totalDuration);
        }

        // Printing the size array with indices
        System.out.println("Size Array with Indices:");
        for (int i = 0; i < sizeArray.size(); i++) {
            System.out.println("Index: " + sizeIndices.get(i) + ", Size: " + sizeArray.get(i) + ", Task: " + wordsArray[sizeIndices.get(i) - 1]);
        }

        // Printing task and size indices
        System.out.println("Task Indices:");
        for (int index : taskIndices) {
            System.out.println(index);
        }

        System.out.println("Size Indices:");
        for (int index : sizeIndices) {
            System.out.println(index);
        }

        // Station initialization (example placeholders)
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
                            // Ignore invalid number format
                        }
                    }
                }
            }
        }
        return 1.0; // Default speed if not found
    }
}
