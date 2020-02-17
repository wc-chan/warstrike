package com.warstrike.entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import com.warstrike.Window;

// TODO: Auto-generated Javadoc
/**
 * The Class Platform.
 */
public class Platform extends AbstractEntity {
	
	/** The Constant WIDTH. */
	public static final float WIDTH = 70.0f; //player width x 1.0
	
	/** The Constant HEIGHT. */
	public static final float HEIGHT = Window.HEIGHT / 11.0f; //player height x 0.5
	
	/** The body. */
	private Image body;
	
	/**
	 * Instantiates a new platform.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public Platform(double x, double y) {
		super(x, y);
		createBox();
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
	 * Render.
	 *
	 * @param g the g
	 * @throws SlickException the slick exception
	 */
	public void render(Graphics g) throws SlickException {
		body.draw((float) getX(), (float) getY());
	}
	
	private void createBox() {
		setBox(new Rectangle((float) getX(), (float) getY(), WIDTH, HEIGHT));
	}

}
