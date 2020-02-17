package com.warstrike.state.server;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.warstrike.Constants;
import com.warstrike.Window;
import com.warstrike.commands.Commands;
import com.warstrike.entities.Bullet;
import com.warstrike.entities.HumanPlayer;
import com.warstrike.entities.Platform;
import com.warstrike.networking.ChangeCodes;
import com.warstrike.networking.Server;

/**
 * The Class ServerPlayState.
 */
public class ServerPlayState extends BasicGameState {

	private static final double P1X = 100.0;
	private static final double P1Y = Window.HEIGHT * 5.55 / 6.0 - 74.0;
	private static final double P2X = Window.WIDTH - 100.0 - 74.0;
	private static final double P2Y = P1Y;
	
	HumanPlayer[] players;
	Bullet[] bullets;
	Platform[] platforms;

	double sm = 1.0;
	
	/** The server. */
	Server server;
	
	/** The command. */
	String command;
	
	/**
	 * Instantiates a new server play state.
	 *
	 * @param _server the server
	 */
	public ServerPlayState(Server _server) {
		super();
		server = _server;
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#init(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		players = new HumanPlayer[3];
		bullets = new Bullet[3];
		platforms = new Platform[Constants.NO_PLATFORMS];
		for (int i = 0; i < Constants.NO_PLATFORMS; i++) {
			platforms[i] = new Platform(Constants.platformX[i], Constants.platformY[i]);
		}
		players[1] = new HumanPlayer(P1X, P1Y, Constants.RIGHT_ORIENTATION);
		players[2] = new HumanPlayer(P2X, P2Y, Constants.LEFT_ORIENTATION);
		bullets[1] = null;
		bullets[2] = null;
	}

	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
	}

	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		for (int i = 1; i <= 2; i++) if (bullets[i] != null) {
			bullets[i].update(delta);
			server.broadcast(ChangeCodes.B1_X * i + ";" + bullets[i].getX());
			server.broadcast(ChangeCodes.B1_Y * i + ";" + bullets[i].getY());
			server.broadcast(ChangeCodes.B1_XVEL * i + ";" + bullets[i].getxVelocity());
			server.broadcast(ChangeCodes.B1_YVEL * i + ";" + bullets[i].getyVelocity());
			if (shouldBeDestroyed(bullets[i])) {
				bullets[i] = null;
				server.broadcast(ChangeCodes.LB_EXISTS * i + ";" + 0.0);
			} else {
				try {
					for (int j = 1; j <= 2; j++) if (bullets[i].collidesWith(players[j])) {
						players[j].takeDamage();
						server.broadcast(ChangeCodes.LP_HP * j + ";" + players[j].getHealth());
						bullets[i] = null;
						server.broadcast(ChangeCodes.LB_EXISTS * i + ";" + 0.0);
					}
				} catch (NullPointerException e) {
					if (bullets[i] == null) {
						System.out.println("Bullet number " + i + " is null");
					}
				}
			}
		}
		for (int i = 1; i <= 2; i++) {
			players[i].regenFuel(delta);
			server.broadcast(ChangeCodes.LP_FUEL * i + ";" + players[i].getFuel());
		}
	}
	
	private boolean shouldBeDestroyed(Bullet bullet) {
		if (bullet.getX() >= Window.WIDTH || bullet.getY() >= Window.HEIGHT * 5.3f / 6.0f || bullet.getX() == 0.0f)
			return true;
		for (Platform p : platforms) {
			if (p != null) {
				if (bullet.collidesWith(p)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Execute a command
	 * @param command the command that is to be exected
	 * @throws SlickException
	 */
	public void execute(String command) throws SlickException {
		String[] tokens = command.split(";");
		int delta = 0;
		int player = Integer.parseInt(tokens[0]);
		String action = tokens[1];
		
		if (!action.equals(Commands.SHOOT)) delta = Integer.parseInt(tokens[2]);
		
		switch (action) {
			case Commands.SHOOT : {
				if (bullets[player] == null) {
					bullets[player] = players[player].shoot();
					server.broadcast(ChangeCodes.LB_EXISTS * player + ";" + 1.0);
				}
				break;
			}
			case Commands.MOVE_LEFT: {
				if (!players[1].collidesWith(players[2]) 
						|| players[player].getOrientation() == Constants.RIGHT_ORIENTATION) {
					players[player].updatePosition(-sm, delta);
					server.broadcast(ChangeCodes.LP_X * player + ";" + players[player].getX());
					server.broadcast(ChangeCodes.LP_FUEL * player + ";" + players[player].getFuel());
				}
				break;
			}
			case Commands.MOVE_RIGHT: {
				if (!players[1].collidesWith(players[2]) 
						|| players[player].getOrientation() == Constants.LEFT_ORIENTATION) {
					players[player].updatePosition(sm, delta);
					server.broadcast(ChangeCodes.LP_X * player + ";" + players[player].getX());
					server.broadcast(ChangeCodes.LP_FUEL * player + ";" + players[player].getFuel());
				}
				break;
			}
			case Commands.ROTATE_DOWN: {
				players[player].updateAngle(-delta * players[player].getOrientation());
				server.broadcast(ChangeCodes.LP_ROT * player + ";" + players[player].getRotation());
				break;
			}
			case Commands.ROTATE_UP: {
				players[player].updateAngle(delta * players[player].getOrientation());
				server.broadcast(ChangeCodes.LP_ROT * player + ";" + players[player].getRotation());
				break;
			}
		}
	}

	@Override
	public int getID() {
		return 0;
	}

}
