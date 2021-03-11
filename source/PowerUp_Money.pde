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
  
  void update()
  {
    drawMoney();
    checkMoney();
  }
  
  void drawMoney()
  {
    pushMatrix();
    pushStyle();
    image(image,0,0);
    popStyle();
    popMatrix();
  }
  
  void checkMoney()
  {
    if(collide())
    {
      collect();
    }
  }
  
  boolean collide()
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
  
  void collect()
  {
    p1.coins += value;
  }
  
  void accelerate()
  {
    //accelerate(gravityForce);
    //vel.add(gravityForce);
    pos.add(vel); 
  }
}