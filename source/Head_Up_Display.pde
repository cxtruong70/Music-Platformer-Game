//displays the whole HUD by calling the healthBarBoss() method and healthBarPlayer() method
void headUpDisplay()
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
void healthBarBoss()
{
  pushStyle();
  stroke(250,67,67);
  fill(250,67,67);
  rect(10,10,(b1.health),10);
  popStyle();
}

//displays the player's health information
void healthBarPlayer()
{
  pushStyle();
  stroke(67,250,67);
  fill(67,250,67);
  rect(10,height -35,p1.health,25);
  popStyle();
}

// draws the icons representing volume controls
// not implemented (taken out due to complications)
void volumeControls()
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
void howToPlay()
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