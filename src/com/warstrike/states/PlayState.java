package com.warstrike.states;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import com.warstrike.Constants;
import com.warstrike.Window;
import com.warstrike.entities.AIPlayer;
import com.warstrike.entities.AbstractPlayer;
import com.warstrike.entities.Bullet;
import com.warstrike.entities.HumanPlayer;
import com.warstrike.networking.Client;


/**
* <h2>Play State</h2>
* The play state defines all the in-game logic.
* @author  Anca Burduv, Andrei Mihai, Kelvin Chua, Liam Skop, Stefan Nedelcu, Weng Chan 
* @version 1.2.1
* @since   29-03-2019
*/
public class PlayState extends BasicGameState {
	
	//Positions of the tanks. P1 = player 1, P2 = player 2
	
	/** The Constant P1X. */
	private static final double P1X = 100.0;

	/** The Constant P1Y. */
	private static final double P1Y = Window.HEIGHT * 5.55 / 6.0- 74.0;
	
	/** The Constant P2X. */
	private static final double P2X = Window.WIDTH - 100.0 - 74.0;
	
	/** The Constant P2Y. */
	private static final double P2Y = P1Y;
		
	//Initialise player classes and bullet
			
	/** player on the left side of the screen, layer on the right side of the screen. */
	private AbstractPlayer leftPlayer, rightPlayer;
	
	/** . */
	private AbstractPlayer currentPlayer, thisPlayer;
	
	/** The bullet. */
	private Bullet bullet;
	
	/** The client. */
	private Client client;
	
	/** A modifier for the movement of the tanks. */
	private double speedModifier;
	
	/** The game-mode. */
	private GameMode gm;
	
	/** Ice-map, Mud-map. */
	private boolean ice, mud;

	/** Whether currentplayer can shoot, Whether currentplayer can move, Whether currentplayer can choose one of eiter action. */
	private boolean canShoot = true, canMove = true, chooseAction = true;

	/** game over?. */
	private boolean endGame = false;

	

	/** . */
	Image backButton, audioOnOff, endTurn, gameOver, currentPlayer1, currentPlayer2;;
	
	
	/** . */
	private int audioOnOffX, audioOnOffY;
	
	/** . */
	private float endTurnX, endTurnY;
	
	/** . */
	private float backButtonX, backButtonY;
	

	
	/** The game over Y. */
	private float gameOverX, gameOverY;
	private float currentPlayerX, currentPlayerY;
	
	/** The game ambience. */
	//Global variables for music/audio files
	private Music gameMusic, gameAmbience;
	
	/** The ammo impact. */
	private Sound bulletLaunch, bulletHit, ammoImpact;
	
	/** The mute sound effects. */
	private boolean muteSoundEffects;
	
	/** Whether AI-tank is moving. */
	private boolean isMoving = false; //boolean indicating whether AI is moving or not
	
	/** The direction in which the AI-tank should move. */
	private double direction = -1.0;

	private TrueTypeFont font1;

	
	/**
	 * Instantiates a new play state.
	 */
	public PlayState() {
		speedModifier = Constants.BASE_MODIFIER;
	}
	
	
	/**
	 * Method to update speedModifier for the current game.
	 */
	private void setSpeedModifier() 
	{
		if(ModifierState.getIceFlag() == true)
		{
			ice = true;
		}
		
		else if(ModifierState.getMudFlag() == true)
		{
			mud = true;
		}
		
		speedModifier *= speedModifier * (ice ? Constants.ICE_MODIFIER : 1.0) * (mud ? Constants.MUD_MODIFIER : 1.0);
	}
	
	/**
	 * The method sets the game mode
	 * @param gm - This parameter defines the game mode
	 */
	public void setGameMode(GameMode gm) {
		if (gm != GameMode.NONE) 
			this.gm = gm;
	}
	
	/**
	 * The method sets the player's position so a decision can be made whether the player is Player 1 (left) or Player 2 (right)
	 * @param gm - This parameter defines the game mode
	 */
	public void setPlayers() throws SlickException {
		if (gm != GameMode.NONE) {
			leftPlayer = new HumanPlayer(P1X, P1Y, Constants.RIGHT_ORIENTATION);
			if (gm == GameMode.SP) {
				rightPlayer = new AIPlayer(P2X, P2Y, Constants.LEFT_ORIENTATION, leftPlayer);
				thisPlayer = leftPlayer;
			} else if (gm == GameMode.MP) {
				client = new Client("localhost", 9565);
				rightPlayer = new HumanPlayer(P2X, P2Y, Constants.LEFT_ORIENTATION);
				/*if (client.ID == 1) 
					thisPlayer = leftPlayer;
				else thisPlayer = rightPlayer;*/
			}
			
			leftPlayer.setImage(new Image("res/Tank_body.png"));
			leftPlayer.setGunbarrel(new Image("res/Turret.png"));
			rightPlayer.setImage((new Image("res/Tank_body.png")).getFlippedCopy(true, false));
			rightPlayer.setGunbarrel(new Image("res/Turret.png"));
	
		currentPlayer = leftPlayer; }
	}
	

	/**
	 * Initialise the game. This can be used to load static resources. It's called before the game loop starts
	 * @param gc - container - The container holding the game
	 * @param sbg - Defines the game is controlled by which basic game state
	 * @throws SlickException - Throw to indicate an internal error
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

		//Set the game modifiers at initialisation
		setSpeedModifier();
		
		//Initialise animations
		/*explosionSheet = new SpriteSheet("res/ExplosionSpriteSheet.png", 110, 118);
		explosionAnimation = new Animation(explosionSheet, 100);
		explosionAnimation.setPingPong(true);*/
		
		//Initialise mute button
		audioOnOff = new Image("res/AudioOnOffButton.png");
		audioOnOff = audioOnOff.getScaledCopy(Constants.MENU_BUTTON_SCALE);		
		audioOnOffX = Window.WIDTH - audioOnOff.getWidth();
		audioOnOffY = 0;
			
		//Initialise the back to main menu button
		backButton = new Image("res/BackButton.png");
		backButton = backButton.getScaledCopy(Constants.MENU_BUTTON_SCALE);
		backButtonX = 0;
		backButtonY = 0;
				
		//Initialise end turn button
		endTurn = new Image("res/endTurn.png");
		endTurn = endTurn.getScaledCopy(0.5f);
		endTurnX = Window.WIDTH / 2 - endTurn.getWidth() / 2;
		endTurnY = Window.HEIGHT - 70;
				
		//Start the game music
		gameMusic = new Music("res/gameMusic.wav");
		gameMusic.setVolume(0.5f);
		gameMusic.loop();
				
		//Initialise bullet fire music
		bulletLaunch = new Sound("res/tankFire.wav");
		ammoImpact = new Sound("res/AmmoImpact.wav");
		
		//Initialise the images of the current player indicator
		currentPlayer1 = new Image("res/CurrentPlayer1.png");
		currentPlayer2 = new Image("res/CurrentPlayer2.png");
		currentPlayer1 = currentPlayer1.getScaledCopy(0.35f);
		currentPlayer2 = currentPlayer2.getScaledCopy(0.35f);
		currentPlayerX = Window.WIDTH / 2 - currentPlayer1.getWidth() / 2 - 20;
		currentPlayerY = Window.HEIGHT * 1/8;
	}

	/**
	 * Render the game's screen here.
	 * @param gc - The container holing this game
	 * @param sbg - Defines the game is controlled by which basic game state
	 * @param g - The graphics context that can be used to render. However, normal rendering routines can also be used.
	 * @throws SlickException - Throw to indicate an internal error
	 */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		renderBackground(g);
		renderHealthBar(g);
		renderFuelBar(g);
		showCurrentPlayer();
		
		//Draw the left and right tanks
		leftPlayer.render(g);
		rightPlayer.render(g);
		
		//Draw the bullet if not already drawn
		if (bullet != null) bullet.render(g);
		
		//Render the buttons
		renderButtons(g);
		
		//Render animation
		//explosionAnimation.draw( 100, 100);
		
		//Render 'game over' message
		if(leftPlayer.getHealth() == 0 || rightPlayer.getHealth() == 0) {
			gameOver = new Image("res/gameOver.png");
			gameOverX = Window.WIDTH / 2 - gameOver.getWidth() / 2;
			gameOverY = Window.HEIGHT / 2 - gameOver.getHeight() / 2 + 30;
			gameOver.draw(gameOverX, gameOverY);
			
		}
	}
	
	/**
<<<<<<< HEAD
	 * Render background.
	 *
	 * @param g the g
	 * @throws SlickException the slick exception
=======
	 * This method defines the logic of the current player's indicator. 
	 * If current player is the left player, the indicator will show Player 1.  
	 * If current player is the right player, the indicator will show Player 2. 
	 * @throws SlickException - Throw to indicate an internal error
	 */
	public void showCurrentPlayer() throws SlickException
	{		
		if(currentPlayer == leftPlayer)
			{
				currentPlayer1.draw(currentPlayerX, currentPlayerY);	
			}
		else if(currentPlayer == rightPlayer)
			{	
				currentPlayer2.draw(currentPlayerX, currentPlayerY);
			}
	}
	
	/**
	 * This method renders the game's background.
	 * @param g A graphics context that can be used to render primatives to the accelerated canvas provided by LWJGL.
	 * @throws SlickException
>>>>>>> real_time_graphics
	 */
	private void renderBackground(Graphics g) throws SlickException {
		
		//Normal mode background
		if(ModifierState.iceFlag == false && ModifierState.mudFlag == false) {
			Image gameBackground = new Image("res/GameBackground.jpg");
	
			gameBackground = gameBackground.getScaledCopy(0.55f);
			gameBackground.draw(0, -60);
			//Attempt to load textures to the rectangle
			Image groundTexture = new Image("res/grassMid.png");
					
			//parameters are x position, y position, width, height, image, offsetX, offsetY
			g.fillRect(0.0f, Window.HEIGHT * 5.55f / 6.0f, (float) Window.WIDTH, Window.HEIGHT / 10.0f, groundTexture, 50, 0);
		}
		
		//Ice mode background
		if(ModifierState.iceFlag == true && ModifierState.mudFlag == false) {
			Image gameBackground = new Image("res/SnowBackground.png");
			gameBackground.draw();
			//Attempt to load textures to the rectangle
			Image groundTexture = new Image("res/snowMid.png");
					
			//parameters are x position, y position, width, height, image, offsetX, offsetY
			g.fillRect(0.0f, Window.HEIGHT * 5.55f / 6.0f, (float) Window.WIDTH, Window.HEIGHT / 10.0f, groundTexture, 50, 0);
		}
		
		//Mud mode background
		if(ModifierState.iceFlag == false && ModifierState.mudFlag == true) 
		{
			Image gameBackground = new Image("res/MudBackground.png");
			gameBackground = gameBackground.getScaledCopy(0.8f);
			gameBackground.draw(0, -60);
			//Attempt to load textures to the rectangle
			Image groundTexture = new Image("res/mudMid.png");
							
			//parameters are x position, y position, width, height, image, offsetX, offsetY
	
			g.fillRect(0.0f, Window.HEIGHT * 5.55f / 6.0f, (float) Window.WIDTH, Window.HEIGHT / 10.0f, groundTexture, 50, 0);
		}
	}
	
	/**
<<<<<<< HEAD
	 * Render health bar.
	 *
	 * @param g the g
	 * @throws SlickException the slick exception
=======
	 * This method renders the game's health bars.
	 * @param g A graphics context that can be used to render primatives to the accelerated canvas provided by LWJGL.
	 * @throws SlickException
>>>>>>> real_time_graphics
	 */
	public void renderHealthBar(Graphics g) throws SlickException
	{
		float playerOneHealthBarX = Window.WIDTH * 1/6;
		float playerTwoHealthBarX = Window.WIDTH * 3.65f/6;
		int playerHealthBarY = Window.HEIGHT * 1/6;
		
		//Set labels and font colours
		g.setColor(Color.white);
		g.drawString("Player 1 Health", playerOneHealthBarX, playerHealthBarY - 20);
		g.drawString("Player 2 Health", playerTwoHealthBarX, playerHealthBarY - 20);
		
		//Player 1's health bar with gradient configurations
		Rectangle playerOneHealthBar = new Rectangle(playerOneHealthBarX, playerHealthBarY, (float) (200 * leftPlayer.getHealth() / Constants.MAX_HEALTH), 20);
        GradientFill fill = new GradientFill(playerOneHealthBarX, 0, new Color(255, 60, 0),
        		playerOneHealthBarX + 150, 0, new Color(255, 180, 0));
        
        g.setColor(Color.darkGray);
        g.fillRect(playerOneHealthBarX, playerHealthBarY, 200, 20);
        g.fill(playerOneHealthBar, fill);
        
        //Player 2's health bar with gradient configurations
		Rectangle playerTwoHealthBar = new Rectangle(playerTwoHealthBarX, playerHealthBarY, (float) (200 * rightPlayer.getHealth() / Constants.MAX_HEALTH), 20);
        fill = new GradientFill(playerTwoHealthBarX, 0, new Color(255, 60, 0),
        		playerTwoHealthBarX + 150, 0, new Color(255, 180, 0));

        g.setColor(Color.darkGray);
        g.fillRect(playerTwoHealthBarX, playerHealthBarY, 200, 20);
        g.fill(playerTwoHealthBar, fill); 
	}
	
	/**
<<<<<<< HEAD
	 * Render fuel bar.
	 *
	 * @param g the g
	 * @throws SlickException the slick exception
=======
	 * This method renders the game's fuel bars.
	 * @param g A graphics context that can be used to render primatives to the accelerated canvas provided by LWJGL.
	 * @throws SlickException
>>>>>>> real_time_graphics
	 */
	public void renderFuelBar(Graphics g) throws SlickException
	{
		float playerOneFuelBarX = Window.WIDTH * 1/6;
		float playerTwoFuelBarX = Window.WIDTH * 3.65f/6;
		float playerFuelBarY = Window.HEIGHT * 1.5f/6;
		
		//Set labels and font colours
		g.setColor(Color.white);
		g.drawString("Player 1 Fuel", playerOneFuelBarX, playerFuelBarY - 20);
		g.drawString("Player 2 Fuel", playerTwoFuelBarX, playerFuelBarY - 20);
		
		//Player 1's fuel bar with gradient configurations
		Rectangle playerOneHealthBar = new Rectangle(playerOneFuelBarX, playerFuelBarY, (float) (200 * leftPlayer.getFuel() / Constants.MAX_FUEL), 20);
        GradientFill fill = new GradientFill(playerOneFuelBarX, 0, new Color(204,204,0),
        		playerOneFuelBarX + 150, 0, new Color(249, 166, 2));

        g.setColor(Color.darkGray);
        g.fillRect(playerOneFuelBarX, playerFuelBarY, 200, 20);
        g.fill(playerOneHealthBar, fill);
        
        //Player 2's fuel bar with gradient configurations
		Rectangle playerTwoHealthBar = new Rectangle(playerTwoFuelBarX, playerFuelBarY, (float) (200 * rightPlayer.getFuel() / Constants.MAX_FUEL), 20);
        fill = new GradientFill(playerTwoFuelBarX, 0, new Color(204 ,204 ,0),
        		playerTwoFuelBarX + 150, 0, new Color(249, 166, 2));

        g.setColor(Color.darkGray);
        g.fillRect(playerTwoFuelBarX, playerFuelBarY, 200, 20);
        g.fill(playerTwoHealthBar, fill); 
	}
	
	/**
	 * Render buttons.
	 * @param g the g
	 * @throws SlickException the slick exception
	 * This method renders the game's buttons.
	 * @param g A graphics context that can be used to render primatives to the accelerated canvas provided by LWJGL.
	 * @throws SlickException - Throw to indicate an internal error
	 */
	public void renderButtons(Graphics g) throws SlickException
	{	
		audioOnOff.draw(audioOnOffX, audioOnOffY);
		backButton.draw(backButtonX,backButtonY);
		endTurn.draw(endTurnX, endTurnY);
	}
	
	

	/**
	 * The method updates the game logic here. No rendering should take place in this method though it won't do any harm.
	 * @param gc container - The container holding this game
	 * @param sbg - Defines the game is controlled by which basic game state
	 * @param delta - The amount of time thats passed since last update in milliseconds
	 * @throws SlickException - Throw to indicate an internal error
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		//Update animation
		//explosionAnimation.update(delta);
	
		//Initialise mouse input
		Input input = gc.getInput();
		
		float posX = Mouse.getX();
		float posY = Mouse.getY();
		
		if (input.isMousePressed(0))
		{
			checkSoundPress(posX, posY);
			checkActionPress(posX, posY);
			
			if (inRect(backButtonX, backButtonY, backButton.getWidth(), backButton.getHeight(), posX, posY)) {
				//Initialise the menu button
				gm = GameMode.NONE;
				MenuState menuState = new MenuState();
				menuState.init(gc, sbg);
				
				sbg.addState(menuState);
				sbg.enterState(States.MENU);		
			}

			if(leftPlayer.getHealth() == 0 || rightPlayer.getHealth() == 0) {
				gm = GameMode.NONE;
				if (inRect(gameOverX, gameOverY, gameOver.getWidth(), gameOver.getHeight(), posX, posY)) {
					MenuState menuState = new MenuState();
					menuState.init(gc, sbg);
					
					sbg.addState(menuState);
					sbg.enterState(States.MENU);
				}
			  }
		}
		
		if (gm == GameMode.MP) {
			if (thisPlayer.equals(currentPlayer)) {
				/*try {
					client.send(model);
				} catch (IOException e) {
					e.printStackTrace();
				}*/
				handleInput(input, delta);
			} else {
				/*try {
					model = client.read();
					parseModel(model);
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}*/
			}
		} else if (gm == GameMode.SP) {
			if (thisPlayer.equals(currentPlayer)) {
				handleInput(input, delta);
			} else {
				List<String> instructions = new ArrayList<String>(3);
				instructions = rightPlayer.play();
				String action = instructions.get(0);
				
				if (action == "shoot" && bullet == null) {
					//Plays the bullet launch/tank fire sound effect
					if (muteSoundEffects == false) {
						bulletLaunch.play(1.0f, 0.5f);
					}

					double xv = Double.parseDouble(instructions.get(1));
					double yv = Double.parseDouble(instructions.get(2));
					Image gb = rightPlayer.getGunbarrel();

					bullet = new Bullet(rightPlayer.getX() + (rightPlayer.getWidth() / 2) + (gb.getHeight() * Math.sin(gb.getRotation())),
							leftPlayer.getY(), xv, yv);
					bullet.setImage(new Image("res/Bullet.png").getScaledCopy(Constants.BULLET_SCALE).getFlippedCopy(true, false));
					
				}
				else if(action =="move") {
					isMoving = true;
					rightPlayer.SetActionToTrue();
				}
				if(isMoving) {
					
					if(Math.abs(leftPlayer.getX() - rightPlayer.getX()) < 400.0) {
						direction = 1.0;
					} else if((leftPlayer.getX() - rightPlayer.getX()) > 450.0) {
						direction = -1.0;
					}
					
					rightPlayer.updatePosition(direction*speedModifier, delta);
					if(rightPlayer.getFuel()<=0) {
						isMoving = false;
						rightPlayer.SetActionToFalse();
						rightPlayer.reInitialise();
					}
				}
					
			}
		}
		
		if(currentPlayer.getFuel() == 0.0) {
			changePlayer();
		}
		
		if (bullet != null) {
			bullet.update(delta);
			if (shouldBeDestroyed(bullet)) {
				bullet = null;
				changePlayer();
			}
			else if (bullet.collidesWith(leftPlayer)) {
				if (muteSoundEffects == false) {
					ammoImpact.play(1.0f, 0.5f);
				}
				leftPlayer.takeDamage();
				bullet = null;
				changePlayer();
			}
			else if (bullet.collidesWith(rightPlayer)) {
				if (muteSoundEffects == false) {
					ammoImpact.play(1.0f, 0.5f);
				}
				rightPlayer.takeDamage();
				bullet = null;
				changePlayer();
			}

		}
	}
	
	
	/**
	 * This method handles the input given by the user.
	 * @param input - The input given by the user
	 * @param delta - The amount of time thats passed since last update in milliseconds
	 * @throws SlickException - Throw to indicate an internal error
	 */
	private void handleInput(Input input, int delta) throws SlickException {
		if (thisPlayer.equals(currentPlayer)) {
			if (canMove) {
				if ((input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D))
						&& (currentPlayer.getOrientation() == -1 || !leftPlayer.collidesWith(rightPlayer))) {
					canShoot = false;
					chooseAction = false;
					currentPlayer.updatePosition(speedModifier, delta);
				}
				if ((input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A))
						&& (currentPlayer.getOrientation() == 1 || !rightPlayer.collidesWith(leftPlayer))) {
					canShoot = false;
					chooseAction = false;
					currentPlayer.updatePosition(-speedModifier, delta);
				}
			}
			
			if (canShoot) {
				if (input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W)) {
					canMove = false;
					chooseAction = false;
					currentPlayer.updateAngle(-delta);
				}
				if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S)) {
					canMove = false;
					chooseAction = false;
					currentPlayer.updateAngle(delta);
				}
				if (input.isKeyDown(Input.KEY_SPACE) && bullet == null) {
					canMove = false;
					chooseAction = false;
					//Plays the bullet launch/tank fire sound effect
					if (muteSoundEffects == false) {
						bulletLaunch.play(1.0f, 0.5f);
					}

					Image gb = leftPlayer.getGunbarrel();
					double xv = Math.cos(Math.toRadians(leftPlayer.getRotation())) * Constants.INIT_B_VELOCITY;
					double yv = -Math.sin(Math.toRadians(leftPlayer.getRotation())) * Constants.INIT_B_VELOCITY;
					bullet = new Bullet(
							leftPlayer.getX() + leftPlayer.getWidth() / 2 + gb.getHeight() * Math.sin(gb.getRotation()),
							leftPlayer.getY(), xv, yv);
					bullet.setImage(new Image("res/Bullet.png").getScaledCopy(Constants.BULLET_SCALE));
				} 
			}
		}
	}
	
	/**
	 * This method checks if the user has clicked on the audio button.
	 * @param posX - The X position of the user's mouse
	 * @param posY - The Y position of the user's mouse
	 */
	private void checkSoundPress(float posX, float posY) {
		
		if (inRect(audioOnOffX, audioOnOffY, audioOnOff.getWidth(), audioOnOff.getHeight(), posX, posY)) {
			if (gameMusic.getVolume() != 0) {
				gameMusic.setVolume(0);
				muteSoundEffects = true;
			} else if (gameMusic.getVolume() == 0) {
				gameMusic.setVolume(0.5f);
				muteSoundEffects = false;
			}
		}
	}
	
	/**
	 * Check action press.
	 *
	 * @param posX the pos X
	 * @param posY the pos Y
	 */
	private void checkActionPress(float posX, float posY) {
		if(gm == GameMode.SP) {
			if(currentPlayer.equals(leftPlayer)) {
			if (inRect(endTurnX, endTurnY, endTurn.getWidth(), endTurn.getHeight(), posX, posY)) {
				canShoot = false;
				canMove = false;
				chooseAction = true;
				changePlayer();
			}
			}
		}
	}
	
	/**
	 * This method checks if the bullet should be destroyed or not.
	 * @param bullet - The bullet object
	 */
	private static boolean shouldBeDestroyed(Bullet bullet) {
		if (bullet.getX() >= Window.WIDTH || 
			bullet.getY() >= Window.HEIGHT * 5.61f / 6.0f || 
			bullet.getX() == 0.0f) {
			return true;
		}
		return false;
	}
	
	/**
	 * Helper method to check if a point is in a rectangle
	 * @param x - X coordinate of the top left corner of the rectangle
	 * @param y - Y coordinate of the top left corner of the rectangle
	 * @param width - Width of the rectangle
	 * @param height - Height of the rectangle
	 * @param mouseX - X coordinate of the point
	 * @param mouseY - Y coordinate of the point
	 * @return <code>True</code> if the point is in the rectangle, <code>False</code> otherwise 
	 */
	private static boolean inRect(float x, float y, float width, float height, float mouseX, float mouseY) {
		mouseY = (float) Window.HEIGHT - mouseY;
		return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
	}
	
	/**
	 * This method changes who is playing currently.
	 */
	private void changePlayer() {
		if (currentPlayer == leftPlayer) {
			leftPlayer.setFuel(leftPlayer.getFuel() + 25.0);
			currentPlayer = rightPlayer;
		}
		else if (currentPlayer == rightPlayer) {
			rightPlayer.setFuel(rightPlayer.getFuel() + 25.0);
			currentPlayer = leftPlayer;
			canMove = true;
			canShoot = true;
			chooseAction = true;
		}
	}
	

	/**
	 * This method returns the game state
	 */
	@Override
	public int getID() {
		return States.GAME;
	}

}
