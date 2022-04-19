package main;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

import javax.swing.SwingUtilities;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class Client extends Application{

	private String filePath = "C:\\Users\\davis\\Downloads\\rickroll.mp4"; //TODO add video inside project
	
	private Socket connection; // connection to server
	private Scanner input; // input from server
	private Formatter output; // output to server
	private String host; // host name for server
	
	public Client() {
		
	}
	
	public Client(String host)
	{
		this.host = host;
		
		//calls the start method for javafx
		launch();
		
	} // end Client constructor
	
	// process messages received by client
	private void processMessage( MediaPlayer mediaPlayer, String message )
	{
		if(message.equals("play")) {
			mediaPlayer.play();
		}
		else if ( message.equals( "pause" ) ) {
			mediaPlayer.pause();
		}
		else if ( message.equals( "stop" ) ) {
			mediaPlayer.stop();
		}
		else {
			//TODO add default case
		}
	} // end processMessage
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try // connect to server, and get streams
		{
			/*
			 * Handled inside of the start() method instead of constructor 
			 * because it was giving NullPointerException when referenced inside start()
			*/
			connection = new Socket( InetAddress.getByName( host ), 12345 );
			
			input = new Scanner( connection.getInputStream() );
			output = new Formatter( connection.getOutputStream() );
		} // end try
		catch ( IOException ioException )
		{
			ioException.printStackTrace();         
		} // end catch
		
		Media media = new Media(new File(filePath).toURI().toString());
		final MediaPlayer mediaPlayer = new MediaPlayer(media);
		MediaView mediaView = new MediaView(mediaPlayer);
		
		//setting view to fit the stage aspect ratio
		mediaView.fitWidthProperty().bind(primaryStage.widthProperty());
		mediaView.fitHeightProperty().bind(primaryStage.heightProperty());
		
		//Play video automatically
		mediaPlayer.setAutoPlay(true);
		
		Button pauseButton = new Button("Pause");
		pauseButton.setLayoutX(39);
		pauseButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent actionEvent) {
				mediaPlayer.pause();
				//TODO send message to server for all clients to pause
			}
		});
		
		Button playButton = new Button("Play");
		playButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent actionEvent) {
				mediaPlayer.play();
				//TODO send message to server for all clients to play
			}
		});
		
		//constructing pane to add to the scene
		Pane root = new Pane();  
		root.getChildren().add(mediaView);
		root.getChildren().add(pauseButton);
		root.getChildren().add(playButton);
		
		//create scene with fixed dimension and show it
		Scene scene = new Scene(root,640,360);
		primaryStage.setScene(scene);  
		primaryStage.setTitle("Playing video");
		primaryStage.setResizable(false);
		primaryStage.show();

		/*
		 * The only way I could get the message processing loop to work
		 * Not really what this method is supposed to be used for but whatever
		 * TODO find alternate method with threads - low priority
		 */
		SwingUtilities.invokeLater(
		         new Runnable() 
		         {
		            public void run() //message processing loop
		            {
		               while(true) {
		            	   if(input.hasNextLine()) {
		            		   processMessage(mediaPlayer, input.nextLine());
		            	   }
		               }
		            } // end run
		         } // end Runnable
		      ); // end SwingUtilities.invokeLater
		
	} //end start
	
} // end Client
