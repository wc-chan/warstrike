package com.warstrike.states;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.warstrike.Window;
import com.warstrike.entities.AIPlayer;


/**
* <h2>Modifier State</h2>
* The modifier state defines all the logic that modifies the game such as maps and speed modes.
* @author  Anca Burduv, Andrei Mihai, Kelvin Chua, Liam Skop, Stefan Nedelcu, Weng Chan 
* @version 1.2.1
* @since   29-03-2019
*/
public class ModifierState extends BasicGameState {
	
	//Global variables
	Image background, tutorial, back, controls, text, iceButton, mudButton, normalButton, easyButton, mediumButton, hardButton;
	
	/** The normal button Y. */
	private int backX, backY, iceButtonX, iceButtonY, mudButtonX, mudButtonY, normalButtonX, normalButtonY;
	
	/** The hard Y. */
	private int easyX, easyY, mediumX, mediumY, hardX, hardY;
	
	/** The ice flag. */
	public static boolean iceFlag = false;
	
	/** The mud flag. */
	public static boolean mudFlag = false;
	
	/** The normal. */
	public static String normal = "Game Type : Normal";
	
	/** The ice. */
	public static String ice = "Game Type : Ice";
	
	/** The mud. */
	public static String mud = "Game Type : Mud";

	/** The easy. */
	public static String easy = "AI Difficulty : Easy";
	
	/** The medium. */
	public static String medium = "AI Difficulty : Medium";
	
	/** The hard. */
	public static String hard = "AI Difficulty : Hard";

	/**
	 * Instantiates a new modifier state.
	 */
	public ModifierState() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#init(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		background = new Image("res/GameBackground.jpg");
		background = background.getScaledCopy(0.55f);
		
		back = new Image("res/back.png");
		back = back.getScaledCopy(0.5f);
		
		iceButton = new Image("res/IceButton.png");
		iceButton = iceButton.getScaledCopy(0.6f);
		
		mudButton = new Image("res/mudButton.png");
		mudButton = mudButton.getScaledCopy(0.6f);
		
		normalButton = new Image("res/normalButton.png");
		normalButton = normalButton.getScaledCopy(0.6f);
		
		// Set the coordinates so that the title and buttons are centered
		iceButtonX = Window.WIDTH / 2 - iceButton.getWidth() / 2 - 500;
		mudButtonX = Window.WIDTH / 2 - mudButton.getWidth() / 2  - 100;
		normalButtonX = Window.WIDTH / 2 - normalButton.getWidth() / 2 - 300;
		
		// Set the coordinates so that the title and buttons are centered
		iceButtonY = Window.HEIGHT / 2 - iceButton.getHeight() / 2;
		mudButtonY = Window.HEIGHT / 2 - mudButton.getHeight() / 2;
		normalButtonY = Window.HEIGHT / 2 - normalButton.getHeight() / 2;
		
		//Initialize difficulty buttons
		easyButton = new Image("res/easyButton.png");
		easyButton = easyButton.getScaledCopy(0.5f);
		
		mediumButton = new Image("res/mediumButton.png");
		mediumButton = mediumButton.getScaledCopy(0.5f);
		
		hardButton = new Image("res/hardButton.png");
		hardButton = hardButton.getScaledCopy(0.5f);
		
		easyX = Window.WIDTH / 2 - easyButton.getWidth() / 2 + 300;
		mediumX = Window.WIDTH / 2 - mediumButton.getWidth() / 2 + 300;
		hardX = Window.WIDTH / 2 - hardButton.getWidth() / 2 + 300;
		
		easyY = Window.HEIGHT / 2 - easyButton.getHeight() / 2 - 100;
		mediumY = Window.HEIGHT / 2 - mediumButton.getHeight() / 2;
		hardY = Window.HEIGHT / 2 - hardButton.getHeight() / 2 + 100;
		
		backX = 10;
		backY = 10;
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#render(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */ 
	/**
	 * Render the modifier state's screen here.
	 * @param gc - The container holding the modifier state
	 * @param sbg - Defines the modifier state is controlled by which basic game state
	 * @param g - The graphics context that can be used to render. However, normal rendering routines can also be used.
	 * @throws SlickException - Throw to indicate an internal error
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		//Draw background
		renderBackground(g);

		//Draw back button
		back.draw(backX, backY);
		
		//Draw ice button
		iceButton.draw(iceButtonX, iceButtonY);
				
		//Draw normal button
		normalButton.draw(normalButtonX, normalButtonY);
				
		//Draw mud button
		mudButton.draw(mudButtonX, mudButtonY);
		
		//Draw difficulty buttons
		easyButton.draw(easyX, easyY);
		mediumButton.draw(mediumX, mediumY);
		hardButton.draw(hardX, hardY);
		
		if(AIPlayer.easy == true && AIPlayer.medium == false && AIPlayer.hard == false) {
			g.drawString(easy, Window.WIDTH / 2 + 200, (float) (Window.HEIGHT / 3.5 - g.getLineWidth()));
		}
		
		if(AIPlayer.easy == false && AIPlayer.medium == true && AIPlayer.hard == false) {
			g.drawString(medium, Window.WIDTH / 2 + 200, (float) (Window.HEIGHT / 3.5 - g.getLineWidth()));
		}
		
		if(AIPlayer.easy == false && AIPlayer.medium == false && AIPlayer.hard == true) {
			g.drawString(hard, Window.WIDTH / 2 + 200, (float) (Window.HEIGHT / 3.5 - g.getLineWidth()));
		}
		
		
		//Show option
		g.setColor(Color.black);
		
		if(iceFlag == false && mudFlag == false) {
			g.drawString(normal, Window.WIDTH / 2 - 400 , (float) (Window.HEIGHT / 3.5 - g.getLineWidth()));
		}
		
		if(iceFlag == true && mudFlag == false) {
			g.drawString(ice, Window.WIDTH / 2 - 400 , (float) (Window.HEIGHT / 3.5 - g.getLineWidth()));
		}
		
		if(iceFlag == false && mudFlag == true) {
			g.setColor(Color.white);
			g.drawString(mud, Window.WIDTH / 2 - 400 , (float) (Window.HEIGHT / 3.5 - g.getLineWidth()));
		}
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	/**
	 * The method updates the modifier state here. No rendering should take place in this method though it won't do any harm.
	 * @param gc container - The container holding this modifier state
	 * @param sbg - Defines the modifier state is controlled by which basic game state
	 * @param delta - The amount of time thats passed since last update in milliseconds
	 * @throws SlickException - Throw to indicate an internal error
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		Input input = gc.getInput();
		// Process the input to check what button is pressed
		if (input.isMouseButtonDown(0)) 
		{
			// Read the position of the mouse pointer
			int posX = Mouse.getX();
			int posY = Mouse.getY();

			// Check if the user has clicked the back button
			if (back != null) 
			{
				if (inRect(backX, backY, back.getWidth(), back.getHeight(), posX, posY)) 
				{
					sbg.enterState(States.MENU);
				}
			}
			
			if (inRect(iceButtonX, iceButtonY, iceButton.getWidth(), iceButton.getHeight(), posX, posY)) 
			{
				iceFlag = true;
				mudFlag = false;
			}
			
			if (inRect(mudButtonX, mudButtonY, mudButton.getWidth(), mudButton.getHeight(), posX, posY)) 
			{
				iceFlag = false;
				mudFlag = true;
			}
			
			if (inRect(normalButtonX, normalButtonY, normalButton.getWidth(), normalButton.getHeight(), posX, posY)) 
			{
				iceFlag = false;
				mudFlag = false;
			}
			
			if (inRect(easyX, easyY, easyButton.getWidth(), easyButton.getHeight(), posX, posY)) {
				AIPlayer.easyModeOn();
			}
			
			if (inRect(mediumX, mediumY, mediumButton.getWidth(), mediumButton.getHeight(), posX, posY)) {
				AIPlayer.mediumModeOn();
			}
			
			if (inRect(hardX, hardY, hardButton.getWidth(), hardButton.getHeight(), posX, posY)) {
				AIPlayer.hardModeOn();
			}
			
		}
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
	private static boolean inRect(int x, int y, int width, int height, int mouseX, int mouseY) {
		mouseY = Window.HEIGHT - mouseY;
		return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
	}
	
	/**
	 * This method sets the ice modifier
	 * @param iceFlag - Tells if the ice modifier is on or off
	 */
	public void setIceFlag(boolean iceFlag) {
		ModifierState.iceFlag = iceFlag;
	}
	
	/**
	 * Sets the mud flag.
	 *
	 * @param mudFlag the new mud flag
	 */
	public void setMudFlag(boolean mudFlag) {
		ModifierState.mudFlag = mudFlag;
	}
	
	/**
	 * Gets the ice flag.
	 *
	 * @return the ice flag
	 */
	public static boolean getIceFlag() {
		return iceFlag;
	}
	
	/**
	 * This method gets the status of the mud modifier
	 * @return mudFlag
	 */
	public static boolean getMudFlag() {
		return mudFlag;
	}
	
	/**
	 * This method renders the modifier state's background.
	 * @param g A graphics context that can be used to render primatives to the accelerated canvas provided by LWJGL.
	 * @throws SlickException - Throw to indicate an internal error
	 */
	private void renderBackground(Graphics g) throws SlickException {
		
		//Normal
		if(ModifierState.getIceFlag() == false && ModifierState.getMudFlag() == false) {
		Image gameBackground = new Image("res/GameBackground.jpg");
		gameBackground = gameBackground.getScaledCopy(0.55f);
		gameBackground.draw(0, -60);
		//Attempt to load textures to the rectangle
		Image groundTexture = new Image("res/grassMid.png");
				
		//parameters are x position, y position, width, height, image, offsetX, offsetY
		g.fillRect(0.0f, Window.HEIGHT * 5.55f / 6.0f, (float) Window.WIDTH, Window.HEIGHT / 10.0f, groundTexture, 50, 0);
		}
		
		//Ice
		if(ModifierState.getIceFlag() == true && ModifierState.getMudFlag() == false) {
		Image gameBackground = new Image("res/SnowBackground.png");
		gameBackground.draw();
		//Attempt to load textures to the rectangle
		Image groundTexture = new Image("res/snowMid.png");
				
		//parameters are x position, y position, width, height, image, offsetX, offsetY
		g.fillRect(0.0f, Window.HEIGHT * 5.55f / 6.0f, (float) Window.WIDTH, Window.HEIGHT / 10.0f, groundTexture, 50, 0);
		}
		
		//Mud
		if(ModifierState.getIceFlag() == false && ModifierState.getMudFlag() == true) {
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
	 * This method returns the game state
	 */
	@Override
	public int getID() {
		return States.MODIFIERS_STATE;
	}
}
