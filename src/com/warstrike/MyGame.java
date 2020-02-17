package com.warstrike;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.warstrike.states.*;

// TODO: Auto-generated Javadoc
/**
 * The Class MyGame.
 */
public class MyGame extends StateBasedGame {

	/** The Constant gameName. */
	private static final String gameName = "Warstrike";

	/**
	 * Instantiates a new my game.
	 *
	 * @param name the name
	 */
	public MyGame(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.newdawn.slick.state.StateBasedGame#initStatesList(org.newdawn.slick.
	 * GameContainer)
	 */
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		gc.setMaximumLogicUpdateInterval(60);
		gc.setTargetFrameRate(Constants.FPS);
		gc.setAlwaysRender(true);
		gc.setShowFPS(Constants.SHOW_FPS);
		gc.setVSync(false);
		this.addState(new MenuState());
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		try {
			AppGameContainer game = new AppGameContainer(new MyGame(gameName));
			game.setDisplayMode(Window.WIDTH, Window.HEIGHT, Window.FULLSCREEN);
			game.setIcon("res/gameIcon32.png");
			game.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
