package com.warstrike.states;

import java.awt.Font;
import java.io.InputStream;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import com.warstrike.Constants;
import com.warstrike.Window;
import com.warstrike.networking.*;
import com.warstrike.state.client.MultiplayerPlayState;

/**
* <h2>Wait State</h2>
* The wait state is the game lobby for multiplayer.
* @author  Anca Burduv, Andrei Mihai, Kelvin Chua, Liam Skop, Stefan Nedelcu, Weng Chan 
* @version 1.2.1
* @since   29-03-2019
*/
public class WaitState extends BasicGameState {
	
	/** The play. */
	Image background, multiplayer, back, play;

	/** The play Y. */
	private int mulX, mulY, backX, backY;
	
	/** The font. */
	TrueTypeFont font;
	
	/** The client. */
	private Client client;
	
	/**
	 * Instantiates a new wait state.
	 */
	public WaitState() {
		super();
		client = new Client("172.22.182.158", Constants.PORT);
		client.start();
	}
	

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#init(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
	
	/**
	 * Initialise the wait state. This can be used to load static resources. 
	 * @param gc - The container holding the wait state
	 * @param sbg - Defines the wait state is controlled by which basic game state
	 * @throws SlickException - Throw to indicate an internal error
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		try {
			InputStream inputStream = ResourceLoader.getResourceAsStream("res/BEBAS.ttf");

			Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont = awtFont.deriveFont(50f);
			font = new TrueTypeFont(awtFont, false);
		} catch (Exception e) {
			e.printStackTrace();
		}

		multiplayer = new Image("res/Multiplayer.png");

		if (ModifierState.getIceFlag() == true) {
			background = new Image("res/SnowBackground.jpg");
			// background = background.getScaledCopy(0.55f);
		}

		else if (ModifierState.getMudFlag() == true) {
			background = new Image("res/GameBackground.jpg");
			background = background.getScaledCopy(0.55f);
		}

		if (ModifierState.getIceFlag() == false && ModifierState.getMudFlag() == false) {
			background = new Image("res/GameBackground.jpg");
			background = background.getScaledCopy(0.55f);
		}

		back = new Image("res/back.png");
		back = back.getScaledCopy(0.5f);

		play = new Image("res/PLAY.png");
		play = play.getScaledCopy(0.55f);

		mulX = Window.WIDTH / 2 - multiplayer.getWidth() / 2;
		mulY = 120;
		backX = 10;
		backY = 10;

	}


	/**
	 * Render the wait state's screen here.
	 * @param gc - The container holding the wait state
	 * @param sbg - Defines the wait state is controlled by which basic game state
	 * @param g - The graphics context that can be used to render. However, normal rendering routines can also be used.
	 * @throws SlickException - Throw to indicate an internal error
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

		renderBackground(g);
		background.draw(0, -60);
		
		// Draw tutorial title
		multiplayer.draw(mulX, mulY);

		// Draw back button
		back.draw(backX, backY);
		font.drawString((float) Window.WIDTH / 2 - 290, (float) Window.HEIGHT / 2, "Waiting for other player...", Color.black);
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	/**
	 * The method updates the wait state here. No rendering should take place in this method though it won't do any harm.
	 * @param gc container - The container holding this wait state
	 * @param sbg - Defines the wait state is controlled by which basic game state
	 * @param delta - The amount of time thats passed since last update in milliseconds
	 * @throws SlickException - Throw to indicate an internal error
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		// Process the input to check what button is pressed
		if (input.isMousePressed(0)) {
			// Read the position of the mouse pointer
			int posX = Mouse.getX();
			int posY = Mouse.getY();

			// Check if the user has clicked the back button
			if (back != null) {
				if (inRect(backX, backY, back.getWidth(), back.getHeight(), posX, posY)) {
					client.interrupt();
					sbg.enterState(States.MENU);
				}
			}

		}
		
		if (client.canSend > 0) {
			MultiplayerPlayState mps = new MultiplayerPlayState(client);
			mps.init(gc, sbg);
			sbg.addState(mps);
			sbg.enterState(States.MP_STATE);
		}
	}

	/**
	 * This method renders the wait state's background.
	 * @param g A graphics context that can be used to render primatives to the accelerated canvas provided by LWJGL.
	 * @throws SlickException - Throw to indicate an internal error
	 */
	private void renderBackground(Graphics g) throws SlickException {

		// Normal
		if (ModifierState.getIceFlag() == false && ModifierState.getMudFlag() == false) {
			Image gameBackground = new Image("res/GameBackground.jpg");
			gameBackground = gameBackground.getScaledCopy(0.55f);
			gameBackground.draw(0, -60);
			// Attempt to load textures to the rectangle
			Image groundTexture = new Image("res/grassMid.png");

			// parameters are x position, y position, width, height, image, offsetX, offsetY
			g.fillRect(0.0f, Window.HEIGHT * 5.55f / 6.0f, (float) Window.WIDTH, Window.HEIGHT / 10.0f, groundTexture,
					50, 0);
		}

		// Ice
		if (ModifierState.getIceFlag() == true && ModifierState.getMudFlag() == false) {
			Image gameBackground = new Image("res/SnowBackground.jpg");
			gameBackground.draw();
			// Attempt to load textures to the rectangle
			Image groundTexture = new Image("res/snowMid.png");

			// parameters are x position, y position, width, height, image, offsetX, offsetY
			g.fillRect(0.0f, Window.HEIGHT * 5.55f / 6.0f, (float) Window.WIDTH, Window.HEIGHT / 10.0f, groundTexture,
					50, 0);
		}

		// Mud
		if (ModifierState.getIceFlag() == false && ModifierState.getMudFlag() == true) {
			Image gameBackground = new Image("res/GameBackground.jpg");
			gameBackground = gameBackground.getScaledCopy(0.55f);
			gameBackground.draw(0, -60);
			// Attempt to load textures to the rectangle
			Image groundTexture = new Image("res/mudMid.png");

			// parameters are x position, y position, width, height, image, offsetX, offsetY
			g.fillRect(0.0f, Window.HEIGHT * 5.55f / 6.0f, (float) Window.WIDTH, Window.HEIGHT / 10.0f, groundTexture,
					50, 0);
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
	 * This method returns the game state
	 */
	@Override
	public int getID() {
		return States.WAITSTATE;
	}

}
