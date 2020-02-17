package com.warstrike.entities;

import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;

import com.warstrike.Constants;

/**
 * The Class AbstractPlayer.
 */
public abstract class AbstractPlayer extends AbstractEntity {
	
	/** The Constant X_VELOCITY. */
	private static final double X_VELOCITY = 0.1;
	
	/** The Constant ANG_VELOCITY. */
	private static final float ANG_VELOCITY = 0.1f;
	
	/** The Constant X_DIFF. */
	private static final float X_DIFF = 5.0f;
	
	/** The Constant Y_DIFF. */
	private static final float Y_DIFF = 72.0f;
	
	/** The Constant WIDTH. */
	private static final double WIDTH = 74.0;
	
	/** The Constant HEIGHT. */
	private static final double HEIGHT = 74.0;
	
	/** The health. */
	private double health;
	
	/** The fuel. */
	private double fuel;
	
	/** The rotation. */
	private float rotation;
	
	/** The or. */
	private final int or;
	
	/** The body. */
	private Image body;
	
	/** The gunbarrel. */
	private Image gunbarrel;
	
	/**
	 * Instantiates a new abstract player.
	 *
	 * @param x the x
	 * @param y the y
	 * @param or the or
	 */
	public AbstractPlayer(double x, double y, int or) {
		super(x, y);
		setWidth(WIDTH);
		setHeight(HEIGHT);
		this.or = or;
		updateBox();
		health = Constants.MAX_HEALTH;
		fuel = Constants.MAX_FUEL;
		rotation = (or == 1 ? 0.0f : 180.0f);
	}
	
	/**
	 * Gets the fuel.
	 *
	 * @return the fuel
	 */
	public double getFuel() {
		return this.fuel;
	}
	
	/**
	 * Sets the fuel.
	 *
	 * @param fuel the new fuel
	 */
	public void setFuel(double fuel) {
		if (fuel < Constants.MIN_FUEL) {
			this.fuel = Constants.MIN_FUEL;
		} else if (fuel > Constants.MAX_FUEL) {
			this.fuel = Constants.MAX_FUEL;
		} else {
			this.fuel = fuel;
		}
	}
	
	/**
	 * Gets the health.
	 * @return the health
	 */
	public double getHealth() {
		return this.health;
	}
	
	/**
	 * Sets the health.
	 *
	 * @param health the new health
	 */
	public void setHealth(double health) {
		if (health < Constants.MIN_HEALTH) {
			this.health = Constants.MIN_HEALTH;
		} else if (health > Constants.MAX_HEALTH) {
			this.health = Constants.MAX_HEALTH;
		} else {
			this.health = health;
		}
	}
	
	/**
	 * Sets the image.
	 *
	 * @param body the new image
	 */
	public void setImage(Image body) {
		this.body = body;
	}
	
	/**
	 * Gets the gunbarrel.
	 *
	 * @return the gunbarrel
	 */
	public Image getGunbarrel() {
		return gunbarrel;
	}
	
	/**
	 * Sets the gunbarrel.
	 *
	 * @param gunbarrel the new gunbarrel
	 */
	public void setGunbarrel(Image gunbarrel) {
		this.gunbarrel = gunbarrel;
	}
	
	/**
	 * Gets the rotation.
	 * @return the rotation
	 */
	public float getRotation() {
		return -rotation;
	}

	/**
	 * Sets the rotation.
	 *
	 * @param rotation the new rotation
	 */
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	
	/**
	 * Gets the orientation.
	 *
	 * @return the orientation
	 */
	public int getOrientation() {
		return this.or;
	}
	
	/**
	 * Render.
	 *
	 * @param g the g
	 * @throws SlickException the slick exception
	 */
	public void render(Graphics g) throws SlickException {
		body.draw((float) getX(), (float) getY());
		Image tmp = (Image) gunbarrel.copy();
		tmp.setCenterOfRotation(gunbarrel.getWidth() / 8.0f, gunbarrel.getHeight() / 2.0f);
		tmp.rotate(rotation);
		tmp.draw((float) (getX() + getWidth() / 2.0), (float) getY() + 10.0f);
		
		g.setColor(Color.black);
		g.setLineWidth(2.0f);
		if (getBox() != null) g.draw(getBox());
	}
	
	/**
	 * Update position.
	 *
	 * @param sm the sm
	 * @param delta the delta
	 */
	public void updatePosition(double sm, double delta) {
		if(fuel > 0) {
			double oldX = getX();
			setX(getX() + X_VELOCITY * sm * delta);
			double offset = Math.abs(oldX - getX());
			setFuel(fuel - offset / 2 / Math.abs(sm));
			updateBox();
		}
	}
	
	/**
	 * Update box.
	 */
	public void updateBox() {
		setBox(new Polygon(Constants.getTankCoordinates(or, (float) getX() + or * X_DIFF, (float) getY() + Y_DIFF)));
	}
	
	/**
	 * Update angle.
	 *
	 * @param delta the delta
	 */
	public void updateAngle(double delta) {
		rotation += delta * ANG_VELOCITY;
	}

	
	/**
	 * Take damage.
	 */
	public void takeDamage() {
		setHealth(health - 20.0);
	}
	
	/**
	 * Play.
	 *
	 * @return the list
	 */
	public List<String> play() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Sets the action to true.
	 */
	public void SetActionToTrue() {
		// TODO Auto-generated method stub
	}

	/**
	 * Sets the action to false.
	 */
	public void SetActionToFalse() {
		// TODO Auto-generated method stub
	}

	/**
	 * Re initialise.
	 */
	public void reInitialise() {
		// TODO Auto-generated method stub
	}
}

