class Boss extends MyCharacter
{
  ArrayList<Projectile> projectilesBoss = new ArrayList<Projectile>(); //ArrayList holding boss' projectiles
  PVector direction; //PVector reference containing boss's movement that changes depending on player position
  PVector projectilePos; //PVector reference containing projectile position that changes as it moves
  final float magnitude = 2; //cap of movement speed
  PVector deathGraveyard; // offscreen graveyard so that the projectilesBoss dont hit the 'dead' characters where dead characters are teleported to

  //constructor of boss class
  Boss(PVector pos)
  {
    super(pos);
    health = 1500; //initial health of boss
    //heckles = new ArrayList<Heclke>(); 
    damage = 20;
    w = 50;
    h = 200;
    deathGraveyard = new PVector(-100,-100);
    coins = 1000; // how many points the boss is worth
  }
  
  //updates, fires, removes projectiles as well as updates the boss character and checks collison between boss and player
  void update()
  {
    //update boss
    super.update();
    difficultyUpdate();
    
    //update boss projectiles
    checkProjectiles(); 
    updateProjectiles();
    removeProjectiles();
    projectileInterval();
    
    //checks collision
    collisionCheck(p1.pos, p1.vel, p1.w, p1.h);
  }  
  
  //moves the boss 
  void move() 
  {
    direction = new PVector(p1.pos.x - pos.x,0);
    direction.setMag(magnitude);
    pos.add(vel);
    pos.add(direction);
    
  }
  
  //changes the difficulty & strength of the boss depending on his health
  void difficultyUpdate()
  {
    if (health <= 1000 && health >500)
    {
      damage = 30;
    }
    else if (health<= 500)
    {
      damage = 40;
    }
  }

  //checks collisions between boss and player and damages player if that is the case
  void collisionCheck(PVector pos, PVector vel, int w, int h) 
  {
    if((abs(this.pos.x - pos.x) <= (this.w/2)+(w/2) && this.pos.x > pos.x) && abs(this.pos.y-pos.y) <= (this.h/2)+h/2)
    {
      this.vel.add(new PVector(5,0));
      vel.add(new PVector (-5,0));
      p1.health -=damage;
    }
    
    if((abs(this.pos.x - pos.x) <= (this.w/2)+(w/2) && this.pos.x < pos.x) && abs(this.pos.y-pos.y) <= (this.h/2)+h/2)
    {
      this.vel.add(new PVector(-5,0));
      vel.add(new PVector (5,0));
      p1.health -=damage;
    }
  }
  
  //checks projectiles for collision with player and damages player if hit
  void checkProjectiles()
  {
    for(int i = 0; i < projectilesBoss.size(); i++)
    {
      Projectile p = projectilesBoss.get(i);
      
      if (p.hit(p1))
      {
        projectilesBoss.remove(i);
        p1.health -=damage;
        if(p1.health<=0)
        {
          b1.alive= false;   
        }
        break;
      }         
    }
  }
  
  //fires projectile as well as calculates the direction 
  void fireProjectile()
  {
    if (projectilesBoss.size()<=3) //fires maximum of 3 projectilesBoss
    {
       direction = new PVector(p1.pos.x-pos.x,p1.pos.y-pos.y);
       projectilePos = new PVector(pos.x,pos.y);
       
       if(health > 1000) 
       {
         Projectile p = new Projectile(projectilePos,direction,money,0.1);
         projectilesBoss.add(p);
         p.update();
       }
       else if (health <= 1000 && health > 500)
       {
         Projectile p = new Projectile(projectilePos,direction,musicAlt,0.25);
         projectilesBoss.add(p);
         p.update();
       }
       else
       {
         Projectile p = new Projectile(projectilePos,direction,booze,0.15);
         projectilesBoss.add(p);
         p.update();
       }
       
    }
  }
  
  //this method only allows projectiles to be shot once every 2/3 of a second
  void projectileInterval()
  {
    if(frameCount % 30 == 0)
    {
      fireProjectile();
    }
  }
  
  //this method removes projectiles by iterating through the projectilesBoss ArrayList
  void removeProjectiles()
  {
    // removes off screen projectilesBoss
    for(int i = 0; i<projectilesBoss.size(); i++)
    {
      Projectile p = projectilesBoss.get(i);
      
      if(p.checkWalls())
      {
        projectilesBoss.remove(i);
      }
    }
  }
  
  // iterates through and updates all boss projectiles
  void updateProjectiles()
  {
    for(int i=1; i<projectilesBoss.size(); i++)
    {
      Projectile p = projectilesBoss.get(i);
      p.update();
    }
  }
  
  // calculates angle between 
  void calculateAngle()
  {
    angle = atan2(pos.y - p1.pos.y, pos.x - p1.pos.x) ;
  }
  
  //draws the boss character
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
  
  //draws the boss when alive
  void drawAlive()
  {
    if(pos.x>=p1.pos.x)
    {
      pushMatrix();
      scale(0.5);
      translate(pos.x*(1/0.5),pos.y*(1/0.5));
      if(walkCounter == 0)
      {
        image(corporate,0,0);
      }
      else
      {
        image(corporateWalking,0,0);
      }
      
      pushMatrix();
      rotate(angle-PI);
      image(weapon2,0,0);
      popMatrix();
      popMatrix();
    }
    else
    {
      pushMatrix();
      scale(0.5);
      translate(pos.x*(1/0.5),pos.y*(1/0.5));
      if(walkCounter == 0)
      {
        image(corporateAlt,0,0);
      }
      else
      {
        image(corporateWalkingAlt,0,0);
      }
      
      pushMatrix();
      rotate(angle-PI);
      image(weapon2,0,0);
      popMatrix();
      popMatrix();
    }
  }
  
  //draws the boss when dead
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
    
    image(money,-100,200);
    image(money,-100,150);
    image(money,-100,100);
    image(money,-100,50);
    //image(money,-100,0);
    
    image(money,0,250);
    image(money,0,200);
    image(money,0,150);
    image(money,0,100);
    image(money,0,50);
    image(money,0,0);
    
    image(money,100,300);
    image(money,100,250);
    image(money,100,200);
    image(money,100,150);
    
    popMatrix();   
  }
}