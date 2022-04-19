module CS412Project {
	requires javafx.controls;
	requires java.desktop;
	requires javafx.media;
	
	opens main to javafx.graphics, javafx.fxml;
}
