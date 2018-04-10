/**
 *
 *  Description:    Contains 6 helper functions:
 *                  the quadratic probing insertion function, quadhash,
 *                  the double hashing insertion functions, doubhash1, doubhash2,
 *                  doubhash3 and doubhash4,
 *                  and the string to int converting function, strhash.
 * 
 * 
 **/

import java.io.*;

public class A2 {
    public static void main(String args[]) throws IOException{

        // Read all 2500 names from the codename text file
        String names [] = new String[2500];
        
        BufferedReader in = new BufferedReader(new FileReader("test.txt"));
        String line;
        
        for (int i = 0; i < 2500; i++){
            line = in.readLine();
            names[i] = line;
        }
        
        // Run the actual hashing methods
        System.out.println("Quadratic Probing:");
        quadhash(names,1,1,5200);
        quadhash(names,2,3,4900);
        quadhash(names,5,7,4700);
        System.out.println("\nDouble Hashing:");
        doubhash1(names,5000);
        doubhash2(names,4900);
        doubhash3(names,4600);
        doubhash4(names,11900);
        
    }
    
    /*
    *   Method: quadhash
    *   Desc:   The quadratic probing hashing method. I have generalized it to
    *           work with any c1 or c2. Outputs a line stating the average number
    *           of comparisons, the total comparisions needed, and the largest number
    *           of comparisons needed for a single string.
    */
    public static void quadhash(String[] names, int c1, int c2, int tablenum){
        int strhash, qcount, qhash;
        int counter = 0;
        int max = 0;
        String table [] = new String[tablenum];
        
        for (int j = 0; j < 2500; j++){
            strhash = (strhash(names[j])%tablenum);
            qcount = 0;
            qhash = strhash;
            while (qcount < tablenum && table[qhash]!= null && !table[qhash].isEmpty()){
                qcount++;
                qhash = (strhash+qcount*c1+qcount*qcount*c2)%tablenum;
            }
            if (table[qhash]== null)
                table[qhash] = names[j];
            
            counter += qcount+1; // add up the total number of comparisons used
            max = Math.max(qcount+1, max); // find the max comparison value
                
        }     
        System.out.println("Average number of comparisons with a table size of " 
                +tablenum+ " and c1 = "+c1+" c2 = "+c2+": "+(float)counter/2500+
                ", with total comparisons of "+counter+" and a max comparison of "+max);
    }
    
    /*
    *   Method: doubhash1
    *   Desc:   The first double hashing method. Uses h'(x) = x % table size and 
    *           h''(x) = (x%7)+1. Takes a list of strings (codenames) and a table size.
	*			Outputs a line stating the average number of comparisons,
    *           the total comparisions needed, and the largest number of comparisons 
    *           needed for a single string.
    *           (all mod numbers were chosen at random for all the double hashing
    *           functions, with the only requirement being that they were relatively
    *           prime to the table size)
    */
    public static void doubhash1(String[] names, int tablenum){
        int strhash, qcount, qhash, s0;
        int counter = 0;
        int max = 0;
        String table [] = new String[tablenum];
        
        for (int j = 0; j < 2500; j++){
            s0 = strhash(names[j]);
            strhash = s0 % tablenum; // h'(x) = x mod the size of the table
            qcount = 0;
            qhash = strhash;
            while (qcount < tablenum && table[qhash]!= null && !table[qhash].isEmpty()){
                qcount++;
                qhash = (strhash + qcount*(s0 % 7 +1))%tablenum; // +1 so h''(x) = 0 never happens
            }
            if (table[qhash]== null)
                table[qhash] = names[j];
            
            counter += qcount+1; // add up the total number of comparisons used
            max = Math.max(qcount+1, max); // find the max comparison value
                
        }     
        System.out.println("Average number of comparisons with a table size of " 
                +tablenum+ ", h(x) of x mod "+tablenum+" and h'(x) of x mod 7 + 1: "+(float)counter/2500+
                ", with total comparisons of "+counter+" and a max comparison of "+max);
    }
    
    /*
    *   Method: doubhash2
    *   Desc:   The second double hashing method. Uses h'(x) = x % table size and 
    *           h''(x) = (x%813)+1. Takes a list of strings (codenames) and a table size.
	*			Outputs a line stating the average number of comparisons,
    *           the total comparisions needed, and the largest number of comparisons 
    *           needed for a single string.
    */
    public static void doubhash2(String[] names, int tablenum){
        int strhash, qcount, qhash, s0;
        int counter = 0;
        int max = 0;
        String table [] = new String[tablenum];
        
        for (int j = 0; j < 2500; j++){
            s0 = strhash(names[j]);
            strhash = s0 % tablenum; // h'(x) = x mod the size of the table
            qcount = 0;
            qhash = strhash;
            while (qcount < tablenum && table[qhash]!= null && !table[qhash].isEmpty()){
                qcount++;
                qhash = (strhash + qcount*(s0 % 813+1))%tablenum; // +1 so h''(x) = 0 never happens
            }
            if (table[qhash]== null)
                table[qhash] = names[j];
            
            counter += qcount+1; // add up the total number of comparisons used
            max = Math.max(qcount+1, max); // find the max comparison value
                
        }     
        System.out.println("Average number of comparisons with a table size of " 
                +tablenum+ ", h(x) of x mod "+tablenum+" and h'(x) of x mod 813 + 1: "+(float)counter/2500+
                ", with total comparisons of "+counter+" and a max comparison of "+max);
    }
    
    /*
    *   Method: doubhash3
    *   Desc:   The first double hashing method. Uses h'(x) = x % table size and 
    *           h''(x) = (x%2997)+1. Takes a list of strings (codenames) and a table size.
	*			Outputs a line stating the average number of comparisons,
    *           the total comparisions needed, and the largest number of comparisons 
    *           needed for a single string.
    */
    public static void doubhash3(String[] names, int tablenum){
        int strhash, qcount, qhash,s0;
        int counter = 0;
        int max = 0;
        String table [] = new String[tablenum];
        
        for (int j = 0; j < 2500; j++){
            s0 = strhash(names[j]);
            strhash = s0 % tablenum; // h'(x) = x mod the size of the table
            qcount = 0;
            qhash = strhash;
            while (qcount < tablenum && table[qhash]!= null && !table[qhash].isEmpty()){
                qcount++;
                qhash = (strhash + qcount*(s0 % 2997+1))%tablenum; // +1 so h''(x) = 0 never happens
            }
            if (table[qhash]== null)
                table[qhash] = names[j];
            
            counter += qcount+1; // add up the total number of comparisons used
            max = Math.max(qcount+1, max); // find the max comparison value
                
        }     
        System.out.println("Average number of comparisons with a table size of " 
                +tablenum+ ", h(x) of x mod "+tablenum+" and h'(x) of x mod 2997 + 1: "+(float)counter/2500+
                ", with total comparisons of "+counter+" and a max comparison of "+max);
    }
     
    /*
    *   Method: doubhash4
    *   Desc:   The fourth double hashing method. More of a test to see what
    *           happens if we use a different function that just mod for h'(x)
    *           or h''(x).
    *           Uses h'(x) = x^2 % table size and h''(x) = (x%7)+1. 
    *           Takes a list of strings (codenames) and a table size.
	*			Outputs a line stating the average number of comparisons,
    *           the total comparisions needed, and the largest number of comparisons 
    *           needed for a single string.
    */
    public static void doubhash4(String[] names, int tablenum){
        int strhash, qcount, qhash, s0;
        int counter = 0;
        int max = 0;
        String table [] = new String[tablenum];
        
        for (int j = 0; j < 2500; j++){
            s0 = strhash(names[j])%tablenum; // added %tablenum as s0*s0 overflows otherwise :(
            strhash = (s0*s0)%tablenum; // h'(x) = x^2 mod the size of the table
            qcount = 0;
            qhash = strhash;
            while (qcount < tablenum && table[qhash]!= null && !table[qhash].isEmpty()){
                qcount++;
                qhash = (strhash + qcount*(s0 % 7+1))%tablenum;
            }
            if (table[qhash]== null)
                table[qhash] = names[j];
            
            counter += qcount+1; // add up the total number of comparisons used
            max = Math.max(qcount+1, max); // find the max comparison value
                
        }     
        System.out.println("Average number of comparisons with a table size of " 
                +tablenum+ ", h(x) of x^2 mod "+tablenum+" and h'(x) of x mod 7 + 1: "+(float)counter/2500+
                ", with total comparisons of "+counter+" and a max comparison of "+max);
    }
    
    /*
    *   Method: strhash
    *   Desc:   Converts a given string into an integer using the general method
    *           as described in the course notes. I picked an arbitrary number, 
    *           3, as the constant.
    */
    public static int strhash(String name){
        int a = 0;
        char x [] = name.toCharArray();
        for (int i = 0; i < name.length(); i++){
            a = a*3 + (int)x[i];
        }
        return a;
    }
    
}
