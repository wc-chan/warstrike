package com.warstrike.networking;

import java.io.Serializable;

import com.warstrike.Constants;

public class Model implements Serializable {

	private double leftPlayerX, leftPlayerY, leftPlayerRot; 
	private double rightPlayerX, rightPlayerY, rightPlayerRot;
	
	public Model() {
		leftPlayerX = Constants.INIT_LPX;
		leftPlayerY = Constants.INIT_LPY;
		rightPlayerX = Constants.INIT_RPX;
		rightPlayerY = Constants.INIT_RPY;
		leftPlayerRot = Constants.INIT_LPR;
		rightPlayerRot = Constants.INIT_RPR;
	}
	
	public double getLeftPlayerX() {
		return leftPlayerX;
	}
	public void setLeftPlayerX(double leftPlayerX) {
		this.leftPlayerX = leftPlayerX;
	}
	public double getLeftPlayerY() {
		return leftPlayerY;
	}
	public void setLeftPlayerY(double leftPlayerY) {
		this.leftPlayerY = leftPlayerY;
	}
	public double getLeftPlayerRot() {
		return leftPlayerRot;
	}
	public void setLeftPlayerRot(double leftPlayerRot) {
		this.leftPlayerRot = leftPlayerRot;
	}
	public double getRightPlayerX() {
		return rightPlayerX;
	}
	public void setRightPlayerX(double rightPlayerX) {
		this.rightPlayerX = rightPlayerX;
	}
	public double getRightPlayerY() {
		return rightPlayerY;
	}
	public void setRightPlayerY(double rightPlayerY) {
		this.rightPlayerY = rightPlayerY;
	}
	public double getRightPlayerRot() {
		return rightPlayerRot;
	}
	public void setRightPlayerRot(double rightPlayerRot) {
		this.rightPlayerRot = rightPlayerRot;
	}
	
	
	
	
}
