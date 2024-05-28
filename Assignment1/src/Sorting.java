import java.util.Arrays;

public class Sorting {
    public void InsertionSort(int [] array) {
        for(int j=1; j<array.length; j++) {
            int key = array[j];
            int i = j-1;
            while (i>=0 && array[i]>key) {
                array[i+1] = array[i];
                i--;
            }
            array[i+1] = key;
        }
    }

    public int[] MergeSort(int [] array) {
        int n = array.length;
        if(n<=1) {
            return array;
        }
        int[] left = Arrays.copyOfRange(array, 0, n/2);
        int[] right = Arrays.copyOfRange(array, (n/2), n);
        left = MergeSort(left);
        right = MergeSort(right);
        return Merge(left, right);
    }

    public int[] Merge(int [] arrayA, int [] arrayB) {
        int[] arrayC = new int[arrayA.length + arrayB.length];
        int arrayIndex = 0;
        int arrayIndexA = 0;
        int arrayIndexB = 0;

        while (arrayIndexA != arrayA.length && arrayIndexB != arrayB.length) {
            if(arrayA[arrayIndexA] > arrayB[arrayIndexB]) {
                arrayC[arrayIndex] = arrayB[arrayIndexB];
                arrayIndex++;
                arrayIndexB++;
            } else {
                arrayC[arrayIndex] = arrayA[arrayIndexA];
                arrayIndex++;
                arrayIndexA++;
            }
        }

        while (arrayIndexA < arrayA.length) {
            arrayC[arrayIndex] = arrayA[arrayIndexA];
            arrayIndex++;
            arrayIndexA++;
        }

        while(arrayIndexB < arrayB.length) {
            arrayC[arrayIndex] = arrayB[arrayIndexB];
            arrayIndex++;
            arrayIndexB++;
        }
        return arrayC;
    }

    public int[] CountingSort(int [] array) {
        int k = array[0];
        for(int i=1; i<array.length; i++) {
            if(array[i] > k) {
                k = array[i];
            }
        }
        int[] count = new int[k+1];
        Arrays.fill(count, 0);
        int[] output = new int[array.length];
        int size = array.length;

        for(int i=0; i<size; i++) {
            count[array[i]]++;
        }
        for(int i=1; i<k+1; i++) {
            count[i] = count[i] + count[i-1];
        }
        for(int i=size-1; i>=0; i--) {
            count[array[i]]--;
            output[count[array[i]]] = array[i];
        }
        return output;
    }
}
