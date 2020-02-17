package com.warstrike.commands;

import com.warstrike.networking.Model;

public class RotateCommand extends TimedCommand {

	private final int direction;
	
	public RotateCommand(CommandType type, int direction, int delta, int or) {
		super(type, delta, or);
		this.direction = direction;
		// TODO Auto-generated constructor stub
	}

	public int getDir() {
		return direction;
	}
	
	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub

	}

}
