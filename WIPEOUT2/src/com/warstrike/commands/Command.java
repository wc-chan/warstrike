package com.warstrike.commands;

import java.io.Serializable;

import com.warstrike.networking.Model;

public abstract class Command implements Serializable {
	
	private final CommandType type;
	private final int or;
	
	public Command(CommandType type, int or) {
		this.type = type;
		this.or = or;
	}
	
	public CommandType getType() {
		return type;
	}
	
	public int getOrient() {
		return or;
	}
	
	public abstract void execute(Model model);
}
