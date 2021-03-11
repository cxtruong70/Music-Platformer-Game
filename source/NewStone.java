import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.sound.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class NewStone extends PApplet {

/*

Written by: Calvin Truong 
Class: IAT 167
Semester: Summer 2016
Title: Gettin' Money With G-Eazy

~References~
********Inspiration: http://www.newgrounds.com/portal/view/670905*********
Also, G-Eazy 

Artist: G-Eazy || Song: Me Myself and I Mashup || Link: https://www.youtube.com/watch?v=B0snmuOULOU
Artist: G-Eazy || Song: Almost Famous|| Link: https://www.youtube.com/watch?v=7LnEeRidcXw
Artist: G-Eazy || Song: Opportunity Cost|| Link: https://www.youtube.com/watch?v=Mko1OVHwzoU
Money Art (retracing): https://hsto.org/getpro/habr/post_images/ea1/826/6c9/ea18266c92d39f9c82c3007fb2bdcc4e.png\
Headphone Art (retracing): http://downloadicons.net/sites/default/files/headset-icon-55084.png
HUD Font: http://www.dafont.com/04b-30.font
Title Font: https://www.onlinewebfonts.com/download/f6a8b2423790ec81bbbd18662bbc7f03

*/

// declaring reference variables for player, boss, enemies, tiles and sound files that will be used to iterate though respective ArrayLists
Player p1; //the player reference variable
Boss b1; // the boss reference variable
Enemy fan; // the fan (basic enemy) reference variable
Tile tile; // the tile reference varaiable
SoundFile file; // SoundFile reference variable to point to each file\

//importing sound library and declaring sound reference variables

SoundFile almostFamous; //reference varaible for song "Almost Famous" by G-Eazy
SoundFile meMyselfAndIMashup; //reference varaible for song "Me Myself and I Mashup" originally by G-Eazy
SoundFile opportunityCosts;//reference varaible for song "Opporunity Costs" by G-Eazy

// declaring image reference variables to hold the named image
PImage weapon;
PImage gEazyStanding;
PImage gEazyWalking;
PImage gEazyStandingAlt;
PImage gEazyWalkingAlt;
PImage music;
PImage musicAlt;
PImage money;
PImage corporate;
PImage corporateAlt;
PImage corporateWalking;
PImage corporateWalkingAlt;
PImage weapon2;
PImage smile;
PImage surprise;
PImage booze;
PImage jillStanding;
PImage jillStandingAlt;
PImage jillWalking;
PImage jillWalkingAlt;
PImage isaStanding;
PImage isaStandingAlt;
PImage isaWalking;
PImage isaWalkingAlt;
PImage pen;

// declaring integer & float variables
int numEnemy; // sets the number of enemies at the beginning and at reset() when you win or lose
int gameState; // gameState switches between 0,1,2 representing playing, winScreen, loseScreen respectively
int bossTimer; // timer that delays the boss's entry into the game
int delay; //delay's player's win & loss to make it less instantaneous
int infoTimer; // timer for the instruction of the game seen at the beginning of the game
int winLoseScreenX; //horizonatal position of the win & lose screen text
int winLoseScreenY; //vertical position of the win & lose screen text
int musicRandomizer; // variable that is randomized to select one of three G-Eazy songs to start the game
int volume; //integer used to control volume
int titleTimer; //integers that displays timer for a set amount of time
int previousGameState; //this integer value allows mousePressed to select whether or not to return to the title screen or the current game depending on what the previous gameState was

// declaring several ArrayLists to hold multiple instances of different objects
ArrayList <MyCharacter> enemies = new ArrayList<MyCharacter>(); //holds all enemies not including boss 
ArrayList<Platform> platforms = new ArrayList<Platform>(); //holds all platforms
ArrayList<ArrayList> backgroundArray = new ArrayList<ArrayList>(); //holds and ArrayList of ArrayList<Tile>
ArrayList<Money> monies = new ArrayList<Money>(); //holds all instances of money drops ** not fully implemented yet

//ArrayLists not in use
//ArrayList<Tile> tiles = new ArrayList<Tile>();
//ArrayList<SoundFile> soundFiles = new ArrayList<SoundFile>();

// declaring font reference variable
PFont font;
PFont titleFont;

public void setup()
{
  
  initiateGame(); //initializes all characters, and sets the gameState to playing (0)
  fontLoad(); //loads the font
  loadImages(); //loads images from the data file into the reference variables delcared above
  centerImages(); //sets images to CENTER MODE
  makePlatforms(); //initializes platforms
  loadMusic(); // loads music files
  musicRandomizer(); //selects a random integer between 0 and 2 for random music
  playMusic(); // plays the selected music file
  initiateTiles(); // intializes the tiles of the background
}

//this method initiates the game the first time;
public void initiateGame()
{
  //initializing bossTimer and gameState
  bossTimer = 0;//delay's bosses entry
  gameState = 99; //0 for playing, 1 for win and 2 for lose
  
  //instatiating characters
  p1 = new Player(new PVector(width/4, height/2));
  b1 = new Boss(new PVector(3*(width/2), height/2));
  numEnemy = 20;
  
  for(int i = 0; i<numEnemy; i++)
  {
    if(i%2 == 0)
    {
      fan = new Jill(new PVector(random(width/2, width),random(0,height-100)));
      enemies.add(fan);
    }
    else
    {
      fan = new Isabella(new PVector(random(width/2, width),random(0,height-100)));
      enemies.add(fan);
    }
  }
  
  //initiate win/lose screen location
  winLoseScreenX=200;
  winLoseScreenY=-50;
}

//this method resets the entire game by removing everything except he platforms and reinitializing the characters as new objects (which discards the old objects)
public void reset() //called from the Keyboard_Input tab in mousePressed() and resets the game 
{
  bossTimer= 0;
  p1 = new Player(new PVector(width/4, height/2));
  b1 = new Boss(new PVector(3*(width/2), height/2)); 

  enemies = new ArrayList<MyCharacter>();
  for(int i = 0; i<numEnemy; i++)
  {
    if(i%2 == 0)
    {
      fan = new Jill(new PVector(random(width/2, width),random(0,height-100)));
      enemies.add(fan);
    }
    else
    {
      fan = new Isabella(new PVector(random(width/2, width),random(0,height-100)));
      enemies.add(fan);
    }
  }
  
  resetTiles(); //resets the background tiles
  gameState = 0; //resets the gamestate
  infoTimer = 0;
  //resetMusic(); //resets music
}

//this method creates the first set of tiles when game is first run
public void initiateTiles()
{ 
  for(int i = 0; i <= (width + 500)/200; i ++)
  { 
    ArrayList<Tile> arrayColumn = new ArrayList<Tile>();
    backgroundArray.add(arrayColumn);
    
    for(int j = 0; j <= (height + 500)/200; j++)
    {
      tile = new Tile(new PVector (200*i,200*j));
      arrayColumn.add(tile);
    }
  }
}

//this method resets the tiles when the game restarts
public void resetTiles()
{
  backgroundArray = new ArrayList<ArrayList>();
  for(int i = 0; i <= (width + 500)/200; i ++)
  { 
    ArrayList arrayColumn = new ArrayList<Tile>();
    backgroundArray.add(arrayColumn);
    
    for(int j = 0; j <= (height + 500)/200; j++)
    {
      tile = new Tile(new PVector (200*i,200*j));
      arrayColumn.add(tile);
    }
  }
}

//this method loads the songs into their reference variables
public void loadMusic()
{
  // Load a soundfile from the /data folder of the sketch and play it back
  almostFamous = new SoundFile(this, "06-g-eazy-almost_famous.mp3");
  meMyselfAndIMashup = new SoundFile(this, "Me, Myself, _ My Treasure - Bebe Rexha x G Eazy x Bruno Mars (Mashup) [E].mp3");
  opportunityCosts = new SoundFile(this,"05-g-eazy-opportunity_cost.mp3");
}

//this method plays one of 3 songs depending on the musicRandomizer variable
public void playMusic()
{
  if(musicRandomizer == 0)
  {
    file = meMyselfAndIMashup;
  }
  else if(musicRandomizer == 1)
  {
    file = almostFamous;
  }
  else
  {
    file = opportunityCosts;
  }
  println(musicRandomizer);
  file.play();
}

//this method randomizes a integer varaiable to select one of three songs
public int musicRandomizer()
{
  return musicRandomizer = PApplet.parseInt(round(random(-0.400f,2.400f)));
}

//this method loads the font of the game
public void fontLoad()
{
  font = loadFont("04b30-48.vlw");
  textFont(font,32);
  titleFont = loadFont("BelindaW00-Regular-48.vlw");
  //textFont(titleFont,32); //this is only used for the title screen and is thus in a push/popStyle structure
}

//this method loads images into the reference variables
public void loadImages()
{
  weapon = loadImage("Weapon.png");
  gEazyStanding = loadImage("No Arms Standing.png");
  gEazyWalking = loadImage("No Arms Walking.png");
  gEazyStandingAlt = loadImage("No Arms Standing - Copy.png");
  gEazyWalkingAlt = loadImage("No Arms Walking - Copy.png");
  music = loadImage("headphones.png");
  musicAlt = loadImage("headphones alt.png");
  money = loadImage("Money.png");
  weapon2 = loadImage("Weapon2.png");
  corporate = loadImage("Corporate.png");
  corporateAlt = loadImage("corporate alt.png");
  corporateWalking = loadImage("Corporate walking.png");
  corporateWalkingAlt = loadImage("corporate walking alt.png");
  smile = loadImage("Smile2.png");
  surprise = loadImage("Surprise.png");
  booze = loadImage("Jack Daniels.png");
  jillStanding = loadImage("Jill Standing.png");
  jillStandingAlt = loadImage("Jill Standing Alt.png");
  jillWalking = loadImage("Jill Walking.png");
  jillWalkingAlt = loadImage("Jill Walking Alt.png");
  isaStanding = loadImage("Isabella Standing.png");
  isaStandingAlt = loadImage("Isabella Standing Alt.png");
  isaWalking = loadImage("Isabella Walking.png");
  isaWalkingAlt = loadImage("Isabella Walking Alt.png");
  pen = loadImage("Pen2.png");
}

//centering images
public void centerImages()
{
  // centering images
  imageMode(CENTER);
  textAlign(CENTER,CENTER);
}

//initializing platforms
public void makePlatforms()
{ 
  platforms.add(new Platform(new PVector(100, height - 100), new PVector(200, 40)));
  platforms.add(new Platform(new PVector(100, height - 200), new PVector(200, 40)));
  platforms.add(new Platform(new PVector(100, height - 300), new PVector(200, 40)));
  platforms.add(new Platform(new PVector(100, height - 400), new PVector(200, 40)));
  platforms.add(new Platform(new PVector(100, height - 500), new PVector(200, 40)));
  platforms.add(new Platform(new PVector(100, height - 600), new PVector(200, 40)));
  
  platforms.add(new Platform(new PVector(400, height - 300), new PVector(200, 40)));
  platforms.add(new Platform(new PVector(700, height - 300), new PVector(200, 40)));
  platforms.add(new Platform(new PVector(1000, height - 300), new PVector(200, 40)));
  platforms.add(new Platform(new PVector(width/2, height), new PVector(width, 40)));
}

//function that draws everything depending on the gameState
public void draw()
{
  drawPlatforms(); //method that draws the platforms
  pushMatrix();
  if(gameState == 99)
  {
    startScreen();
  }
  if(gameState == 100)
  {
    infoScreen();
  }
  if(gameState == 0)
  {
    drawPlaying();
  }
  if (gameState == 1)
  {
    winScreen();
  }
  if (gameState == 2)
  {
    loseScreen();
  }
  
  popMatrix();
}

//this method draws the title screen
public void startScreen()
{
  if(titleTimer <= 240)
  {
    titleTimer++;
    background(0);
    pushStyle();
    textFont(titleFont,32);
    textSize(100);
    text("Gettin' Money", width/2,height/2-75);
    textSize(50);
    text("with", width/2,height/2);
    textSize(100);
    text("G-Eazy", width/2,height/2+75);
    popStyle();
  }
  else
  {
    background(0);
    pushStyle();
    textFont(titleFont,32);
    textSize(50);
    text("Start", width/2,height/2-50);
    strokeWeight(2);
    stroke(255);
    line(width/3,height/2,width*2/3,height/2);
    text("How to Play", width/2,height/2+50);
    popStyle();
  }
}

//this method displays the information page on how to play the game
public void infoScreen()
{
  background(30);
  
  //text identifying health bars
  pushStyle();
  fill(67,250,67);
  text("Player Health",180,height-60);
  fill(250,67,67);
  text("Boss Health",153,50);
  
  // instructions on how to play
  pushMatrix();
  pushStyle();
  translate(width/2-175,height/2-50);
  fill(200);
  text("Controls",180,120);
  fill(250,250,67);
  textSize(50);
  text("W", 50,10);
  text("A", 0,60);
  text("S", 50,60);
  text("D", 100,60);
  textSize(30);
  translate(300,0);
  text("+",-120,30);
  text("Left", 0,0);
  text("Mouse", 0,30);
  text("Button", 0,60);
  popStyle();
  popMatrix();
  popStyle();
  
  //boss health bar example
  pushStyle();
  stroke(67,250,67);
  fill(67,250,67);
  rect(10,height -35,p1.health,25);
  popStyle();
  
  //play health bar example
  pushStyle();
  stroke(250,67,67);
  fill(250,67,67);
  rect(10,10,b1.health,10);
  popStyle();
  
  //back button
  pushStyle();
  textFont(titleFont,32);
  text("Back",width-100,height-50);
  popStyle();
  
  //identifying characters and features
  pushStyle();
  pushMatrix();
  textFont(titleFont,32);
  text("Featuring", 100,200);
  translate(100,350); 
  scale(0.5f);
  image(jillStanding,0,0);
  image(isaStanding,200,0);
  popMatrix();
  popStyle();
  
  //hovering pop-up name information
  pushStyle();
  pushMatrix();
  translate(100,500);
  textFont(titleFont,32);
  if(mouseX>61&&mouseX<133&&mouseY>246&&mouseY<449)
  {
    text("Jilliana",0,0);
  }
  if(mouseX>169&&mouseX<231&&mouseY>253&&mouseY<445)
  {
    text("Isabella",105,0);
  }
  popMatrix();
  popStyle();
}


//increases the bossTimer
public void bossTimer()
{
  bossTimer++;
}

//resets the music
public void resetMusic()
{
  loadMusic();
  playMusic();
}

//displays the "Win Screen"
public void winScreen()
{
  background(30);
  //identifying characters and features
  pushStyle();
  pushMatrix();
  textFont(titleFont,32);
  translate(100,350); 
  scale(0.5f);
  image(jillStanding,0,0);
  image(isaStanding,200,0);
  scale(1.5f);
  image(smile,350,40);
  popMatrix();
  popStyle();
    
  //hovering pop-up name information
  pushStyle();
  pushMatrix();
  translate(100,475);
  textFont(titleFont,32);
  if(mouseX>61&&mouseX<133&&mouseY>246&&mouseY<449)
  {
    text("Jilliana",0,0);
  }
  if(mouseX>169&&mouseX<231&&mouseY>253&&mouseY<445)
  {
    text("Isabella",105,0);
  }
  popMatrix();
  popStyle();
  
  //show a "you win" screen
  pushMatrix();
  translate(winLoseScreenX,winLoseScreenY);
  pushStyle();
  textSize(40);
  fill(67,250,67);
  text("You Win!",width/2,height/2-50);
  fill(250,250,67);
  textSize(30);
  text("CASH: " + p1.coins,width/2,height/2);
  fill(255);
  //textSize(20);
  text("All I can say is",width/2,height/2+100);
  text("play on G-Eazy, play on.",width/2,height/2+150);
  fill(67,250,67);
  textSize(40);
  text("PLAY AGAIN",width/2,height/2+250);
  popStyle();
  popMatrix();
  
  //button for display of info & control page
  howToPlay();
}

//displays the "Lose Screen"
public void loseScreen()
{
    // show a "shocked" sprite signifying loss and disbelief
    background(50);
    pushMatrix();
    translate(300,400);
    scale(1);
    image(surprise,0,0);
    popMatrix();
    
    // show a "you lose" screen
    pushMatrix();
    translate(winLoseScreenX,winLoseScreenY);
    pushStyle();
    textSize(40);
    fill(250,67,67);
    text("You Lost.",width/2,height/2-50);
    fill(250,250,67);
    textSize(30);
    text("CASH: " + p1.coins,width/2,height/2);
    fill(255);
    text("Step it up and",width/2,height/2+100);
    text("slay 'em with the next album",width/2,height/2+150);
    fill(67,250,67);
    textSize(40);
    text("PLAY AGAIN",width/2,height/2+250);
    popStyle();
    popMatrix();
    
    //button for display of info & control page
    howToPlay();
}

//draws the playing state of the game
public void drawPlaying()
{
    background(50);
    if(backgroundArray != null)
    {
      drawBackground(); //draws the backround (tiles) of the game and is under the tab "drawBackground"
    }
    drawPlatforms(); //iterates through each platforms and redraws it
    
    p1.update(); //updates player
    bossTimer(); //increments bossTimer variable
    
    if(bossTimer >= 360)
    {
      b1.update();
      pushStyle();
      fill(250,67,67);
      stroke(250,67,67);
      popStyle();
    }
    
    //updates enemies when enemies exist & checks collisions between enemies
    for (int i = 0; i<enemies.size(); i++)
    {
      //update enemies
      MyCharacter enemy = enemies.get(i);
      if (!(enemies.size()==0)) enemy.update();
    
      //checks for collisions between player and enemy
      if(p1.hitCharacter(enemy))
      {
        p1.health = p1.health - enemy.damage;
      }
      
      //checks for collisions between enemies and other enemies (excluding boss)
      for(int j = 1; j <+enemies.size(); j++)
      {
        MyCharacter enemy2 = enemies.get(j);
        enemy.collide(enemy2);
      }
    }
    
    //forces that move the character
    if (up) p1.move(upForce);
    if (left) 
    {
      p1.move(leftForce);
      p1.alt = 0;
    }
    if (right)
    {
      p1.move(rightForce);
      p1.alt = 1;
    }
    
    //checks if player is alive and goes to loseScreen() if dead
    if(p1.health<=0)
    {
      delay++;
      if(delay >=60)
      {
        gameState = 2;
      }
    }
    
    //checks for win condition to go to winScreen()
    if(enemies.size() == 0 && b1.health <= 0 && p1.health>0)
    {
      delay++;
      if(delay >=200)
      {
        gameState = 1;
      }
    }
    
    // this method is part of the main sketch under the "Head_Up_Display" tab and is where the HUD is drawn
    headUpDisplay(); 
}

public void drawPlatforms()
{
   for (int i = 0; i < platforms.size(); i++) 
   {
     Platform p=platforms.get(i);
     p.draw();
   }
}
class Boss extends MyCharacter
{
  ArrayList<Projectile> projectilesBoss = new ArrayList<Projectile>(); //ArrayList holding boss' projectiles
  PVector direction; //PVector reference containing boss's movement that changes depending on player position
  PVector projectilePos; //PVector reference containing projectile position that changes as it moves
  final float magnitude = 2; //cap of movement speed
  PVector deathGraveyard; // offscreen graveyard so that the projectilesBoss dont hit the 'dead' characters where dead characters are teleported to

  //constructor of boss class
  Boss(PVector pos)
  {
    super(pos);
    health = 1500; //initial health of boss
    //heckles = new ArrayList<Heclke>(); 
    damage = 20;
    w = 50;
    h = 200;
    deathGraveyard = new PVector(-100,-100);
    coins = 1000; // how many points the boss is worth
  }
  
  //updates, fires, removes projectiles as well as updates the boss character and checks collison between boss and player
  public void update()
  {
    //update boss
    super.update();
    difficultyUpdate();
    
    //update boss projectiles
    checkProjectiles(); 
    updateProjectiles();
    removeProjectiles();
    projectileInterval();
    
    //checks collision
    collisionCheck(p1.pos, p1.vel, p1.w, p1.h);
  }  
  
  //moves the boss 
  public void move() 
  {
    direction = new PVector(p1.pos.x - pos.x,0);
    direction.setMag(magnitude);
    pos.add(vel);
    pos.add(direction);
    
  }
  
  //changes the difficulty & strength of the boss depending on his health
  public void difficultyUpdate()
  {
    if (health <= 1000 && health >500)
    {
      damage = 30;
    }
    else if (health<= 500)
    {
      damage = 40;
    }
  }

  //checks collisions between boss and player and damages player if that is the case
  public void collisionCheck(PVector pos, PVector vel, int w, int h) 
  {
    if((abs(this.pos.x - pos.x) <= (this.w/2)+(w/2) && this.pos.x > pos.x) && abs(this.pos.y-pos.y) <= (this.h/2)+h/2)
    {
      this.vel.add(new PVector(5,0));
      vel.add(new PVector (-5,0));
      p1.health -=damage;
    }
    
    if((abs(this.pos.x - pos.x) <= (this.w/2)+(w/2) && this.pos.x < pos.x) && abs(this.pos.y-pos.y) <= (this.h/2)+h/2)
    {
      this.vel.add(new PVector(-5,0));
      vel.add(new PVector (5,0));
      p1.health -=damage;
    }
  }
  
  //checks projectiles for collision with player and damages player if hit
  public void checkProjectiles()
  {
    for(int i = 0; i < projectilesBoss.size(); i++)
    {
      Projectile p = projectilesBoss.get(i);
      
      if (p.hit(p1))
      {
        projectilesBoss.remove(i);
        p1.health -=damage;
        if(p1.health<=0)
        {
          b1.alive= false;   
        }
        break;
      }         
    }
  }
  
  //fires projectile as well as calculates the direction 
  public void fireProjectile()
  {
    if (projectilesBoss.size()<=3) //fires maximum of 3 projectilesBoss
    {
       direction = new PVector(p1.pos.x-pos.x,p1.pos.y-pos.y);
       projectilePos = new PVector(pos.x,pos.y);
       
       if(health > 1000) 
       {
         Projectile p = new Projectile(projectilePos,direction,money,0.1f);
         projectilesBoss.add(p);
         p.update();
       }
       else if (health <= 1000 && health > 500)
       {
         Projectile p = new Projectile(projectilePos,direction,musicAlt,0.25f);
         projectilesBoss.add(p);
         p.update();
       }
       else
       {
         Projectile p = new Projectile(projectilePos,direction,booze,0.15f);
         projectilesBoss.add(p);
         p.update();
       }
       
    }
  }
  
  //this method only allows projectiles to be shot once every 2/3 of a second
  public void projectileInterval()
  {
    if(frameCount % 30 == 0)
    {
      fireProjectile();
    }
  }
  
  //this method removes projectiles by iterating through the projectilesBoss ArrayList
  public void removeProjectiles()
  {
    // removes off screen projectilesBoss
    for(int i = 0; i<projectilesBoss.size(); i++)
    {
      Projectile p = projectilesBoss.get(i);
      
      if(p.checkWalls())
      {
        projectilesBoss.remove(i);
      }
    }
  }
  
  // iterates through and updates all boss projectiles
  public void updateProjectiles()
  {
    for(int i=1; i<projectilesBoss.size(); i++)
    {
      Projectile p = projectilesBoss.get(i);
      p.update();
    }
  }
  
  // calculates angle between 
  public void calculateAngle()
  {
    angle = atan2(pos.y - p1.pos.y, pos.x - p1.pos.x) ;
  }
  
  //draws the boss character
  public void drawCharacter()
  {
    angle = atan2(pos.y - p1.pos.y, pos.x - p1.pos.x) - PI;
    
    if (livingCheck()) 
    {
      drawAlive();
    }
    else
    {
      drawDead();
    }
  }
  
  //draws the boss when alive
  public void drawAlive()
  {
    if(pos.x>=p1.pos.x)
    {
      pushMatrix();
      scale(0.5f);
      translate(pos.x*(1/0.5f),pos.y*(1/0.5f));
      if(walkCounter == 0)
      {
        image(corporate,0,0);
      }
      else
      {
        image(corporateWalking,0,0);
      }
      
      pushMatrix();
      rotate(angle-PI);
      image(weapon2,0,0);
      popMatrix();
      popMatrix();
    }
    else
    {
      pushMatrix();
      scale(0.5f);
      translate(pos.x*(1/0.5f),pos.y*(1/0.5f));
      if(walkCounter == 0)
      {
        image(corporateAlt,0,0);
      }
      else
      {
        image(corporateWalkingAlt,0,0);
      }
      
      pushMatrix();
      rotate(angle-PI);
      image(weapon2,0,0);
      popMatrix();
      popMatrix();
    }
  }
  
  //draws the boss when dead
  public void drawDead()
  {
    if(!alreadyDead)
    {
      deathPosX = PApplet.parseInt(pos.x);
      deathPosY = PApplet.parseInt(pos.y);
      alreadyDead = true;
      //p1.money += X;
    }
    pos = new PVector(deathGraveyard.x,deathGraveyard.y);
    pushMatrix();
    translate(deathPosX,deathPosY);
    scale(0.1f);
    
    image(money,-100,200);
    image(money,-100,150);
    image(money,-100,100);
    image(money,-100,50);
    //image(money,-100,0);
    
    image(money,0,250);
    image(money,0,200);
    image(money,0,150);
    image(money,0,100);
    image(money,0,50);
    image(money,0,0);
    
    image(money,100,300);
    image(money,100,250);
    image(money,100,200);
    image(money,100,150);
    
    popMatrix();   
  }
}
class Enemy extends MyCharacter
{
  PVector direction; //this variable is used to aim the weapon (pen) at the player
  float magnitude; //the speed of the enemy
  PVector deathGraveyard; // offscreen graveyard so that the projectiles dont hit the 'dead' characters where dead characters are teleported to
  boolean leftOf; // boolean to check and flip character depending on whether or not p1 is to the left or right of the enemy
  int damageTimer; // delays damage to player if it has been hit for 0.5 seconds
 
  //constructor of enemy class
  Enemy(PVector pos) 
  {
    super(pos);
    magnitude = random(2,6);
    w = 50;
    h = 125;
    damage = 20;
    coins = 100;
    deathGraveyard = new PVector(-100,-100);
  }
  
  //deaful move method that moves the enemy
  public void move()
  {
    if(frameCount % PApplet.parseInt(random(40,60)) == 0)
    {
      jump();
    }
    if(abs(p1.pos.x-pos.x)>=5)
    {
      direction = new PVector(p1.pos.x - pos.x, 0);
      direction.setMag(magnitude);
      pos.add(vel);
      pos.add(direction);
    } 
  }
 
  //updates the enemy and checks for collisions between itself and the player
  public void update()
  {
    super.update();
    collisionCheck(p1.pos, p1.vel, p1.w, p1.h);
  }
  
  //checks for collision between player and enemy
  public void collisionCheck(PVector pos, PVector vel, int w, int h) 
  {
    if((abs(this.pos.x - pos.x) <= (this.w/2)+(w/2) && this.pos.x > pos.x) && abs(this.pos.y-pos.y) <= (this.h/2)+h/2)
    {
      this.vel.add(new PVector(5,0));
      vel.add(new PVector (-5,0));
      p1.health-=damage;
    }
    
    if((abs(this.pos.x - pos.x) <= (this.w/2)+(w/2) && this.pos.x < pos.x) && abs(this.pos.y-pos.y) <= (this.h/2)+h/2)
    {
      this.vel.add(new PVector(-5,0));
      vel.add(new PVector (5,0));
      p1.health-=damage;
    }
  }
  
  //draws the default enemy
  public void drawCharacter()
  {
    angle = atan2(pos.y - p1.pos.y, pos.x - p1.pos.x) - PI;
    
    if (livingCheck()) 
    {
      drawAlive();
    }
    else
    {
      drawDead();
    }
  }
  
  //draws the enemy when dead
  public void drawDead()
  {
    if(!alreadyDead)
    {
      deathPosX = PApplet.parseInt(pos.x);
      deathPosY = PApplet.parseInt(pos.y);
      alreadyDead = true;
    }
    
    pos = new PVector(deathGraveyard.x,deathGraveyard.y);
    pushMatrix();
    translate(deathPosX,deathPosY);
    scale(0.1f);    
    popMatrix();
  }
   
  //draws the enemy when alive 
  public void drawAlive()
  {
    rect(10,10,10,10); //placeholder
  }
}
//displays the whole HUD by calling the healthBarBoss() method and healthBarPlayer() method
public void headUpDisplay()
{
  pushStyle();
  textAlign(LEFT,CENTER);
  if(bossTimer >= 360) healthBarBoss();
  healthBarPlayer();
  textSize(20);
  fill(67,250,67);
  fill(250,67,67);
  fill(250,250,67);
  text(p1.coins + "  CASH",40,60);
  //volumeControls();
  howToPlay();
  popStyle();
}

//displays the boss' health information
public void healthBarBoss()
{
  pushStyle();
  stroke(250,67,67);
  fill(250,67,67);
  rect(10,10,(b1.health),10);
  popStyle();
}

//displays the player's health information
public void healthBarPlayer()
{
  pushStyle();
  stroke(67,250,67);
  fill(67,250,67);
  rect(10,height -35,p1.health,25);
  popStyle();
}

// draws the icons representing volume controls
// not implemented (taken out due to complications)
public void volumeControls()
{
  pushMatrix();
  pushStyle();
  fill(250);
  translate(width-175,50);
  textFont(titleFont,32);
  text("Volume",0,0);
  popStyle();
  popMatrix();
}

//draws the how to play button that takes the player to the info screen
public void howToPlay()
{
  pushMatrix();
  pushStyle();
  textAlign(LEFT,CENTER);
  fill(250);
  translate(width-175,50);
  textFont(titleFont,32);
  text("How to Play",0,0);
  popStyle();
  popMatrix();
}
/*
This class is Isabella and is a subclass of enemy. 
It is almost identical to the Jilliana class and has the functionality of the enemy superclass.
The main difference is it uses a different set of images to animate Jilliana and it extends Jilliana.
*/

class Isabella extends Jill
{ 
  //Isabella contructor which makes a slightly larger hit box that Jilliana
  Isabella(PVector pos)
  {
    super(pos);
    scale = 0.35f;
    standing = isaStanding;
    walking = isaWalking;
    w = 55;
    h = 125; 
  }
  
  //updates Isabella
  public void update()
  {
    super.update();
  }
   
  //draws Isabella alive
  public void drawAlive()
  {
    if(pos.x>=p1.pos.x)
    {
      pushMatrix(); 
      scale(scale);
      if(walkCounter == 0)
      {
        image(isaStandingAlt,pos.x*(1/scale),pos.y*(1/scale));
      }
      else
      {
        image(isaWalkingAlt,pos.x*(1/scale),pos.y*(1/scale));
      }
      translate(pos.x*(1/scale),pos.y*(1/scale));
      rotate(angle);
      image(pen,0,0);
      popMatrix();
    }
    else
    {
      pushMatrix(); 
      scale(scale);
       if(walkCounter == 0)
      {
        image(isaStanding,pos.x*(1/scale),pos.y*(1/scale));
      }
      else
      {
        image(isaWalking,pos.x*(1/scale),pos.y*(1/scale));
      }
      translate(pos.x*(1/scale),pos.y*(1/scale));
      rotate(angle);
      image(pen,0,0);
      popMatrix();
    }
  }
}
/*
This class is Jilliana and is a subclass of enemy. 
It is almost identical to the Isabella class and has the functionality of the enemy superclass.
The main difference is it uses a different set of images to animate Jilliana.
*/

class Jill extends Enemy
{
  PImage standing; //varaible for standing image
  PImage walking;  //varaible for walking image
  float scale; //scale varaible to alter the size of Jill
  
  //Jilliana contructor which makes a slightly smaller hit box that Isabella
  Jill(PVector pos)
  {
    super(pos);
    scale = 0.35f;
    standing = jillStanding;
    walking = jillWalking;
    w = 50;
    h = 125;
    damage = 20;
  }
  
  //updates Jill
  public void update()
  {
    super.update();
  }
  
  //draws Jill alive
  public void drawAlive()
  {
    if(pos.x>=p1.pos.x)
    {
      pushMatrix(); 
      scale(scale);
      if(walkCounter == 0)
      {
        image(jillStandingAlt,pos.x*(1/scale),pos.y*(1/scale));
      }
      else
      {
        image(jillWalkingAlt,pos.x*(1/scale),pos.y*(1/scale));
      }
      translate(pos.x*(1/scale),pos.y*(1/scale));
      rotate(angle);
      image(pen,0,0);
      popMatrix();
    }
    else
    {
      pushMatrix(); 
      scale(scale);
       if(walkCounter == 0)
      {
        image(jillStanding,pos.x*(1/scale),pos.y*(1/scale));
      }
      else
      {
        image(jillWalking,pos.x*(1/scale),pos.y*(1/scale));
      }
      translate(pos.x*(1/scale),pos.y*(1/scale));
      rotate(angle);
      image(pen,0,0);
      popMatrix();
    }
  }
}
//these variables represent different vector forces that are used to move the player
float speed = 1;
PVector upForce = new PVector(0, -speed*2);
PVector downForce = new PVector(0, speed);
PVector leftForce = new PVector(-speed, 0);
PVector rightForce = new PVector(speed, 0);

//these booleans evaluate whether not certain keys are being pressed (WASD)
boolean up, down, left, right, walking; //movement directions

// this method fires the weapon when the left mouse button is clicked as well as the first half of the weapon kickback
// mousepressed also controls most of the switching between gamestates other than win/lose conditions that automically take the player to the winScreen or loseScreen
public void mousePressed() 
{
  println(mouseX, mouseY);
  if (mouseButton == LEFT) 
  {
    startScreenButtons();
    navigateFromInfoPage();
    navigateFromPlayingState();
    navigateFromLoseOrWinToInfo();
    resetCondition();
  }
  //volumeChange();
}

//volume change was semi-working but not worthwile in implementing
//allows the user to control volume in the game
public void volumeChange()
{
  if(mouseX<width && mouseX>width-50 && mouseY>50 && mouseY<100)
  {
    volume++;
    if(volume >=10)
    {
      volume = 10;
    }
    file.amp(1*volume);
    if(volume >=5)
    {
      volume = 5;
    }
  }
  
  if(mouseX<width-50 && mouseX>width-100 && mouseY>50 && mouseY<100)
  {
    volume--;
    if(volume <=0)
    {
      volume = 0;
    }
    file.amp(0.5f*volume);
  }
  
  if(mouseX<width-100 && mouseX>width-150 && mouseY>50 && mouseY<100)
  {
      volume = 0;
  }
}

//this method animates the second half the weapon kickback
public void mouseReleased()
{
  if (mouseButton == LEFT)
  {
    p1.weaponKickBack = 0;
  }
}


//this method evaluates keys that are pressed into true and false booleans used for player movement
public void keyPressed() 
{
  if (key == 'w') 
  {
    up = true;
    if(!p1.inAir)
    {
      p1.inAir = true;
      p1.jump();
    }
  }p1.wKey = true;
  if (key == 's') down = true; p1.sKey = true;
  if (key == 'a') left = true; p1.aKey = true;
  if (key == 'd') right = true; p1.dKey = true;
  if (key == ' ') p1.spacebar = true;
}

//this method evaluates if keys are released in booleans used for player movement (or lack thereof)
public void keyReleased() 
{
  if (key == 'w') up = false; p1.wKey = false;
  if (key == 's') down = false; p1.sKey = false;
  if (key == 'a') left = false; p1.aKey = false;
  if (key == 'd') right = false; p1.dKey = false;
  if (key == ' ') p1.spacebar = false;
}

//this method makes the boundary conditions for the buttons on the start screen and directs the game into different states 
public void startScreenButtons()
{
  if(gameState==99 && titleTimer > 240)
    {
      if(mouseX< 659 && mouseX > 547 && mouseY < 323 && mouseY > 276)
      {
        gameState = 0;
      }
      else if(mouseX>473 && mouseX< 733 && mouseY >378 && mouseY<438)
      {
        previousGameState = 99;
        gameState = 100;
      }
    }
}

//this method makes boundary conditions for the "back button" on the info page and either directs the game back to the start screen or the playing state itself dpending on what the previous gameState was
public void navigateFromInfoPage()
{
  if(gameState == 100)
  {
    if(mouseX< 1136 && mouseX > 1062 && mouseY < 674 && mouseY > 632 && previousGameState == 99)
    {
      gameState = 99;
    }
    else if(mouseX< 1136 && mouseX > 1062 && mouseY < 674 && mouseY > 632 && previousGameState == 0)
    {
      gameState = 0;
    }
    else if(mouseX< 1136 && mouseX > 1062 && mouseY < 674 && mouseY > 632 && previousGameState == 1)
    {
      gameState = 1;
    }
    else if(mouseX< 1136 && mouseX > 1062 && mouseY < 674 && mouseY > 632 && previousGameState == 2)
    {
      gameState = 2;
    }
  }
}

//this method fires projectiles while animating half of the weapon kick back animation as well as defining the boundary for the in-game "How to Play" button and directing the gameState to the infoPage
public void navigateFromPlayingState()
{
  if(gameState==0)
  {
    p1.fireProjectile();
    p1.weaponKickBack = 1;
    if(mouseX > 1022 && mouseX < 1187 && mouseY > 35 && mouseY < 74)
    {
      previousGameState = 0;
      gameState = 100; 
    }
  }
}

//directs the gameState to the info page when either in the win or lose screen
public void navigateFromLoseOrWinToInfo()
{
  if(gameState==1)
  {
    if(mouseX > 1022 && mouseX < 1187 && mouseY > 35 && mouseY < 74)
    {
      previousGameState = 1;
      gameState = 100; 
    }
  }
  
  if(gameState==2)
  {
    if(mouseX > 1022 && mouseX < 1187 && mouseY > 35 && mouseY < 74)
    {
      previousGameState = 2;
      gameState = 100; 
    }
  }
}

// this method creates the boundary conditions for the "Play Again" buttons on the win screen and lose screen and resets the game if the conidtions are met
public void resetCondition()
{
  if ((gameState == 1 || gameState == 2) && mouseX <= 965 && mouseX >= 631 && mouseY >= 528 && mouseY < 564)
  {
    reset();
  }
}
/*
This is the main superclass upon most of which other classes are built.
Player, Enemy and Money are all subclasses of this superclass and Jill and Isabella are second-teir subclasses of this superclass.
*/

class MyCharacter
{
  // default parameters of MyCharacter superclass
  // these are called member variables
  PVector pos, vel; // the position, velocity  of characters
  int coins; //this variable stores the amount of money each character has
  int health; // default health
  int w, h; //default width and height 
  int damage; // varies per subclass
  float acc; // accelerate
  boolean walking; // fully implemented in boss and 
  int walkTimer; // player but not yet implemented in enemy
  boolean alive; // this boolean checks if the character is alive
  int deathTimer; //this varaible is a timer that is added to every frame to
  float angle; //this variable calculates the angle betweent any character and the player
  boolean wKey; //boolean for if the "W" key is pressed
  boolean aKey; //boolean for if the "W" key is pressed
  boolean sKey; //boolean for if the "W" key is pressed
  boolean dKey; //boolean for if the "W" key is pressed
  boolean spacebar; //boolean for if the "SPACEBAR" key is pressed
  int walkCounter; //an integer variable that iterates between 0 and 1 to animate walking
  int deathPosX; //a integer variable that stores the horizontal coordinate of the character's death location
  int deathPosY; //a integer variable that stores the vertical coordinate of the character's death location
  boolean alreadyDead; // a boolean that checks if the character is already dead
  boolean inAir; // a boolean that checks if the character is in the air versus on a platform
  
  Platform platform = null; //sets the initial platform reference to null
  
  //default constructor
  MyCharacter(PVector pos)
  {
    this.pos = pos;
    deathTimer = 0;
    angle = 0;
    w = 100;
    h = 125;
    health = 150;
    damage = 10;
    acc = 0.5f;
    wKey = false;
    aKey = false;
    sKey = false;
    dKey = false;
    walkCounter = 0;
    alive = true;
    vel = new PVector ();
    inAir = true;
  }
  
  //updates the character
  public void update()
  {
    healthZero();
    calculateAngle();
    drawCharacter();
    move();
    walkChange();
    checkWalls();
    damp();
    if(!livingCheck())
    {
      deathTimer();
    }
    platformCheck();
  }
  
  //dampens the character's speed
  public void damp()
  {
    if(frameCount % 5 ==0)
      {
        vel.x = vel.x*acc;
        vel.y = vel.y*acc;
      }
  }

  // moves the character by calling for a type of force (left, right, down and up)
  public void moveForce(PVector force) 
  {
    vel.add(force);
  }
  
  // moves the character
  public void move()
  {
    pos.add(vel);
  }
  
  // draws the Character
  public void drawCharacter()
  {
    rect(pos.x,pos.y,100,100); //dummy method
  }
  
  //changes the walking varaible considering if the player has keys pressed and switches between two integers
  public void walkChange()
  {
    if(wKey || aKey || sKey || dKey)
    {
      walking = true;  
    }
    else
    {
      walking = false;
      //walkCounter = 0;
    }
    
    if(frameCount % 10 == 0 && walkCounter == 0)
    {
      walkCounter = 1;
    }
    else if (frameCount % 10 == 0 && walkCounter == 1)
    {
      walkCounter = 0;
    }
    
  }
  
  //checks boundaries for collision
  public void checkWalls()
  {
    //keeps the character on screen
    if (pos.x > width - w/2) moveForce(new PVector(-5,0));
    if (pos.x < w/2) moveForce(new PVector(5,0));
    if (pos.y > height - h/2) moveForce(new PVector(0,-5));
    if (pos.y < h/2) moveForce(new PVector(0,5));
  }
  
  //checks for collisions between characters
  public boolean hitCharacter(MyCharacter character)
  {
    if(abs(character.pos.x - pos.x)<w/2 && abs(character.pos.y - pos.y)<h/2)
    {
      return true; 
    }
    else
    {
      return false;
    }
  }
  
  // checks if character is alive
  public boolean livingCheck()
  {
    if(health>0)
    {
      return true;
    }
    else
    {
      return false;
    }
  }
  
  // decreases health of character
  public void decreaseHealth(int damage)
  {
    health = health - damage;
  }
  
  //iterates the death timer and the character  removes itself if the deathtimer reaches the threshold
  public void deathTimer()
  {
    deathTimer++; 
    if (deathTimer == 500)
    {
      enemies.remove(this);
      //DeathTimer = 0;
    }
  }
  
  //calculates angle between player and character
  public void calculateAngle()
  {
    angle = atan2(pos.y - p1.pos.y, pos.x - p1.pos.x);
  }
  
  //makes health 0 instead of negative
  public void healthZero()
  {
    if (health < 0) health = 0;
  }
  
  //checks for platform collisions and makes the player stop moving if it lands
  public void platformCheck()
  {
    if (platform!=null) 
    {
        if (!platform.isOn(this))
        {
            inAir = true; //if the character has walked off his platform, then the player is inAir
        }
    }
    
    for(int i = 0; i < platforms.size(); i++)
    {
      platform = platforms.get(i);
      
      if(platform.isOn(this))
      {
        if(vel.y >= 0)
        {
          landOn(platform);
        }
      }
      else
      {
        if(inAir)
        {
          fall();
        }
      }
    }    
  }
  
  //this method performs the land on action by stopping the character from moving
  public void landOn(Platform p) 
  {
    inAir = false;
    platform = p;
    vel.y = 0; 
  }

  //makes character fall
  public void fall() 
  {
    inAir = true;
    if (vel.y <= 10)
    {
      vel.y += 0.5f;
    }
  }
  
  public void jump()
  {
    vel.add(new PVector(0,random(-30,-25)));
  }
  
   public void collide(MyCharacter enemy)
  {
    if(abs(enemy.pos.x - pos.x) <=enemy.w/2 + w/2 && enemy.pos.x > pos.x && abs(enemy.pos.y - pos.y)< enemy.h/2+h/2)
    {
      pos.add(new PVector(-5,0));
      enemy.pos.add(new PVector(5,0));
    }
    
    if(abs(enemy.pos.x - pos.x) <=enemy.w/2 + w/2 && enemy.pos.x < pos.x && abs(enemy.pos.y - pos.y)< enemy.h/2+h/2)
    {
      pos.add(new PVector(5,0));
      enemy.pos.add(new PVector(-5,0));
    }
  }
}
/*
Tile class defines what a platform is and does. 
The platform class it used by all of the characters to stand on.
*/

class Platform 
{
  PVector pos, dim; //these varaibles respectively store position and dimensions of the platform
  
  //This contructor calls for the dimensions and position of a platform
  Platform(PVector pos, PVector dim) 
  {
    this.pos = pos;
    this.dim = dim;
  }

  //returns true if the character is on top of the platform 
  public boolean isOn(MyCharacter c) 
  {
    if (abs(c.pos.x - pos.x) < c.w/2 + dim.x/2 && abs((c.pos.y+c.h/2)-(pos.y-dim.y/2)) < 10) 
    { 
      return true;
    }
    else
    {
      return false;
    }
  }

  //simple collision check between the Character and the Platform
  public boolean collisionCheck(MyCharacter c) 
  {
    if (abs(c.pos.x - pos.x) < c.w/2 + dim.x/2 && c.pos.y == pos.y - c.h/2 - dim.y / 2) 
    {
      //pos.y = p.pos.y - h /2 - p.dim.y / 2;
      return true;
    }
    return false;
  }
  
  //this method draws the platform
  public void draw() 
  {
    pushMatrix();
    pushStyle();
    translate(pos.x, pos.y);
    fill(0);
    rect(-dim.x/2, -dim.y/2, dim.x, dim.y);
    //image(brickWall,-dim.x/2, -dim.y/2, dim.x, dim.y);
    popStyle();
    popMatrix();
  }

}
/*
Tile class defines the player and how it works. 
The player is the protagonist of the game which the user controls.
It uses several variables such as ArrayLists to store projectiles and a weaponKickBack variable to animate the weapon's kick-back.
*/

class Player extends MyCharacter
{
  ArrayList<Projectile> projectiles = new ArrayList<Projectile>(); //stores all player's projectiles
  PVector direction; // PVector for projectile direction
  PVector projectilePos; // Reference varaible for the projectile's position
  int weaponKickBack; // a integer variable that iterates between 0 and 1 to animate the gun's kick-back
  //int money; //not used currently
  int alt; //alt is a variable that is used to switch between left and right images of the character
  PVector deathGraveyard; // offscreen graveyard so that the projectiles dont hit the 'dead' characters where dead characters are teleported to
  
  //the contructor takes in an initial position for the character and defines its dimensions
  Player(PVector pos)
  {
    super(pos);
    //overridden paremeters
    w = 50;
    h = 125;
    damage = 40;
    coins = 10;
    health = 300;
    deathGraveyard = new PVector(-100,-100);
    alt = 1; 
  }
  
  //moves the player
  public void move(PVector force) 
  {
    vel.add(force);
  }
  
  //This updates the player, projectile collision check, update projectiles, and remove projectiles
  public void update()
  {
    super.update();
    //methods unique to player and boss subclasses
    checkProjectiles();
    updateProjectiles();
    removeProjectiles();
    //updateMoney();
    //jump();
  }  
  
  //iterates through projectiles ArrayList and updates each projectiles
  public void updateProjectiles()
  {
    for(int i=1; i<projectiles.size(); i++)
    {
      Projectile p = projectiles.get(i);
      p.update();
    }
  }
  
  //not currently implemented
  //iterates through monies ArrayLisr and updates each monies (monies in a colloquial term used, in the case, for the money that the 
  public void updateMoney()
  {
    for(int i=1; i<monies.size(); i++)
    {
      Money m = monies.get(i);
      m.update();
    }
  }
  
  //fires the projectile and detracts money from the player (cost)
  public void fireProjectile()
  {
    if (projectiles.size()<=3) //fires maximum of 3 projectiles
    {
       direction = new PVector(mouseX-pos.x,mouseY-pos.y);
       projectilePos = new PVector(pos.x,pos.y);
       Projectile p = new Projectile(projectilePos,direction,music,0.2f);
       projectiles.add(p);
       p.update();
       coins--;
    }
  }
  
  //checks walls removes the projectile
  public void removeProjectiles()
  {
    // removes off screen projectiles
    for(int i = 0; i<projectiles.size(); i++)
    {
      Projectile p = projectiles.get(i);
      
      if(p.checkWalls())
      {
        projectiles.remove(i);
      }
    }
  }
  
  //this draws the character
  public void drawCharacter()
  { 
    if (livingCheck()) 
    {
      drawAlive();
    }
    else
    {
      drawDead();
    }
  }
  
  //draws the character alive
  public void drawAlive()
  {
    pushMatrix();
    scale(0.3f);
    if(inAir)
    {
      if (alt == 1)
      {
        image(gEazyWalking,pos.x*(1/0.3f),pos.y*(1/0.3f));
      }
      else
      {
        image(gEazyWalkingAlt,pos.x*(1/0.3f),pos.y*(1/0.3f));
      }
    }
    else if(walking)
    {
      if(walkCounter == 0)
      {
        if (b1.alive == false)
        {
           if (alt == 1)
           {
              image(gEazyWalking,pos.x*(1/0.3f),pos.y*(1/0.3f));
           }
            else
           {
              image(gEazyWalkingAlt,pos.x*(1/0.3f),pos.y*(1/0.3f));
           }
          //image(smile,pos.x*(1/0.3),pos.y*(1/0.3));
          //image(gEazyWalking,pos.x*(1/0.3),pos.y*(1/0.3));
        }
        else 
        {
          if (alt == 1)
           {
              image(gEazyWalking,pos.x*(1/0.3f),pos.y*(1/0.3f));
           }
            else
           {
              image(gEazyWalkingAlt,pos.x*(1/0.3f),pos.y*(1/0.3f));
           }
        }
      }
      else
      {
        if (alt == 1)
        {
           image(gEazyStanding,pos.x*(1/0.3f),pos.y*(1/0.3f));
        }
         else
        {
           image(gEazyStandingAlt,pos.x*(1/0.3f),pos.y*(1/0.3f));
        }
        //image(gEazyStanding,pos.x*(1/0.3),pos.y*(1/0.3));
      }
    } 
    else if(!walking)
    {
      if (alt == 1)
      {
         image(gEazyStanding,pos.x*(1/0.3f),pos.y*(1/0.3f));
      }
      else
      {
         image(gEazyStandingAlt,pos.x*(1/0.3f),pos.y*(1/0.3f));
      }
      //image(gEazyStanding,pos.x*(1/0.3),pos.y*(1/0.3));
    }
    
    translate(pos.x*(1/0.3f),pos.y*(1/0.3f));
    rotate(angle-PI);
    
    // makes weapon kick back when shooting and also draws the weapon itself
    if(weaponKickBack == 0)
    {
       image(weapon,0,0);
    }
    else if (weaponKickBack == 1)
    {
       image(weapon,-20,0);
    }
    popMatrix();
  }
  
  //draws character when dead
  public void drawDead()
  {
    if(!alreadyDead)
    {
      deathPosX = PApplet.parseInt(pos.x);
      deathPosY = PApplet.parseInt(pos.y);
      alreadyDead = true;
      //p1.money += X;
    }
    pos = new PVector(deathGraveyard.x,deathGraveyard.y);
    pushMatrix();
    translate(deathPosX,deathPosY);
    scale(0.1f);
    image(booze,0,0);
    popMatrix();
    // draw money dropping 
    // image(...) or perhaps a class call to money
    
  }
  
  //checks projectiles for collisions with boss and enemies
  public void checkProjectiles()
  {
    for(int i = 0; i < projectiles.size(); i++)
    {
      Projectile p = projectiles.get(i);
      
      if (p.hit(b1))
      {
        p1.coins+=50;
        projectiles.remove(i);
        b1.health -=damage;
        if(b1.health<=0)
        {
          p1.coins+=b1.coins;
          b1.alive= false;   
        }
        break;
      }
      
      for(int j = 0; j < enemies.size(); j++)
      {
        MyCharacter e = enemies.get(j);
        
        if (e.deathTimer == 120)
        {
          deathTimer = 0;
          enemies.remove(e);
          p1.coins+=e.coins;
        }
        
        if(p.hit(e))
        {
            p1.coins+=10;
            projectiles.remove(i);
            e.health = e.health-damage;
            if(e.health<=0)
            {
              e.alive= false;   
            }
            break;
        } 
      }
    }
  }
  
  // updates the angle between the cursor and the player
  public void calculateAngle()
  {
    angle = atan2(pos.y - mouseY, pos.x - mouseX)+0.15f;
  }
  
  // makes the player jump
  public void jump()
  {
    vel.add(new PVector(0,-40));
  }
}
/*
NOT YET IMPLIMENTED
Tile class defines what a monies is and does. 
Basically the money drops after an enemy dies and leaves a POWER_UP for the player to utilize

This will extend the money sub-class which itself is a subclass of MyCharacter because of similar functionality
*/
/*
NOT YET IMPLIMENTED
Tile class defines what a monies is and does. 
Basically the money drops after an enemy dies and leaves a POWER_UP for the player to utilize
*/

class Money extends MyCharacter
{
  PVector pos, vel;
  float angle;
  int w, h; //width and height of projectile
  float acc;
  final float magnitude = 10; //initial speed of vector
  PImage image;
  float scale;
  int value;
  int posX;
  int posY;
  boolean inAir;
  Platform platform = null;
  
  Money(PVector pos)
  {
    super(pos);
    w = 50;
    h = 125;
    damage = 40;
    coins = 10;
    health = 300;
  }
  
  public void update()
  {
    drawMoney();
    checkMoney();
  }
  
  public void drawMoney()
  {
    pushMatrix();
    pushStyle();
    image(image,0,0);
    popStyle();
    popMatrix();
  }
  
  public void checkMoney()
  {
    if(collide())
    {
      collect();
    }
  }
  
  public boolean collide()
  {
    if((abs(p1.pos.x-pos.x)<=(w/2+p1.w/2)) && (abs(p1.pos.y-pos.y)<=(h/2+p1.h/2)))
    {
      return true;
    }
    else
    {
      return false;
    }
  }
  
  public void collect()
  {
    p1.coins += value;
  }
  
  public void accelerate()
  {
    //accelerate(gravityForce);
    //vel.add(gravityForce);
    pos.add(vel); 
  }
}
/*
Tile class defines what a projectile is and does. 
The projectile class it used by both the boss and the player to shoot.
*/

class Projectile
{
  PVector pos, vel; //PVectors containing the instance's position and velocity respecrtively
  int w, h; //width and height of projectile
  float acc; //acceleration of projectile
  final float magnitude = 10; //initial speed of vector
  PImage image; //reference variable for image of projectile
  float scale; //scale of projectile
  
  //The constructor takes in a starting position, direction of movement, image of a projectile and scale for size.
  Projectile(PVector pos, PVector direction, PImage image, float scale)
  {
    this.pos = pos;
    //direction.x = direction.x*0.05;
    //direction.y = direction.y*0.05;
    direction.setMag(magnitude);
    this.vel = direction;
    this.image = image;
    this.scale = scale;
    acc = 1.1f;
    
    w = 10;
    h = 10;
  }
  
  //updates projectile
  public void update()
  {
    move();
    accelerate();
    drawProjectile();
  }
  
  //accelerates projectile based on current speed
  public void accelerate()
  {
    if(frameCount % 10 ==0)
      {
        vel.x = vel.x*acc;
        vel.y = vel.y*acc;
      }
  }

  //moves the projectile
  public void move()
  {
    pos.add(vel);
  }
  
  //draws the projectile
  public void drawProjectile()
  {
    pushMatrix();
    pushStyle();
    fill(0);
    translate(pos.x,pos.y-10);
    scale(scale);
    image(image,0,0);
    popStyle();
    popMatrix();
  }
  
  //boundary detection
  public boolean checkWalls()
  {
    if ((pos.x>=width+w/2) || (pos.x<=-w/2) || (pos.y>=h/2+height) || (pos.y<=h/2))
    {
      return true;
    }
    else
    {
      return false;
    }
  }
  
  //collision detection calling an instance of a character 
  public boolean hit(MyCharacter character)
  {
    if((abs(character.pos.x-pos.x)<=(w/2+character.w/2)) && (abs(character.pos.y-pos.y)<=(h/2+character.h/2)))
    {
      return true;
    }
    else
    {
      return false;
    }
  }
}
/*
Tile class defines what a tile is and does. This tile is in the background 
and moves depending on the player's position horizontally.
This movement is to add a stylistic feeling of motion to the game. 
*/

class Tile 
{
  PVector pos;
  PVector p1Vel;
  
  //the constructor takes in an initial position and moves based on the class' p1Vel PVector which is constantly updated by the player's position
  Tile(PVector pos)
  {
    this.pos = pos;
  }
  
  public void update()
  {
    drawTile();
    playerRelativity();
    move();
  }
  
  //this method draws the tiles
  public void drawTile()
  {
    pushMatrix();
    translate(pos.x, pos.y);
    scale(0.3f);
    image(music,0,0);
    popMatrix();
  }
  
  //this method updates the p1Vel PVector of the tiles depending on the player's location, hence "playerRelativity"
  public void playerRelativity()
  {
    p1Vel = new PVector(-p1.vel.x*0.5f,0); //without vertical motion
  }
   
  //this method changes the position of the tiles with the updates p1Vel PVector 
  public void move()
  {
    pos.add(p1Vel);
  }
}
public void drawBackground() //draws the background
{
  //iterates through a 2d array of arrays of tiles and updates each tile
  for(int i = 0; i < (width + 500)/200; i ++)
  {
    ArrayList<Tile> array = backgroundArray.get(i);
    
    for(int j = 0; j <=  (height + 500)/200; j++)
    {
      Tile tile = array.get(j);
      tile.update();
    }
  }
}
  public void settings() {  size(1200,700); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "NewStone" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
