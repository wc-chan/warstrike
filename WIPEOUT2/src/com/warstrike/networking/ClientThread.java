package com.warstrike.networking;

import java.net.*;
import java.io.*;

public class ClientThread extends Thread { 
	private Socket           socket   = null;
	private Client       client   = null;
	private ObjectInputStream  streamIn = null;

	public ClientThread(Client _client, Socket _socket) { 
		client   = _client;
		socket   = _socket;
		open();  
		start();
	}
	
	public void open() { 
		try { 
			streamIn  = new ObjectInputStream(socket.getInputStream());
		} catch(IOException ioe) { 
			System.out.println("Error getting input stream: " + ioe);
			client.stop();
		}
	}
	
	public void close() { 
		try { 
			if (streamIn != null) streamIn.close();
		} catch(IOException ioe) { 
			System.out.println("Error closing input stream: " + ioe);
		}
	}
	
	public void run() { 
		while (true) { 
			try {
				client.handle((Model) streamIn.readObject());
			} catch(IOException ioe) { 
				System.out.println("Listening error: " + ioe.getMessage());
				client.stop();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}