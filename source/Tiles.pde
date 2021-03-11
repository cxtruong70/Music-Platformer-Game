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
  
  void update()
  {
    drawTile();
    playerRelativity();
    move();
  }
  
  //this method draws the tiles
  void drawTile()
  {
    pushMatrix();
    translate(pos.x, pos.y);
    scale(0.3);
    image(music,0,0);
    popMatrix();
  }
  
  //this method updates the p1Vel PVector of the tiles depending on the player's location, hence "playerRelativity"
  void playerRelativity()
  {
    p1Vel = new PVector(-p1.vel.x*0.5,0); //without vertical motion
  }
   
  //this method changes the position of the tiles with the updates p1Vel PVector 
  void move()
  {
    pos.add(p1Vel);
  }
}