package com.warstrike.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerThread.
 */
public class ServerThread extends Thread {

	/** The server. */
	private Server server = null;
	
	/** The socket. */
	private Socket socket = null;
	
	/** The stream in. */
	private BufferedReader streamIn = null;

	/** The stream out.  */
	private PrintWriter streamOut = null;
	private final int ID;
	
	/**
	 * Instantiates a new server thread.
	 * 
	 * @param _server
	 * @param _socket
	 * @param ID
	 */
	public ServerThread(Server _server, Socket _socket, int ID) {
		socket = _socket;
		server = _server;
		this.ID = ID;
		openStreams();
	}

	/**
	 * Helper method to initialise streams.
	 */
	private void openStreams() {
		try {
			streamIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			streamOut = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Method to send a change through the socket.
	 *
	 * @param change The change that's been made to the internal state of the game world
	 */
	public void send(String change) {
		streamOut.write(change + "\n");
		streamOut.flush();
	}

	/**
	 * Tell the clients whether they can send commands or not
	 */
	public void sendBoolean() {
		streamOut.write(ID);
		streamOut.flush();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		System.out.println("Server Thread running");
		String command;
		while (true) {
			try {
				command = streamIn.readLine();
				server.handle(ID + ";" + command);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
