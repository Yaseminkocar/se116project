import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Station_Req7 {
    public static void main(String[] args) {

        WorkFlow();

    }

    public static void WorkFlow() {

        int keepTrack = 0;

        StringBuilder contentBuilder = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader("InputFile.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append(" ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (contentBuilder.length() > 0) {
            contentBuilder.setLength(contentBuilder.length() - 1);
        }

        String ElementString = contentBuilder.toString();

        String[] lines = ElementString.split("\n");
        List<String> InputList = new ArrayList<>();

        for (String print : lines) {
            String[] words = print.trim().split("\\s+");
            for (String word : words) {
                InputList.add(word.replaceAll("[()]",""));
            }
        }

        String[] wordsArray = InputList.toArray(new String[0]);
        for (String word : wordsArray) {
            System.out.println(word);
        }

        int startlinenumber=0;
        int endlinenumber=0;



        for(int i=0;i<wordsArray.length;i++){
            if(wordsArray[i].equals("JOBTYPES")){
                startlinenumber=i;
            }
        }
        for(int j=0;j<wordsArray.length;j++){
            if(wordsArray[j].equals("STATIONS")){
                endlinenumber=j;
            }
        }
        int lineS=startlinenumber;
        int lineE=endlinenumber;

         System.out.println("Line number of where 'JobTypes' is "+startlinenumber);
        System.out.println("Line number of where 'Station' is "+endlinenumber);

        // Task array list and size array
        List<String> taskArrayList = new ArrayList<>();
        List<Integer> sizeArray = new ArrayList<>();
        List<Integer> taskIndices = new ArrayList<>();
        List<Integer> sizeIndices = new ArrayList<>();

        // J1's task list
        List<String> j1TaskArrayList = new ArrayList<>();
        boolean isJ1 = false;

        // Extracting T elements and other values
        for (int i = lineS + 1; i < lineE; i++) {
            if (wordsArray[i].equals("J1")) {
                isJ1 = true;
            } else if (wordsArray[i].startsWith("J") && !wordsArray[i].equals("J1")) {
                isJ1 = false;
            }

            if (wordsArray[i].startsWith("T")) {
                taskArrayList.add(wordsArray[i]);
                taskIndices.add(i);
                if (isJ1) {
                    j1TaskArrayList.add(wordsArray[i]);
                }
            } else {
                try {
                    int size = Integer.parseInt(wordsArray[i]);
                    sizeArray.add(size);
                    sizeIndices.add(i);
                } catch (NumberFormatException e) {
                    //  System.out.println("Invalid number format: " + wordsArray[i]);
                }
            }
        }

        // Printing J1's task array list
        System.out.println("J1 Task Array List:");
        for (String task : j1TaskArrayList) {
            System.out.print(task + " ");
        }
        System.out.println();

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










        //  do{

        Station stationNN = new Station();
        Station stationNY = new Station();
        Station stationYN = new Station();
        Station stationYY = new Station();


        // }while();




    }
}
