import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args){
        try {
            String input = TaskReading("InputFile.txt");
            String result = Rename(input);
            System.out.println("Modified task statement: " + result);
        } catch (IOException e) {
            System.out.println("Invalid tasktypeID " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        String filePath = "InputFile.txt";

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
    }
     public static String TaskReading(String Inputfile) throws IOException {
         StringBuilder stringBuilder = new StringBuilder();
         try (BufferedReader reader = new BufferedReader(new FileReader(Inputfile))) {
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


    


}