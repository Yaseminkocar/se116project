import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class d1 {

    public static void main(String[] args) {
        String fileName = "WrongFile.txt";
        try {
            // Dosyayı kontrol eden metodu çağırıyoruz
            checkWorkflowFile(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void checkWorkflowFile(String fileName) throws IOException {
        // Dosyayı okumak için BufferedReader kullanıyoruz
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        int lineNumber = 0;

        // ID'leri saklamak için listeler oluşturuyoruz
        List<String> taskTypeIds = new ArrayList<>();
        List<String> jobTypeIds = new ArrayList<>();
        List<String> stationIds = new ArrayList<>();

        // Dosyadaki her satırı okuyoruz
        while ((line = reader.readLine()) != null) {
            lineNumber++;
            line = line.trim(); // Satırın başındaki ve sonundaki boşlukları kaldırıyoruz

            if (line.isEmpty() || line.startsWith("#")) {
                continue; // Boş satırları ve yorumları atlıyoruz
            }

            // Satırın içeriğine göre uygun kontrol metodunu çağırıyoruz
            if (line.startsWith("(TASKTYPES")) {
                checkTaskTypes(line, lineNumber, taskTypeIds);
            } else if (line.startsWith("(JOBTYPES")) {
                checkJobTypes(reader, lineNumber, jobTypeIds, taskTypeIds);
            } else if (line.startsWith("(STATIONS")) {
                checkStations(reader, lineNumber, stationIds, taskTypeIds);
            } else {
                System.out.println("Syntax error on line " + lineNumber + ": " + line);
            }
        }

        // Dosyayı kapatıyoruz
        reader.close();
    }

    public static void checkTaskTypes(String line, int lineNumber, List<String> taskTypeIds) {
        // İlk boşluktan sonrasını alıp boşluklara göre bölüyoruz
        String[] parts = line.substring(line.indexOf(' ') + 1).split("\\s+");
        List<String> seenTaskTypes = new ArrayList<>();

        // Her parçayı kontrol ediyoruz
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (!part.startsWith("[a-zA-Z]\\w*")) {
                // Eğer part geçerli bir task type id ise
                if (seenTaskTypes.contains(part)) {
                    System.out.println("Semantic error on line " + lineNumber + ": " + part + " is listed twice.");
                } else {
                    seenTaskTypes.add(part);
                }
                if (taskTypeIds.contains(part)) {
                    System.out.println("Semantic error on line " + lineNumber + ": Duplicate task type ID " + part);
                } else {
                    taskTypeIds.add(part);
                }
                // Boyut değerinin var olup olmadığını kontrol ediyoruz
                if (i + 1 < parts.length && parts[i + 1].matches("[0-9.]+")) {
                    i++; // Boyut değerini atlıyoruz
                } else {
                    System.out.println("Semantic error on line " + lineNumber + ": Task type " + part + " is missing a size value.");
                }
            } else {
                System.out.println("Semantic error on line " + lineNumber + ": " + part + " is an invalid tasktypeID.");
            }
        }
    }

    public static void checkJobTypes(BufferedReader reader, int lineNumber, List<String> jobTypeIds, List<String> taskTypeIds) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            lineNumber++;
            line = line.trim();

            if (line.startsWith(")")) {
                break; // Kapanış parantezine kadar okuyoruz
            }

            String[] parts = line.substring(line.indexOf(' ') + 1).split("\\s+");
            String jobId = parts[0];
            if (jobTypeIds.contains(jobId)) {
                System.out.println("Semantic error on line " + lineNumber + ": " + jobId + " already declared.");
            } else {
                jobTypeIds.add(jobId);
            }

            for (int i = 1; i < parts.length; i++) {
                String taskType = parts[i];
                if (!taskTypeIds.contains(taskType)) {
                    System.out.println("Semantic error on line " + lineNumber + ": " + taskType + " is not one of the declared task types.");
                }
                if (i + 1 < parts.length && parts[i + 1].matches("[0-9.]+")) {
                    i++; // Boyut değerini atlıyoruz
                } else {
                    System.out.println("Semantic error on line " + lineNumber + ": Task type " + taskType + " is missing a size value.");
                }
            }
        }
    }

    public static void checkStations(BufferedReader reader, int lineNumber, List<String> stationIds, List<String> taskTypeIds) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            lineNumber++;
            line = line.trim();

            if (line.startsWith(")")) {
                break; // Kapanış parantezine kadar okuyoruz
            }

            String[] parts = line.substring(line.indexOf(' ') + 1).split("\\s+");
            String stationId = parts[0];
            if (stationIds.contains(stationId)) {
                System.out.println("Semantic error on line " + lineNumber + ": Duplicate station ID " + stationId);
            } else {
                stationIds.add(stationId);
            }

            for (int i = 4; i < parts.length; i++) {
                String taskType = parts[i];
                if (!taskTypeIds.contains(taskType)) {
                    System.out.println("Semantic error on line " + lineNumber + ": " + taskType + " is not one of the declared task types.");
                }
                if (i + 1 < parts.length && parts[i + 1].matches("[0-9.]+")) {
                    i++; // Hız değerini atlıyoruz
                } else {
                    System.out.println("Semantic error on line " + lineNumber + ": Task type " + taskType + " is missing a speed value.");
                }
            }
        }
    }
}
