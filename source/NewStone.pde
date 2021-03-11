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
import processing.sound.*;
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

void setup()
{
  size(1200,700);
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
void initiateGame()
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
void reset() //called from the Keyboard_Input tab in mousePressed() and resets the game 
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
void initiateTiles()
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
void resetTiles()
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
void loadMusic()
{
  // Load a soundfile from the /data folder of the sketch and play it back
  almostFamous = new SoundFile(this, "06-g-eazy-almost_famous.mp3");
  meMyselfAndIMashup = new SoundFile(this, "Me, Myself, _ My Treasure - Bebe Rexha x G Eazy x Bruno Mars (Mashup) [E].mp3");
  opportunityCosts = new SoundFile(this,"05-g-eazy-opportunity_cost.mp3");
}

//this method plays one of 3 songs depending on the musicRandomizer variable
void playMusic()
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
int musicRandomizer()
{
  return musicRandomizer = int(round(random(-0.400,2.400)));
}

//this method loads the font of the game
void fontLoad()
{
  font = loadFont("04b30-48.vlw");
  textFont(font,32);
  titleFont = loadFont("BelindaW00-Regular-48.vlw");
  //textFont(titleFont,32); //this is only used for the title screen and is thus in a push/popStyle structure
}

//this method loads images into the reference variables
void loadImages()
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
void centerImages()
{
  // centering images
  imageMode(CENTER);
  textAlign(CENTER,CENTER);
}

//initializing platforms
void makePlatforms()
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
void draw()
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
void startScreen()
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
void infoScreen()
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
  scale(0.5);
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
void bossTimer()
{
  bossTimer++;
}

//resets the music
void resetMusic()
{
  loadMusic();
  playMusic();
}

//displays the "Win Screen"
void winScreen()
{
  background(30);
  //identifying characters and features
  pushStyle();
  pushMatrix();
  textFont(titleFont,32);
  translate(100,350); 
  scale(0.5);
  image(jillStanding,0,0);
  image(isaStanding,200,0);
  scale(1.5);
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
void loseScreen()
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
void drawPlaying()
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

void drawPlatforms()
{
   for (int i = 0; i < platforms.size(); i++) 
   {
     Platform p=platforms.get(i);
     p.draw();
   }
}