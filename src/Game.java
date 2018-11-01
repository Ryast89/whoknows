/* Game.java
 * Space Invaders Main Program
 *
 */

import javax.swing.*;

import java.io.File;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Game extends Canvas {


      	private BufferStrategy strategy;   // take advantage of accelerated graphics
        private boolean waitingForKeyPress = true;  // true if game held up until
        private map test = new map(1);                                      // a key is pressed
        private boolean leftPressed = false;  // true if left arrow key currently pressed
        private boolean rightPressed = false; // true if right arrow key currently pressed
        private boolean leftPressed2 = false;  // true if left arrow key currently pressed
        private boolean rightPressed2 = false; // true if right arrow key currently pressed
        private boolean jumpPressed = false; // true if firing
        private boolean jumpPressed2 = false; // true if firing
        private boolean jump = false;
        private boolean up = false;
        private boolean jump2 = false;
        private boolean up2 = false;
        private boolean gameRunning = true;
        private long lastJump = 0;
        private long lastJump2 = 0;
        private Entity tile;
       
        private ArrayList entities = new ArrayList(); // list of entities
                                                      // in game
        private ArrayList tiles = new ArrayList(); // list of entities
        private ArrayList removeTiles = new ArrayList();
        private ArrayList removeEntities = new ArrayList(); // list of entities
                                                            // to remove this loop
        private Entity player1;  // the first player
        private Entity player2;  // the second player
        private double moveSpeed = 250; // hor. vel. of ship (px/s)
        private long lastFire = 0; // time last shot fired
        private long firingInterval = 500; // interval between shots (ms)
        private int alienCount; // # of aliens left on screen

        private String message = ""; // message to display while waiting
                                     // for a key press

        private boolean logicRequiredThisLoop = false; // true if logic
                                                       // needs to be 
                                                       // applied this loop

    	/*
    	 * Construct our game and set it running.
    	 */
    	public Game() {
    		
    		int level = 1;
    		map test = new map(level);
    		// create a frame to contain game
    		JFrame container = new JFrame("Castle Defense");
    
    		// get hold the content of the frame
    		JPanel panel = (JPanel) container.getContentPane();
    
    		// set up the resolution of the game
    		panel.setPreferredSize(new Dimension(600,600));
    		panel.setLayout(null);
    
    		// set up canvas size (this) and add to frame
    		setBounds(0,0,600,600);
    		panel.add(this);
    
    		// Tell AWT not to bother repainting canvas since that will
            // be done using graphics acceleration
    		setIgnoreRepaint(true);
    
    		// make the window visible
    		container.pack();
    		container.setResizable(false);
    		container.setVisible(true);
    
    
            // if user closes window, shutdown game and jre
    		container.addWindowListener(new WindowAdapter() {
    			public void windowClosing(WindowEvent e) {
    				System.exit(0);
    			} // windowClosing
    		});
    
    		// add key listener to this canvas
    		addKeyListener(new KeyInputHandler());
    
    		// request focus so key events are handled by this canvas
    		requestFocus();

    		// create buffer strategy to take advantage of accelerated graphics
    		createBufferStrategy(2);
    		strategy = getBufferStrategy();
    
    		// initialize entities
    		initEntities();
    		initTiles();
    
    		// start the game
    		gameLoop();
        } // constructor
    
    
        /* initEntities
         * input: none
         * output: none
         * purpose: Initialise the starting state of the ship and alien entities.
         *          Each entity will be added to the array of entities in the game.
    	 */
    	private void initEntities() {
              // create the ship and put in center of screen
              // create a block of aliens (5x12)
    		player1 = new jumper(this, "sprites/ship.gif", 310, 520);
    		entities.add(player1);
    		player2 = new jumper(this, "sprites/ship.gif", 290, 520);
    		entities.add(player2);
    	} // initEntities
    	
    	private void initTiles() {
    		
    		File file = new File ("graphics1.txt");
    		try {
    			
    		Scanner sc = new Scanner(file);
    		for(int i = 0; i < 30; i++) {
    			for(int j = 0; j < 30; j++) {
    				int temp = sc.nextInt();
    				
    				if(temp == 0) {
    					
    					continue;
    				}
    				
    				tile = new Tile(this, "sprites/" + temp + ".png", j*20, i*20);
    				
    				tiles.add(tile);
    				
    			}
    		}
    		} catch (Exception e) {
    			System.out.println("testing");
    		return;
    		}
    		
    		
    		//int i = sc.nextInt();
    		
    	}
    	
    	
    	
        /* Notification from a game entity that the logic of the game
         * should be run at the next opportunity 
         */
         public void updateLogic() {
           logicRequiredThisLoop = true;
         } // updateLogic

         /* Remove an entity from the game.  It will no longer be
          * moved or drawn.
          */
         public void removeEntity(Entity entity) {
           removeEntities.add(entity);
         } // removeEntity

         /* Notification that the player has died.
          */
         public void notifyDeath() {
           message = "You DEAD!  Try again?";
           waitingForKeyPress = true;
         } // notifyDeath
         public void jump() {
        	 try {
        		 if (test.pixels[player1.getX()][player1.getY() + 40] == 0){
        			 
        			 return;
        		 } // if
        	 } catch (Exception e) {

        		 
        		 return;
        	 }
        	 jump = true;
        	 up = true;
        	 lastJump = System.currentTimeMillis();
        
              
         }
         public void jump2() {
        	 try {
        	 if (test.pixels[player2.getX()][player2.getY() + 40] == 0){
 
                 return;
               } // if
        	 } catch (Exception e) {

        		
        		 return;
        	 }
        	 jump2 = true;
        	 up2 = true;
        	 lastJump2 = System.currentTimeMillis();
              
         }

         /* Notification that the play has killed all aliens
          */
         public void notifyWin(){
           message = "You kicked some ALIEN BUTT!  You win!";
           waitingForKeyPress = true;
         } // notifyWin
          


	/*
	 * gameLoop
         * input: none
         * output: none
         * purpose: Main game loop. Runs throughout game play.
         *          Responsible for the following activities:
	 *           - calculates speed of the game loop to update moves
	 *           - moves the game entities
	 *           - draws the screen contents (entities, text)
	 *           - updates game events
	 *           - checks input
	 */
	public void gameLoop() {
		
          long lastLoopTime = System.currentTimeMillis();

          // keep loop running until game ends
          while (gameRunning) {
        	
              if (jumpPressed == true) {
            	 
              	jump();
              }
              if (jumpPressed2 == true) {
             	 
                	jump2();
                }
            // calc. time since last update, will be used to calculate
            // entities movement
            long delta = System.currentTimeMillis() - lastLoopTime;
            lastLoopTime = System.currentTimeMillis();

            // get graphics context for the accelerated surface and make it black
            Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
            g.setColor(Color.black);
            g.fillRect(0,0,600,600);

            // move each entity
            if (!waitingForKeyPress) {
              for (int i = 0; i < entities.size(); i++) {
                Entity entity = (Entity) entities.get(i);
                entity.move(delta);
              } // for
            } // if

            // draw all entities
            for (int i = 0; i < entities.size(); i++) {
               Entity entity = (Entity) entities.get(i);
               entity.draw(g);
            } // for
            for (int i = 0; i < tiles.size(); i++) {
                Entity tile = (Entity) tiles.get(i);
                tile.draw(g);
             } // for

            // brute force collisions, compare every entity
            // against every other entity.  If any collisions
            // are detected notify both entities that it has
            // occurred
           for (int i = 0; i < entities.size(); i++) {
             for (int j = i + 1; j < entities.size(); j++) {
                Entity me = (Entity)entities.get(i);
                Entity him = (Entity)entities.get(j);

                if (me.collidesWith(him)) {
                  me.collidedWith(him);
                  him.collidedWith(me);
                } // if
             } // inner for
           } // outer for

           // remove dead entities
           entities.removeAll(removeEntities);
           removeEntities.clear();

           // run logic if required
           if (logicRequiredThisLoop) {
             for (int i = 0; i < entities.size(); i++) {
               Entity entity = (Entity) entities.get(i);
               entity.doLogic();
             } // for
             logicRequiredThisLoop = false;
           } // if

           // if waiting for "any key press", draw message
           if (waitingForKeyPress) {
             g.setColor(Color.white);
             g.drawString(message, (600 - g.getFontMetrics().stringWidth(message))/2, 250);
             g.drawString("Press any key", (600 - g.getFontMetrics().stringWidth("Press any key"))/2, 300);
           }  // if

            // clear graphics and flip buffer
            g.dispose();
            strategy.show();

            // ship should not move without user input
            player1.setHorizontalMovement(0);
            player2.setHorizontalMovement(0);
            // respond to user moving ship
            if ((leftPressed) && (!rightPressed)) {
            	  player1.setHorizontalMovement(-moveSpeed);
            } else if ((rightPressed) && (!leftPressed)) {
            	  player1.setHorizontalMovement(moveSpeed);
            } // else
            if ((leftPressed2) && (!rightPressed2)) {
          	  player2.setHorizontalMovement(-moveSpeed);
          } else if ((rightPressed2) && (!leftPressed2)) {
          	  player2.setHorizontalMovement(moveSpeed);
          } // else
            
            if(jump == true) {
            	 player1.setVerticalMovement(-400 + (0.398) * (System.currentTimeMillis() - lastJump));
            } // if
            if(jump2 == true) {
            	player2.setVerticalMovement(-400 + (0.398) * (System.currentTimeMillis() - lastJump2));
            } // if
            if(test.pixels[player1.getX()][player1.getY()] == 1) {
            	jump = false;
            	player1.setVerticalMovement(1);
            }
            if(test.pixels[player2.getX()][player2.getY()] == 1) {
            	jump2 = false;
            	player2.setVerticalMovement(1);
            }
          }  
          

	} // gameLoop


        /* startGame
         * input: none
         * output: none
         * purpose: start a fresh game, clear old data
         */
         private void startGame() {
            // clear out any existing entities and initalize a new set
            entities.clear();
            
            initEntities();
            
            // blank out any keyboard settings that might exist
            leftPressed = false;
            rightPressed = false;
            jumpPressed = false;
            leftPressed2 = false;
            rightPressed2 = false;
            jumpPressed2 = false;
         } // startGame
        

        /* inner class KeyInputHandler
         * handles keyboard input from the user
         */
	private class KeyInputHandler extends KeyAdapter {
                 
                 private int pressCount = 1;  // the number of key presses since
                                              // waiting for 'any' key press

                /* The following methods are required
                 * for any class that extends the abstract
                 * class KeyAdapter.  They handle keyPressed,
                 * keyReleased and keyTyped events.
                 */
		public void keyPressed(KeyEvent e) {

                  // if waiting for keypress to start game, do nothing
                  if (waitingForKeyPress) {
                    return;
                  } // if
                  
                  // respond to move left, right or fire
                  if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    leftPressed = true;
                  } // if

                  if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    rightPressed = true;
                  } // if

                  if (e.getKeyCode() == KeyEvent.VK_UP) {
                    jumpPressed = true;
                    
                  } // if
                  if (e.getKeyCode() == KeyEvent.VK_A) {
                      leftPressed2 = true;
                    } // if

                    if (e.getKeyCode() == KeyEvent.VK_D) {
                      rightPressed2 = true;
                    } // if
                    if (e.getKeyCode() == KeyEvent.VK_W) {
                        jumpPressed2 = true;
                      } // if

		} // keyPressed

		public void keyReleased(KeyEvent e) {
                  // if waiting for keypress to start game, do nothing
                  if (waitingForKeyPress) {
                    return;
                  } // if
                  
                  // respond to move left, right or fire
                  if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    leftPressed = false;
                  } // if

                  if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    rightPressed = false;
                  } // if
                  
                  if (e.getKeyCode() == KeyEvent.VK_A) {
                      leftPressed2 = false;
                    } // if

                    if (e.getKeyCode() == KeyEvent.VK_D) {
                      rightPressed2 = false;
                    } // if

                  if (e.getKeyCode() == KeyEvent.VK_UP) {
                    jumpPressed = false;
                    
                  } // if
                  if (e.getKeyCode() == KeyEvent.VK_W) {
                      jumpPressed2 = false;
                    } // if

		} // keyReleased

 	        public void keyTyped(KeyEvent e) {

                   // if waiting for key press to start game
 	           if (waitingForKeyPress) {
                     if (pressCount == 1) {
                       waitingForKeyPress = false;
                       startGame();
                       pressCount = 0;
                     } else {
                       pressCount++;
                     } // else
                   } // if waitingForKeyPress

                   // if escape is pressed, end game
                   if (e.getKeyChar() == 27) {
                     System.exit(0);
                   } // if escape pressed

		} // keyTyped

	} // class KeyInputHandler
	
	


	/**
	 * Main Program
	 */
	public static void main(String [] args) {
        // instantiate this object
		new Game();
	} // main
} // Game