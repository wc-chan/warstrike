package com.warstrike.networking;

import java.net.*;

import org.newdawn.slick.Input;

import com.warstrike.commands.Command;

import java.io.*;

public class Client implements Runnable {  
	private Socket socket              = null;
	private Thread thread              = null;
	private ObjectInputStream  console   = null;
	private ObjectOutputStream streamOut = null;
	private ClientThread client    = null;
	public Model model;
	private Command	command;
	private boolean send = false;

	public Client(String serverName, int serverPort) {  
		System.out.println("Establishing connection. Please wait ...");
		try {  
			socket = new Socket(serverName, serverPort);
			System.out.println("Connected: " + socket);
			model = new Model();
			start();
			System.out.println("Started Client");
		} catch(UnknownHostException uhe) { 
			System.out.println("Host unknown: " + uhe.getMessage()); 
		} catch(IOException ioe) { 
			System.out.println("Unexpected exception: " + ioe.getMessage());
		}
	}
	
	public void run() { 
		System.out.println("Entered run");
		while (thread != null) { 
			try {
				if (send) System.out.println("Sent is true so we send");
				if (send) {
					
					streamOut.writeObject(command);
					streamOut.flush();
					send = false;
				}
			} catch(IOException ioe) { 
				System.out.println("Sending error: " + ioe.getMessage());
				stop();
			}
		}
	}
	
	public void send(Command command) {
		this.command = command;
		send = true;
	}
	
	public void handle(Model model) {
		this.model = model;
		/*if (msg.equals(".bye")) { 
			System.out.println("Good bye. Press RETURN to exit ...");
			stop();
		} else
			System.out.println(msg);*/
	}
	
	public void start() throws IOException { 
		console   = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
		streamOut = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		if (thread == null) { 
			client = new ClientThread(this, socket);
			thread = new Thread(this);                   
			thread.start();
		}
	}
	
	public void stop() {  
		if (thread != null) { 
			thread.interrupt();  
			thread = null;
		} try { 
			if (console   != null)  console.close();
			if (streamOut != null)  streamOut.close();
			if (socket    != null)  socket.close();
		} catch(IOException ioe) { 
			System.out.println("Error closing ..."); 
		}
		client.close();  
		client.interrupt();
	}
}
