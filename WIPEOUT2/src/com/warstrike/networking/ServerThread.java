package com.warstrike.networking;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.warstrike.commands.Command;

public class ServerThread extends Thread {
	
	private Server       server    = null;
	private Socket           socket    = null;
	private int              ID        = -1;
	private ObjectInputStream  streamIn  =  null;
	private ObjectOutputStream streamOut = null;

	public ServerThread(Server _server, Socket _socket) {  
		super();
		server = _server;
		socket = _socket;
		ID     = socket.getPort();
	}
	
	public void send(Model model){   
		try { 
			streamOut.writeObject(model);
			streamOut.flush();
		} catch(IOException ioe) {  
			System.out.println(ID + " ERROR sending: " + ioe.getMessage());
			server.remove(ID);
			interrupt();
		}
	}
	
	public int getID() {  
		return ID;
	}
	
	public void run() {  
		System.out.println("Server Thread " + ID + " running.");
		while (true) {  
			try {  
				server.handle(ID, (Command) streamIn.readObject());
			} catch(IOException ioe) {  
				System.out.println(ID + " ERROR reading: " + ioe.getMessage());
				server.remove(ID);
				interrupt();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void open() throws IOException {  
		streamIn = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
		streamOut = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		streamOut.flush();
	}
	
	public void close() throws IOException {  
		if (socket != null)    socket.close();
		if (streamIn != null)  streamIn.close();
		if (streamOut != null) streamOut.close();
	}
}
/* extends Thread {
	
	ObjectInputStream fromClient;
	ObjectOutputStream toClient;
	Model model;
	
	public ServerThread(Socket s1, Socket s2, Model m) throws IOException {
		fromClient = new ObjectInputStream(new BufferedInputStream(s1.getInputStream()));
        toClient = new ObjectOutputStream(new BufferedOutputStream(s2.getOutputStream()));
        toClient.flush();
        model = m;
	}
	
	public void run() {
		Command c;
		while (true) {
			try {
				if (fromClient.read() != -1) {
					c = (Command) fromClient.readObject();
					parseCommand(c);
					toClient.writeObject(model);
				}
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void parseCommand(Command c) {
		System.out.println("Parsing command");
		c.execute(model);
	}
}*/
