import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

public class Quiz3 {
    // THIS ALGORITHM IS NOT WORKING CORRECTLY
    public static void main(String[] args) throws IOException {

        // TODO: Use the first command line argument (args[0]) as the file to read the input from
        // TODO: Your code goes here
        // TODO: Print the solution to STDOUT

        List<String> lines = Files.readAllLines(Paths.get(args[0]));
        int testNum = Integer.parseInt(lines.get(0));
        double[] result = new double[testNum];
        int curIndex = 0;
        for(int i = 0; i < testNum; i++) {
            curIndex++;
            String[] strInfo = lines.get(curIndex).split(" ");
            int equipped = Integer.parseInt(strInfo[0]);
            int total = Integer.parseInt(strInfo[1]);
            ArrayList<ArrayList<Integer>> tempGraph = new ArrayList<>(total);
            ArrayList<ArrayList<Double>> doubleGraph = new ArrayList<>();

            for(int j = curIndex+1; j < curIndex+total+1; j++) { // all elements of one test case
                String[] strInfo2 = lines.get(j).split(" ");
                int x = Integer.parseInt(strInfo2[0]);
                int y = Integer.parseInt(strInfo2[1]);
                ArrayList<Integer> tempInfo = new ArrayList<>();
                tempInfo.add(x);
                tempInfo.add(y);
                tempGraph.add(tempInfo);
            }

            for(int j = 0; j < tempGraph.size(); j++) { // initializing all connections
                for(int k = 0; k < tempGraph.size(); k++) {
                    if(j==k) {
                        continue;
                    }
                    ArrayList<Double> tempInfo = new ArrayList<>();
                    tempInfo.add((double) j);
                    tempInfo.add((double) k);
                    double squareX = (tempGraph.get(j).get(0) - tempGraph.get(k).get(0))*(tempGraph.get(j).get(0) - tempGraph.get(k).get(0));
                    double squareY = (tempGraph.get(j).get(1) - tempGraph.get(k).get(1))*(tempGraph.get(j).get(1) - tempGraph.get(k).get(1));
                    double distance = Math.sqrt(squareX+squareY);
                    tempInfo.add(distance);
                    doubleGraph.add(tempInfo);

                }
            }
            doubleGraph.sort(Comparator.comparing(arr -> arr.get(arr.size() - 1)));
            ArrayList<ArrayList<Double>> doubleGraph2 = new ArrayList<>();
            for(int j = 0; j< doubleGraph.size(); j++) { // to not take same connection twice
                if(j % 2 == 0) {
                    doubleGraph2.add(doubleGraph.get(j));
                }
            }

            ArrayList<Double> tempResults = new ArrayList<>();
            int[] parent = new int[total];
            Arrays.fill(parent, -1);
            parent[doubleGraph2.get(0).get(0).intValue()] = doubleGraph2.get(0).get(0).intValue();
            for(int j = 0; j< doubleGraph2.size(); j++) {
                if(parent[doubleGraph2.get(j).get(0).intValue()] == -1 || parent[doubleGraph2.get(j).get(1).intValue()] == -1) {

                    tempResults.add(doubleGraph2.get(j).get(2));
                    if(parent[doubleGraph2.get(j).get(1).intValue()] == -1) {
                        parent[doubleGraph2.get(j).get(1).intValue()] = doubleGraph2.get(j).get(0).intValue();
                    }else {
                        parent[doubleGraph2.get(j).get(0).intValue()] = doubleGraph2.get(j).get(1).intValue();
                    }
                }
            }

            for(int j = 0; j< equipped-1; j++) {
                tempResults.remove(tempResults.size()-1);
            }

            result[i] = tempResults.get(tempResults.size()-1);
            curIndex += total;
        }

        for(int i = 0; i < result.length; i++) {
            System.out.printf(Locale.US, "%.2f", result[i] );
            System.out.println();
        }
    }
}
