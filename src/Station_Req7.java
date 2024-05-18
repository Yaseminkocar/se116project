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
                InputList.add(word);
            }
        }

        String[] wordsArray = InputList.toArray(new String[0]);
        for (String word : wordsArray) {
            //System.out.println(word);
        }

        int startlinenumber=0;
        int endlinenumber=0;

        for(int i=0;i<wordsArray.length;i++){
            if(wordsArray[i].equals("(JOBTYPES")){
                startlinenumber=i;
            }
        }
        for(int j=0;j<wordsArray.length;j++){
            if(wordsArray[j].equals("(STATIONS")){
                endlinenumber=j;
            }
        }

        System.out.println(startlinenumber);
        System.out.println(endlinenumber);












        //  do{

        Station stationNN = new Station();
        Station stationNY = new Station();
        Station stationYN = new Station();
        Station stationYY = new Station();


        // }while();




    }
}
