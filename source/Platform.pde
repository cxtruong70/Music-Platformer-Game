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
  boolean isOn(MyCharacter c) 
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
  boolean collisionCheck(MyCharacter c) 
  {
    if (abs(c.pos.x - pos.x) < c.w/2 + dim.x/2 && c.pos.y == pos.y - c.h/2 - dim.y / 2) 
    {
      //pos.y = p.pos.y - h /2 - p.dim.y / 2;
      return true;
    }
    return false;
  }
  
  //this method draws the platform
  void draw() 
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