package com.warstrike.entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Transform;

// TODO: Auto-generated Javadoc
/**
 * The Class Bullet.
 */
public class Bullet extends AbstractEntity {

	/** The Constant GRAVITY. */
	private static final double GRAVITY = 0.001;

	/** The horizontal instant velocity of the bullet */
	private double xVelocity;
	/**
	 * The vertical instant velocity of the bullet. Negative values signify upward
	 * velocity.
	 */
	private double yVelocity;

	/** The rotation of the bullet */
	private float rotation;

	/** The horizontal image corresponding of the bullet when its rotation is 0. */
	private Image image;

	/**
	 * Instantiates a new bullet.
	 *
	 * @param x         the x
	 * @param y         the y
	 * @param xVelocity the x velocity
	 * @param yVelocity the y velocity
	 * @throws SlickException the slick exception
	 */
	public Bullet(double x, double y, double xVelocity, double yVelocity) throws SlickException {
		super(x, y);
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
		rotation = (float) Math.atan(-yVelocity / xVelocity);
		setBox(new Ellipse((float) x, (float) y, (int) (38.0 / 2.5), 26 / 3));
		rotateBox(rotation);
	}

	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * Sets the image.
	 *
	 * @param image the new image
	 */
	public void setImage(Image image) {
		image.setCenterOfRotation(image.getWidth() / 2, image.getHeight() / 2);
		this.image = image;
	}

	/**
	 * Update.
	 *
	 * @param delta the delta
	 */
	public void update(int delta) {
		float xOffset = (float) xVelocity * delta;
		float yOffset = (float) yVelocity * delta;
		setX(getX() + xOffset);
		setY(getY() + yOffset);
		yVelocity += GRAVITY * delta;
		float newRotation = (float) Math.atan(-yVelocity / xVelocity);
		rotateBox(newRotation - rotation);
		rotation = newRotation;
		moveBox(xOffset, yOffset);
	}

	/**
	 * Render.
	 *
	 * @param g the g
	 */
	public void render(Graphics g) {
		g.setLineWidth(2.0f);
		g.draw(getBox());
		Image tmp = image.copy();
		tmp.rotate(-(float) Math.toDegrees(rotation));
		tmp.drawCentered((float) getX(), (float) getY());
	}

	/**
	 * Rotate box.
	 *
	 * @param degrees the degrees
	 */
	public void rotateBox(float degrees) {
		setBox(getBox()
				.transform(Transform.createRotateTransform(-degrees, getBox().getCenterX(), getBox().getCenterY())));
	}

	public void moveBox(float xOffset, float yOffset) {
		setBox(getBox().transform(Transform.createTranslateTransform(xOffset, yOffset)));
	}

	/**
	 * Gets the x velocity.
	 *
	 * @return the x velocity
	 */
	public double getxVelocity() {
		return xVelocity;
	}

	/**
	 * Gets the y velocity.
	 *
	 * @return the y velocity
	 */
	public double getyVelocity() {
		return yVelocity;
	}

	/**
	 * Gets the rotation.
	 *
	 * @return the rotation
	 */
	public double getRotation() {
		return rotation;
	}

	/**
	 * Sets the x velocity.
	 *
	 * @param xVelocity the new x velocity
	 */
	public void setxVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}

	/**
	 * Sets the y velocity.
	 *
	 * @param yVelocity the new y velocity
	 */
	public void setyVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}

	/**
	 * Sets the rotation.
	 *
	 * @param rotation the new rotation
	 */
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
}
