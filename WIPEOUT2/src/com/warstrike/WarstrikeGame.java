package com.warstrike;

import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.warstrike.commands.Command;
import com.warstrike.networking.Client;
import com.warstrike.states.PlayState;

public class WarstrikeGame extends StateBasedGame {
	
	public WarstrikeGame(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		gc.setMaximumLogicUpdateInterval(Constants.UP_RATE);
		gc.setTargetFrameRate(Constants.FPS);
		gc.setAlwaysRender(Constants.AL_RENDER);
		gc.setShowFPS(Constants.SHOW_FPS);
		gc.setVSync(Constants.VSYNC);
		PlayState ps = new PlayState();
		this.addState(ps);
	}
	
}
