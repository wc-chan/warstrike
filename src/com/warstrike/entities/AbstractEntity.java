package com.warstrike.entities;

import org.newdawn.slick.geom.Shape;

import com.warstrike.Window;

public abstract class AbstractEntity {

	private double x;
	private double y;

	private double width;
	private double height;

	private Shape box;
	
	/**
	 * Instantiates an entity at a given position
	 * @param x the x coordinate of the entity
	 * @param y the y coordinate of the entity
	 */
	public AbstractEntity(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the x coordinate of the entity
	 * @return the x coordinate of the entity
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Sets the value of the x coordinate of the entity
	 * @param x the value to which the x coordinate is to be set
	 */
	public void setX(double x) {
		if (x < 0.0) {
			this.x = 0.0;
		} else if (x + width > Window.WIDTH) {
			this.x = (double) Window.WIDTH - width;
		} else {
			this.x = x;
		}
	}
	
	/**
	 * Returns the y coordinate of the entity
	 * @return the y coordinate of the entity
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Sets the value of the y coordinate of the entity
	 * @param y the value to which the y coordinate is to be set
	 */
	public void setY(double y) {
		if (y < 0.0) {
			this.y = 0.0;
		} else if (y + height > Window.HEIGHT) {
			this.y = Window.HEIGHT - height;
		} else {
			this.y = y;
		}
	}

	/**
	 * Returns the width of the entity
	 * @return the width of the entity
	 */
	public double getWidth() {
		return width;
	}
	
	/**
	 * Sets the width of the entity
	 * @param width the value to which the width is to be set
	 */
	public void setWidth(double width) {
		this.width = width;
	}
	
	/**
	 * Returns the height of the entity
	 * @return the height of the entity
	 */
	public double getHeight() {
		return height;
	}
	
	/**
	 * Sets the height of the entity
	 * @param height the value to which the height is to be set
	 */
	public void setHeight(double height) {
		this.height = height;
	}
	
	/**
	 * Returns a shape that represents the hit box of the entity
	 * @return the hit box of the entity
	 */
	public Shape getBox() {
		return box;
	}
	
	/**
	 * Set the hit box of the entity
	 * @param box the shape to which the hit box of the entity is to be set
	 */
	public void setBox(Shape box) {
		this.box = box;
	}
	
	/**
	 * Checks if this entity is colliding with another entity
	 * @param entity the entity for which is checked whether this entity collides with or not
	 * @return true if the entities are colliding; false if they are not
	 */
	public boolean collidesWith(AbstractEntity entity) {
		return this.getBox().intersects(entity.getBox());
	}


}
