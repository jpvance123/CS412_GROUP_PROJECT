
import java.io.File;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class VideoPlayerTest extends Application{

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		String filePath = "C:\\Users\\chowd\\Downloads\\Dark_Haired_Girl_smiling_up_at_camera_1.mp4";
		Media media = new Media(new File(filePath).toURI().toString());
		
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		
		MediaView mediaView = new MediaView(mediaPlayer);
		
		mediaPlayer.setAutoPlay(true);
		
		Group root = new Group();  
        root.getChildren().add(mediaView);  
        Scene scene = new Scene(root,500,400);  
        primaryStage.setScene(scene);  
        primaryStage.setTitle("Playing video");  
        primaryStage.show();
		
	}

}
