/**
 * File name: RenV_Assignment1.java
 * Author: Vivian Ren
 * Date: November 16 - 26, 2012
 * Description: A Tic Tac Toe game.
 */

import java.io.*;
import java.util.Scanner;

class RenV_Assignment1
{
  
  /** 
   * Main method.
   * All this does is refer to the menu method.
   * 
   * @param none 
   * @return none
   */
  public static void main (String [] args) throws IOException
  {
    menu(0);
  } // Main method
  
  /** 
   * Instructions method.
   * Just outputs instructions to the user.
   * 
   * @param none
   * @return none
   */
  public static void instructions ()
  {
    System.out.println("Welcome to Vivian's Tic Tac Toe game!");
    System.out.println("How to play:");
    System.out.println("The numbers on the left side of the board are the rows while");
    System.out.println("the numbers on the right side are the columns.");
    System.out.println("Use these numbers to enter the position you want to place");
    System.out.println("your x or o! First one to get a certain number in a row wins.");
    System.out.println("(Really, once the board size is greater than 4x4, it's Connect Four.)\n");
  } // Instructions method
  
  /** 
   * Menu method.
   * Refers to other methods based on the user's choice.
   * 
   * @param secret A number that does something to the menu if you win against the computer 
   * @return none
   */
  public static void menu (int secret)throws IOException
  {
    String userchoicechk;
    Scanner inp = new Scanner (System.in);
    int userchoice;
    FileReader fr;
    BufferedReader br;
    FileWriter fw;
    BufferedWriter bw;
    String line;
    String [] secretlines;
    
    if (secret == 0)
    {
      instructions();
    }
    else
    {
     secretlines = ancii();
     for (int i = 0; i < secretlines.length; i++)
       System.out.println(secretlines[i]);
     System.out.println("");
    }
    
    while (true) // Loop until user gives a valid number
    {
      System.out.println("Enter 1 to start a game of Tic Tac Toe, 2 for the settings or 0 to exit."); 
      userchoice = -1;
      
      while (userchoice != 0 || userchoice != 1 || userchoice !=2)    
      {
        userchoicechk = inp.nextLine();
        userchoice = isnumber(userchoicechk); // To error checking method.
        
        if (userchoice == 0 || userchoice == 1 || userchoice == 2)
        {
          break;
        }
        else
        {
          System.out.println("That's not a 0, 1, nor a 2. Try again.");
        }
      }
      
      if (userchoice == 0) // Exit game choice
      {
        System.out.println("Created by Vivian Ren, 2012.");
        try
        {
          Thread.sleep(1000);
        }
        catch(InterruptedException ex){}
        System.exit(userchoice);
      }
      else if (userchoice == 1) // Game selection choice
      {
        System.out.println("You appear to have decided to start a game.");
        System.out.println("Enter 1 for single player, 2 for two players, 0 to return to the menu.");
        while (userchoice != 0 || userchoice != 1 || userchoice != 2)
        {
          userchoicechk = inp.nextLine();
          userchoice = isnumber(userchoicechk);
          
          if (userchoice == 0 || userchoice == 1 || userchoice == 2)
          {
            break;
          }
          else
          {
            System.out.println("That's not a 0, 1, nor a 2. Try again.");
          }
        }
        if (userchoice == 0)
        {
          System.out.println("Back to the menu.");
        }
        else if (userchoice == 1 || userchoice == 2)
        {
          if (userchoice == 1)
            System.out.println("Starting a player vs. computer game.\n");
          else
            System.out.println("Starting a multiplayer game.\n");
          game(userchoice); // To the game method, uses the user's selection for single player or multiplayer.
          break;
        }
        
      }
      else if (userchoice == 2) // Settings choice.
      {
        fr = new FileReader ("settings.txt");
        br = new BufferedReader (fr);
        line = br.readLine();
   
        System.out.print("The current dimensions of the board is "); // Read the current dimensions in the settings.txt file, output to user
   
        for (int i = 0; i < 2; i++)
        {
          if (i < 1)
          {
            System.out.print(line + "x");
            line = br.readLine();
          }
          else
            System.out.println(line + ".");
        }
        fw = new FileWriter ("settings.txt"); // Clear the current file for new input.
        bw = new BufferedWriter (fw);
        settings(); // To the settings method to re-enter dimensions.
      }
    }
  }
  
  /** 
   * Settings method.
   * Depending on the dimensions the user inputs, outputs values to the "settings.txt" file, and later uses these values
   * to configure board size.
   * 
   * @param none
   * @return none
   */
  public static void settings () throws IOException
  {
    Scanner inp = new Scanner (System.in);
    int userchoice = 0;
    final String DEFAULT = "3"; // Default size is 3x3.
    String userchoicechk;
    
    FileWriter fw = new FileWriter ("settings.txt");
    BufferedWriter bw = new BufferedWriter (fw);
    
    while(true)
    {
      System.out.println("Enter 0 for default board size (3x3), or 1 to change the size");
      userchoicechk = inp.nextLine();
      userchoice = isnumber(userchoicechk);
      
      if (userchoice == 0) // Write to settings.txt the default (3x3) if 0 is entered
      {
        bw.write(DEFAULT);
        bw.newLine();
        bw.write(DEFAULT);
        break;
      }
      else if (userchoice == 1) // Allow user to change board size
      {
        for (int i = 0; i < 2; i++)
        {
          if (i == 0)
            System.out.println("Enter what the length of the board should be: ");
          else
            System.out.println("Enter what the width of the board should be: ");
          
          while (true)
          {
            userchoicechk = inp.nextLine(); 
            userchoice = isnumber(userchoicechk);
            
            if (userchoice < 3) // 2 x anything boards are not allowed
              System.out.println("Boards that have dimensions less than 3 are not permitted. Try again.");
            else
              break;
          }
          
          bw.write(userchoicechk);
          bw.newLine(); 
        }
        break;
      }
     else
       System.out.println("Not an accepted number.");
    }
    
    System.out.println("Saved. Returning to the menu.\n");
    bw.close();
    fw.close();
 } // settings method
  
  /**
   * Isnumber method.
   * This is an error checking method. Because of the occasional user error, error checking is needed!
   * Takes the previously acquired user input from a string and tries to convert it into an integer variable.
   * If this cannot be accomplished, continuously bugs the user for a proper number.
   * 
   * @param userchoicechk The user's input in a string format.
   * @return The user's input parsed into an integer.
   */
  public static int isnumber (String userchoicechk)
  {
    int userchoice;
    Scanner inp = new Scanner (System.in);
    
    while (true)
    {  
      try
      {
        userchoice = Integer.parseInt (userchoicechk);
        break;
      }
      catch (NumberFormatException g)
      {
        System.out.println ("The value you entered is not an number.");
        System.out.println ("Try again."); 
        userchoicechk = inp.nextLine(); 
      }
    }
    return userchoice;
  } // isnumber method
    
  /**
   * Game method.
   * The actual tic tac toe game. 
   * 
   * @param player The user's inputted number of players.
   * @return none
   */
  public static void game (int player) throws IOException
  {
    String userchoicechk;
    Scanner inp = new Scanner (System.in);
    int userchoice;
    int row;
    int column;
    int [] aichoice = new int [2]; // Used in the ai method, for 
    int turncount = 0; // Counts the turn number, %2 to determine which player's turn it is
    boolean winner = false; // There is no winner as long as this condition is false, determined by the win method
    boolean tied = false; // There is no tie as long as this condition is false, determined by the tie method
    
    char [][] board = createboard(); // To the createboard method, generates the empty board.
    
    if (board.length == 3 || board [0].length == 3)
      System.out.println("Get 3 in a row to win!\n");
    else
      System.out.println("Get 4 in a row to win!\n");
    
    while (winner==false && tied == false) // Keeps running as long as there is no winner or the game does not appear to be tied
    {
      System.out.print(" \t");
      
      for (int j = 0; j < board[0].length; j++) // Outputs column numbers
      {
        if (j < 9)
          System.out.print((j+1) + " ");
        else if (j >= 9) //bluh can't really format this properly to display rows with double digits other than using tab for the entire board and that would be really dumb
          System.out.print((j+1));
      }
      System.out.println("\n\n\n");
    
      for (int i = 0; i < board.length; i++)
      {
        System.out.print((i+1) + "\t"); // Outputs row numbers
        
        for (int j = 0; j < board[0].length; j++) // Outputs the board.
        {
          if (j == board[0].length - 1)
          {
            System.out.print(board [i][j]);
          }
          else
          {
            System.out.print(board [i][j] +"|");
          }
        }
        System.out.println("");
      }
      System.out.println("");
      
      if (player == 1) // Outputs who's who.
        System.out.println("\tYou: x\n\tComputer: o\n");
      else if (player == 2)
        System.out.println("\tPlayer 1: x\n\tPlayer 2: o\n");
      
      row = -1;
      column = -1;
      
      // Depending on whether or not the user selected single player or multiplayer, output different statements for turns.
      if (turncount%2 == 0 && player == 1)
        System.out.println("Your turn.");
      else if (turncount%2 == 0 && player == 2)
        System.out.println("Player 1's turn.");
      else if (turncount%2 != 0 && player == 1)
        System.out.println("Computer's turn."); 
      else
        System.out.println("Player 2's turn.");
      
      // For first player, multiplayer.
      if (turncount%2 == 0 || player == 2)
      {
        do 
        {
          System.out.println("Row:"); // Ask for the row of the spot.
          userchoicechk = inp.nextLine();
          row = isnumber(userchoicechk);
          if (row <= board.length && row > 0)
            break;
          else
            System.out.println("Not a valid row.");
        }while (row > board.length || row <= 0 && winner ==false && tied == false);
        
        do
        {
          System.out.println("Column:"); // Column of spot.
          userchoicechk = inp.nextLine();
          column = isnumber(userchoicechk);
          if (column <= board[0].length && row > 0 && column > 0)
          {
            if (board[row-1][column-1] == 32 || board[row-1][column-1] == 95) // If the spot is empty (contains an underscore or space), allow users to place the x.
            {
              if (turncount % 2 == 0) // Player 1's turn.
              {
                board[row-1][column-1] = 120; // Place x in array.
                winner = win (board); // To the win method, determines if the move won the game
                tied = tie (board); // To the tie method, determines if the move tied the game
                
                if (winner == true) // If there is 3 or 4 in a row found (won the game), output this to the user and end game.
                {
                  if (player == 1)
                    System.out.println("\nYou have won!");
                  else
                    System.out.println("\nPlayer 1 has won!");
                  break;
                }
                else if(tied == true && winner == false) // If there is a tie, output this to the user and end game.
                {
                  System.out.println("Tie game!");
                  break;
                }
                else
                  turncount++; // Continue on with the game if there is no winner or tie game, increase turn count for next player's turn.
              }
              else // Player 2's move, turncount %2 != 0.
              {
                board[row-1][column-1] = 111;
                winner = win (board); // Same as previous, except for player 2.
                tied = tie (board);
                
                if (winner == true)
                {
                  System.out.println("\nPlayer 2 has won!");
                  break;
                }        
                else if(tied == true && winner == false)
                {
                  System.out.println("Tie game!");
                  break;
                }
                else
                  turncount++;
              }
              System.out.println("");
              break;
            }
            else if (board[row-1][column-1] == 120 ||board[row-1][column-1] == 111) // Space already occupied, doesn't do anything to the array.
              System.out.println("Space already occupied.\n");
            else
              System.out.println("Not a valid column.");
          }
          else
            System.out.println("Not a valid column.");
        }while (column > board[0].length || column <= 0 && winner == false && tied == false);
      }
      else if (turncount%2 != 0 && player == 1) // Single player, for ai's turn
      {
        aichoice = ai(board); // To the ai method, chooses where the computer should go.
        board[aichoice [0]][aichoice[1]] = 111;
        winner = win (board); // Same as previous ones, except for the computer.
        tied = tie (board);
        
        if (winner == true)
        {
          System.out.println("The computer has won!");
          break;
        }        
        else if(tied == true && winner == false)
        {
          System.out.println("Tie game!");
          break;
        }
        else
          turncount++;
      }
    }
    
    board = winboard(board);
    
    for (int i = 0; i < board.length; i++) // Outputs the board to the user after the winning move
    {
      for (int j = 0; j < board[0].length; j++)
      {
        if (j == board[0].length - 1)
        {
          System.out.print(board [i][j]);
        }
        else
        {
          System.out.print(board [i][j] +"|");
        }
      }
      System.out.println("");
    }
    
    System.out.println("Returning you to the menu.\n"); // MENU.
    if (turncount%2 == 0 && player == 1 && tied == false)
      menu(1);
    else
      menu(0);
  } // Game method
  
  /**
   * Createboard method.
   * Generates the empty tic tac toe board, with the dimensions as specified in settings.txt.
   * @param none
   * @return The tic tac toe board. 
   * */
  public static char [][] createboard () throws IOException
  {
    FileReader fr = new FileReader ("settings.txt");
    BufferedReader br = new BufferedReader (fr);
    String line;
    int length;
    int width;
    
    // Reads the settings.txt file for the board size
    line = br.readLine();
    length = Integer.parseInt (line);
    line = br.readLine();
    width = Integer.parseInt (line);
    
    char [][] board = new char [length][width];
    
    // Sets up spaces for the board
    for (int i = 0; i < board.length; i++)
     {
      for (int j = 0; j < board[0].length; j++)
      {
        if (i == board.length - 1)
          board [i][j] = 32;
        else
          board [i][j] = 95;
      }
    }
    
    return board;
  } // Createboard method
  
  /** 
   * Win method.
   * Determines if someone has won or not by finding 3 x's or o's in a row, horizontally, vertically or diagonally, does
   * the same if the board size is greater than 3x3 and so needs 4 in a row to win.
   * If this condition is met, returns true. 
   * If conditions: 1 - Horizontal. 2 - Vertical. 3 - Diagonal from the left down. 4 - Diagonal from the left up.
   * 
   * @param board The entire array containing the current tic tac toe board.
   * @return True or false depending on whether or not the row of characters is found in the array.
   */
  public static boolean win (char [][] board)
  {
    for (int i = 0; i < board.length;i++)
    {
      for (int j = 0; j < board [0].length;j++)
      {
        if (board.length == 3 || board[0].length == 3) // 3 in a row
        {
        if (i+2 < board.length && ((board[i][j] == 120 && board[i+1][j] == 120 && board[i+2][j] == 120)  || (board[i][j] == 111 && board[i+1][j] == 111 && board[i+1][j] == 111)) && board [i][j] == board [i+1][j] && board [i][j] == board [i+2][j])
          return true; // Horizontal win
        else if (j+2 < board[0].length && ((board[i][j] == 120 && board[i][j+1] == 120 && board[i][j+2] == 120)  || (board[i][j] == 111 && board[i][j+1] == 111 && board[i][j+2] == 111)) && board [i][j] == board [i][j+1] && board [i][j] == board [i][j+2])
          return true; // Vertical win
        else if (i+2 < board.length && j+2 < board[0].length && ((board[i][j] == 120 && board[i+1][j+1] == 120 && board[i+2][j+2] == 120)  || (board[i][j] == 111 && board[i+1][j+1] == 111 && board[i+2][j+2] == 111)) && board [i][j] == board [i+1][j+1] && board [i][j] == board [i+2][j+2])
          return true; // Diagonal down win
        else if (i-2 > -1 && j+2 < board[0].length && ((board[i][j] == 120 && board[i-1][j+1] == 120 && board[i-2][j+2] == 120)  || (board[i][j] == 111 && board[i-1][j+1] == 111 && board[i-2][j+2] == 111)) && board [i][j] == board [i-1][j+1] && board [i][j] == board [i-2][j+2])
          return true; // Diagonal up win
        }
        else // 4 in a row
        {
          if (i+3 < board.length && ((board[i][j] == 120 && board[i+1][j] == 120 && board[i+2][j] == 120 && board[i+3][j] == 120)  || (board[i][j] == 111 && board[i+1][j] == 111 && board[i+2][j] == 111 && board[i+3][j] == 111)) && board [i][j] == board [i+1][j] && board [i][j] == board [i+2][j] && board [i][j] == board [i+3][j])
          return true; // Horizontal win
        else if (j+3 < board[0].length && ((board[i][j] == 120 && board[i][j+1] == 120 && board[i][j+2] == 120 && board[i][j+3] == 120)  || (board[i][j] == 111 && board[i][j+1] == 111 && board[i][j+2] == 111)) && board [i][j] == board [i][j+1] && board [i][j] == board [i][j+2] && board[i][j] == board [i][j+3])
          return true; // Vertical win
        else if (i+3 < board.length && j+3 < board[0].length && ((board[i][j] == 120 && board[i+1][j+1] == 120 && board[i+2][j+2] == 120 && board[i+3][j+3] == 120)  || (board[i][j] == 111 && board[i+1][j+1] == 111 && board[i+2][j+2] == 111 && board[i+3][j+3] == 111)) && board [i][j] == board [i+1][j+1] && board [i][j] == board [i+2][j+2] && board [i][j] == board [i+3][j+3])
          return true; // Diagonal down win
        else if (i-3 > -1 && j+3 < board[0].length && ((board[i][j] == 120 && board[i-1][j+1] == 120 && board[i-2][j+2] == 120 && board[i-3][j+3] == 120)  || (board[i][j] == 111 && board[i-1][j+1] == 111 && board[i-2][j+2] == 111&& board[i-3][j+3] == 111)) && board [i][j] == board [i-1][j+1] && board [i][j] == board [i-2][j+2] && board [i][j] == board [i-3][j+3])
          return true; // Diagonal up win
      }
    }
    }
    return false;
  } // Win method

    /** 
   * Winboard method.
   * If someone has won, this is used to show which move on the board won.
   * It changes the original lowercase of the placed x's and o's and
   * changes them to capital X's and O's to make them stand out on the board.
   * This new changed board is output to the user afterwards as the winning board.
   * 
   * @param board The entire array containing the current tic tac toe board.
   * @return The new board containing the row found in capital letters.
   */
  public static char [][] winboard (char [][] board)
  {
    for (int i = 0; i < board.length;i++)
    {
      for (int j = 0; j < board [0].length;j++)
      {
        if (board.length == 3 || board[0].length == 3) // 3 in a row
        {
          if (i+2 < board.length && ((board[i][j] == 120 && board[i+1][j] == 120 && board[i+2][j] == 120)  || (board[i][j] == 111 && board[i+1][j] == 111 && board[i+1][j] == 111)) && board [i][j] == board [i+1][j] && board [i][j] == board [i+2][j])
          {
            if (board[i][j] == 120) // x
            {
              board[i][j] = 88;
              board[i+1][j] = 88;
              board[i+2][j] = 88;
            }
            else // o
            {
              board[i][j] = 79;
              board[i+1][j] = 79;
              board[i+2][j] = 79;
            }
            return board; // Horizontal win
          }
          else if (j+2 < board[0].length && ((board[i][j] == 120 && board[i][j+1] == 120 && board[i][j+2] == 120)  || (board[i][j] == 111 && board[i][j+1] == 111 && board[i][j+2] == 111)) && board [i][j] == board [i][j+1] && board [i][j] == board [i][j+2])
          {
            if (board[i][j]==120)
            {
              board[i][j] = 88;
              board[i][j+1] = 88;
              board[i][j+2] = 88;
            }
            else
            {
              board[i][j] = 79;
              board[i][j+1] = 79;
              board[i][j+2] = 79;
            }
            return board; // Vertical win
          }
          else if (i+2 < board.length && j+2 < board[0].length && ((board[i][j] == 120 && board[i+1][j+1] == 120 && board[i+2][j+2] == 120)  || (board[i][j] == 111 && board[i+1][j+1] == 111 && board[i+2][j+2] == 111)) && board [i][j] == board [i+1][j+1] && board [i][j] == board [i+2][j+2])
          {
            if (board[i][j]==120)
            {
              board[i][j] = 88;
              board[i+1][j+1] = 88;
              board[i+2][j+2] = 88;
            }
            else
            {
              board[i][j] = 79;
              board[i+1][j+1] = 79;
              board[i+2][j+2] = 79;
            }
            return board; // Diagonal down win
          }
          else if (i-2 > -1 && j+2 < board[0].length && ((board[i][j] == 120 && board[i-1][j+1] == 120 && board[i-2][j+2] == 120)  || (board[i][j] == 111 && board[i-1][j+1] == 111 && board[i-2][j+2] == 111)) && board [i][j] == board [i-1][j+1] && board [i][j] == board [i-2][j+2])
          {
            if (board[i][j]==120)
            {
              board[i][j] = 88;
              board[i-1][j+1] = 88;
              board[i-2][j+2] = 88;
            }
            else
            {
              board[i][j] = 79;
              board[i-1][j+1] = 79;
              board[i-2][j+2] = 79;
            }
            return board; // Diagonal up win
          }
        }
        else // 4 in a row
        {
          if (i+3 < board.length && ((board[i][j] == 120 && board[i+1][j] == 120 && board[i+2][j] == 120 && board[i+3][j] == 120)  || (board[i][j] == 111 && board[i+1][j] == 111 && board[i+2][j] == 111 && board[i+3][j] == 111)) && board [i][j] == board [i+1][j] && board [i][j] == board [i+2][j] && board [i][j] == board [i+3][j])
          {
            if (board[i][j]==120)
            {
              board[i][j] = 88;
              board[i+1][j] = 88;
              board[i+2][j] = 88;
              board[i+3][j] = 88;
            }
            else
            {
              board[i][j] = 79;
              board[i+1][j] = 79;
              board[i+2][j] = 79;
              board[i+3][j] = 79;
            }
            return board; // Horizontal win
          }
          else if (j+3 < board[0].length && ((board[i][j] == 120 && board[i][j+1] == 120 && board[i][j+2] == 120 && board[i][j+3] == 120)  || (board[i][j] == 111 && board[i][j+1] == 111 && board[i][j+2] == 111)) && board [i][j] == board [i][j+1] && board [i][j] == board [i][j+2] && board[i][j] == board [i][j+3])
          {
            if (board[i][j]==120)
            {
              board[i][j] = 88;
              board[i][j+1] = 88;
              board[i][j+2] = 88;
              board[i][j+3] = 88;
            }
            else
            {
              board[i][j] = 79;
              board[i][j+1] = 79;
              board[i][j+2] = 79;
              board[i][j+3] = 79;
            }
            return board; // Vertical win
          }
          else if (i+3 < board.length && j+3 < board[0].length && ((board[i][j] == 120 && board[i+1][j+1] == 120 && board[i+2][j+2] == 120 && board[i+3][j+3] == 120)  || (board[i][j] == 111 && board[i+1][j+1] == 111 && board[i+2][j+2] == 111 && board[i+3][j+3] == 111)) && board [i][j] == board [i+1][j+1] && board [i][j] == board [i+2][j+2] && board [i][j] == board [i+3][j+3])
          {
            if (board[i][j]==120)
            {
              board[i][j] = 88;
              board[i+1][j+1] = 88;
              board[i+2][j+2] = 88;
              board[i+3][j+3] = 88;
            }
            else
            {
              board[i][j] = 79;
              board[i+1][j+1] = 79;
              board[i+2][j+2] = 79;
              board[i+3][j+3] = 79;
            }
            return board; // Diagonal down win
          }
          else if (i-3 > -1 && j+3 < board[0].length && ((board[i][j] == 120 && board[i-1][j+1] == 120 && board[i-2][j+2] == 120 && board[i-3][j+3] == 120)  || (board[i][j] == 111 && board[i-1][j+1] == 111 && board[i-2][j+2] == 111&& board[i-3][j+3] == 111)) && board [i][j] == board [i-1][j+1] && board [i][j] == board [i-2][j+2] && board [i][j] == board [i-3][j+3])
          {
            if (board[i][j]==120)
            {
              board[i][j] = 88;
              board[i-1][j+1] = 88;
              board[i-2][j+2] = 88;
              board[i-3][j+3] = 88;
            }
            else
            {
              board[i][j] = 79;
              board[i-1][j+1] = 79;
              board[i-2][j+2] = 79;
              board[i-3][j+3] = 79;
            }
            return board; // Diagonal up win
          }
      }
      }
    }
    return board;
  } // Winboard method
  
  /** 
   * Tie method.
   * Determines whether or not the game ended in a tie.
   * This is determined by just simply counting the total number of spots that have been used, and then comparing it
   * to the size of the board.
   * If they are equal, it is a tie and it returns true.
   * 
   * @param board The entire array containing the current tic tac toe board.
   * @return True or false depending on whether or not the array is completely filled with x's and o's.
   */
  public static boolean tie (char [][] board)
  {
    int counter = 0;
    
    for (int i = 0; i < board.length;i++)
    {
      for (int j = 0; j < board [0].length;j++)
      {
       if(board[i][j] == 120 || board[i][j] == 111)
         counter++; // Increase counter value for every spot taken by x's and o's
      }
    }
    
    if (counter == ((board.length*board[0].length))) // If the counter is equal to the area of the board, it is a tie.
      return true;
    else
      return false;
  } // Tie method
  
  /**
   * AI method.
   * A somewhat competent opponent. First calculates if there is an immediate way to win.
   * Also blocks opponent if they are about to win.
   * This is done by seeing if there are 2 in a row in three ways: -oo,oo- and o-o, with the - being blanks, not already filled on the board.
   * (If the size of the board is greater than 3, then finds -ooo, o-oo, oo-o and ooo-).
   * It is repeated 4 times, for the horizontal, vertical, and 2 diagonal conditions (up and down).
   * If there is nothing present that is 2 in a row, it will attempt to make a move close to a nearby spot that has already been taken
   * (To either block the opponent or create 2 in a row), through the use of an RNG.
   * If unsuccessful (because the rng can only generate -1, 0, 1 and the spot cannot be already taken), it just chooses a 
   * random spot that hasn't already been taken.
   * Also outputs emoticons depending on what situation the computer is in.
   * 
   * @param board The entire array containing the current tic tac toe board.
   * @return An array containing the coordinates of the computer's moves.
   */
  public static int [] ai (char [][] board)
  {
    int [] ai = new int [2];
    ai [0] = -1;
    ai [1] = -1;
    
    if (board.length > 3 || board[0].length > 3)
    {
      for (int i = 0; i < board.length;i++)
      {
        for (int j = 0; j < board [0].length;j++)
        {
          if (i+3 < board.length && board[i][j] != 120 && board[i+1][j] == 111 && board[i+2][j] == 111 && board[i+3][j] == 111)// horizontal 3 in a row back
          {
            ai [0] = i;
            ai [1] = j;
            System.out.println ("Computer: (*v*)\n");
            return ai;
          }
          else if (i+3 < board.length && board[i][j] == 111 && board[i+1][j] == 111 && board[i+2][j] == 111 && board[i+3][j] != 120)// horizontal 3 in a row front
          {
            ai [0] = i+3;
            ai [1] = j;
            System.out.println ("Computer: (*v*)\n");
            return ai;
          }
          else if (i+3 < board.length && board[i][j] == 111 && board[i+1][j] != 120 && board[i+2][j] == 111 && board[i+3][j] == 111)// horizontal 3 across
          {
            ai [0] = i+1;
            ai [1] = j;
            System.out.println ("Computer: (*v*)\n");
          return ai;
          }
          else if (i+3 < board.length && board[i][j] == 111 && board[i+1][j] == 111 && board[i+2][j] != 120 && board[i+3][j] == 111)// horizontal 3 across
          {
            ai [0] = i+2;
            ai [1] = j;
            System.out.println ("Computer: (*v*)\n");
            return ai;
          }
          else if (j+3 < board[0].length && board[i][j] != 120 && board[i][j+1] == 111 && board[i][j+2] == 111 && board[i][j+3] == 111)// vertical 3 in a row back
          {
            ai [0] = i;
            ai [1] = j;
            System.out.println ("Computer: (*v*)\n");
          return ai;
          }
          else if (j+3 < board[0].length && board[i][j] == 111 && board[i][j+1] == 111 && board[i][j+2] == 111 && board[i][j+3] != 120)// vertical 3 in a row front
          {
            ai [0] = i;
            ai [1] = j+3;
            System.out.println ("Computer: (*v*)\n");
            return ai;
          }
          else if (j+3 < board[0].length && board[i][j] == 111 && board[i][j+1] != 120 && board[i][j+2] == 111 && board[i][j+3] == 111)// vertical 3 across
          {
            ai [0] = i;
            ai [1] = j+1;
            System.out.println ("Computer: (*v*)\n");
            return ai;
          }
          else if (j+3 < board[0].length && board[i][j] == 111 && board[i][j+1] == 111 && board[i][j+2] != 120 && board[i][j+3] == 111)// vertical 3 across
          {
            ai [0] = i;
            ai [1] = j+2;
            System.out.println ("Computer: (*v*)\n");
            return ai;
          }
          else if (i+3 < board.length && j+3 < board[0].length && board[i][j] != 120 && board[i+1][j+1] == 111 && board[i+2][j+2] == 111 && board[i+3][j+3] == 111)// diagonal 3 in a row down back
          {
            ai [0] = i;
            ai [1] = j;
            System.out.println ("Computer: (*v*)\n");
            return ai;
          }
          else if (i+3 < board.length && j+3 < board[0].length && board[i][j] == 111 && board[i+1][j+1] == 111 && board[i+2][j+2] == 111 && board[i+3][j+3] != 120)// diagonal 3 in a row down front
          {
            ai [0] = i+3;
            ai [1] = j+3;
            System.out.println ("Computer: (*v*)\n");
          return ai;
          }
          else if (i+3 < board.length && j+3 < board[0].length && board[i][j] == 111 && board[i+1][j+1] != 120 && board[i+2][j+2] == 111 && board[i+3][j+3] == 111)// diagonal 3 across down
          {
            ai [0] = i+1;
            ai [1] = j+1;
            System.out.println ("Computer: (*v*)\n");
            return ai;
          }
          else if (i+3 < board.length && j+3 < board[0].length && board[i][j] == 111 && board[i+1][j+1] == 111 && board[i+2][j+2] != 120 && board[i+3][j+3] == 111)// diagonal 3 across down
          {
            ai [0] = i+2;
            ai [1] = j+2;
            System.out.println ("Computer: (*v*)\n");
            return ai;
          }
          else if (i-3 > -1 && j+3 < board[0].length && board[i][j] != 120 && board[i-1][j+1] == 111 && board[i-2][j+2] == 111 && board[i-3][j+3] == 111)// diagonal 3 in a row up back
          {
            ai [0] = i;
            ai [1] = j;
            System.out.println ("Computer: (*v*)\n");
            return ai;
          }
          else if (i-3 > -1 && j+3 < board[0].length && board[i][j] == 111 && board[i-1][j+1] == 111 && board[i-2][j+2] == 111 && board[i-3][j+3]!= 120)// diagonal 3 in a row up front
          {
            ai [0] = i-3;
            ai [1] = j+3;
            System.out.println ("Computer: (*v*)\n");
            return ai;
          }
          else if (i-3 > -1 && j+3 < board[0].length && board[i][j] == 111 && board[i-1][j+1] != 120 && board[i-2][j+2] == 111 && board[i-3][j+3] == 111)// diagonal 3 across up
          {
            ai [0] = i-1;
            ai [1] = j+1;
            System.out.println ("Computer: (*v*)\n");
            return ai;
          }
          else if (i-3 > -1 && j+3 < board[0].length && board[i][j] == 111 && board[i-1][j+1] == 111 && board[i-2][j+2] != 120 && board[i-3][j+3] == 111)// diagonal 3 across up
          {
            ai [0] = i-2;
            ai [1] = j+2;
            System.out.println ("Computer: (*v*)\n");
            return ai;
          }
        }
      }
      
      // Blocks the opponent if they have 3 units in a row
      // Only after finding it can't win with the current state of the board.
      for (int i = 0; i < board.length;i++)
      {
        for (int j = 0; j < board [0].length;j++)
        {
          if (i+3 < board.length && board[i][j] == 120 && board[i+1][j] == 120 && board[i+2][j] == 120 && board[i+3][j] != 120 && board[i+3][j] != 111)// opponent horizontal 3 in a row front
          {
            ai [0] = i+3;
            ai [1] = j;
            System.out.println ("Computer: (T^T )\n");
            return ai;
          }
          else if (i+3 < board.length && board[i][j] != 111 && board[i][j] != 120 && board[i+1][j] == 120 && board[i+2][j] == 120 && board[i+3][j] == 120)// opponent horizontal 3 in a row back
          {
            ai [0] = i;
            ai [1] = j;
            System.out.println ("Computer: (T^T )\n");
            return ai;
          }
          else if (i+3 < board.length && board[i][j] == 120 && board[i+1][j] != 111 && board[i+1][j] != 120 &&  board[i+2][j] == 120 && board[i+3][j] == 120)// opponent horizontal 3 across
          {
          ai [0] = i+1;
          ai [1] = j;
          System.out.println ("Computer: (T^T )\n");
          return ai;
          }
          else if (i+3 < board.length && board[i][j] == 120 && board[i+1][j] == 120 && board[i+2][j] != 111 && board[i+2][j] != 120 &&  board[i+3][j] == 120)// opponent horizontal 3 across
          {
          ai [0] = i+2;
          ai [1] = j;
          System.out.println ("Computer: (T^T )\n");
          return ai;
          }
          else if (j+3 < board[0].length && board[i][j] == 120 && board[i][j+1] == 120 && board[i][j+2] == 120 && board[i][j+3] != 120 &&  board[i][j+3] != 111)// opponent vertical 3 in a row front
          {
            ai [0] = i;
            ai [1] = j+3;
            System.out.println ("Computer: (T^T )\n");
            return ai;
          }
          else if (j+3 < board[0].length && board[i][j] != 111 && board[i][j+1] == 120 && board[i][j+2] == 120 && board[i][j+3] == 120)// opponent vertical 3 in a row back
          {
            ai [0] = i;
            ai [1] = j;
            System.out.println ("Computer: (T^T )\n");
            return ai;
          }
          else if (j+3 < board[0].length && board[i][j] == 120 && board[i][j+1] != 111 && board[i][j+2] == 120 && board[i][j+3] == 120)// opponent vertical 3 across
          {
            ai [0] = i;
            ai [1] = j+1;
            System.out.println ("Computer: (T^T )\n");
            return ai;
          }
          else if (j+3 < board[0].length && board[i][j] == 120 && board[i][j+1] == 120 && board[i][j+2] != 111 && board[i][j+3] == 120)// opponent vertical 3 across
          {
            ai [0] = i;
            ai [1] = j+2;
            System.out.println ("Computer: (T^T )\n");
            return ai;
          }
          else if (i+3 < board.length && j+3 < board[0].length && board[i][j] == 120 && board[i+1][j+1] == 120 && board[i+2][j+2] == 120 && board[i+3][j+3] != 111)// opponent diagonal 3 in a row down front
          {
            ai [0] = i+3;
            ai [1] = j+3;
            System.out.println ("Computer: (T^T )\n");
            return ai;
          }
          else if (i+3 < board.length && j+3 < board[0].length && board[i][j] != 111 && board[i+1][j+1] == 120 && board[i+2][j+2] == 120 && board[i+3][j+3] == 120)// opponent diagonal 3 in a row down back
          {
            ai [0] = i;
            ai [1] = j;
            System.out.println ("Computer: (T^T )\n");
            return ai;
          }
          else if (i+3 < board.length && j+3 < board[0].length && board[i][j] == 120 && board[i+1][j+1] != 111 && board[i+2][j+2] == 120 && board[i+3][j+3] == 120)// opponent diagonal 3 across down
          {
            ai [0] = i+1;
            ai [1] = j+1;
            System.out.println ("Computer: (T^T )\n");
            return ai;
          }
          else if (i+3 < board.length && j+3 < board[0].length && board[i][j] == 120 && board[i+1][j+1] == 120 && board[i+2][j+2] != 111 && board[i+3][j+3] == 120)// opponent diagonal 3 across down
          {
            ai [0] = i+2;
            ai [1] = j+2;
            System.out.println ("Computer: (T^T )\n");
            return ai;
          }
          else if (i-3 > -1 && j+3 < board[0].length && board[i][j] != 111 && board[i-1][j+1] == 120 && board[i-2][j+2] == 120 && board[i-3][j+3] == 120)// opponent diagonal 3 in a row up back
          {
            ai [0] = i;
            ai [1] = j;
            System.out.println ("Computer: (T^T )\n");
            return ai;
          }
          
          else if (i-3 > -1 && j+3 < board[0].length && board[i][j] == 120 && board[i-1][j+1] == 120 && board[i-2][j+2] == 120 && board[i-3][j+3]!= 111)// opponent diagonal 3 in a row up front
          {
            ai [0] = i-3;
            ai [1] = j+3;
            System.out.println ("Computer: (T^T )\n");
            return ai;
          }
          else if (i-3 > -1 && j+3 < board[0].length && board[i][j] == 120 && board[i-1][j+1] != 111 && board[i-2][j+2] == 120 && board[i-3][j+3] == 120)// opponent diagonal 3 across up
          {
            ai [0] = i-1;
            ai [1] = j+1;
            System.out.println ("Computer: (T^T )\n");
            return ai;
          }
          else if (i-3 > -1 && j+3 < board[0].length && board[i][j] == 120 && board[i-1][j+1] == 120 && board[i-2][j+2] != 111 && board[i-3][j+3] == 120)// opponent diagonal 3 across up
          {
            ai [0] = i-2;
            ai [1] = j+2;
            System.out.println ("Computer: (T^T )\n");
            return ai;
          }
        }
      }
    }
    
    // Calculates where to go if it has units that are 2 in a row.
    // This must be done first because it's aiming to WIN.
    for (int i = 0; i < board.length;i++)
    {
      for (int j = 0; j < board [0].length;j++)
      {
        if (i+2 < board.length && board[i][j] != 120 && board[i][j] != 111 && board[i+1][j] == 111 && board[i+2][j] == 111)// horizontal 2 in a row back
        {
          ai [0] = i;
          ai [1] = j;
          System.out.println ("Computer: (`3`)\n");
          return ai;
        }
        else if (i+2 < board.length && board[i][j] == 111 && board[i+1][j] == 111 && board[i+2][j] != 120 && board[i+2][j] != 111)// horizontal 2 in a row front
        {
          ai [0] = i+2;
          ai [1] = j;
          System.out.println ("Computer: (`3`)\n");
          return ai;
        }
        else if (i+2 < board.length && board[i][j] == 111 && board[i+1][j] != 120 && board[i+1][j] != 111 && board[i+2][j] == 111)// horizontal 2 across
        {
          ai [0] = i+1;
          ai [1] = j;
          System.out.println ("Computer: (`3`)\n");
          return ai;
        }
        else if (j+2 < board[0].length && board[i][j] != 120 && board[i][j] != 111 && board[i][j+1] == 111 && board[i][j+2] == 111)// vertical 2 in a row back
        {
          ai [0] = i;
          ai [1] = j;
          System.out.println ("Computer: (`3`)\n");
          return ai;
        }
        else if (j+2 < board[0].length && board[i][j] == 111 && board[i][j+1] == 111 && board[i][j+2] != 111 && board[i][j+2] != 120)// vertical 2 in a row front
        {
          ai [0] = i;
          ai [1] = j+2;
          System.out.println ("Computer: (`3`)\n");
          return ai;
        }
        else if (j+2 < board[0].length && board[i][j] == 111 && board[i][j+1] != 120 && board[i][j+1] != 111 && board[i][j+2] == 111)// vertical 2 across
        {
          ai [0] = i;
          ai [1] = j+1;
          System.out.println ("Computer: (`3`)\n");
          return ai;
        }
        else if (i+2 < board.length && j+2 < board[0].length && board[i][j] != 120 && board[i][j] != 111 && board[i+1][j+1] == 111 && board[i+2][j+2] == 111)// diagonal 2 in a row down back
        {
          ai [0] = i;
          ai [1] = j;
          System.out.println ("Computer: (`3`)\n");
          return ai;
        }
        else if (i+2 < board.length && j+2 < board[0].length && board[i][j] == 111 && board[i+1][j+1] == 111 && board[i+2][j+2] != 111 && board[i+2][j+2] != 120 )// diagonal 2 in a row down front
        {
          ai [0] = i+2;
          ai [1] = j+2;
          System.out.println ("Computer: (`3`)\n");
          return ai;
        }
        else if (i+2 < board.length && j+2 < board[0].length && board[i][j] == 111 && board[i+1][j+1] != 120 && board[i+1][j+1] != 111 && board[i+2][j+2] == 111)// diagonal 2 across down
        {
          ai [0] = i+1;
          ai [1] = j+1;
          System.out.println ("Computer: (`3`)\n");
          return ai;
        }
        else if (i-2 > -1 && j+2 < board[0].length && board[i][j] != 120 && board[i][j] != 111 && board[i-1][j+1] == 111 && board[i-2][j+2] == 111)// diagonal 2 in a row up back
        {
          ai [0] = i;
          ai [1] = j;
          System.out.println ("Computer: (`3`)\n");
          return ai;
        }
        else if (i-2 > -1 && j+2 < board[0].length && board[i][j] == 111 && board[i-1][j+1] == 111 && board[i-2][j+2] != 111 && board[i-2][j+2]!= 120)// diagonal 2 in a row up front
        {
          ai [0] = i-2;
          ai [1] = j+2;
          System.out.println ("Computer: (`3`)\n");
          return ai;
        }
        else if (i-2 > -1 && j+2 < board[0].length && board[i][j] == 111 && board[i-1][j+1] != 120 && board[i-1][j+1] != 111 && board[i-2][j+2] == 111)// diagonal 2 across up
        {
          ai [0] = i-1;
          ai [1] = j+1;
          System.out.println ("Computer: (`3`)\n");
          return ai;
        }
      }
    }
    
    // Blocks the opponent if they have 2 units in a row
    // Only after finding it can't win with the current state of the board.
    for (int i = 0; i < board.length;i++)
    {
      for (int j = 0; j < board [0].length;j++)
      {
        if (i+2 < board.length && board[i][j] == 120 && board[i+1][j] == 120 && board[i+2][j] != 111 && board[i+2][j] != 120)// opponent horizontal 2 in a row front
        {
          ai [0] = i+2;
          ai [1] = j;
          System.out.println ("Computer: (@^@ )\n");
          return ai;
        }
        else if (i+2 < board.length && board[i][j] != 111 && board[i][j] != 120 && board[i+1][j] == 120 && board[i+2][j] == 120)// opponent horizontal 2 in a row back
        {
          ai [0] = i;
          ai [1] = j;
          System.out.println ("Computer: (@^@ )\n");
          return ai;
        }
        else if (i+2 < board.length && board[i][j] == 120 && board[i+1][j] != 111 && board[i+1][j] != 120 && board[i+2][j] == 120)// opponent horizontal 2 across
        {
          ai [0] = i+1;
          ai [1] = j;
          System.out.println ("Computer: (@^@ )\n");
          return ai;
        }
        else if (j+2 < board[0].length && board[i][j] == 120 && board[i][j+1] == 120 && board[i][j+2] != 111 && board[i][j+2] != 120)// opponent vertical 2 in a row front
        {
          ai [0] = i;
          ai [1] = j+2;
          System.out.println ("Computer: (@^@ )\n");
          return ai;
        }
        else if (j+2 < board[0].length && board[i][j] != 111 && board[i][j] != 120 && board[i][j+1] == 120 && board[i][j+2] == 120)// opponent vertical 2 in a row back
        {
          ai [0] = i;
          ai [1] = j;
          System.out.println ("Computer: (@^@ )\n");
          return ai;
        }
        else if (j+2 < board[0].length && board[i][j] == 120 && board[i][j+1] != 111 && board[i][j+1] != 120 && board[i][j+2] == 120)// opponent vertical 2 across
        {
          ai [0] = i;
          ai [1] = j+1;
          System.out.println ("Computer: (@^@ )\n");
          return ai;
        }
        else if (i+2 < board.length && j+2 < board[0].length && board[i][j] == 120 && board[i+1][j+1] == 120 && board[i+2][j+2] != 111 && board[i+2][j+2] != 120)// opponent diagonal 2 in a row down front
        {
          ai [0] = i+2;
          ai [1] = j+2;
          System.out.println ("Computer: (@^@ )\n");
          return ai;
        }
        else if (i+2 < board.length && j+2 < board[0].length && board[i][j] != 111 && board[i][j] != 120 && board[i+1][j+1] == 120 && board[i+2][j+2] == 120)// opponent diagonal 2 in a row down back
        {
          ai [0] = i;
          ai [1] = j;
          System.out.println ("Computer: (@^@ )\n");
          return ai;
        }
        else if (i+2 < board.length && j+2 < board[0].length && board[i][j] == 120 && board[i+1][j+1] != 111 && board[i+1][j+1] != 120 && board[i+2][j+2] == 120)// opponent diagonal 2 across down
        {
          ai [0] = i+1;
          ai [1] = j+1;
          System.out.println ("Computer: (@^@ )\n");
          return ai;
        }
        else if (i-2 > -1 && j+2 < board[0].length && board[i][j] != 111 && board[i][j] != 120 && board[i-1][j+1] == 120 && board[i-2][j+2] == 120)// opponent diagonal 2 in a row up back
        {
          ai [0] = i;
          ai [1] = j;
          System.out.println ("Computer: (@^@ )\n");
          return ai;
        }
        else if (i-2 > -1 && j+2 < board[0].length && board[i][j] == 120 && board[i-1][j+1] == 120 && board[i-2][j+2]!= 111 && board[i-2][j+2]!= 120)// opponent diagonal 2 in a row up front
        {
          ai [0] = i-2;
          ai [1] = j+2;
          System.out.println ("Computer: (@^@ )\n");
          return ai;
        }
        else if (i-2 > -1 && j+2 < board[0].length && board[i][j] == 120 && board[i-1][j+1] != 111 && board[i-1][j+1] != 120 && board[i-2][j+2] == 120)// opponent diagonal 2 across up
        {
          ai [0] = i-1;
          ai [1] = j+1;
          System.out.println ("Computer: (@^@ )\n");
          return ai;
        }
      }
    }
    
    //Attempt to find nearest free spot to some already taken spot if previous methods cannot be found
    for (int i = 0; i < board.length;i++)
    {
      for (int j = 0; j < board [0].length;j++)
      {
       if (board[i][j] == 120 || board[i][j] == 111)
       {
         ai [0] = (int)(Math.random()*2)-1; //Move 1 space anywhere down or up
         ai [1] = (int)(Math.random()*2)-1; //Move 1 space anywhere side to side
         
         // Is the spot valid in the array and not already taken?
         if (ai[0] != 0 && ai[1] != 0 && ai[0]+i >-1 && ai[1]+j >-1 && ai[0]+i < board.length && ai[1]+j < board[0].length) 
         {
           if (board[ai[0]+i][ai [1]+j] != 120 && board[ai [0]+i][ai [1]+j] != 111)
           {
             ai[0] = ai[0]+i;
             ai[1] = ai[1]+j;
             System.out.println ("Computer: ('u' )\n");
             return ai;
           }
         }
         else
         {
           ai [0] = -1;
           ai [1] = -1;
         }
       }
      }
    }
    
    //If nothing else works, just find a random free spot.
    if (ai [0] <= -1 && ai [1] <= -1)
    {
      while (true)
      {
        ai [0] = (int)(Math.random()*(board.length));
        ai [1] = (int)(Math.random()*(board[0].length));
        // If the spot's not taken, keep the coordinates and get out of the loop. Otherwise, keep going until a valid spot is found.
        if (ai[0] != board.length && ai[1] != board[0].length && board[ai[0]][ai [1]] != 120 && board[ai [0]][ai [1]] != 111) 
        {
          System.out.println ("Computer: ('u' )\n");
          break;
        }
      }
    }  
    return ai;
  } // AI method
  
    /**
   * ANCII method.
   * Outputs when the user wins against a computer.
   * 
   * @param none.
   * @return An array containing various lines of characters to form ANCII art.
   */
  public static String [] ancii () throws IOException
  {
    int random = (int)(Math.random()*6+1);
    FileReader fr = new FileReader ("a"+random+".txt");
    BufferedReader br = new BufferedReader (fr);
    int counter = 0;
    String line;
    
    line = br.readLine();
    while (line != null)
    {
      counter++;
      line = br.readLine();
    }
      
    fr = new FileReader ("a"+random+".txt");
    br = new BufferedReader (fr);
    
    String [] ancii = new String [counter]; 
    
    line = br.readLine();
    
    for (int i = 0; i < ancii.length; i++)
    {
      ancii [i] = line;
      line = br.readLine();
    }
    
    return ancii;
  }
  
}// RenV_Assignment1.class