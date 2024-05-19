import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class D3 {
    public static void main(String[] args) {
        Control();
    }

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

        System.out.println(jobTypesIndex);
        System.out.println(stationsIndex);

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
}
