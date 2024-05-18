import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class d1 {

    public static void main(String[] args) {
        String fileName = "WrongFile.txt";
        try {
            checkWorkflowFile(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

            if (i + 1 < parts.length && isNumeric(parts[i + 1])) {
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

                if (i + 1 < parts.length && isNumeric(parts[i + 1])) {
                    // Eğer bir sonraki parça sayısal ise büyüklüğü geçerli olarak kabul ederiz
                    double size = Double.parseDouble(parts[i + 1]);
                    System.out.println("Task Type: " + taskType + ", Size: " + size);  // Bilgi çıktısı
                    i++; // Büyüklük değerini atlamak için i'yi artırıyoruz
                } else {
                    // Eğer bir sonraki parça sayısal değilse ya da mevcut değilse hata mesajı ekliyoruz
                    errors.add("Error on line " + lineNumber + ": Task type " + taskType + " is missing a size value.");
                }
            }
        }
    }

    /* public static void checkJobTypes(BufferedReader reader, int lineNumber, List<String> jobTypeIds, List<String> taskTypeIds, List<String> errors) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            lineNumber++;
            line = line.trim();

            if (line.startsWith(")")) {
                break;
            }

            String[] parts = line.substring(line.indexOf(' ') + 1).split("\\s+");
            String jobId = parts[0];
            /*if (jobTypeIds.contains(jobId)) {
                errors.add("Error on line " + lineNumber + ": " + jobId + " already declared.");
            } else {
                jobTypeIds.add(jobId);
            }


            for (int i = 1; i < parts.length; i++) {
                String taskType = parts[i];
                if (!taskTypeIds.contains(taskType)) {
                    errors.add("Error on line " + lineNumber + ": " + taskType + " is not one of the declared task types.");
                }
                if (i + 1 < parts.length && isNumeric(parts[i + 1])) {
                    i++;
                } else {
                    errors.add("Error on line " + lineNumber + ": Task type " + taskType + " is missing a size value.");
                }
            }
        }
    }

    */
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
                if (i + 1 < parts.length && isNumeric(parts[i + 1])) {
                    i++;
                } /*else {
                    errors.add("Error on line " + lineNumber + ": Task type " + taskType + " is missing a speed value.");
                }
                */
            }
        }
    }

   /* public static boolean isNumeric(String str) {
        return str.chars().allMatch(Character::isDigit) || str.matches("-?[0-9]+(\\.[0-9]+)?");
    }
    */
    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}
