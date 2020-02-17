package com.warstrike.state.client;

import java.util.Scanner;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.warstrike.Constants;
import com.warstrike.Window;
import com.warstrike.commands.Commands;
import com.warstrike.entities.Platform;
import com.warstrike.networking.Client;
import com.warstrike.states.Model;
import com.warstrike.states.ModifierState;
import com.warstrike.states.States;

public class MultiplayerPlayState extends BasicGameState {
	
	float playerOneHealthBarX, playerTwoHealthBarX, playerHealthBarY;
	float playerOneFuelBarX, playerTwoFuelBarX, playerFuelBarY;

	private int audioOnOffX, audioOnOffY;
	private float backButtonX, backButtonY;
	
	Image backButton, moveButton, shootButton, moveButtonChosen, shootButtonChosen, audioOnOff, endTurn;
	Image gameBackground, groundTexture;
	
	Image gunbarrel;
	Image leftBullet, rightBullet, leftTank, rightTank, tmp;
	Image youWin, youLose;
	
	private Music gameMusic;
	private Sound bulletLaunch;
	private boolean muteSoundEffects;
	
	public Client client;
	private Model model;
	Scanner scanner;

	/**
	 * Instantiate the multiplayer state and initialize its connection to the server
	 * @param client the client thread through which the game connects to the server
	 */
	public MultiplayerPlayState(Client client) {
		this.client = client;
	}
	
	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#init(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		playerOneFuelBarX = playerOneHealthBarX = Window.WIDTH * 1 / 6;
		playerTwoFuelBarX = playerTwoHealthBarX = Window.WIDTH * 3.65f / 6;
		playerHealthBarY = Window.HEIGHT * 1 / 6;
		playerFuelBarY = Window.HEIGHT * 1.5f / 6;
		
		youWin = new Image("res/youWin.png");
		youLose = new Image("res/youLose.png");
		
		audioOnOff = new Image("res/AudioOnOffButton.png");
		audioOnOff = audioOnOff.getScaledCopy(Constants.MENU_BUTTON_SCALE);
		
		audioOnOffX = Window.WIDTH - audioOnOff.getWidth();
		audioOnOffY = 0;

		// Initialise the back to main menu button
		backButton = new Image("res/BackButton.png");
		backButton = backButton.getScaledCopy(Constants.MENU_BUTTON_SCALE);
		backButtonX = 0;
		backButtonY = 0;

		leftTank = new Image("res/Tank_body.png");
		rightTank = leftTank.getFlippedCopy(true, false);
		leftBullet = (new Image("res/Bullet.png")).getScaledCopy(Constants.BULLET_SCALE);
		rightBullet = leftBullet.getFlippedCopy(true, false);
		gunbarrel = new Image("res/Turret.png");
		
		// Start the game music
		gameMusic = new Music("res/gameMusic.wav");
		gameMusic.setVolume(0.5f);
		gameMusic.loop();

		// Initialise bullet fire music
		bulletLaunch = new Sound("res/tankFire.wav");
		
		// Normal
		if (ModifierState.iceFlag == false && ModifierState.mudFlag == false) {
			gameBackground = new Image("res/GameBackground.jpg");
			gameBackground = gameBackground.getScaledCopy(0.55f);
			groundTexture = new Image("res/grassMid.png");
		}

		// Ice
		if (ModifierState.iceFlag == true && ModifierState.mudFlag == false) {
			gameBackground = new Image("res/SnowBackground.jpg");
			groundTexture = new Image("res/snowMid.png");
		}

		// Mud
		if (ModifierState.iceFlag == false && ModifierState.mudFlag == true) {
			gameBackground = new Image("res/GameBackground.jpg");
			gameBackground = gameBackground.getScaledCopy(0.55f);
			groundTexture = new Image("res/mudMid.png");
		}
	}
	
	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#render(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		gameBackground.draw();
		g.fillRect(0.0f, Window.HEIGHT * 5.55f / 6.0f, (float) Window.WIDTH, Window.HEIGHT / 10.0f, groundTexture,
				50, 0);
		for (int i = 0; i < Constants.NO_PLATFORMS; i++) {
			g.fillRect(Constants.platformX[i], Constants.platformY[i], Platform.WIDTH, Platform.HEIGHT, groundTexture, 0, 0);
			// draw platforms based on groundtexture, Constants.platformX[i] and Constants.platformY[i]
		}
		renderButtons(g);
		renderHealthBar(g);
		renderFuelBar(g);
		renderEntities(g);
	}
	
	private void renderEntities(Graphics g) {
		leftTank.draw((float) model.getLeftPlayerX(), Constants.TANK_Y);
		rightTank.draw((float) model.getRightPlayerX(), Constants.TANK_Y);

		tmp = gunbarrel.copy();
		tmp.setCenterOfRotation(gunbarrel.getWidth() / 8.0f, gunbarrel.getHeight() / 2.0f);
		tmp.rotate((float) model.getLeftPlayerRot());
		tmp.draw((float) (model.getLeftPlayerX() + leftTank.getWidth() / 2.0), Constants.TANK_Y + 10.0f);
		
		tmp = gunbarrel.copy();
		tmp.setCenterOfRotation(gunbarrel.getWidth() / 8.0f, gunbarrel.getHeight() / 2.0f);
		tmp.rotate((float) model.getRightPlayerRot());
		tmp.draw((float) (model.getRightPlayerX() + rightTank.getWidth() / 2.0), Constants.TANK_Y + 10.0f);
		
		
		if (model.getBullet1Exists() == 1.0) {
			tmp = leftBullet.copy();
			tmp.rotate(-(float) Math.toDegrees((float) Math.atan(-model.getY1Vel() / model.getX1Vel())));
			tmp.drawCentered((float) model.getBullet1X(), (float) model.getBullet1Y());
		}
		
		if (model.getBullet2Exists() == 1.0) {
			tmp = rightBullet.copy();
			tmp.rotate(-(float) Math.toDegrees((float) Math.atan(-model.getY2Vel() / model.getX2Vel())));
			tmp.drawCentered((float) model.getBullet2X(), (float) model.getBullet2Y());
		}
		
		if (client.canSend == -1) {
			youLose.draw((Window.WIDTH - youLose.getWidth()) / 2, (Window.HEIGHT - youLose.getHeight()) / 2);
		} else if (client.canSend == -2) {
			youWin.draw((Window.WIDTH - youWin.getWidth()) / 2, (Window.HEIGHT - youWin.getHeight()) / 2);
		}
	}
	
	/**
	 * Renders the buttons
	 * @param g a graphics object
	 * @throws SlickException
	 */
	public void renderButtons(Graphics g) throws SlickException {
		audioOnOff.draw(audioOnOffX, audioOnOffY);
		backButton.draw(backButtonX, backButtonY);
	}
	
	/**
	 * Changes health bare so that it matches the model
	 * @param g a graphics object
	 * @throws SlickException
	 */
	public void renderHealthBar(Graphics g) throws SlickException {
		g.setColor(Color.white);
		g.drawString("Player 1 Health", playerOneHealthBarX, playerHealthBarY - 20);
		g.drawString("Player 2 Health", playerTwoHealthBarX, playerHealthBarY - 20);
		
		Rectangle playerOneHealthBar = new Rectangle(playerOneHealthBarX, playerHealthBarY, (float) (200 * model.getLeftPlayerHealth() / Constants.MAX_HEALTH), 20);
        GradientFill fill = new GradientFill(playerOneHealthBarX, 0, new Color(255, 60, 0),
        		playerOneHealthBarX + 150, 0, new Color(255, 180, 0));

        g.setColor(Color.darkGray);
        g.fillRect(playerOneHealthBarX, playerHealthBarY, 200, 20);
        g.fill(playerOneHealthBar, fill);

		Rectangle playerTwoHealthBar = new Rectangle(playerTwoHealthBarX, playerHealthBarY, (float) (200 * model.getRightPlayerHealth() / Constants.MAX_HEALTH), 20);
        fill = new GradientFill(playerTwoHealthBarX, 0, new Color(255, 60, 0),
        		playerTwoHealthBarX + 150, 0, new Color(255, 180, 0));

        g.setColor(Color.darkGray);
        g.fillRect(playerTwoHealthBarX, playerHealthBarY, 200, 20);
        g.fill(playerTwoHealthBar, fill);
	}
	
	/**
	 * Changes fuel bare so that it matches the model
	 * @param g a graphics object
	 * @throws SlickException
	 */
	public void renderFuelBar(Graphics g) throws SlickException {
		g.setColor(Color.white);
		g.drawString("Player 1 Fuel", playerOneFuelBarX, playerFuelBarY - 20);
		g.drawString("Player 2 Fuel", playerTwoFuelBarX, playerFuelBarY - 20);

		Rectangle playerOneHealthBar = new Rectangle(playerOneFuelBarX, playerFuelBarY, 150 * (float) (model.getLeftPlayerFuel() / Constants.MAX_FUEL),
				20);
		GradientFill fill = new GradientFill(playerOneFuelBarX, 0, new Color(204, 204, 0), playerOneFuelBarX + 150, 0,
				new Color(249, 166, 2));

		g.setColor(Color.darkGray);
		g.fillRect(playerOneFuelBarX, playerFuelBarY, 150, 20);
		g.fill(playerOneHealthBar, fill);

		Rectangle playerTwoHealthBar = new Rectangle(playerTwoFuelBarX, playerFuelBarY, 150 * (float) (model.getRightPlayerFuel() / Constants.MAX_FUEL),
				20);
		fill = new GradientFill(playerTwoFuelBarX, 0, new Color(204, 204, 0), playerTwoFuelBarX + 150, 0,
				new Color(249, 166, 2));

		g.setColor(Color.darkGray);
		g.fillRect(playerTwoFuelBarX, playerFuelBarY, 150, 20);
		g.fill(playerTwoHealthBar, fill);
	}
	
	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if (client.canSend != 0) {
			Input input = gc.getInput();
			float posX = Mouse.getX();
			float posY = Mouse.getY();
			
			if (client.canSend > 0) {
				model = client.model;
				handleInput(input, delta);
			}
			
			if (input.isMousePressed(0)) {
				checkSoundPress(posX, posY);
				if (inRect(backButtonX, backButtonY, backButton.getWidth(), backButton.getHeight(), posX, posY)) {
					sbg.enterState(States.MENU);
				}
			}
		}
	}
	
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
	
	private static boolean inRect(float x, float y, float width, float height, float mouseX, float mouseY) {
		mouseY = (float) Window.HEIGHT - mouseY;
		return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
	}

	/**
	 * @param input
	 * @param delta
	 * @throws SlickException
	 */
	private void handleInput(Input input, int delta) throws SlickException {
		
		if (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D)) {
			client.send(Commands.MOVE_RIGHT + ";" + delta);
		}
		if (input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A)) {
			client.send(Commands.MOVE_LEFT + ";" + delta);
		}
		if (input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W)) {
			client.send(Commands.ROTATE_UP + ";" + delta);
		}
		if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S)) {
			client.send(Commands.ROTATE_DOWN + ";" + delta);
		}
		if (input.isKeyDown(Input.KEY_SPACE)) {
			client.send(Commands.SHOOT + ";");
			if (muteSoundEffects == false) {
				bulletLaunch.play(1.0f, 0.5f);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.BasicGameState#getID()
	 */
	@Override
	public int getID() {
		return States.MP_STATE;
	}
	
}
