public class RadixSorter {

    /**
     * TODO: Execute radix sort in-place on the given array.
    **/
    void radixSort(int array[]) {
        int max=this.getMax(array);
        String m=String.valueOf(max);
        int length=m.length();
        for(int i=0; i<length; i++){
            this.countingSort(array, i+1);
        }
    }

    /**
     * Does counting sort on the given array for the given digit.
     * digit is 10^(digit to sort by)
    **/
    void countingSort(int array[], int digit) {
        //does this implementation just not work or am I stupid?
        if(digit==1){
            // Array to temporarily store sorted array
            int output[] = new int[array.length];
            // Stores how many elements we have of each digit
            int count[] = new int[10];

            // Count how many elements we have of each digit
            for (int num : array) {
                    count[(num / digit) % 10]++;
            }

            // Count how many elements we have of each digit less than or equal to itself
            for (int i = 1; i < 10; i++) {
                    count[i] += count[i - 1];
            }

            // Sort the array by digit
            for (int i = array.length - 1; i >= 0; i--) {
                    int num = (array[i] / digit) % 10;
                    output[count[num] - 1] = array[i];
                    count[num]--;
            }
            
                // Copy output array into original array
            for (int i = 0; i < array.length; i++) {
                    array[i] = output[i];
            }
        }
        //Fine, I'll do it myself
        else{
            int endpos[]=new int[10];
            int output[]=new int[array.length];
            int count[]=new int[10];
            for(int i=0; i<array.length; i++){
                String[] digits=String.valueOf(array[i]).split("(?<=.)");
                if(digits.length<digit){
                    count[0]++;
                }
                else{
                    int num=Integer.valueOf(digits[digits.length-digit]);
                    count[num]++;
                }
            }
            int currentPos=-1;
            for(int i=0; i<count.length; i++){
                currentPos+=count[i];
                endpos[i]=currentPos;
            }
            for(int i=array.length-1; i>=0; i--){
                String[] digits=String.valueOf(array[i]).split("(?<=.)");
                if(digits.length<digit){
                    output[endpos[0]]=array[i];
                    endpos[0]--;
                }
                else{
                    int num=Integer.valueOf(digits[digits.length-digit]);
                    output[endpos[num]]=array[i];
                    endpos[num]--;
                }
            }
            for(int i=0; i<array.length; i++){
                array[i]=output[i];
            }
        }
        
    }

    /**
     * Get the maximal element of a given array
    **/
    int getMax(int array[]) {
            int max = array[0];
            for (int num : array) {
                    max = num > max ? num : max;
            }
            return max;
    }

    public static void main(String[] args) {
            System.out.println("This is RadixSorter");
    }
}
