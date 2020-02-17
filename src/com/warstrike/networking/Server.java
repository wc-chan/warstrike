package com.warstrike.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import org.newdawn.slick.SlickException;

import com.warstrike.state.server.ServerPlayState;

// TODO: Auto-generated Javadoc
/**
 * The Class Server.
 */
public class Server extends Thread {

	/** The Constant MAX_CONNECTIONS. */
	private static final int MAX_CONNECTIONS = 2;

	/** The clients. */
	private ServerThread[] clients = new ServerThread[MAX_CONNECTIONS + 1];
	
	/** The server socket. */
	private ServerSocket serverSocket = null;
	
	/** The client count. */
	private int clientCount = 0;

	/** The sps. */
	public ServerPlayState sps;
	
	/** The commands. */
	public LinkedList<String> commands = new LinkedList<String>();
	
	/**
	 * Instantiates a new server.
	 *
	 * @param port the port
	 */
	public Server(int port) {
		try {
			System.out.println("Binding to port" + port + ", please wait ...");
			serverSocket = new ServerSocket(port);
			System.out.println("Server started");
		} catch (IOException ioe) {
			ioe.printStackTrace();
			
		}
	}

	/**
	 * Method to accept client connections. The serverSocket accepts a limited
	 * amount of connections When a connection is established, a new thread is
	 * started
	 */
	public void acceptClients() {
		while (clientCount < MAX_CONNECTIONS) {
			try {
				System.out.println("Waiting for client " + (++clientCount) + " to connect");
				addThread(serverSocket.accept());
				System.out.println("Client " + clientCount + " connected");
			} catch (IOException ioe) {
				System.out.println("Server accept error");
				ioe.printStackTrace();
			}
		}
		for (int i = 1; i <= MAX_CONNECTIONS; i++) {
			clients[i].start();
			clients[i].sendBoolean();
		}

		System.out.println("Server threads started");
		start();
	}

	/**
	 * Helper method for adding client connections to the pool.
	 *
	 * @param socket The socket to the client to be added
	 */
	private void addThread(Socket socket) {
		System.out.println("Client accepted: " + socket);
		clients[clientCount] = new ServerThread(this, socket, clientCount);
	}

	/**
	 * Method that sends a change to all the clients
	 * 
	 * @param model The change to be sent
	 */
	public void broadcast(String change) {
		for (int i = 1; i <= MAX_CONNECTIONS; i++) {
			clients[i].send(change);
		}
	}
	
	/**
	 * Method that formats the command string and adds it to the list of commands that need to be executed.
	 *
	 * @param command the command
	 */
	public synchronized void handle(String command) {
		command = command.trim();
		commands.add(command);
	}
	
	public void run() {
		String command;
		while (true) {
			try {
				synchronized (this) {
					command = commands.pop();
					sps.execute(command);
				}
			} catch (NoSuchElementException e) {
				
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}

}
