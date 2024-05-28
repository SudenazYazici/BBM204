import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

public class Quiz1 {
    public static String[] wordsToIgnore;
    public static void main(String[] args) throws IOException {

        // TODO: Use the first command line argument (args[0]) as the file to read the input from
        // TODO: Your code goes here
        // TODO: Print the solution to STDOUT

        Locale.setDefault(Locale.ENGLISH);

        List<String> lines = Files.readAllLines(Paths.get(args[0]));
        String[] array = new String[lines.size()];
        int border=0;
        for(int i=0; i<lines.size(); i++) {
            array[i] = lines.get(i).toLowerCase();
            if(array[i].equals("...")) {
                border = i;
            }
        }

        wordsToIgnore = new String[border];
        System.arraycopy(array, 0, wordsToIgnore, 0, border);
        ArrayList<String[]> sentences = new ArrayList<>();
        for(int i=border+1; i< array.length; i++) {
            String[] temp = array[i].split("\\s+");

            int index=0;
            while (index < temp.length) {

                if(isNotInContext(temp[index])) {
                    index++;
                    continue;
                }

                if(index == temp.length) {
                    break;
                }
                String[] temp2 = new String[2];
                temp2[0] = temp[index];
                temp2[1] = "";
                for(int k=0; k< temp.length; k++) {
                    if(k == index) {
                        temp2[1] += temp[k].toUpperCase() + " ";
                    } else {
                        temp2[1] += temp[k] + " ";
                    }
                }

                sentences.add(temp2);

                index++;

            }

        }
        for (int k = 0; k < sentences.size(); k++) { // insertion sort
            for (int m = k; m > 0; m--) {
                if(sentences.get(m)[0].compareTo(sentences.get(m - 1)[0]) < 0) {
                    Collections.swap(sentences,m,m-1);
                }
            }
        }

        for (int k = 0; k < sentences.size(); k++) {
            System.out.println(sentences.get(k)[1]);
        }

    }

    public static boolean isNotInContext(String str) {
        for(int k=0; k< wordsToIgnore.length; k++) {
            if(str.equals(wordsToIgnore[k])) {
                return true;
            }
        }
        return false;
    }
}
