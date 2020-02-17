package com.warstrike.states;

import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.warstrike.Constants;
import com.warstrike.commands.Command;
import com.warstrike.commands.CommandType;
import com.warstrike.commands.MoveCommand;
import com.warstrike.commands.RotateCommand;
import com.warstrike.networking.Client;
import com.warstrike.networking.Model;

public class PlayState extends BasicGameState {
	
	Model model;
	Client client;
	
	private final int side;
	private static int nextSide = 0;
	
	public PlayState() {
		super();
		side = ++nextSide;
		this.client = new Client("localhost", Constants.PORT);
		System.out.println("Created client");
	};
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		model = new Model();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setColor(Color.blue);
		g.drawRect((float) model.getLeftPlayerX(), (float) model.getLeftPlayerY(), 40.0f, 40.0f);
		g.setColor(Color.red);
		g.drawRect((float) model.getRightPlayerX(), (float) model.getRightPlayerY(), 40.0f, 40.0f);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		//model = client.model;
		try {
			handleInput(gc.getInput(), delta);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void handleInput(Input input, int delta) throws IOException {
		
		
		
		if (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D)) {
			Command c = new MoveCommand(CommandType.MOVE, 1, delta, side);
			//client.send(c);
			System.out.println("Created Command and sent from client " + side);
		}
		
		if (input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A)) {
			Command c = new MoveCommand(CommandType.MOVE, -1, delta, side);
			//client.send(c);
			System.out.println("Created Command and sent from client " + side);
		}
		
		/*if (input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W)) {
			Command c = new RotateCommand(CommandType.ROTATE, 1, delta, side);
			client.send(c);
		}
		
		if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S)) {
			Command c = new RotateCommand(CommandType.ROTATE, -1, delta, side);
			client.send(c);
		}*/
	}

	public void setModel(Model model) {
		this.model = model;
	}
	
	

}
