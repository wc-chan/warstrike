package com.warstrike.networking;

import com.warstrike.Constants;
import com.warstrike.state.server.ServerPlayState;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerGame.
 */
public class ServerGame extends StateBasedGame {

	/** The server. */
	private Server server;
	private ServerPlayState sps;
	
	/**
	 * Instantiates a new server game.
	 *
	 * @param name the name
	 */
	public ServerGame(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.StateBasedGame#initStatesList(org.newdawn.slick.GameContainer)
	 */
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		gc.setMaximumLogicUpdateInterval(Constants.UP_RATE);
		gc.setTargetFrameRate(Constants.FPS);
		gc.setAlwaysRender(Constants.AL_RENDER);
		gc.setShowFPS(Constants.SHOW_FPS);
		gc.setVSync(Constants.VSYNC);
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		ServerGame sg = new ServerGame("");
		sg.server = new Server(Constants.PORT);
		sg.sps = new ServerPlayState(sg.server);
		sg.server.sps = sg.sps;
		sg.server.acceptClients();

		try {
			AppGameContainer game = new AppGameContainer(sg);
			sg.sps.init(game, sg);
			sg.addState(sg.sps);
			game.setDisplayMode(800, 600, false);
			game.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
