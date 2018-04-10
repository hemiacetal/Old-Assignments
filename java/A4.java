/*              
 * Description:    Sees if a word is a palindrome or not.
 */

import java.util.Scanner;

class A4
{
 public static void main (String[] args)
 {
   String user;
   String word;
   char wchar[];
   int length;
   Scanner meow = new Scanner (System.in);
   int n = 0;
   
   user = meow.nextLine();
   word = user.toUpperCase();
   
   wchar = word.toCharArray();
   length = word.length();
   char bchar[] = new char[length];
   
   for (int i = length-1; i>=0; i--) // Character arrays and decending values, if backwards = forwards it works.
   {
     bchar [n] = wchar [i];
     n++;
   }
   
   String backward = new String (bchar);
   
   if (backward.equals(word) && length > 1)
   {
    System.out.println(word + " is a palindrome.");
   }
   else if (length <= 1)
   {
    System.out.println("A word longer than 1 letter, please.");
   }
   else
   {
    System.out.println(word + " is not a palindrome.");
   }  
 }// main method
} // RenV_Strings3 class