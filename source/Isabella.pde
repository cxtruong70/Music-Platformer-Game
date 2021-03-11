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
    scale = 0.35;
    standing = isaStanding;
    walking = isaWalking;
    w = 55;
    h = 125; 
  }
  
  //updates Isabella
  void update()
  {
    super.update();
  }
   
  //draws Isabella alive
  void drawAlive()
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