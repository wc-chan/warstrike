package com.warstrike.states;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.warstrike.Constants;
import com.warstrike.Window;

// TODO: Auto-generated Javadoc
/**
 * The Class MenuState.
 */
public class MenuState extends BasicGameState {

	Image spButton, mpButton, gameModifiersButton, tutorial, title, exit, background, soundOn, soundOff, audioOnOff;
	private Music menuMusic;
	/**
	 * X coordinate of the top left corner of the rectangle that encompasses the
	 * title
	 */
	private int titleX;
	/**
	 * Y coordinate of the top left corner of the rectangle that encompassed the
	 * title
	 */
	private int titleY;

	/** The sp X. */
	// Coordinates for drawing single player and multiplayer buttons
	private int spX;

	/** The sp Y. */
	private int spY;

	/** The mp X. */
	private int mpX;

	/** The mp Y. */
	private int mpY;

	/** The tut X. */
	private int tutX;

	/** The tut Y. */
	private int tutY;

	/** The game modifiers button X. */
	private int gameModifiersButtonX;

	/** The game modifiers button Y. */
	private int gameModifiersButtonY;

	/** The exit X. */
	private int exitX;

	/** The exit Y. */
	private int exitY;

	/** The sound X. */
	private int soundX;

	/** The sound Y. */
	private int soundY;

	/** The audio on off X. */
	private int audioOnOffX;

	/** The audio on off Y. */
	private int audioOnOffY;

	/** The font. */
	TrueTypeFont font;

	/** The gm. */
	private GameMode gm;

	/**
	 * Instantiates a new menu state.
	 */
	public MenuState() {
		gm = GameMode.NONE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.newdawn.slick.state.BasicGameState#getID()
	 */
	@Override
	public int getID() {
		return States.MENU;
	}

	/**
	 * Method that loads images for components
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

		// Initialise the main menu music
		menuMusic = new Music("res/MenuIntro.wav");
		menuMusic.setVolume(0.5f);
		menuMusic.loop();

		// Define the file paths for each image
		title = new Image("res/Warstrike.png");
		// title = title.getScaledCopy(2.0f);

		if (ModifierState.getIceFlag() == false && ModifierState.getMudFlag() == false) {
			background = new Image("res/GameBackground.jpg");
			background = background.getScaledCopy(0.8f);
		}

		spButton = new Image("res/SinglePlayer.png");
		spButton = spButton.getScaledCopy(Constants.MENU_BUTTON_SCALE);

		mpButton = new Image("res/MultiPlayer.png");
		mpButton = mpButton.getScaledCopy(Constants.MENU_BUTTON_SCALE);

		gameModifiersButton = new Image("res/GameModifiersButton.png");
		gameModifiersButton = gameModifiersButton.getScaledCopy(Constants.ACTION_BUTTON_SCALE);

		tutorial = new Image("res/Tutorial.png");
		tutorial = tutorial.getScaledCopy(Constants.MENU_BUTTON_SCALE);

		exit = new Image("res/Exit.png");
		exit = exit.getScaledCopy(Constants.MENU_BUTTON_SCALE);

		audioOnOff = new Image("res/AudioOnOffButton.png");
		audioOnOff = audioOnOff.getScaledCopy(Constants.ACTION_BUTTON_SCALE);

		// Set the coordinates so that the title and buttons are centered
		titleX = Window.WIDTH / 2 - title.getWidth() / 2;
		spX = Window.WIDTH / 2 - spButton.getWidth() / 2;
		mpX = Window.WIDTH / 2 - mpButton.getWidth() / 2;
		tutX = Window.WIDTH / 2 - tutorial.getWidth() / 2;
		gameModifiersButtonX = Window.WIDTH - gameModifiersButton.getWidth();
		exitX = Window.WIDTH / 2 - exit.getWidth() / 2;
		audioOnOffX = Window.WIDTH - audioOnOff.getWidth();

		titleY = Window.HEIGHT / 8;
		spY = Window.HEIGHT / 3;
		mpY = spY + spButton.getHeight() / 2 + 20 + mpButton.getHeight() / 2;
		tutY = mpY + mpButton.getHeight() / 2 + 20 + tutorial.getHeight() / 2;
		exitY = tutY + tutorial.getHeight() / 2 + 20 + exit.getHeight() / 2;
		audioOnOffY = 0;
		gameModifiersButtonY = 0 + audioOnOff.getHeight();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.newdawn.slick.state.GameState#render(org.newdawn.slick.GameContainer,
	 * org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

		renderBackground(g);

		if (ModifierState.getIceFlag() == false && ModifierState.getMudFlag() == false) {
			// Attempt to load textures to the rectangle
			Image groundTexture = new Image("res/grassMid.png");

			// parameters are x position, y position, width, height, image, offsetX, offsetY
			g.fillRect(0.0f, Window.HEIGHT * 5.55f / 6.0f, (float) Window.WIDTH, Window.HEIGHT / 10.0f, groundTexture,
					50, 0);
		}

		if (ModifierState.getIceFlag() == true) {
			// Attempt to load textures to the rectangle
			Image groundTexture = new Image("res/snowMid.png");

			// parameters are x position, y position, width, height, image, offsetX, offsetY

			g.fillRect(0.0f, Window.HEIGHT * 5.55f / 6.0f, (float) Window.WIDTH, Window.HEIGHT / 10.0f, groundTexture,
					50, 0);
		}

		if (ModifierState.getMudFlag() == true) {
			// Attempt to load textures to the rectangle
			Image groundTexture = new Image("res/mudMid.png");

			// parameters are x position, y position, width, height, image, offsetX, offsetY
			g.fillRect(0.0f, Window.HEIGHT * 5.55f / 6.0f, (float) Window.WIDTH, Window.HEIGHT / 10.0f, groundTexture,
					50, 0);
		}

		title.draw(titleX, titleY);

		// Draw single player button
		spButton.draw(spX, spY);
		// Draw multiplayer button
		mpButton.draw(mpX, mpY);
		// Draw tutorial button
		tutorial.draw(tutX, tutY);
		// Draw sound button
		gameModifiersButton.draw(gameModifiersButtonX, gameModifiersButtonY);
		// Draw exit button
		exit.draw(exitX, exitY);
		// Draw sound button
		audioOnOff.draw(audioOnOffX, audioOnOffY);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer,
	 * org.newdawn.slick.state.StateBasedGame, int)
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

		if (ModifierState.getIceFlag() == true) {
			background = new Image("res/SnowBackground.png");
		} else if (ModifierState.getMudFlag() == true) {
			background = new Image("res/MudBackground.png");
			background = background.getScaledCopy(0.8f);
		} else {
			background = new Image("res/GameBackground.jpg");
			background = background.getScaledCopy(0.55f);
		}

		Input input = gc.getInput();

		// Process the input to check what button is pressed
		if (input.isMousePressed(0)) {
			// Read the position of the mouse pointer
			int posX = Mouse.getX();
			int posY = Mouse.getY();

			// Check if the user has chosen a game mode
			if (inRect(spX, spY, spButton.getWidth(), spButton.getHeight(), posX, posY)) {
				gm = GameMode.SP;
			}

			else if (inRect(mpX, mpY, mpButton.getWidth(), mpButton.getHeight(), posX, posY)) {
				WaitState waitState = new WaitState();
				waitState.init(gc, sbg);
				sbg.addState(waitState);
				sbg.enterState(States.WAITSTATE);
			}

			else if (inRect(tutX, tutY, tutorial.getWidth(), tutorial.getHeight(), posX, posY)) {
				TutorialState tutState = new TutorialState();
				tutState.init(gc, sbg);
				sbg.addState(tutState);
				sbg.enterState(States.TUTORIAL);
			}

			else if (inRect(gameModifiersButtonX, gameModifiersButtonY, gameModifiersButton.getWidth(),
					gameModifiersButton.getHeight(), posX, posY)) {
				ModifierState modifierState = new ModifierState();
				modifierState.init(gc, sbg);
				sbg.addState(modifierState);
				sbg.enterState(States.MODIFIERS_STATE);
			}

			else if (inRect(exitX, exitY, exit.getWidth(), exit.getHeight(), posX, posY)) {
				System.exit(0);
			}

			if (inRect(audioOnOffX, audioOnOffY, audioOnOff.getWidth(), audioOnOff.getHeight(), posX, posY)) {
				// Mute if audio is not already muted
				if (menuMusic.getVolume() != 0) {
					menuMusic.setVolume(0);
				}
				// Re-enable music if audio is already muted
				else if (menuMusic.getVolume() == 0) {
					menuMusic.setVolume(0.5f);
				}
			}

			// Enter into the game state if a game mode has been chosen
			if (gm != GameMode.NONE) {
				PlayState playState = new PlayState();
				playState.setGameMode(gm);
				playState.init(gc, sbg);
				playState.setPlayers();
				sbg.addState(playState);

				menuMusic.stop();

				sbg.enterState(States.GAME);
				System.out.println("Entered playstate");
			}
		}
	}

	/**
	 * Helper method to check if a point is in a rectangle
	 *
	 * @param x      X coordinate of the top left corner of the rectangle
	 * @param y      Y coordinate of the top left corner of the rectangle
	 * @param width  Width of the rectangle
	 * @param height Height of the rectangle
	 * @param mouseX X coordinate of the point
	 * @param mouseY Y coordinate of the point
	 * @return <code>True</code> if the point is in the rectangle,
	 *         <code>False</code> otherwise
	 */
	private static boolean inRect(int x, int y, int width, int height, int mouseX, int mouseY) {
		mouseY = Window.HEIGHT - mouseY;
		return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
	}

	/**
	 * Render background.
	 *
	 * @param g the g
	 * @throws SlickException the slick exception
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
			Image gameBackground = new Image("res/SnowBackground.png");
			gameBackground.draw();
			// Attempt to load textures to the rectangle
			Image groundTexture = new Image("res/snowMid.png");

			// parameters are x position, y position, width, height, image, offsetX, offsetY
			g.fillRect(0.0f, Window.HEIGHT * 5.55f / 6.0f, (float) Window.WIDTH, Window.HEIGHT / 10.0f, groundTexture,
					50, 0);
		}

		// Mud
		if (ModifierState.getIceFlag() == false && ModifierState.getMudFlag() == true) {
			Image gameBackground = new Image("res/MudBackground.png");
			gameBackground = gameBackground.getScaledCopy(0.8f);
			gameBackground.draw(0, -60);
			// Attempt to load textures to the rectangle
			Image groundTexture = new Image("res/mudMid.png");

			// parameters are x position, y position, width, height, image, offsetX, offsetY
			g.fillRect(0.0f, Window.HEIGHT * 5.55f / 6.0f, (float) Window.WIDTH, Window.HEIGHT / 10.0f, groundTexture,
					50, 0);
		}
	}
}