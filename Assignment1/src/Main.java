import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

class Main {

    static int[] inputAxis = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};
    static double[][] yAxis = new double[3][10];
    public static void main(String args[]) throws IOException {

        System.out.println("Random Input Data");
        System.out.println("--------------------");
        sortRandom(500,0);
        sortRandom(1000,1);
        sortRandom(2000,2);
        sortRandom(4000,3);
        sortRandom(8000,4);
        sortRandom(16000,5);
        sortRandom(32000,6);
        sortRandom(64000,7);
        sortRandom(128000,8);
        sortRandom(250000,9);
        showAndSaveChart("Tests on Random Data", inputAxis, yAxis);

        System.out.println("Sorted Input Data");
        System.out.println("--------------------");
        sortSorted(500,0);
        sortSorted(1000,1);
        sortSorted(2000,2);
        sortSorted(4000,3);
        sortSorted(8000,4);
        sortSorted(16000,5);
        sortSorted(32000,6);
        sortSorted(64000,7);
        sortSorted(128000,8);
        sortSorted(250000,9);
        showAndSaveChart("Tests on Sorted Data", inputAxis, yAxis);

        System.out.println("Reversely Sorted Input Data");
        System.out.println("--------------------");
        sortReverse(500,0);
        sortReverse(1000,1);
        sortReverse(2000,2);
        sortReverse(4000,3);
        sortReverse(8000,4);
        sortReverse(16000,5);
        sortReverse(32000,6);
        sortReverse(64000,7);
        sortReverse(128000,8);
        sortReverse(250000,9);
        showAndSaveChart("Tests on Reversely Sorted Data", inputAxis, yAxis);

        System.out.println("Searching Algorithms");
        System.out.println("--------------------");
        search(500,0);
        search(1000,1);
        search(2000,2);
        search(4000,3);
        search(8000,4);
        search(16000,5);
        search(32000,6);
        search(64000,7);
        search(128000,8);
        search(250000,9);
        showAndSaveChartForSearching("Tests on Searching Algorithms", inputAxis, yAxis);

    }

    public static void showAndSaveChart(String title, int[] xAxis, double[][] yAxis) throws IOException {
        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
                .yAxisTitle("Time in Milliseconds").xAxisTitle("Input Size").build();

        // Convert x axis to double[]
        double[] doubleX = Arrays.stream(xAxis).asDoubleStream().toArray();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Add a plot for a sorting algorithm
        chart.addSeries("Insertion sort", doubleX, yAxis[0]);
        chart.addSeries("Merge sort", doubleX, yAxis[1]);
        chart.addSeries("Counting sort", doubleX, yAxis[2]);


        // Save the chart as PNG
        BitmapEncoder.saveBitmap(chart, title + ".png", BitmapEncoder.BitmapFormat.PNG);

        // Show the chart
        new SwingWrapper(chart).displayChart();
    }

    public static void showAndSaveChartForSearching(String title, int[] xAxis, double[][] yAxis) throws IOException {
        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
                .yAxisTitle("Time in Nanoseconds").xAxisTitle("Input Size").build();

        // Convert x axis to double[]
        double[] doubleX = Arrays.stream(xAxis).asDoubleStream().toArray();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Add a plot for a sorting algorithm
        chart.addSeries("Linear search (random data)", doubleX, yAxis[0]);
        chart.addSeries("Linear search (sorted data)", doubleX, yAxis[1]);
        chart.addSeries("Binary search (sorted data)", doubleX, yAxis[2]);


        // Save the chart as PNG
        BitmapEncoder.saveBitmap(chart, title + ".png", BitmapEncoder.BitmapFormat.PNG);

        // Show the chart
        new SwingWrapper(chart).displayChart();
    }

    public static int[] reverseArray(int[] array) {
        int[] temp = new int[array.length];
        for(int i= array.length-1; i>=0; i--) {
            temp[array.length-1-i] = array[i];
        }
        return temp;
    }

    public static void sortRandom(int n, int y) {
        Sorting sorting = new Sorting();
        int[] originalArray = FileOperations.readData("src/TrafficFlowDataset.csv", n);
        long insertionTime = 0;
        long mergeTime = 0;
        long countingTime = 0;
        for(int i=0; i<10; i++) {
            int[] tempArray = new int[n];
            System.arraycopy(originalArray, 0, tempArray, 0, n);
            long time1 = System.currentTimeMillis();
            sorting.InsertionSort(tempArray);
            long time2 = System.currentTimeMillis();

            System.arraycopy(originalArray, 0, tempArray, 0, n);
            long time3 = System.currentTimeMillis();
            sorting.MergeSort(tempArray);
            long time4 = System.currentTimeMillis();

            System.arraycopy(originalArray, 0, tempArray, 0, n);
            long time5 = System.currentTimeMillis();
            sorting.CountingSort(tempArray);
            long time6 = System.currentTimeMillis();

            insertionTime = insertionTime + (time2-time1);
            mergeTime = mergeTime + (time4-time3);
            countingTime = countingTime + (time6-time5);
        }
        insertionTime = insertionTime/10;
        mergeTime = mergeTime/10;
        countingTime = countingTime/10;
        System.out.print("insertion: "+insertionTime + " ");
        yAxis[0][y] = insertionTime;
        System.out.print("merge: "+mergeTime + " ");
        yAxis[1][y] = mergeTime;
        System.out.println("counting: "+countingTime);
        yAxis[2][y] = countingTime;
    }

    public static void sortSorted(int n, int y) {
        Sorting sorting = new Sorting();
        int [] originalArray = FileOperations.readData("src/TrafficFlowDataset.csv", n);
        Arrays.sort(originalArray);
        long insertionTime = 0;
        long mergeTime = 0;
        long countingTime = 0;
        for(int i=0; i<10; i++) {
            int[] tempArray = new int[n];
            System.arraycopy(originalArray, 0, tempArray, 0, n);
            long time1 = System.currentTimeMillis();
            sorting.InsertionSort(tempArray);
            long time2 = System.currentTimeMillis();

            System.arraycopy(originalArray, 0, tempArray, 0, n);
            long time3 = System.currentTimeMillis();
            sorting.MergeSort(tempArray);
            long time4 = System.currentTimeMillis();

            System.arraycopy(originalArray, 0, tempArray, 0, n);
            long time5 = System.currentTimeMillis();
            sorting.CountingSort(tempArray);
            long time6 = System.currentTimeMillis();

            insertionTime = insertionTime + (time2-time1);
            mergeTime = mergeTime + (time4-time3);
            countingTime = countingTime + (time6-time5);
        }
        insertionTime = insertionTime/10;
        mergeTime = mergeTime/10;
        countingTime = countingTime/10;
        System.out.print("insertion: "+insertionTime + " ");
        yAxis[0][y] = insertionTime;
        System.out.print("merge: "+mergeTime + " ");
        yAxis[1][y] = mergeTime;
        System.out.println("counting: "+countingTime);
        yAxis[2][y] = countingTime;
    }

    public static void sortReverse(int n, int y) {
        Sorting sorting = new Sorting();
        int [] originalArray = FileOperations.readData("src/TrafficFlowDataset.csv", n);
        Arrays.sort(originalArray);
        originalArray = reverseArray(originalArray);
        long insertionTime = 0;
        long mergeTime = 0;
        long countingTime = 0;
        for(int i=0; i<10; i++) {
            int[] tempArray = new int[n];
            System.arraycopy(originalArray, 0, tempArray, 0, n);
            long time1 = System.currentTimeMillis();
            sorting.InsertionSort(tempArray);
            long time2 = System.currentTimeMillis();

            System.arraycopy(originalArray, 0, tempArray, 0, n);
            long time3 = System.currentTimeMillis();
            sorting.MergeSort(tempArray);
            long time4 = System.currentTimeMillis();

            System.arraycopy(originalArray, 0, tempArray, 0, n);
            long time5 = System.currentTimeMillis();
            sorting.CountingSort(tempArray);
            long time6 = System.currentTimeMillis();

            insertionTime = insertionTime + (time2-time1);
            mergeTime = mergeTime + (time4-time3);
            countingTime = countingTime + (time6-time5);
        }
        insertionTime = insertionTime/10;
        mergeTime = mergeTime/10;
        countingTime = countingTime/10;
        System.out.print("insertion: "+insertionTime + " ");
        yAxis[0][y] = insertionTime;
        System.out.print("merge: "+mergeTime + " ");
        yAxis[1][y] = mergeTime;
        System.out.println("counting: "+countingTime);
        yAxis[2][y] = countingTime;
    }

    public static void search(int n, int y) {
        Searching searching = new Searching();
        int [] originalArray = FileOperations.readData("src/TrafficFlowDataset.csv", n);
        long linearRandom = 0;
        long linearSorted = 0;
        long binarySorted = 0;
        int[] tempArray = new int[n];
        System.arraycopy(originalArray, 0, tempArray, 0, n);
        Arrays.sort(tempArray);
        for(int i=0; i<1000; i++) {
            int rnd = new Random().nextInt(originalArray.length);
            long time1 = System.nanoTime();
            searching.LinearSearch(originalArray, originalArray[rnd]);
            long time2 = System.nanoTime();

            long time3 = System.nanoTime();
            searching.LinearSearch(tempArray, originalArray[rnd]);
            long time4 = System.nanoTime();

            long time5 = System.nanoTime();
            searching.BinarySearch(tempArray, originalArray[rnd]);
            long time6 = System.nanoTime();

            linearRandom = linearRandom + (time2-time1);
            linearSorted = linearSorted + (time4-time3);
            binarySorted = binarySorted + (time6-time5);
        }
        linearRandom = linearRandom/1000;
        linearSorted = linearSorted/1000;
        binarySorted = binarySorted/1000;
        System.out.print("linearRandom: "+linearRandom + " ");
        yAxis[0][y] = linearRandom;
        System.out.print("linearSorted: "+linearSorted + " ");
        yAxis[1][y] = linearSorted;
        System.out.println("binarySorted: "+binarySorted);
        yAxis[2][y] = binarySorted;
    }
}
