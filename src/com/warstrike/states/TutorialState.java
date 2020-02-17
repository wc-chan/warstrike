package com.warstrike.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.warstrike.Window;

import org.lwjgl.input.Mouse;

public class TutorialState extends BasicGameState{
	
	/** The text. */
	Image background, tutorial, back, controls, text;
	
	/** The controls Y. */
	private int tutX, tutY, backX, backY, controlsX, controlsY;
	
	/** The font 2. */
	TrueTypeFont font, font2;

	/**
	 * 
	 * Instantiates a new tutorial state.
	 */
	public TutorialState() {
		
	}
	

	/**
	 * Initialise the tutorial state. This can be used to load static resources. 
	 * @param gc - The container holding the tutorial state
	 * @param sbg - Defines the tutorial state is controlled by which basic game state
	 * @throws SlickException - Throw to indicate an internal error
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

		text = new Image("res/test.png");

		if(ModifierState.getIceFlag() == true)
		{
			background = new Image("res/SnowBackground.png");
		}
		
		else if(ModifierState.getMudFlag() == true)
		{
			background = new Image("res/MudBackground.png");
			background = background.getScaledCopy(0.8f);
		}
		
		if(ModifierState.getIceFlag() == false && ModifierState.getMudFlag() == false)
		{
			background = new Image("res/GameBackground.jpg");
			background = background.getScaledCopy(0.55f);
		}
		
		tutorial = new Image("res/Tutorial.png");
		tutorial = tutorial.getScaledCopy(0.6f);
		back = new Image("res/back.png");
		controls = new Image("res/controls.png");
		controls = controls.getScaledCopy(0.45f);
		back = back.getScaledCopy(0.5f);
		
		tutX = Window.WIDTH / 2 - tutorial.getWidth() / 2;
		tutY = 60;
		backX = 10;
		backY = 10;
		controlsX = Window.WIDTH / 2 - controls.getWidth() / 2;
		controlsY = Window.HEIGHT / 2 - controls.getHeight() / 2;
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#render(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

		renderBackground(g);

		if(ModifierState.getIceFlag() == false && ModifierState.getMudFlag() == false) {
		//Attempt to load textures to the rectangle
		Image groundTexture = new Image("res/grassMid.png");
				
		//parameters are x position, y position, width, height, image, offsetX, offsetY
		g.fillRect(0.0f, Window.HEIGHT * 5.55f / 6.0f, (float) Window.WIDTH, Window.HEIGHT / 10.0f, groundTexture, 50, 0);
		}
			
		if(ModifierState.getIceFlag() == true) {
		//Attempt to load textures to the rectangle
		Image groundTexture = new Image("res/snowMid.png");
			
		//parameters are x position, y position, width, height, image, offsetX, offsetY

		g.fillRect(0.0f, Window.HEIGHT * 5.55f / 6.0f, (float) Window.WIDTH, Window.HEIGHT / 10.0f, groundTexture, 50, 0);
		}
			
		if(ModifierState.getMudFlag() == true) {
		//Attempt to load textures to the rectangle
		Image groundTexture = new Image("res/mudMid.png");
			
		//parameters are x position, y position, width, height, image, offsetX, offsetY
		g.fillRect(0.0f, Window.HEIGHT * 5.55f / 6.0f, (float) Window.WIDTH, Window.HEIGHT / 10.0f, groundTexture, 50, 0);
		}
		
		// Draw tutorial title
		tutorial.draw(tutX, tutY);
		
		//Draw back button
		back.draw(backX, backY);
	
		//Draw controls
		controls.draw(controlsX, controlsY);
		
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
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
	 * Draw string.
	 *
	 * @param x the x
	 * @param y the y
	 * @param string the string
	 */
	public void drawString(float x, float y, String string) {
		
	}
	
	/**
	 * This method renders the tutorial state's background.
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
			Image gameBackground = new Image("res/SnowBackground.jpg");
			gameBackground.draw();
			//Attempt to load textures to the rectangle
			Image groundTexture = new Image("res/snowMid.png");
					
			//parameters are x position, y position, width, height, image, offsetX, offsetY
			g.fillRect(0.0f, Window.HEIGHT * 5.55f / 6.0f, (float) Window.WIDTH, Window.HEIGHT / 10.0f, groundTexture, 50, 0);
		}
		
		//Mud
		if(ModifierState.getIceFlag() == false && ModifierState.getMudFlag() == true) {
			Image gameBackground = new Image("res/GameBackground.jpg");
			gameBackground = gameBackground.getScaledCopy(0.55f);
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
		return States.TUTORIAL;
	}
	
}