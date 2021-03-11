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
    scale = 0.35;
    standing = jillStanding;
    walking = jillWalking;
    w = 50;
    h = 125;
    damage = 20;
  }
  
  //updates Jill
  void update()
  {
    super.update();
  }
  
  //draws Jill alive
  void drawAlive()
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