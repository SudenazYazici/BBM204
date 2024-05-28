import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

public class Quiz2 {
    public static void main(String[] args) throws IOException {
        
        // TODO: Use the first command line argument (args[0]) as the file to read the input from
        // TODO: Your code goes here
        // TODO: Print the solution to STDOUT

        List<String> lines = Files.readAllLines(Paths.get(args[0]));
        String[] strInfo = lines.get(0).split(" ");
        int[] info = new int[strInfo.length];
        for(int i=0; i < strInfo.length; i++) {
            info[i] = Integer.parseInt(strInfo[i]);
        }

        if(info[0]<1 || info[0] > 10000 || info[1]<1 || info[1]>300) {
            System.exit(0);
        }

        String[] strWeights = lines.get(1).split(" ");
        int[] weights = new int[strWeights.length];
        for(int i=0; i < strWeights.length; i++) {
            weights[i] = Integer.parseInt(strWeights[i]);
            if(weights[i]<0 || weights[i]>100000) {
                System.exit(0);
            }
        }

        boolean[][] L = new boolean[info[0]+1][info[1]+1];
        for(int m=0; m < info[0]+1; m++) {
            for(int i=0; i < info[1]+1; i++) {
                if(i==0 && m==0) {
                    L[m][i] = true;
                } else if(i==0 && m>0){
                    L[m][i] = false;
                } else if(i>0 && weights[i-1]>m) {
                    L[m][i] = L[m][i-1];
                }else {
                    L[m][i] = L[m][i-1] || L[m-weights[i-1]][i-1];
                }
            }
        }
        for(int i=L.length-1; i>=0; i--) {
            if(L[i][L[0].length - 1]) {
                System.out.println(i);
                break;
            }
        }

        for(int m=0; m < info[0]+1; m++) {
            for(int i=0; i < info[1]+1; i++) {
                if(L[m][i]) {
                    System.out.print(1);
                }else {
                    System.out.print(0);
                }
            }
            System.out.println();
        }
    }
}
