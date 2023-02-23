import java.util.Arrays;

/**
 * Class that prints out a an array of arrays, each one sorted by RadixSorter.
**/
public class SortPrinter {
        public static void main(String args[]) {
                RadixSorter sorter = new RadixSorter();

                int[][] myArrays = {
                        {5, 7, 3, 2, 8},
                        { 15, 5, 20, 12, 80, 49, 2}
                };
                
                for (int[] arr : myArrays) {
                        sorter.radixSort(arr);
                        System.out.println(Arrays.toString(arr));
                }
                
              
              
              
        }
}