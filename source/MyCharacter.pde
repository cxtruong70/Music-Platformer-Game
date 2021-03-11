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
    acc = 0.5;
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
  void update()
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
  void damp()
  {
    if(frameCount % 5 ==0)
      {
        vel.x = vel.x*acc;
        vel.y = vel.y*acc;
      }
  }

  // moves the character by calling for a type of force (left, right, down and up)
  void moveForce(PVector force) 
  {
    vel.add(force);
  }
  
  // moves the character
  void move()
  {
    pos.add(vel);
  }
  
  // draws the Character
  void drawCharacter()
  {
    rect(pos.x,pos.y,100,100); //dummy method
  }
  
  //changes the walking varaible considering if the player has keys pressed and switches between two integers
  void walkChange()
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
  void checkWalls()
  {
    //keeps the character on screen
    if (pos.x > width - w/2) moveForce(new PVector(-5,0));
    if (pos.x < w/2) moveForce(new PVector(5,0));
    if (pos.y > height - h/2) moveForce(new PVector(0,-5));
    if (pos.y < h/2) moveForce(new PVector(0,5));
  }
  
  //checks for collisions between characters
  boolean hitCharacter(MyCharacter character)
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
  boolean livingCheck()
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
  void decreaseHealth(int damage)
  {
    health = health - damage;
  }
  
  //iterates the death timer and the character  removes itself if the deathtimer reaches the threshold
  void deathTimer()
  {
    deathTimer++; 
    if (deathTimer == 500)
    {
      enemies.remove(this);
      //DeathTimer = 0;
    }
  }
  
  //calculates angle between player and character
  void calculateAngle()
  {
    angle = atan2(pos.y - p1.pos.y, pos.x - p1.pos.x);
  }
  
  //makes health 0 instead of negative
  void healthZero()
  {
    if (health < 0) health = 0;
  }
  
  //checks for platform collisions and makes the player stop moving if it lands
  void platformCheck()
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
  void landOn(Platform p) 
  {
    inAir = false;
    platform = p;
    vel.y = 0; 
  }

  //makes character fall
  void fall() 
  {
    inAir = true;
    if (vel.y <= 10)
    {
      vel.y += 0.5;
    }
  }
  
  void jump()
  {
    vel.add(new PVector(0,random(-30,-25)));
  }
  
   void collide(MyCharacter enemy)
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