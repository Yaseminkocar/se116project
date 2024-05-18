import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Job_Req5 {
    public static void main(String[] args) throws IOException {


        StringBuilder contentBuilder = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader("jobfile.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append(" ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Remove the trailing space
        if (contentBuilder.length() > 0) {
            contentBuilder.setLength(contentBuilder.length() - 1);
        }

        String jobString = contentBuilder.toString();
        //System.out.println(jobString);

        String[] lines= jobString.split("\n");
        List<String> wordsList = new ArrayList<>();

        for(String print: lines){
            String[] words= print.trim().split("\\s+");
            for(String word: words){
                wordsList.add(word);
            }
        }

        String[] wordsArray= wordsList.toArray(new String[0]);

        for(String word: wordsArray){
            //System.out.println(word);
        }

        ArrayList<String>jobIDList=new ArrayList<>();
        ArrayList<String>jobTypeIDList=new ArrayList<>();
        ArrayList<String>startList=new ArrayList<>();
        ArrayList<String>durationList=new ArrayList<>();
        ArrayList<Integer>deadlineList=new ArrayList<>();

        for(int i=0;i<wordsArray.length;i+=4){
            jobIDList.add(wordsArray[i]);
        }
        for(int i=1;i<wordsArray.length;i+=4){
            jobTypeIDList.add(wordsArray[i]);
        }for(int i=2;i<wordsArray.length;i+=4){
            startList.add(wordsArray[i]);
        }for(int i=3;i<wordsArray.length;i+=4){
            durationList.add(wordsArray[i]);
        }

        for(int i=0;i<5;i++){
            System.out.println(jobTypeIDList.get(i));
        }




    }

}
