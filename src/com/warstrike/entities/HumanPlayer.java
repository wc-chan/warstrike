package com.warstrike.entities;

import org.newdawn.slick.SlickException;

import com.warstrike.Constants;


// TODO: Auto-generated Javadoc
/**
 * The Class HumanPlayer.
 */
public class HumanPlayer extends AbstractPlayer {

	/**
	 * Instantiates a new human player.
	 *
	 * @param x the x
	 * @param y the y
	 * @param or the or
	 */
	public HumanPlayer(double x, double y, int or) {
		super(x, y, or);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Shoot a bullet/
	 * @return the bullet object that is being shot
	 * @throws SlickException
	 */
	public Bullet shoot() throws SlickException {
		Bullet bullet = new Bullet(
				getX() + getWidth() / 2 + 9 * Math.sin(getRotation()),
				getY(), 
				Math.cos(Math.toRadians(getRotation())) * Constants.INIT_B_VELOCITY,
				Math.sin(Math.toRadians(getRotation())) * Constants.INIT_B_VELOCITY);
		return bullet;
	}
	
	/**
	 * Regenerate the fuel
	 * @param delta a value corresponding to the amount of time that passed since the last update tick
	 */
	public void regenFuel(int delta) {
		setFuel(getFuel() + delta * Constants.FUEL_REGEN_RATE);
	}

}
