/**
 * 	            bin_search and trin_search as provided by pseudocode given by Dr. Robin W. Dawes
 *
 *  Description:    There are a total of 6 methods:
 *                  expt1 and expt2 run the cases of Experiment 1 and Experiment
 *                  2, respectively, as explained in the assignment.
 *                  mergesort and merge collectively are used to sort any
 *                  array of integers through the merge sort algorithm.
 *                  bin_search and trin_search are the binary and ternary search
 *                  methods, as given/outlined by the assignment.
 * 
 **/

import java.time.Clock;

public class A1 {
    
    public static void main(String[] args){
        
       // for merge sort testing
       /* int testarray[] = new int[1000];
        for (int j = 0; j < testarray.length; j++){
            testarray[j] = (int)(Math.random() * 1000);
        } 
        
        int sorted[] = mergesort(testarray,testarray.length);
        for (int i = 0; i < testarray.length; i++)
            System.out.println(sorted[i]); */
       
       // Just calls the experiments. Nothing to see here.
       expt1();
       expt2();        
    }
    
    /*
    *
    *   Method: expt1
    *   Desc:   Experiment 1 searches 10 instances of each value in the query
    *           array using both binary and ternary search, outputting the total
    *           search time for all searches in milliseconds. 
    *           Specifically in this case: Random numbers from 0 - 1000 are put
    *           into the query and run through merge sort. 
    *           I have also gone up to 1024000 for the value of n. (11 cases total)
    *
    *   Input:  N/A
    *   Output: Prints to the screen the runtime results of Experiment 1 in
    *           a nice, easy-to-read graph format. All time values are in milliseconds.
    *
    */
    public static void expt1(){
        
        // these are the n values.
        int nMultiplier[] = {1000,2000,4000,8000,16000,32000,64000,128000,256000,512000,1024000};
        // n amount of random ints will be placed in nrandom, the query array
        int nrandom[];
        // n*100 search values will be placed in searchval
        int searchval[];
        int ncounter;
        // the clock, to measure time
        Clock sysclock = Clock.systemDefaultZone();
        long start_time;
        
        System.out.println("Experiment 1\t| Binary Search Time (ms)\t| Ternary Search Time (ms)");
        System.out.println("----------------------------------------------------------------------------");
        
        // runs for all cases of given n values
        for (int i = 0; i < nMultiplier.length; i++){
            nrandom = new int[nMultiplier[i]];
            searchval = new int[nMultiplier[i]*10];
            ncounter = 0;
            
            // generates all random int values ranging from 0 - 1000 into
            // the query array
            for (int j = 0; j < nMultiplier[i]; j++){
                nrandom[j] = (int)(Math.random() * 1000);
            }
            
            // merge sort the query array
            nrandom = mergesort(nrandom,nMultiplier[i]);
            
            // for each n, put 10 instances of it into the search array 
            // the only reason i used searchval.len/nMult was because I wanted
            // to see if I could get better results with more search values
            for (int k = 0; k < nrandom.length; k++){
               for (int l = 0; l < searchval.length/nMultiplier[i]; l++){
                   searchval[ncounter] = nrandom[k];
                   ncounter++;
               }
            }
            
            System.out.print("n = " + nMultiplier[i]+"\t|");
            
            // time the binary search in milliseconds and print total time
            start_time = sysclock.millis();
            for (int p = 0; p < searchval.length; p++){
                bin_search (nrandom,0, nrandom.length-1,searchval[p]);
            } 
            
            System.out.print(sysclock.millis() - start_time + "\t\t\t\t|");  
            
            // time the ternary search in milliseconds and print total time
            start_time = sysclock.millis();
            for (int p = 0; p < searchval.length; p++){
                trin_search (nrandom,0, nrandom.length-1,searchval[p]);
            } 
            
            System.out.print(sysclock.millis() - start_time+"\n");
            System.out.println("----------------------------------------------------------------------------");
        }
    }
    
    /*
    *
    *   Method: expt2
    *   Desc:   Experiment 2 is basically the same as Experiment 1 except it
    *           uses values that are not in the query array for the search.
    *           Specifically in this case: all values in the query array are odd
    *           integer values that go up to 1000. Values that are being searched
    *           for are even and also go up to 1000.
    *           I have also gone up to 1024000 for the value of n. (11 cases total)
    *
    *   Input:  N/A
    *   Output: Prints to the screen the runtime results of Experiment 2 in
    *           a nice, easy-to-read graph format. All time values are in milliseconds.
    *
    */
    public static void expt2(){
        
        // all variables in expt2 are similar to that of expt1, with the exception
        // of confirmeven, a placeholder int to check if a number is even or odd.  
        int nMultiplier[] = {1000,2000,4000,8000,16000,32000,64000,128000,256000,512000,1024000};
        int nrandom[];
        int searchval[];
        
        Clock sysclock = Clock.systemDefaultZone();
        
        int confirmeven;
        
        long start_time;
        
        System.out.println("\n\nExperiment 2\t| Binary Search Time (ms)\t| Ternary Search Time (ms)");
        System.out.println("----------------------------------------------------------------------------");
        
        for (int i = 0; i < nMultiplier.length; i++){
            nrandom = new int[nMultiplier[i]];
            searchval = new int[nMultiplier[i]*10];
            
            // for all the n values, add only random odd numbers into the array
            for (int j = 0; j < nMultiplier[i]; j++){
                confirmeven = (int)(Math.random() * 1000);
                while (confirmeven %2 == 0)
                    confirmeven = (int)(Math.random() * 1000);
                nrandom[j] = confirmeven;
            }
            
            nrandom = mergesort(nrandom,nMultiplier[i]);
            
            // for all the search values, add only random even numbers into the array
            for (int m = 0; m < searchval.length; m++){
                confirmeven = (int)(Math.random() * 1000);
                while (confirmeven %2 != 0)
                    confirmeven = (int)(Math.random() * 1000);
                searchval[m] = confirmeven;
            }
            
            System.out.print("n = " + nMultiplier[i]+"\t|");
            
            start_time = sysclock.millis();
            for (int p = 0; p < searchval.length; p++){
                bin_search (nrandom,0, nrandom.length-1,searchval[p]);
            } 
            
            System.out.print(sysclock.millis() - start_time + "\t\t\t\t|");  
            
            start_time = sysclock.millis();
            for (int p = 0; p < searchval.length; p++){
                trin_search (nrandom,0, nrandom.length-1,searchval[p]);
            } 
            
            System.out.print(sysclock.millis() - start_time+"\n");
            System.out.println("----------------------------------------------------------------------------");
        }
    }
    
    /*
    *
    *   Method: mergesort
    *   Desc:   Recursively sorts an integer array (in ascending order) 
    *           with the merge sort algorithm. Uses the aid of the merge method.
    *
    *   Input:  nrandom, an integer array
    *           length, an integer indicating the length of nrandom
    *   Output: the sorted integer array. 
    *
    */   
    public static int[] mergesort(int[] nrandom, int length){
        
        // Base cases: there is nothing or only one integer in the array.
        // (the nothing part will never happen in this program though)
        switch (length) {
            case 0:
                return nrandom;
            case 1:
                return nrandom;
            default:
                // all other cases. split the array based its given length.
                int firsthalf[];
                int secondhalf[];
                int flength = length/2;
                int slength = length/2;
                
                if (length%2 != 0){
                    // length does not split evenly. I decided that the second half
                    // of the array gets the extra length.
                    slength++;
                } 
                // else, length splits evenly. doing nothing means just spliting
                // the array into two halves.
                firsthalf = new int[flength];
                secondhalf = new int[slength];
                
                System.arraycopy (nrandom, 0, firsthalf, 0, flength);
                System.arraycopy (nrandom, flength, secondhalf, 0, slength);
                
                // Recurse through the array to merge starting from each length 1
                firsthalf = mergesort(firsthalf,flength);
                secondhalf = mergesort(secondhalf,slength);
                
                // return the sorted merge of the halves.
                return merge(firsthalf, secondhalf, flength, slength);
        }
            
    }

    /*
    *
    *   Method: merge
    *   Desc:   Takes the two given int arrays and merges them. Recursive nature 
    *           of mergesort + conditions of the merge ensures that it will be
    *           completed in ascending order. 
    *
    *   Input:  fhalf, the "first half" of the split integer array
    *           shalf, the "second half" of the split integer array
    *           flength, the length of fhalf
    *           slength, the length of shalf
    *   Output: the sorted merge of the two integer arrays.          
    *
    */  
    public static int[] merge(int[] fhalf, int[] shalf, int flength, int slength){
        
        // fhalf is the first half to merge, shalf is the second
        
        int length = flength + slength;
        
        int[] merged = new int[length];
        
        // need counters for while loop
        int fcount = 0;
        int scount = 0;
        int lencount = 0;
        
        while (lencount < length){
            // loop until the merged array is full
            if (fcount < flength && scount < slength){
                // both halves contain values
                if (fhalf[fcount] < shalf[scount]){
                    // current fhalf is smaller than current shalf,
                    // add the smaller value to the array first, b/c ascending
                    // order.
                    
                    merged[lencount] = fhalf[fcount];
                    lencount++;
                    fcount++;
                }
                else if (fhalf[fcount] == shalf[scount]){
                    // current fhalf and shalf is equal, just add both to the
                    // merged array. technically this case is equivalent to just 
                    // the else case + another go of the while loop, 
                    // not really necessary to include this. same results either 
                    // way. I just wanted to add it for no reason
                    merged[lencount] = fhalf[fcount];
                    merged[lencount+1] = shalf[scount];
                    lencount = lencount+2;
                    fcount++;
                    scount++;
                }
                else{
                    // current shalf is smaller, add it to the array first
                    merged[lencount] = shalf[scount];
                    lencount++;
                    scount++;
                }
            }
            else{
                if (fcount < flength){
                    // only fhalf has values left
                    merged[lencount] = fhalf[fcount];
                    lencount++;
                    fcount++;
                }
                if (scount < slength){
                    // only shalf has values left
                    merged[lencount] = shalf[scount];
                    lencount++;
                    scount++;
                }
            }
           
                
        }
        
        return merged;
    }

    /*
    *
    *   Method: bin_search
    *   Desc:   As given within the assignment, modified for Java. 
    *           A binary search.
    *
    *   Input:  A, the query integer array
    *           first, the first integer value to look at in the array
    *           last, the last integer value to look at in the array
    *           target, the integer value to search for
    *   Output: the index of target in A, -1 if target is not found.
    *   
    */      
    public static int bin_search(int[] A, int first, int last, int target) {
    // returns index of target in A, if present
    // returns -1 if target is not present in A
        if (first > last)
           return -1;
        else {
            int mid = (first+last)/2;
            if (A[mid] == target)
                return mid;
            else if (A[mid] > target)
                return bin_search(A,first,mid-1,target);
            else
                return bin_search(A,mid+1,last,target);
        }
    } 

    /*
    *
    *   Method: trin_search
    *   Desc:   Refined from the pseudo-code as found within the assignment.
    *           A ternary search.
    *
    *   Input:  A, the query integer array
    *           first, the first integer value to look at in the array
    *           last, the last integer value to look at in the array
    *           target, the integer value to search for
    *   Output: the index of target in A, -1 if target is not found.
    *   
    */ 
    public static int trin_search(int[] A,int first,int last,int target){
    // returns index of target in A, if present
    // returns -1 if target is not present in A
        if (first > last)
            return -1;
        else {
            int one_third = first + (last-first)/3;
            int two_thirds = first + 2*(last-first)/3;
        
            if (A[one_third] == target)
                return one_third;
            else if (A[one_third] > target){
                // search the left-hand third
                return trin_search(A,first,one_third-1,target);
            }     
            else if (A[two_thirds] == target)
                return two_thirds;
            else if (A[two_thirds] > target){
                // search the middle third
                return trin_search(A,one_third+1,two_thirds-1,target);
            }
            else{
                // search the right-hand third
                return trin_search(A,two_thirds+1,last,target);
            }
        }
    }
}

