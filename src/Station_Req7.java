import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Station_Req7 {
    public static void main(String[] args) {
        WorkFlow();
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

        List<String> taskArrayList = new ArrayList<>();
        List<Double> sizeArray = new ArrayList<>();
        List<Integer> taskIndices = new ArrayList<>();
        List<Integer> sizeIndices = new ArrayList<>();

        // J1's task list
        List<Task> j1TaskArrayList = new ArrayList<>();
        boolean isJ1 = false;

        // Extracting T elements and other values
        for (int i = startLineNumber + 1; i < endLineNumber; i++) {
            if (wordsArray[i].equals("J1")) {
                isJ1 = true;
            } else if (wordsArray[i].startsWith("J") && !wordsArray[i].equals("J1")) {
                isJ1 = false;
            }

            if (wordsArray[i].startsWith("T")) {
                taskArrayList.add(wordsArray[i]);
                taskIndices.add(i);
                if (isJ1) {
                    Task task = new Task(wordsArray[i], 0); // Initial size 0, will be set later
                    j1TaskArrayList.add(task);
                }
            } else {
                try {
                    double size = Double.parseDouble(wordsArray[i]);
                    sizeArray.add(size);
                    sizeIndices.add(i);
                    if (isJ1 && !j1TaskArrayList.isEmpty()) {
                        j1TaskArrayList.get(j1TaskArrayList.size() - 1).setTaskSize(size); // Set the size of the last task
                    }
                } catch (NumberFormatException e) {
                    //  System.out.println("Invalid number format: " + wordsArray[i]);
                }
            }
        }

        // Check and set sizes to 0 where not specified
        for (int i = 0; i < j1TaskArrayList.size(); i++) {
            Task task = j1TaskArrayList.get(i);
            if (task.getTaskSize() == 0) {
                if (i + 1 < j1TaskArrayList.size()) {
                    try {
                        double size = Double.parseDouble(wordsArray[taskIndices.get(i) + 1]);
                        task.setTaskSize(size);
                    } catch (NumberFormatException e) {
                        task.setTaskSize(0); // Set size to 0 if the next element is not a number
                    }
                } else {
                    task.setTaskSize(0); // Set size to 0 if there is no next element
                }
            }
        }

        // Printing J1's task array list
        System.out.println("J1 Task Array List:");
        for (Task task : j1TaskArrayList) {
            System.out.println("Task Type: " + task.getTaskType() + ", Task Size: " + task.getTaskSize());
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
}

