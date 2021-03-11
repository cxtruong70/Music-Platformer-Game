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
  void move()
  {
    if(frameCount % int(random(40,60)) == 0)
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
  void update()
  {
    super.update();
    collisionCheck(p1.pos, p1.vel, p1.w, p1.h);
  }
  
  //checks for collision between player and enemy
  void collisionCheck(PVector pos, PVector vel, int w, int h) 
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
  void drawCharacter()
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
  void drawDead()
  {
    if(!alreadyDead)
    {
      deathPosX = int(pos.x);
      deathPosY = int(pos.y);
      alreadyDead = true;
    }
    
    pos = new PVector(deathGraveyard.x,deathGraveyard.y);
    pushMatrix();
    translate(deathPosX,deathPosY);
    scale(0.1);    
    popMatrix();
  }
   
  //draws the enemy when alive 
  void drawAlive()
  {
    rect(10,10,10,10); //placeholder
  }
}