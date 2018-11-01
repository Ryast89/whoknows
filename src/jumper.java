public class jumper extends Entity {
	 private Game game; // the game in which the ship exists
	 private map test = new map(1);
	 private boolean logTime = true;
	 private long lastFall = 0;
	  /* construct the player's ship
	   * input: game - the game in which the ship is being created
	   *        ref - a string with the name of the image associated to
	   *              the sprite for the ship
	   *        x, y - initial location of ship
	   */
	  public jumper(Game g, String r, int newX, int newY) {
	    super(r, newX, newY);  // calls the constructor in Entity
	    game = g;
	  } // constructor

	  /* move
	   * input: delta - time elapsed since last move (ms)
	   * purpose: move ship 
	   */
	  public void move (long delta){
		
		 
		
	    // stop at left side of screen
	    if ((dx < 0) && (x < 1)) {
	      super.scroll(590);
	    } // if
	    // stop at right side of screen
	    if ((dx > 0) && (x > 599)) {
	      super.scroll(-590);
	    } // if  
	  
	    if (dy <= 0 && test.pixels[this.getX()][this.getY()] == 1) {
			  
			   dy = 0;
			   logTime = true;
	    }
	    
	   if (dy >= 0 && test.pixels[this.getX()][this.getY() + 40] == 1) {
		  
		   dy = 0;
		   logTime = true;
	   } else if(dy >= 0 && test.pixels[this.getX()][this.getY() + 40] == 0){
		  
		   if (logTime) {

			   lastFall = System.currentTimeMillis();
			   logTime = false;

			  
		   }
		   dy = + 0.398 * (System.currentTimeMillis() - lastFall);
		   
	   }
	   
	   if ((dx <= 0 && test.pixels[this.getX()][this.getY() + 35] == 1) || (dx <= 0 && test.pixels[this.getX()][this.getY() + 5] == 1)) {
		
		 	super.scroll(1);  
		
    }
	   if ((dx >= 0 && test.pixels[this.getX()][this.getY() + 30] == 1) || (dx >= 0 && test.pixels[this.getX()][this.getY() + 35] == 1)) {
			  
		 	super.scroll(-1);
		
   }
	   
	   if (test.pixels[this.getX()][this.getY() + 40] == 2) {
		   game.notifyDeath();
	   }
	   if (test.pixels[this.getX()][this.getY() + 40] == 3 || test.pixels[this.getX()][this.getY() + 40] == 3) {
		   game.notifyWin();
	   }
	    super.move(delta);  // calls the move method in Entity
	  } // move
	  
	  
	  /* collidedWith
	   * input: other - the entity with which the ship has collided
	   * purpose: notification that the player's ship has collided
	   *          with something
	   */
	   public void collidedWith(Entity other) {
	 
	   } // collidedWith    

}