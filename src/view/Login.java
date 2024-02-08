package view;

import java.io.File;
import model.AccessUsers;
import model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login {

	public Scene exec(Stage primaryStage) {

		Label userLabel = new Label("Username: ");
		userLabel.setFont(Font.font("Book Antiqua", FontWeight.BOLD, 16));
		TextField userField = new TextField();
		userField.setPromptText("Enter Username");

		Label passLabel = new Label("Password: ");
		passLabel.setFont(Font.font("Book Antiqua", FontWeight.BOLD, 16));
		PasswordField passField = new PasswordField();
		passField.setPromptText("Enter Password");

		Text err = new Text("Username and Password do not match!");
		err.setStroke(Color.RED);
		err.getStyleClass().add("error");
		err.setVisible(false);

		Button login = new Button("Login");
		login.getStyleClass().add("login-but");
		Button cancel = new Button("Cancel");
		cancel.getStyleClass().addAll("cancel", "login-but");

		GridPane gp = new GridPane();
		gp.setHgap(20);
		gp.setVgap(20);
		gp.setPadding(new Insets(10, 10, 10, 10));
		gp.addRow(0, userLabel, userField);
		gp.addRow(1, passLabel, passField);
		gp.setAlignment(Pos.CENTER);

		HBox hb = new HBox(login, cancel);
		hb.setPadding(new Insets(5, 5, 5, 5));
		hb.setMargin(login, new Insets(0, 10, 0, 0));
		hb.setAlignment(Pos.CENTER);

		HBox hb_err = new HBox(err);
		hb_err.setAlignment(Pos.CENTER);

		GridPane mainPane = new GridPane();
		Text welcome = new Text("Welcome to CD Store Project");
		welcome.setId("welcome-text");
		welcome.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		mainPane.addColumn(0, welcome, gp, hb_err, hb);
		mainPane.setId("actiontarget");
		mainPane.setAlignment(Pos.CENTER);

		login.setOnAction(e -> {

			String username = userField.getText();
			String password = passField.getText();

			File file = new File(AccessUsers.filename);

			if (username.equals("admin") && password.equals("admin")) {
				System.out.println("Logged in");

				User overrideUser = new User("admin", "admin", 1);
				primaryStage.setScene((new MainMenu(overrideUser)).exec(primaryStage));
			} else if (file.exists() && !file.isDirectory()) {
				AccessUsers fileUsers = new AccessUsers();

				User user = fileUsers.checkUser(username, password);

				if (user != null) {
					System.out.println("Logged in");
					primaryStage.setScene((new MainMenu(user)).exec(primaryStage));
				}
			} else {
				err.setVisible(true);
			}
		});

		cancel.setOnAction(e -> {
			primaryStage.close();
		});

		mainPane.setStyle("-fx-background-image: url('resources/images/bb.png');" + "-fx-background-repeat: stretch;");
		Scene scene = new Scene(mainPane, 600, 300);
		scene.getStylesheets().addAll(this.getClass().getResource("../resources/css/style.css").toExternalForm());
		primaryStage.setTitle("Login");

		return scene;
	}

}
