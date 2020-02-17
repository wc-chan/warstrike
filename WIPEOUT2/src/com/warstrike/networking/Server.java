package com.warstrike.networking;

import java.net.*;

import com.warstrike.commands.Command;

import java.io.*;

public class Server implements Runnable {
	
	private ServerThread[] clients = new ServerThread[2];
	private ServerSocket server = null;
	private Thread       thread = null;
	private int clientCount = 0;

	Model model;
	
	public Server(int port) {
		try {
			System.out.println("Binding to port " + port + ", please wait  ...");
			server = new ServerSocket(port);  
			System.out.println("Server started: " + server);
			model = new Model();
			start(); 
		} catch(IOException ioe) {  
			System.out.println("Can not bind to port " + port + ": " + ioe.getMessage());
		}
	}
	
	public void run() {  
		while (thread != null && clientCount < 2) {
			try {  
				System.out.println("Waiting for client " + (clientCount + 1) + "..."); 
				addThread(server.accept());
				System.out.println("Client " + clientCount + " connected");
			} catch(IOException ioe) {  
				System.out.println("Server accept error: " + ioe); 
				stop();
			}
		}
	}
	
	public void start() { 
		if (thread == null) {  
			thread = new Thread(this); 
			thread.start();
		} 
	}
   
	public void stop() { 
		if (thread != null) {  
			thread.interrupt(); 
			thread = null;
		} 
	}
	
	private int findClient(int ID) {  
		for (int i = 0; i < clientCount; i++)
			if (clients[i].getID() == ID)
				return i;
		return -1;
	}
	
	public synchronized void handle(int ID, Command cmd) {
		cmd.execute(model);
		for (int i = 0; i < clientCount; i++)
			clients[i].send(model);   
	}
	
	public synchronized void remove(int ID) {
		int pos = findClient(ID);
		if (pos >= 0) { 
			ServerThread toTerminate = clients[pos];
			System.out.println("Removing client thread " + ID + " at " + pos);
			if (pos < clientCount-1)
				for (int i = pos+1; i < clientCount; i++)
					clients[i-1] = clients[i];
			clientCount--;
			try { 
				toTerminate.close();
			} catch(IOException ioe) { 
				System.out.println("Error closing thread: " + ioe);
			}
			toTerminate.interrupt(); 
		}
	}
	
	private void addThread(Socket socket) { 
		if (clientCount < clients.length) { 
			System.out.println("Client accepted: " + socket);
			clients[clientCount] = new ServerThread(this, socket);
			try { 
				clients[clientCount].open(); 
				clients[clientCount].start();  
				clientCount++; 
			} catch(IOException ioe) {  
				System.out.println("Error opening thread: " + ioe); 
			} 
		} else {
			System.out.println("Client refused: maximum " + clients.length + " reached.");
		}
	}
	
	public static void main(String args[]) { 
		Server server = new Server(9565);
	}
}

/*import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import com.warstrike.Constants;



public class Server {
	
	public static void main(String[] args) throws IOException {
		
		Model model = new Model();
		
		try (ServerSocket listener = new ServerSocket(Constants.PORT)) {
			
            System.out.println("Game Server is Running");
            
            Socket s1 = listener.accept();
            Socket s2 = listener.accept();
            
            System.out.println("Clients Connected");
            
            ChatServerThread p1 = new ChatServerThread(s1, s2, model);
            ChatServerThread p2 = new ChatServerThread(s2, s1, model);
            p1.start();
            p2.start();
            System.out.println("Connections Established");
            
        }
		
	}
	
}*/
