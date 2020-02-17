package com.warstrike.commands;

public abstract class TimedCommand extends Command {

	private final int delta;
	
	public TimedCommand(CommandType type, int delta, int or) {
		super(type, or);
		this.delta = delta;
	}

	public int getDelta() {
		return delta;
	}

}
