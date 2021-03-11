//these variables represent different vector forces that are used to move the player
float speed = 1;
PVector upForce = new PVector(0, -speed*2);
PVector downForce = new PVector(0, speed);
PVector leftForce = new PVector(-speed, 0);
PVector rightForce = new PVector(speed, 0);

//these booleans evaluate whether not certain keys are being pressed (WASD)
boolean up, down, left, right, walking; //movement directions

// this method fires the weapon when the left mouse button is clicked as well as the first half of the weapon kickback
// mousepressed also controls most of the switching between gamestates other than win/lose conditions that automically take the player to the winScreen or loseScreen
void mousePressed() 
{
  println(mouseX, mouseY);
  if (mouseButton == LEFT) 
  {
    startScreenButtons();
    navigateFromInfoPage();
    navigateFromPlayingState();
    navigateFromLoseOrWinToInfo();
    resetCondition();
  }
  //volumeChange();
}

//volume change was semi-working but not worthwile in implementing
//allows the user to control volume in the game
void volumeChange()
{
  if(mouseX<width && mouseX>width-50 && mouseY>50 && mouseY<100)
  {
    volume++;
    if(volume >=10)
    {
      volume = 10;
    }
    file.amp(1*volume);
    if(volume >=5)
    {
      volume = 5;
    }
  }
  
  if(mouseX<width-50 && mouseX>width-100 && mouseY>50 && mouseY<100)
  {
    volume--;
    if(volume <=0)
    {
      volume = 0;
    }
    file.amp(0.5*volume);
  }
  
  if(mouseX<width-100 && mouseX>width-150 && mouseY>50 && mouseY<100)
  {
      volume = 0;
  }
}

//this method animates the second half the weapon kickback
void mouseReleased()
{
  if (mouseButton == LEFT)
  {
    p1.weaponKickBack = 0;
  }
}


//this method evaluates keys that are pressed into true and false booleans used for player movement
void keyPressed() 
{
  if (key == 'w') 
  {
    up = true;
    if(!p1.inAir)
    {
      p1.inAir = true;
      p1.jump();
    }
  }p1.wKey = true;
  if (key == 's') down = true; p1.sKey = true;
  if (key == 'a') left = true; p1.aKey = true;
  if (key == 'd') right = true; p1.dKey = true;
  if (key == ' ') p1.spacebar = true;
}

//this method evaluates if keys are released in booleans used for player movement (or lack thereof)
void keyReleased() 
{
  if (key == 'w') up = false; p1.wKey = false;
  if (key == 's') down = false; p1.sKey = false;
  if (key == 'a') left = false; p1.aKey = false;
  if (key == 'd') right = false; p1.dKey = false;
  if (key == ' ') p1.spacebar = false;
}

//this method makes the boundary conditions for the buttons on the start screen and directs the game into different states 
void startScreenButtons()
{
  if(gameState==99 && titleTimer > 240)
    {
      if(mouseX< 659 && mouseX > 547 && mouseY < 323 && mouseY > 276)
      {
        gameState = 0;
      }
      else if(mouseX>473 && mouseX< 733 && mouseY >378 && mouseY<438)
      {
        previousGameState = 99;
        gameState = 100;
      }
    }
}

//this method makes boundary conditions for the "back button" on the info page and either directs the game back to the start screen or the playing state itself dpending on what the previous gameState was
void navigateFromInfoPage()
{
  if(gameState == 100)
  {
    if(mouseX< 1136 && mouseX > 1062 && mouseY < 674 && mouseY > 632 && previousGameState == 99)
    {
      gameState = 99;
    }
    else if(mouseX< 1136 && mouseX > 1062 && mouseY < 674 && mouseY > 632 && previousGameState == 0)
    {
      gameState = 0;
    }
    else if(mouseX< 1136 && mouseX > 1062 && mouseY < 674 && mouseY > 632 && previousGameState == 1)
    {
      gameState = 1;
    }
    else if(mouseX< 1136 && mouseX > 1062 && mouseY < 674 && mouseY > 632 && previousGameState == 2)
    {
      gameState = 2;
    }
  }
}

//this method fires projectiles while animating half of the weapon kick back animation as well as defining the boundary for the in-game "How to Play" button and directing the gameState to the infoPage
void navigateFromPlayingState()
{
  if(gameState==0)
  {
    p1.fireProjectile();
    p1.weaponKickBack = 1;
    if(mouseX > 1022 && mouseX < 1187 && mouseY > 35 && mouseY < 74)
    {
      previousGameState = 0;
      gameState = 100; 
    }
  }
}

//directs the gameState to the info page when either in the win or lose screen
void navigateFromLoseOrWinToInfo()
{
  if(gameState==1)
  {
    if(mouseX > 1022 && mouseX < 1187 && mouseY > 35 && mouseY < 74)
    {
      previousGameState = 1;
      gameState = 100; 
    }
  }
  
  if(gameState==2)
  {
    if(mouseX > 1022 && mouseX < 1187 && mouseY > 35 && mouseY < 74)
    {
      previousGameState = 2;
      gameState = 100; 
    }
  }
}

// this method creates the boundary conditions for the "Play Again" buttons on the win screen and lose screen and resets the game if the conidtions are met
void resetCondition()
{
  if ((gameState == 1 || gameState == 2) && mouseX <= 965 && mouseX >= 631 && mouseY >= 528 && mouseY < 564)
  {
    reset();
  }
}