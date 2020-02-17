package com.warstrike.states;

import com.warstrike.Window;

public class Model {


	/**
	 * 
	 */
	private double leftPlayerX, leftPlayerRot;
	private double rightPlayerX, rightPlayerRot;
	private double leftPlayerFuel, rightPlayerFuel;
	private double leftPlayerHealth, rightPlayerHealth;
	private double bullet1Exists, bullet2Exists;
	private double bullet1X, bullet1Y, x1Vel, y1Vel;
	private double bullet2X, bullet2Y, x2Vel, y2Vel;

	/**
	 * Instantiates a new model.
	 */
	public Model() {
		leftPlayerX = 100.0;
		leftPlayerRot = 0.0;
		rightPlayerX = Window.WIDTH - 100.0 - 74.0;
		rightPlayerRot = 180.0;
		leftPlayerFuel = rightPlayerFuel = 100.0;
		leftPlayerHealth = rightPlayerHealth = 100.0;
		bullet1Exists = bullet2Exists = 0.0;
	}
	
	/**
	 * Set the x coordinate of the left player
	 * @param leftPlayerX the value to which the x coordinate of the left player is to be set
	 */
	public void setLeftPlayerX(double leftPlayerX) {
		this.leftPlayerX = leftPlayerX;
	}

	/**
	 * Set the angle of the gun barrel of the left player
	 * @param leftPlayerRot the value to which the angle of the gun barrel of the left player is to be set
	 */
	public void setLeftPlayerRot(double leftPlayerRot) {
		this.leftPlayerRot = leftPlayerRot;
	}
	
	/**
<<<<<<< HEAD
	 * Set the angle of the gun barrel of the right player
	 * @param rightPlayerRot the value to which the angle of the gun barrel of the right player is to be set
=======
	 * @param rightPlayerRot
>>>>>>> AI
	 */
	public void setRightPlayerRot(double rightPlayerRot) {
		this.rightPlayerRot = rightPlayerRot;
	}
	
	/**
	 * Set the x coordinate of the rigth player
	 * @param rightPlayerX the value to which the x coordinate of the right player is to be set
	 */
	public void setRightPlayerX(double rightPlayerX) {
		this.rightPlayerX = rightPlayerX;
	}
	
	/**
	 * Set the x coordinate of the bullet corresponding to left player
	 * @param bulletX the value to which the x coordinate of the bullet corresponding to left player is to be set
	 */
	public void setBullet1X(double bulletX) {
		this.bullet1X = bulletX;
	}
	
	/**
	 * Set the y coordinate of the bullet corresponding to left player
	 * @param bulletY the value to which the y coordinate of the bullet corresponding to left player is to be set
	 */
	public void setBullet1Y(double bulletY) {
		this.bullet1Y = bulletY;
	}

	/**
	 * Set the x velocity of the bullet corresponding to left player
	 * @param xVel the value to which the x velocity of the bullet corresponding to left player is to be set
	 */
	public void setx1Vel(double xVel) {
		this.x1Vel = xVel;
	}

	/**
	 * Set the y velocity of the bullet corresponding to left player
	 * @param yVel the value to which the y velocity of the bullet corresponding to left player is to be set
	 */
	public void sety1Vel(double yVel) {
		this.y1Vel = yVel;
	}

	/**
	 * Gets the left player X.
	 *
	 * @return the left player X
	 */
	public double getLeftPlayerX() {
		return leftPlayerX;
	}

	/**
<<<<<<< HEAD
	 * Gets the left player rot.
	 *
	 * @return the left player rot
=======
	 * @return
>>>>>>> AI
	 */
	public double getLeftPlayerRot() {
		return leftPlayerRot;
	}
	
	/**
	 * Gets the right player rot.
	 *
	 * @return the right player rot
	 */
	public double getRightPlayerRot() {
		return rightPlayerRot;
	}

	/**
	 * Gets the right player X.
	 *
	 * @return the right player X
	 */
	public double getRightPlayerX() {
		return rightPlayerX;
	}
	
	/**
	 * Gets the left bullet X.
	 *
	 * @return the left bullet X
	 */
	public double getBullet1X() {
		return bullet1X;
	}

	/**
	 * Gets the left bullet y.
	 *
	 * @return the left bullet y
	 */
	public double getBullet1Y() {
		return bullet1Y;
	}

	/**
	 * Set the fuel of left player
	 * @param leftPlayerFuel the value to which the fuel of left player is to be set
	 */
	public void setLeftPlayerFuel(double leftPlayerFuel) {
		this.leftPlayerFuel = leftPlayerFuel;
	}

	/**
	 * Set the fuel of left player
	 * @param rightPlayerFuel the value to which the fuel of left player is to be set
	 */
	public void setRightPlayerFuel(double rightPlayerFuel) {
		this.rightPlayerFuel = rightPlayerFuel;
	}

	/**
	 * Set the health of left player
	 * @param leftPlayerHealth the value to which the health of left player is to be set
	 */
	public void setLeftPlayerHealth(double leftPlayerHealth) {
		this.leftPlayerHealth = leftPlayerHealth;
	}

	/**
	 * Set the health of right player
	 * @param rightPlayerHealth the value to which the health of right player is to be set
	 */
	public void setRightPlayerHealth(double rightPlayerHealth) {
		this.rightPlayerHealth = rightPlayerHealth;
	}

	/**
	 * Gets the left bullet X velocity.
	 *
	 * @return the left bullet X velocity
	 */
	public double getX1Vel() {
		return x1Vel;
	}

	/**
	 * Set the x velocity of bullet corresponding to the left player
	 * @param x1Vel the value to which the x velocity of bullet corresponding to the right player is to be set
	 */
	public void setX1Vel(double x1Vel) {
		this.x1Vel = x1Vel;
	}

	/**
	 * Gets the left bullet y velocity.
	 *
	 * @return the left bullet y velocity
	 */
	public double getY1Vel() {
		return y1Vel;
	}

	/**
	 * Set the y velocity of bullet corresponding to the left player
	 * @param y1Vel the value to which the y velocity of bullet corresponding to the left player is to be set
	 */
	public void setY1Vel(double y1Vel) {
		this.y1Vel = y1Vel;
	}

	/**
	 * Gets the right bullet X.
	 *
	 * @return the right bullet X
	 */
	public double getBullet2X() {
		return bullet2X;
	}

	/**
	 * Set the x coordinate of the bullet corresponding to right player
	 * @param bullet2x the value to which the x coordinate of the bullet corresponding to right player is to be set
	 */
	public void setBullet2X(double bullet2x) {
		bullet2X = bullet2x;
	}

	/**
	 * Gets the right bullet y.
	 *
	 * @return the right bullet y
	 */
	public double getBullet2Y() {
		return bullet2Y;
	}
	
	/**
	 * Set the y coordinate of the bullet corresponding to right player
	 * @param bullet2y the value to which the y coordinate of the bullet corresponding to right player is to be set
	 */
	public void setBullet2Y(double bullet2y) {
		bullet2Y = bullet2y;
	}

	/**
	 * Gets the right bullet X velocity.
	 *
	 * @return the right bullet X velocity
	 */
	public double getX2Vel() {
		return x2Vel;
	}
	
	/**
	 * Set the x velocity of bullet corresponding to the right player
	 * @param x2Vel the value to which the x velocity of bullet corresponding to the right player is to be set
	 */
	public void setX2Vel(double x2Vel) {
		this.x2Vel = x2Vel;
	}
	
	/**
	 * Gets the right bullet y velocity.
	 *
	 * @return the right bullet y velocity
	 */
	public double getY2Vel() {
		return y2Vel;
	}
	
	/**
	 * Set the y velocity of bullet corresponding to the right player
	 * @param y2Vel the value to which the y velocity of bullet corresponding to the right player is to be set
	 */
	public void setY2Vel(double y2Vel) {
		this.y2Vel = y2Vel;
	}
	
	/**
	 * Gets the left player fuel
	 *
	 * @return the left player fuel
	 */
	public double getLeftPlayerFuel() {
		return leftPlayerFuel;
	}

	/**
	 * Gets the right player fuel.
	 *
	 * @return the right player fuel
	 */
	public double getRightPlayerFuel() {
		return rightPlayerFuel;
	}
	
	/**
	 * Gets the left  player health
	 *
	 * @return the left player health
	 */
	public double getLeftPlayerHealth() {
		return leftPlayerHealth;
	}

	/**
	 * Gets the right  player health.
	 *
	 * @return the right  player health
	 */
	public double getRightPlayerHealth() {
		return rightPlayerHealth;
	}
	
	/**
	 * Gets if the left bullet exists.
	 *
	 * @return 1 if the left bullet exists; 0 otherwise
	 */
	public double getBullet1Exists() {
		return bullet1Exists;
	}
	
	/**
	 * Set whether the left player's bullet exists
	 * @param bullet1Exists 1 if the left bullet exists; 0 otherwise
	 */
	public void setBullet1Exists(double bullet1Exists) {
		this.bullet1Exists = bullet1Exists;
	}

	/**
	 * Gets if the right bullet exists.
	 *
	 * @return 1 if the right bullet exists; 0 otherwise
	 */
	public double getBullet2Exists() {
		return bullet2Exists;
	}
	
	/**
	 * Set whether the left player's bullet exists
	 * @param bullet1Exists 1 if the left bullet exists; 0 otherwise
	 */
	public void setBullet2Exists(double bullet2Exists) {
		this.bullet2Exists = bullet2Exists;
	}
}
