package com.warstrike.commands;

import com.warstrike.Constants;
import com.warstrike.networking.Model;

public class MoveCommand extends TimedCommand {

	private int direction;
	
	public MoveCommand (CommandType type, int direction, int delta, int side) {
		super(type, delta, side);
		this.direction = direction;
	}
	
	public int getDir() {
		return direction;
	}
	
	public void execute(Model model) {
		if (getOrient() == 1) {
			model.setLeftPlayerX(model.getLeftPlayerX() + getDelta() * direction * Constants.PLAYER_VEL);
			System.out.println("New left player x is " + model.getLeftPlayerX());
		} else {
			model.setRightPlayerX(model.getRightPlayerX() + getDelta() * direction * Constants.PLAYER_VEL);
			System.out.println("New right player x is " + model.getRightPlayerX());
		}
		
	}

}
