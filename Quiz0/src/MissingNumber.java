import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class MissingNumber {

    public static void main(String[] args) throws IOException {

        // First command-line argument refers to the number of integers
        // Second command-line argument contains the path to the input file
        // Your program should only print a single integer to the standard output
        // For the sample input, your output should be:
        // 2

        int n = Integer.parseInt(args[0]);
        List<String> lines = Files.readAllLines(Paths.get(args[1]));
        lines.replaceAll(String::trim);
        String[] temp = lines.get(0).trim().split(" ");
        int[] numbers = new int[temp.length];
        for(int i=0; i<temp.length; i++) {
            if (!temp[i].isEmpty() && temp[i] != "\n") {
                numbers[i] = Integer.parseInt(temp[i]);
            }
        }
        Arrays.sort(numbers);
        for(int i=0; i<numbers.length; i++){
            if(numbers[i] != i) {
                System.out.println(i);
                break;
            }
        }

    }

}