

public class Tile extends Entity{
	 private Game game; // the game in which the tile exists
	 public Tile(Game g, String r, int newX, int newY) {
		    super(r, newX, newY);  // calls the constructor in Entity
		    game = g;
		  } // constructor
	
	 public void collidedWith(Entity other) {
		 
	   } // collidedWith
}
