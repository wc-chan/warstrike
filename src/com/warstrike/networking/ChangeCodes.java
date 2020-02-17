package com.warstrike.networking;

public class ChangeCodes {

	/** Code referring to the x coordinate of the bullet corresponding to the left tank */
	public static final int B1_X = 1;
	/** Code referring to the x coordinate of the bullet corresponding to the right tank */
	public static final int B2_X = 2;
	
	/** Code referring to the y coordinate of the bullet corresponding to the left tank */
	public static final int B1_Y = 3;
	/** Code referring to the y coordinate of the bullet corresponding to the right tank */
	public static final int B2_Y = 6;
	
	/** Code referring to the horizontal velocity of the bullet corresponding to the left tank */
	public static final int B1_XVEL = 4;
	/** Code referring to the horizontal velocity of the bullet corresponding to the right tank */
	public static final int B2_XVEL = 8;
	
	/** Code referring to the vertical velocity of the bullet corresponding to the left tank */
	public static final int B1_YVEL = 5;
	/** Code referring to the vertical velocity of the bullet corresponding to the right tank */
	public static final int B2_YVEL = 10;

	/** Code referring to the x coordinate of the left tank */
	public static final int LP_X = 7;
	/** Code referring to the x coordinate of the right tank */
	public static final int RP_X = 14;
	
	/** Code referring to the angle of the gun barrel corresponding to the left tank */
	public static final int LP_ROT = 9;
	/** Code referring to the angle of the gun barrel corresponding to the right tank */
	public static final int RP_ROT = 18;
	
	/** Code referring to the health value of the left tank */
	public static final int LP_HP = 11;
	/** Code referring to the health value of the right tank */
	public static final int RP_HP = 22;
	
	/** Code referring to the fuel value of the left tank */
	public static final int LP_FUEL = 12;
	/** Code referring to the fuel value of the right tank */
	public static final int RP_FUEL = 24;
	
	/** Code referring to whether the bullet corresponding to the left tank exists */
	public static final int LB_EXISTS = 13;
	/** Code referring to whether the bullet corresponding to the right tank exists */
	public static final int RB_EXISTS = 26;
	
}
