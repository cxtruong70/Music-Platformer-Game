/*
Tile class defines the player and how it works. 
The player is the protagonist of the game which the user controls.
It uses several variables such as ArrayLists to store projectiles and a weaponKickBack variable to animate the weapon's kick-back.
*/

class Player extends MyCharacter
{
  ArrayList<Projectile> projectiles = new ArrayList<Projectile>(); //stores all player's projectiles
  PVector direction; // PVector for projectile direction
  PVector projectilePos; // Reference varaible for the projectile's position
  int weaponKickBack; // a integer variable that iterates between 0 and 1 to animate the gun's kick-back
  //int money; //not used currently
  int alt; //alt is a variable that is used to switch between left and right images of the character
  PVector deathGraveyard; // offscreen graveyard so that the projectiles dont hit the 'dead' characters where dead characters are teleported to
  
  //the contructor takes in an initial position for the character and defines its dimensions
  Player(PVector pos)
  {
    super(pos);
    //overridden paremeters
    w = 50;
    h = 125;
    damage = 40;
    coins = 10;
    health = 300;
    deathGraveyard = new PVector(-100,-100);
    alt = 1; 
  }
  
  //moves the player
  void move(PVector force) 
  {
    vel.add(force);
  }
  
  //This updates the player, projectile collision check, update projectiles, and remove projectiles
  void update()
  {
    super.update();
    //methods unique to player and boss subclasses
    checkProjectiles();
    updateProjectiles();
    removeProjectiles();
    //updateMoney();
    //jump();
  }  
  
  //iterates through projectiles ArrayList and updates each projectiles
  void updateProjectiles()
  {
    for(int i=1; i<projectiles.size(); i++)
    {
      Projectile p = projectiles.get(i);
      p.update();
    }
  }
  
  //not currently implemented
  //iterates through monies ArrayLisr and updates each monies (monies in a colloquial term used, in the case, for the money that the 
  void updateMoney()
  {
    for(int i=1; i<monies.size(); i++)
    {
      Money m = monies.get(i);
      m.update();
    }
  }
  
  //fires the projectile and detracts money from the player (cost)
  void fireProjectile()
  {
    if (projectiles.size()<=3) //fires maximum of 3 projectiles
    {
       direction = new PVector(mouseX-pos.x,mouseY-pos.y);
       projectilePos = new PVector(pos.x,pos.y);
       Projectile p = new Projectile(projectilePos,direction,music,0.2);
       projectiles.add(p);
       p.update();
       coins--;
    }
  }
  
  //checks walls removes the projectile
  void removeProjectiles()
  {
    // removes off screen projectiles
    for(int i = 0; i<projectiles.size(); i++)
    {
      Projectile p = projectiles.get(i);
      
      if(p.checkWalls())
      {
        projectiles.remove(i);
      }
    }
  }
  
  //this draws the character
  void drawCharacter()
  { 
    if (livingCheck()) 
    {
      drawAlive();
    }
    else
    {
      drawDead();
    }
  }
  
  //draws the character alive
  void drawAlive()
  {
    pushMatrix();
    scale(0.3);
    if(inAir)
    {
      if (alt == 1)
      {
        image(gEazyWalking,pos.x*(1/0.3),pos.y*(1/0.3));
      }
      else
      {
        image(gEazyWalkingAlt,pos.x*(1/0.3),pos.y*(1/0.3));
      }
    }
    else if(walking)
    {
      if(walkCounter == 0)
      {
        if (b1.alive == false)
        {
           if (alt == 1)
           {
              image(gEazyWalking,pos.x*(1/0.3),pos.y*(1/0.3));
           }
            else
           {
              image(gEazyWalkingAlt,pos.x*(1/0.3),pos.y*(1/0.3));
           }
          //image(smile,pos.x*(1/0.3),pos.y*(1/0.3));
          //image(gEazyWalking,pos.x*(1/0.3),pos.y*(1/0.3));
        }
        else 
        {
          if (alt == 1)
           {
              image(gEazyWalking,pos.x*(1/0.3),pos.y*(1/0.3));
           }
            else
           {
              image(gEazyWalkingAlt,pos.x*(1/0.3),pos.y*(1/0.3));
           }
        }
      }
      else
      {
        if (alt == 1)
        {
           image(gEazyStanding,pos.x*(1/0.3),pos.y*(1/0.3));
        }
         else
        {
           image(gEazyStandingAlt,pos.x*(1/0.3),pos.y*(1/0.3));
        }
        //image(gEazyStanding,pos.x*(1/0.3),pos.y*(1/0.3));
      }
    } 
    else if(!walking)
    {
      if (alt == 1)
      {
         image(gEazyStanding,pos.x*(1/0.3),pos.y*(1/0.3));
      }
      else
      {
         image(gEazyStandingAlt,pos.x*(1/0.3),pos.y*(1/0.3));
      }
      //image(gEazyStanding,pos.x*(1/0.3),pos.y*(1/0.3));
    }
    
    translate(pos.x*(1/0.3),pos.y*(1/0.3));
    rotate(angle-PI);
    
    // makes weapon kick back when shooting and also draws the weapon itself
    if(weaponKickBack == 0)
    {
       image(weapon,0,0);
    }
    else if (weaponKickBack == 1)
    {
       image(weapon,-20,0);
    }
    popMatrix();
  }
  
  //draws character when dead
  void drawDead()
  {
    if(!alreadyDead)
    {
      deathPosX = int(pos.x);
      deathPosY = int(pos.y);
      alreadyDead = true;
      //p1.money += X;
    }
    pos = new PVector(deathGraveyard.x,deathGraveyard.y);
    pushMatrix();
    translate(deathPosX,deathPosY);
    scale(0.1);
    image(booze,0,0);
    popMatrix();
    // draw money dropping 
    // image(...) or perhaps a class call to money
    
  }
  
  //checks projectiles for collisions with boss and enemies
  void checkProjectiles()
  {
    for(int i = 0; i < projectiles.size(); i++)
    {
      Projectile p = projectiles.get(i);
      
      if (p.hit(b1))
      {
        p1.coins+=50;
        projectiles.remove(i);
        b1.health -=damage;
        if(b1.health<=0)
        {
          p1.coins+=b1.coins;
          b1.alive= false;   
        }
        break;
      }
      
      for(int j = 0; j < enemies.size(); j++)
      {
        MyCharacter e = enemies.get(j);
        
        if (e.deathTimer == 120)
        {
          deathTimer = 0;
          enemies.remove(e);
          p1.coins+=e.coins;
        }
        
        if(p.hit(e))
        {
            p1.coins+=10;
            projectiles.remove(i);
            e.health = e.health-damage;
            if(e.health<=0)
            {
              e.alive= false;   
            }
            break;
        } 
      }
    }
  }
  
  // updates the angle between the cursor and the player
  void calculateAngle()
  {
    angle = atan2(pos.y - mouseY, pos.x - mouseX)+0.15;
  }
  
  // makes the player jump
  void jump()
  {
    vel.add(new PVector(0,-40));
  }
}