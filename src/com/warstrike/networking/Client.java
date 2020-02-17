package com.warstrike.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.warstrike.states.Model;

// TODO: Auto-generated Javadoc
/**
 * The Class Client.
 */
public class Client extends Thread {

	/** The socket that handles communication to and from the server */
	private Socket socket = null;

	/** The stream used for receiving data from the server */
	private BufferedReader streamIn = null;
	/** The writer used for sending data from the server */
	private PrintWriter streamOut = null;

	/** A model encapsulating the relevant data about the game world */
	public Model model;
	/** A field that controls whether this client is allowed to send data to the server */
	public int canSend;
	
	/**
	 * Instantiates a new client, giving it an initial model of the game world
	 *
	 * @param address the address of the server
	 * @param port the port to which this client connects
	 */
	public Client(String address, int port) {
		canSend = 0;
		model = new Model();
		System.out.println("Establishing connection. Please wait ...");
		try {
			socket = new Socket(address, port);
			System.out.println("Connected " + socket);
			openStreams();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	/**
	 * Opens commmunication streams through and from the server
	 */
	public void openStreams() {
		try {
			streamOut = new PrintWriter(socket.getOutputStream());
			streamIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	/**
	 * Sends a string representing a command that can change the game world to the server
	 *
	 * @param command the command to be sent
	 */
	public void send(String command) {
		if (canSend > 0) {
			streamOut.write(command + "\n");
			streamOut.flush();
		}
	}

	/**
	 * Updates the local model based on the information received from the server
	 * 
	 * @param change A string that encapsulates data what has changed and its new value
	 * @throws IOException
	 */
	private void updateModel(String change) throws IOException {
		String[] changes = change.split(";");
		int changeCode = Integer.parseInt(changes[0]);
		double newValue = Double.parseDouble(changes[1]);
		switch (changeCode) {
		case ChangeCodes.B1_X : {
			model.setBullet1X(newValue);
			break;
		}
		case ChangeCodes.B1_Y : {
			model.setBullet1Y(newValue);
			break;
		}
		case ChangeCodes.B1_XVEL : {
			model.setx1Vel(newValue);
			break;
		}
		case ChangeCodes.B1_YVEL : {
			model.sety1Vel(newValue);
			break;
		}
		case ChangeCodes.B2_X : {
			model.setBullet2X(newValue);
			break;
		}
		case ChangeCodes.B2_Y : {
			model.setBullet2Y(newValue);
			break;
		}
		case ChangeCodes.B2_XVEL : {
			model.setX2Vel(newValue);
			break;
		}
		case ChangeCodes.B2_YVEL : {
			model.setY2Vel(newValue);
			break;
		}
		case ChangeCodes.LP_X : {
			model.setLeftPlayerX(newValue);
			break;
		}
		case ChangeCodes.LP_ROT : {
			model.setLeftPlayerRot(newValue);
			break;
		}
		case ChangeCodes.LP_HP : {
			model.setLeftPlayerHealth(newValue);
			if (newValue == 0.0) {
				if (canSend == 1) {
					canSend = -1;
				} else {
					canSend = -2;
				}
			}
			break;
		}
		case ChangeCodes.LP_FUEL : {
			model.setLeftPlayerFuel(newValue);
			break;
		}
		case ChangeCodes.RP_X : {
			model.setRightPlayerX(newValue);
			break;
		}
		case ChangeCodes.RP_ROT : {
			model.setRightPlayerRot(newValue);
			break;
		}
		case ChangeCodes.RP_HP : {
			model.setRightPlayerHealth(newValue);
			if (newValue == 0.0) {
				if (canSend == 2) {
					canSend = -1;
				} else {
					canSend = -2;
				}
			}
			break;
		}
		case ChangeCodes.RP_FUEL : {
			model.setRightPlayerFuel(newValue);
			break;
		}
		case ChangeCodes.LB_EXISTS : {
			model.setBullet1Exists(newValue);
			break;
		}
		case ChangeCodes.RB_EXISTS : {
			model.setBullet2Exists(newValue);
			break;
		}
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		while (canSend == 0) {
			try {
				canSend = streamIn.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		while (true) {
			try {
				String change = streamIn.readLine();
				change = change.trim();
				updateModel(change);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
