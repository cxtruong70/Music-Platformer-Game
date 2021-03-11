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
    acc = 1.1;
    
    w = 10;
    h = 10;
  }
  
  //updates projectile
  void update()
  {
    move();
    accelerate();
    drawProjectile();
  }
  
  //accelerates projectile based on current speed
  void accelerate()
  {
    if(frameCount % 10 ==0)
      {
        vel.x = vel.x*acc;
        vel.y = vel.y*acc;
      }
  }

  //moves the projectile
  void move()
  {
    pos.add(vel);
  }
  
  //draws the projectile
  void drawProjectile()
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
  boolean checkWalls()
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
  boolean hit(MyCharacter character)
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