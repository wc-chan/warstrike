package com.warstrike.state.server;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.warstrike.networking.Server;
import com.warstrike.networking.ServerGame;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerWaitState.
 */
public class ServerWaitState extends BasicGameState {

	/** The server. */
	Server server;
	
	/**
	 * @param s
	 */
	public ServerWaitState(Server s) {
		server = s;
	}
	
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		ServerGame sg = (ServerGame) sbg;

	}
	
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString("Not all players connected", 4, 4);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		// check for client connections
		// when a client connects, make it enter its wait state
		// if both clients connected, enter play state

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
