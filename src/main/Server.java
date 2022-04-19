package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server implements Runnable{
	
	//TODO create inner class to hold connection, input, and output for each client
	
	private ServerSocket server;
	private ExecutorService runGame;
	private Scanner input;
	private Formatter output;
	private Socket connection;
	
	public Server()
	{
		
		runGame = Executors.newFixedThreadPool(1);
		
		try
		{
			server = new ServerSocket( 12345, 2 ); // set up ServerSocket
			connection = server.accept();
			connection = server.accept();
			
			
			input = new Scanner( connection.getInputStream() );
			output = new Formatter( connection.getOutputStream() );
		} // end try
		catch ( IOException ioException ) 
		{
			ioException.printStackTrace();
			System.exit( 1 );
		} // end catch
		
		runGame.execute(this);
	} // end Server constructor
	
	public void run() {
		//TODO message processing loop
	}
}
