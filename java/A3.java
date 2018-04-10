// The "VortexCipher" class.
import java.awt.*;
import hsa.Console;
import java.io.*;

public class A3
{
    static Console c;           // The output console

    public static void main (String[] args)throws IOException
    {
	c = new Console ();

	//String[] word = new String [i];
	
	FileReader fr = new FileReader ("DATA41.txt");
	FileWriter fw = new FileWriter ("OUTPUT41.txt");
	PrintWriter pw = new PrintWriter (fw);
	BufferedReader br = new BufferedReader (fr);

	String word = c.readString(); 
	char wordchar[] = word.toCharArray ();

	if (word.length () == 1)
	{
	    if (wordchar [0] == 97)
	    {
		wordchar [0] = 122;
	    }
	    else
	    {
		wordchar [0] = (char) (wordchar [0] - 1);
	    }
	    pw.print (wordchar [0]);
	    pw.close();

	}

	if (word.length () == 2)
	{
	    if (wordchar [0] == 97 && wordchar [1] == 97)
	    {
		wordchar [0] = (char) 122;
		wordchar [1] = (char) 122;
	    }
	    else if (wordchar [1] == 97)
	    {
		wordchar [1] = (char) 122;
		wordchar [0] = (char) (word.charAt (0) - 1);
	    }
	    else if (wordchar [0] == 97)
	    {
		wordchar [0] = (char) 122;
		wordchar [1] = (char) (word.charAt (1) - 1);
	    }
	    else
	    {
		wordchar [0] = (char) (word.charAt (0) - 1);
		wordchar [1] = (char) (word.charAt (1) - 1);
	    }
	    char x = wordchar [0];
	    wordchar [0] = wordchar [1];
	    wordchar [1] = x;
	    word = new String (wordchar);
	    pw.print (word);
	    pw.close();
	}

	if (word.length () == 3)
	{
	}



    } // main method
} // VortexCipher class
