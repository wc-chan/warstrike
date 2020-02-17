package com.warstrike;

/**
 * This class defines all the constants in the game
 * @author Anca Burduv, Andrei Mihai, Kelvin Chua, Liam Skop, Stefan Nedelcu, Weng Chan
 */
public class Constants {

	/** The Constant PORT. */
	public static final int PORT = 25582;	
	
	/** The Constant UP_RATE. */
	public static final int UP_RATE = 60;
	
	/** The Constant FPS. */
	public static final int FPS = 60;
	
	/** The Constant AL_RENDER. */
	public static final boolean AL_RENDER = false;
	
	/** The Constant SHOW_FPS. */
	public static final boolean SHOW_FPS = true;
	
	/** The Constant VSYNC. */
	public static final boolean VSYNC = true;

	/** The Constant MENU_BUTTON_SCALE. */
	public static final float MENU_BUTTON_SCALE = 0.45f;
	
	/** The Constant ACTION_BUTTON_SCALE. */
	public static final float ACTION_BUTTON_SCALE = 0.5f;
	
	/** The Constant RIGHT_ORIENTATION. */
	/* Values representing the orientation of entities */
	public static final int RIGHT_ORIENTATION = 1;
	
	/** The Constant LEFT_ORIENTATION. */
	public static final int LEFT_ORIENTATION = -1;

	/* Min and max values for tank objects */

	/** The Constant MIN_FUEL. */
	public static final double MIN_FUEL = 0.0;
	
	/** The Constant MAX_FUEL. */
	public static final double MAX_FUEL = 100.0;
	
	/** The Constant MIN_HEALTH. */
	public static final double MIN_HEALTH = 0.0;
	
	/** The Constant MAX_HEALTH. */
	public static final double MAX_HEALTH = 100.0;
	
	/** The Constant FUEL_REGEN_RATE. */
	public static final double FUEL_REGEN_RATE = 0.01;
	
	/** The Constant TANK_Y. */
	public static final float TANK_Y = Window.HEIGHT * 5.55f / 6.0f - 74.0f;

	/** The Constant BULLET_SCALE. */
	public static final float BULLET_SCALE = 1.0f;
	
	/** The Constant INIT_B_VELOCITY. */
	public static final double INIT_B_VELOCITY = 0.7;

	/** The Constant MUD_MODIFIER. */
	/* Values used for modifying movement speed */
	public static final double MUD_MODIFIER = 0.5;
	
	/** The Constant ICE_MODIFIER. */
	public static final double ICE_MODIFIER = 1.5;
	
	/** The Constant BASE_MODIFIER. */
	public static final double BASE_MODIFIER = 1.0;
	
	public static final int NO_PLATFORMS = 4;
	public static final float[] platformX = new float[] {250.0f, 570.0f, 640.0f, 960.0f};
	public static final float[] platformY = new float[] {
			Window.HEIGHT * 5.55f / 8.5f,
			Window.HEIGHT * 5.55f / 7.7f,
			Window.HEIGHT * 5.55f / 7.7f,
			Window.HEIGHT * 5.55f / 8.5f
	};
	
	/**
	 * Generates an array of coordinates necessary for creating the tank hit box
	 *
<<<<<<< HEAD
	 * @param or the orientation of the tank: 1, if it is facing right, -1 if it is facing left
	 * @param xa the x coordinate of the pivot
	 * @param ya the y coordinate of the pivot
	 * @return An array containing the x and y coordinates of the corners of the polygon that is the hit box
=======
	 * @param or orientation
	 * @param xa
	 * @param ya 
	 * @return the tank coordinates
>>>>>>> AI
	 */
	public static float[] getTankCoordinates(int or, float xa, float ya) {
		float[] coords = new float[20];
		xa += (or > 0 ? 0 : 74.0f);
		coords[0] = xa;
		coords[1] = ya;
		float xb, yb, xc, yc, xd, yd, xe, ye, xf, yf, xg, yg, xh, yh, xi, yi, xj, yj;
		xb = xa;
		coords[2] = xb;
		yb = ya - 29.0f;
		coords[3] = yb;
		xc = xb + or * 8.0f;
		coords[4] = xc;
		yc = yb - 16.0f;
		coords[5] = yc;
		xd = xc + or * 9.0f;
		coords[6] = xd;
		yd = yc - 1.0f;
		coords[7] = yd;
		xe = xd + or * 1.0f;
		coords[8] = xe;
		ye = yd - 13.0f;
		coords[9] = ye;
		xf = xe + or * 14.0f;
		coords[10] = xf;
		yf = ye - 7.0f;
		coords[11] = yf;
		xg = xf + or * 12.0f;
		coords[12] = xg;
		yg = yf + 6.0f;
		coords[13] = yg;
		xh = xg + or * 1.0f;
		coords[14] = xh;
		yh = yg + 14.0f;
		coords[15] = yh;
		xi = xh + or * 18.0f;
		coords[16] = xi;
		yi = yh + 1.0f;
		coords[17] = yi;
		xj = xa + or * 63.0f;
		coords[18] = xj;
		yj = ya;
		coords[19] = yj;
		return coords;
	}
}
