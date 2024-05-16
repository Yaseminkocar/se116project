import java.io.IOException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            String input = readTaskFromFile("InputFile.txt");
            String result = modifyTaskFormat(input);
            System.out.println("Modified task statement: " + result);
        } catch (IOException e) {
            System.out.println("Invalid tasktypeID " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}