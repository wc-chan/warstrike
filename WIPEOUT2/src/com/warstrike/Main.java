package com.warstrike;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;


public class Main {

	public static void main(String[] args) {
		
		try {
			WarstrikeGame wg = new WarstrikeGame("Warstrike");
			AppGameContainer game = new AppGameContainer(wg);
			game.setDisplayMode(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, false);
			game.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
