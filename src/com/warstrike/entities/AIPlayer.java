package com.warstrike.entities;

import java.util.ArrayList;
import java.util.List;
import com.warstrike.Constants;

/**
 * The additional code specific to the AI-controlled Tank extending the abstract class AbstractPlayer
 * The AIPlayer has 3 levels of difficulty
 * EASY(default): The AI-tank has the same range as the human player's tank. It misses its target, on average, 35% of the time
 * MEDIUM: The AI-tank has a slightly longer range than the human player's tank. It misses its target, on average, 20% of the time
 * HARD: The AI-tank has a much larger range than the human player's tank. It misses its target, on average only, 5% of the time
 * @author Liam Skop
 *
 */
public class AIPlayer extends AbstractPlayer {
	
	private double init_y; // initial height of the AI-controlled tank
	private double init_b_v_x; // the initial x velocity of the bullet
	private double init_b_v_y; // the initial y velocity of the bullet
	private double init_b_x; // initial x-coordinate of the bullet
		
	AbstractPlayer h; // reference to the instantiation of HumanPlayer
	List<Double> failedAngles = new ArrayList<Double>(); // List that contains angles that the aiming algorithm no longer needs to try out
	    
    String action; // either "move", "action" or "". Is used to tell the main update method which action the AI chooses
    private double angle = 0.00; // the angle at which the 
	private double angIncr = 45.000; // degrees by which the choosing angle is increased by 
	private final double init_angle = 10.0; // then initial angle at which the casting should begin
	private final double g = 0.001; //gravity
	private double y; //y-coordinate of the targeted player
	private double extraRange = 0.0; // the additional range the player gets in medium and hard mode
	private boolean noAction = false; // if true, AIPlayer cannot move or shoot
	private double error = 0;
	public static boolean easy = true;
	public static boolean medium = false; // AI's medium difficulty switch
	public static boolean hard = false; // AI's hard difficulty switch
	
	/**
	 * constructor of AIPlayer
	 * @param x x-coordinate of the AIPlayer
	 * @param y y-coordinate of the AIPlayer
	 * @param or orientation of the AIPlayer
	 * @param h reference to the HumanPlayer opponent
	 */
	public AIPlayer(double x, double y, int or, AbstractPlayer h) {
		super(x, y, or);
		this.h = h;
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 *play is called in the update method in PlayState.
	 *It stores the result of the Calculate method in the boolean calculate
	 *@return List of strings containing necessary information for update in PlayState to execute the AIPlayer's turn
	 */
	@Override
	public List<String> play(){
		
		failedAngles.clear();//clears the list of failed angles from last turn
		boolean calculate = Calculate(init_angle);
		List<String> instructions = new ArrayList<String>();
		if(!calculate) {
			//calculate whether player moves closer or further from player
			
			if(noAction) {
				action = "";
			}else {
				action = "move";
			}
				
			instructions.add(0, action);
		} else {
			action = "shoot";
			if(!noAction) {
				instructions.add(0, action);
				instructions.add(1, Double.toString(init_b_v_x));
				instructions.add(2, Double.toString(init_b_v_y));
			}
		}
		if(instructions.isEmpty()) { // if instructions is empty, add an empty string element to prevent java.lang.IndexOutOfBoundsException
			instructions.add(0, "");
		}
		return instructions;
	}
	
	
	/**
	 * checks whether the given coordinates of a tank are on the predicted trajectory of the AIPlayer's bullet
	 * The margin of error is added to make the AI less predictable and more realistic. This is so the AI can also miss occasionally and not all move are necessarily rational or "the best possible move"
	 * @param width width of the tank
	 * @param height height of the tank
	 * @param centre_x x-coordinate of the centre of the tank
	 * @param centre_y y-coordinate of the centre of the tank
	 * @param i_b_v_x the initial x-velocity of the bullet
	 * @param i_b_v_y the initial y-velocity of the bullet 
	 * @return True if tank is on the trajectory of the bullet. False if not 
	 */
	public boolean isPlayerNearTrajectory(double width, double height, double centre_x, double centre_y, double i_b_v_x, double i_b_v_y) {

		y = init_y + i_b_v_y*((centre_x - init_b_x)/i_b_v_x) - 0.5*g*Math.pow((centre_x-init_b_x)/i_b_v_x, 2); //gives y-coordinate on the bullet's trajectory given an x
		
		
		if(((centre_y +(height/2)) > y) && ((centre_y - (height/2)) > y)) { //calculates whether the bullet is going to land near enough to the centre of the tank
			return true;
		}
		else
			return false;
	}
	
	/**
	 * recursive method that calculates at whether the AIPlayer is going to shoot or move
	 * it also calculates the velocity of the bullet and the angle of the shot if it decides to shoot
	 * The Calculate algorithm typically prioritises shooting over moving unless the Tank is completely out of range
	 * step by step:
	 * 1. set angle to ang (init_angle in initial function call) 
	 * 2. calculate velocity at which the bullet would fly on this specific angle
	 * 3. if bullet velocity exceeds the velocity of the human player's bullet velocity then flag it so that shooting will not happen on this iteration of Calculate(),this is to prevent the AI from shooting with unrealistic velocities
	 * 4. check if opposing player is near enough to the trajectory of the bullet to determine whether the AI should attempt shooting
	 * 5. if the velocity is not too high and the opposing player is near enough to the AIPlayer's bullet's trajectory then return true to shoot
	 * 6. else add the angle that was tried out to the list of failed angles, increase the angle and call Calculate() recursively with the new angle
	 * 7. if after a while, the condition to allow shooting is not reached, Calculate() will return false for the AI-tank to move towards the player
	 * @param ang the angle at which 
	 * @return true for the AI-tank to shoot, false for the AI-tank to move.
	 */
	public boolean Calculate(double ang) {
		angle = ang;
		double h_x = h.getX();//human player's x-coordinate
		double h_y = h.getY();//human player's y-coordinate
		double h_width = h.getWidth();//human player's width
		double h_height = h.getHeight();//human player's height
		double h_centre_x = h_x + (h_width/2);//human player's x-coordinate at it's centre
		double h_centre_y = h_y + (h_height/2);//human player's y-coordinate at it's centre
		double range = Math.abs(h_x - this.getX());//distance between human player and AI player
		double direction = (h_x - this.getX())/Math.abs(h_x - this.getX());//determines whether angle is negative of positive.

		if (range < 400.0) { //if players are too close to each other, AI-tank will back
			return false;
		}
		double velocity = Math.sqrt((range*g)/Math.sin(Math.toRadians(2*angle)));
		boolean velocityTooHigh = false; //if the velocity of the bullet coming from the AI-tank is higher than the constant bullet velocity of the human player
		
		if(hard) { //if hard, allow AI to shoot much further than human player
			extraRange = 0.1;
		}else if(medium) { //if medium, allow AI to shoot a bit further than human player
			extraRange = 0.05;
		}else if(easy) {//if normal, allow AI to shoot just as far as human player
			extraRange = 0.0;
		}
		
		if(!(velocity <= (Constants.INIT_B_VELOCITY + extraRange))) { //using !(<=) instead of > because the condition is also accounting for the case where velocity is NaN
			velocityTooHigh = true;
		}
		if (easy) {
			if(Math.random() <= 0.35) // will miss 35% of the time
				error = 0.1;
			else 
				error = 0; 
		} else if(medium) { 
			if(Math.random() <= 0.20)  // will miss 20% of the time
				error = 0.1; 
			else 
				error = 0;
		} else if(hard) {
			if(Math.random() <= 0.05)  // will miss 5% of the time
				error = 0.1;
			else 
				error = 0; 
		}

		init_b_v_x = direction * Math.cos(Math.toRadians(angle)) * velocity + error;
		init_b_v_y = direction * Math.sin(Math.toRadians(angle)) * velocity + error;
			
		if(isPlayerNearTrajectory(h_width, h_height, h_centre_x, h_centre_y, init_b_v_x, init_b_v_y) && !velocityTooHigh) {
			if(direction == 1) {
				this.setRotation((float) angle);
			} else {
				this.setRotation(180f + (float) angle);
			}
			return true;
		}else {
			failedAngles.add(angle);
			if(angIncr < 5.6) {//if the increment is this small then return false because algorithm is unlikely to return true
				return false;
			}
			angle+=angIncr; //increase the angle by the defined increment
			if(angle>90) { //if the angle is greater than 90 degrees, halve the increment and reset the angle
				angIncr = angIncr/2;
				angle = angIncr;
			}

			while(failedAngles.contains(angle)) { //find an angle that hasn't already been tried out 
				angle+=angIncr;
				}
			}
			return Calculate(angle);
		}
	

	/**
	 * Used to signal that AIPlayer is allowed to send the instruction (to PlayState) to take an action (shoot or move)
	 */
	@Override
	public void SetActionToTrue() {
		noAction = true;
	}
	
	
	/**
	 * Used to signal that AIPlayer is NOT allowed to send the instruction (to PlayState) to take an action (shoot or move)
	 */
	@Override
	public void SetActionToFalse() {
		noAction = false;
	}
	
	/**
	 * resetting values that may have been changed in the AIPlayer's last turn
	 */
	public void reInitialise() {
		angle = 0.00;
		angIncr = 45.000; //degrees by which the choosing angle is increased by 
		failedAngles.clear();//List that contains angles that the aiming algorithm no longer needs to try out

	}
	
	/**
	 * setting the difficulty to easy
	 */
	public static void easyModeOn() {
		easy = true;
		medium = false;
		hard = false;
	}
	
	/**
	 * setting the difficulty to medium
	 */
	public static void mediumModeOn() {
		easy = false;
		medium = true;
		hard = false;
	}
	
	/**
	 * setting the difficulty to hard
	 */
	public static void hardModeOn() {
		easy = false;
		medium = false;
		hard = true;
	}
}

