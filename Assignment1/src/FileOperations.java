import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileOperations {

    public static int[] readData(String path, int lineNum) { // lineNum equals to number of lines we want to analyze
        try{
            List<String> lines = Files.readAllLines(Paths.get(path));
            int[] resultArray = new int[lineNum];
            for (int i=1;i<lineNum;i++) { // iterating over every line of input file
                String[] temp = lines.get(i).split(",");
                resultArray[i-1] = Integer.parseInt(temp[6]);
            }
            return resultArray;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
