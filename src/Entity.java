/* Entity.java
 * An entity is any object that appears in the game.
 * It is responsible for resolving collisions and movement.
 */
 
 import java.awt.*;
 
 public abstract class Entity {

    // Java Note: the visibility modifier "protected"
    // allows the variable to be seen by this class,
    // any classes in the same package, and any subclasses
    // "private" - this class only
    // "public" - any class can see it
    
    protected double x;   // current x location
    protected double y;   // current y location
    protected Sprite sprite; // this entity's sprite
    protected double dx; // Vertical speed (px/s)  + -> right
    protected double dy; // Horizontal speed (px/s) + -> down
    
    private Rectangle me = new Rectangle(); // bounding rectangle of
                                            // this entity
    private Rectangle him = new Rectangle(); // bounding rect. of other
                                             // entities
                                             
    /* Constructor
     * input: reference to the image for this entity,
     *        initial x and y location to be drawn at
     */
     public Entity(String r, int newX, int newY) {
       x = newX;
       y = newY;
       sprite = (SpriteStore.get()).getSprite(r);
     } // constructor

    
     public void scroll(int scrollX) {
    	 System.out.println("scroll");
    
    	 if(x + scrollX < 600 && x + scrollX > -1) {
    		 
    		 //System.out.println(x + scrollX);
    		x += scrollX; 
    	 } else {
    		 x -= scrollX;
    		 
    	 } 
    	 
    	 
     }
     /* move
      * input: delta - the amount of time passed in ms
      * output: none
      * purpose: after a certain amout of time has passed,
      *          update the location
      */
     public void move(long delta) {
       // update location of entity based ov move speeds
       x += (delta * dx) / 1700;
       y += (delta * dy) / 1700;
       System.out.println("move");
     } // move

     // get and set velocities
     public void setVerticalMovement(double newDX) {
       dy = newDX;
     } // setVerticalMovement

     public void setHorizontalMovement(double newDY) {
       dx = newDY;
     } // setHorizontalMovement

     public double getVerticalMovement() {
       return dx;
     } // getVerticalMovement

     public double getHorizontalMovement() {
       return dy;
     } // getHorizontalMovement

     // get position
     public int getX() {
       return (int) x;
     } // getX

     public int getY() {
       return (int) y;
     } // getY

    /*
     * Draw this entity to the graphics object provided at (x,y)
     */
     public void draw (Graphics g) {
       sprite.draw(g,(int)x,(int)y);
     }  // draw
     
    /* Do the logic associated with this entity.  This method
     * will be called periodically based on game events.
     */
     public void doLogic() {}
     
     /* collidesWith
      * input: the other entity to check collision against
      * output: true if entities collide
      * purpose: check if this entity collides with the other.
      */
     public boolean collidesWith(Entity other) {
       me.setBounds((int)x, (int)y, sprite.getWidth(), sprite.getHeight());
       him.setBounds(other.getX(), other.getY(), 
                     other.sprite.getWidth(), other.sprite.getHeight());
       return me.intersects(him);
     } // collidesWith
     
     /* collidedWith
      * input: the entity with which this has collided
      * purpose: notification that this entity collided with another
      * Note: abstract methods must be implemented by any class
      *       that extends this class
      */
      public abstract void collidedWith(Entity other);

 } // Entity class