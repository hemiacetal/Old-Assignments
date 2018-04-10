int intX = 40;
int intY = 100;
PImage bat;
PImage bat2;
PImage bg;
PImage start;
PImage title;
PImage exit;
PImage again;
PImage score;
PImage pauseimg;
PImage cont;
import java.util.Arrays;
PImage over;
PImage instruc;
PImage menu;
PImage instrucimg;
PImage hscore;
PImage credit;
PFont f;
boolean [] pressed;
float b = 1;
boolean pause = true;
int points = 0;
block first = new block ();
block second = new block ();
block third = new block ();
block fourth = new block ();
block fifth = new block ();
int [] showscores = new int [11];
int [] collision = new int [2];
String page = "title";
int counter = 1;
int k = 0;
boolean in = false;
BufferedReader input;
PrintWriter output;
String line = "";

void setup()
{
  background(#92AA7B);
  // blah the font i want to use doesn't work and i can't find the file for it for the loadFont thing so i'm really
  // just using the default font
  f = createFont("Small Fonts",24,true);
  size(640,480);
  frameRate(30);
  smooth();
  bat = loadImage("batf.gif");
  bat2 = loadImage("bats.gif");
  bg = loadImage("bg2.png");
  start = loadImage("start.gif");
  cont = loadImage("space.gif");
  title = loadImage("title.gif");
  again = loadImage("repeat.gif");
  exit = loadImage("exit.gif");
  over = loadImage("over.gif");
  pauseimg = loadImage("pause.gif");
  score = loadImage("score.gif");
  instruc = loadImage("inst.gif");
  menu = loadImage("menu.gif");
  instrucimg = loadImage("instrucimg.gif");
  hscore = loadImage("high scores.gif");
  credit = loadImage("creds.gif");
  pressed = new boolean [4];
  pressed [0] = false;
  pressed [1] = false;
  pressed [2] = false;
  pressed [3] = false;
  
  for (int x = 0; x < 10; x++)
  {
    showscores[x] = 0;
  }
}
  
// Draw all buttons, everything
void draw() {
  background(bg);
  noStroke();
  fill(000);
  rect (0,0,640,30);
  rect (0,450,640,30);
  
  if (page.equals("title"))
  {
    image(title,145,180);
    image(start,170,280);
    image(instrucimg,300,276);
    image(hscore,245,320);
    image(exit,294,360);
  }
  
  // Draw the actual game
  if (page.equals("game"))
  {
    // Show everything even when paused, but nothing is added to the score and nothing is moving
    if (pause==true)
    {      
      if (in==false)
        image(bat,intX,intY);
      if (in==true)
        image(bat2,intX,intY);
        
      first.show();
      second.show();
      third.show();
      fourth.show();
      fifth.show();
      
      textFont(f,20);      
      fill(0);
      if (points == 0)
        image(cont,158,225);
      else
        image(pauseimg,130,224);
      image(score,400,40);
      text(points,500,55);
    }
  
    // actually running the game
    if (pause==false)
    {
      counter++;
      // for animation of the bat, switch sprite every 15 the counter hits
      if(counter%15==0)
        in=!in;
      if (in==false)
        image(bat,intX,intY);
      if (in==true)
        image(bat2,intX,intY);
  
      textFont(f,20);      
      fill(0);
      image(score,400,40);
      text(points,500,55);
  
      points+=1; 
      
      // show all blocks (or rather, move them onscreen) based on the number of points the person has
      first.movement(points);
      first.show();
      first.reset();
  
      if (points > 500)
      {
        second.movement(points);
        second.show();
        second.reset();
      }
  
      if (points > 2000)
      {
        third.movement(points);
        third.show();
        third.reset();
      }
  
      if (points > 3000)
      {
        fourth.movement(points);
        fourth.show();
        fourth.reset();
      }
  
      if (points > 5000)
      {
        fifth.movement(points);
        fifth.show();
        fifth.reset();
      }
  
      if (intY>=370)
      {}
      else
      {
        intY+=b;
        b*=1.08; // gravity, slow increments
      }
  
      if (pressed [0] == true)
      {
        println("left");
        if (intX<=5)
        {}
        else
          intX-=5;
      }
      if (pressed [1] == true)
      {
        println("up");
        if (intY<=0)
        {
        }
        else
        {
          intY-=7;
          b=1; //resets gravity
        }
      }
      if (pressed [2] == true)
      {
        println("right");
        if (intX>=545)
        {}
        else
          intX+=5;
      }
      if (pressed [3] == true)
      {
        println("down");
        if (intY>=370)
        {
        }
        else
          intY+=3;
      }
      
      // collisions - the wings of the bat are not part of the hitbox,  if the xy coordinates of the block
      // are the same as the bat 
      for (int y = 0; y < 5; y++)
      {
        if (y == 0)
          collision = first.location();
        if (y == 1)
          collision = second.location();
        if (y == 2)
          collision = third.location();
        if (y == 3)
          collision = fourth.location();
        if (y == 4)
          collision = fifth.location();  
        // Collision from different sides of the block, so that the wings of the bat are not in the hitbox and only the body of it
        if (intX+80 >= collision [0] && intX+40 <= collision [0]+70 && intY+70 >= collision [1] && intY+50 <= collision [1]+70 || intY>=370 || intY<=0)
        {
           println(collision[0]+" "+collision[1] + "\n" + intX + " " + intY);
           pause = true;
           page = "game over";
        }
      }
      
      // Reset collision coordinates so that it's not game over if the bat hits a place the block previously was
      collision [0] = -10;
      collision [1] = -10;
      
    }
  }
  
  if (page.equals("game over"))
  {
    image(over,175,190);
    image(again,255,280);
    image(score,250,250);
    text(points,350,265);
    image(exit,294,350);
    image(hscore,245,320);
    
    readhighscore();
    // sorts the scores
    showscores[10] = points;
    showscores = reverse(sort(showscores));
   
   
    input = createReader("highscores.txt");
    output = createWriter("highscores.txt");
    for(int d = 0; d <10; d++)
    {
      output.println(showscores[d]); //well it certainly writes, but eVERYTHING IS THE SAME NUMBER TABLEFLIPS i give up
    }
    output.close();
  }
  
  if (page.equals("exit"))
  {
    image(credit,0,0);
    
    k++;
    
    if (k == 50)
      System.exit(0);
  }
  
  if (page.equals("instructions"))
  {
    image(instruc,0,0);
    image(menu,284,400);
  } 
  
  if (page.equals("scores"))
  {
    readhighscore();
    textFont(f,20);
    for (int b = 0; b < 10; b++)
    {
      text(b+1+".   "+showscores[b],300,80+b*30);
    }
    image(menu,284,400);
  } 
} 

// This is just what happens when different buttons are pressed on different screens
void mouseReleased() 
{
  if  (page.equals("instructions"))
  {
     if (mouseX >= 284 && mouseX <= 356 && mouseY >= 400 && mouseY <= 416)
    {
      page = "title";
    }
  }
  
  if (page.equals("scores"))
  {     
    if (mouseX >= 284 && mouseX <= 356 && mouseY >= 400 && mouseY <= 416)
    {
      page = "title";
    }
  }
  
  if (page.equals("game over"))
  {
    if (mouseX >= 294 && mouseX <= 346 && mouseY >= 350 && mouseY <= 374)
     page = "exit";
    
    if (mouseX >= 255 && mouseX <=405 && mouseY >= 280 && mouseY <= 310)
    {
      points = 0;
      intX = 40;
      intY = 100;
      counter = 1;
      b = 1;
      first = new block ();
      second = new block ();
      third = new block ();
      fourth = new block ();
      fifth = new block ();
      page = "game";
    }
    if (mouseX >= 245 && mouseX <= 516 && mouseY >= 320 && mouseY <= 352)
    {
      points = 0;
      intX = 40;
      intY = 100;
      counter = 1;
      b = 1;
      first = new block ();
      second = new block ();
      third = new block ();
      fourth = new block ();
      fifth = new block ();
      page = "scores";
    }
  }
  if (page.equals("title"))
  {
    if (mouseX >= 170 && mouseX <=240 && mouseY >= 280 && mouseY <= 300)
      page = "game";
    if (mouseX >= 300 && mouseX <=476 && mouseY >= 276 && mouseY <= 300)
      page = "instructions";
    if (mouseX >= 294 && mouseX <= 346 && mouseY >= 350 && mouseY <= 374)
      page = "exit";
    if (mouseX >= 245 && mouseX <= 516 && mouseY >= 320 && mouseY <= 352)
      page = "scores";
  }
}

void keyPressed() 
{
  if (keyCode == 37)
  {
    pressed [0] = true;
  }
  if (keyCode == 38)
  {
    pressed [1] = true;
  }
  if (keyCode == 39)
  {
    pressed [2] = true;
  }
  if (keyCode == 40)
  {
    pressed [3] = true;
  }
   
  println("pressed " + int(key) + " " + keyCode);
}

void keyReleased() 
{
  println("released " + int(key) + " " + keyCode);
  if (keyCode == 37)
  {
    pressed [0] = false;
  }
  if (keyCode == 38)
  {
    pressed [1] = false;
  }
  if (keyCode == 39)
  {
    pressed [2] = false;
  }
  if (keyCode == 40)
  {
    pressed [3] = false;
  }
    if (keyCode == 32 && page.equals("game"))
  {
    pause = !pause; // the space is a pause button
  }
}

public class block 
{
  private String name;
  private int blocx;
  private int blocy;
  private int move;
         
  block()
  {
    blocx=650;
    blocy=(int)(Math.random()*350+30); // Makes the block appear at a random y position
    move=-5;
  }

  // Movement speed increases as the player gets a higher score
  void movement(int score)
  {
    blocx+=move;
    if (score > 500 && score <= 1000)
    {
      move = -6;
    }
    else if (score > 1000 && score <= 2000)
    {
      move = -8;
    }
    else if (score > 2000 && score <= 3000)
    {
      move = -10;
    }
    else if (score > 3000 && score <= 4000)
    {
      move = -12;
    } 
    else if (score > 4000 && score <= 6000)
    {
      move = -14;
    }       
    else if (score > 6000)
    {
      move = -16;
    }     
  }
 
  void show()
  {
    fill(0);
    rect(blocx,blocy,70,70);
  }
  
  // Output the position of the block (for the collision)
  int [] location()
  {
    int [] coordi = new int [2];
    coordi [0] = blocx;
    coordi [1] = blocy;
    return coordi;
  }
  
  // Reset the block's position once it leaves the screen
  void reset()
  {
    if(blocx<-100)
    {
      blocx=650;
      blocy=(int)(Math.random()*360+30);
    }
  }
}

void readhighscore()
{
  input = createReader("highscores.txt");
    try {
    line = input.readLine();
  } catch (IOException e) {
    e.printStackTrace();
    line = null;
  }
  for (int c = 0; c < 10; c++)
  {
  if (line == null) {
    noLoop();  
  } else {
    showscores[c] = Integer.parseInt(line);
  }
    try {
    line = input.readLine();
  } catch (IOException e) {
    e.printStackTrace();
    line = null;
  }
  }
}
