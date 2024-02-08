import view.Login;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Appi extends Application {

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setScene((new Login()).exec(primaryStage));
		primaryStage.show();
		primaryStage.getIcons().add(new Image("resources/images/cd.png"));
	}

	public static void main(String[] args) {
		launch(args);
	}
}
