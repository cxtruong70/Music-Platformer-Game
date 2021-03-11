void drawBackground() //draws the background
{
  //iterates through a 2d array of arrays of tiles and updates each tile
  for(int i = 0; i < (width + 500)/200; i ++)
  {
    ArrayList<Tile> array = backgroundArray.get(i);
    
    for(int j = 0; j <=  (height + 500)/200; j++)
    {
      Tile tile = array.get(j);
      tile.update();
    }
  }
}