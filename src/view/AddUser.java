package view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.AccessUsers;
import model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddUser {

	User currentUser;
	Button create1;
	Label usernField;
	Label passwdField;

	public AddUser(User cUser) {
		currentUser = cUser;
	}

	public Scene exec(Stage primaryStage) {

		Text successfulUser = new Text("User successfuly created!");
		successfulUser.setStroke(Color.RED);
		successfulUser.setVisible(false);

		Label username = new Label("Username: ");
		Label password = new Label("Password: ");
		TextField userField = new TextField();
		TextField passField = new TextField();

		Button create = new Button("Create");
		Button cancel = new Button("Cancel");

		HBox buttons = new HBox();
		buttons.getChildren().addAll(create, cancel);
		buttons.setAlignment(Pos.CENTER);
		buttons.setMargin(create, new Insets(0, 10, 0, 0));

		ToggleGroup position = new ToggleGroup();
		RadioButton cashier = new RadioButton("Cashier");
		RadioButton manager = new RadioButton("Manager");
		RadioButton admin = new RadioButton("Administrator");
		position.getToggles().addAll(cashier, manager, admin);
		position.selectToggle(cashier);

		HBox radios = new HBox(cashier, manager, admin);
		radios.setMargin(manager, new Insets(0, 10, 0, 10));
		radios.setAlignment(Pos.CENTER);

		VBox vbox = new VBox(radios, buttons);

		GridPane fieldPane = new GridPane();
		fieldPane.addRow(0, username, userField);
		fieldPane.addRow(1, password, passField);
		fieldPane.setAlignment(Pos.CENTER);
		fieldPane.setPadding(new Insets(10, 10, 10, 10));
		fieldPane.setHgap(5);
		fieldPane.setVgap(5);

		BorderPane topPane = new BorderPane();
		topPane.setCenter(fieldPane);
		topPane.setBottom(vbox);
		topPane.getStyleClass().add("bluebox");
		TitledPane newUser = new TitledPane("Register New User", topPane);

		cancel.setOnAction(new CancelHandler(this.currentUser, primaryStage));

		create.setOnAction(e -> {
			String usernameData = userField.getText();
			String passwordData = passField.getText();

			int type = 3;

			String posit = ((RadioButton) position.getSelectedToggle()).getText();

			if (posit == "Administrator")
				type = 1;
			else if (posit == "Manager")
				type = 2;
			else if (posit == "Cashier")
				type = 3;

			Pattern pattern = Pattern.compile("^(?=.*?\\d)(?=.*?[a-zA-Z])[a-zA-Z\\d]+$");
			Matcher matcher = pattern.matcher(passwordData);
			if (matcher.matches()) {
				AccessUsers userFile = new AccessUsers();
				userFile.addUser(new User(usernameData, passwordData, type));

				successfulUser.setText("User successfuly created!");
				successfulUser.setVisible(true);
				userField.setText("");
				passField.setText("");
				System.out.println("User: " + usernameData + ", " + passwordData + " -> " + posit);
			} else {
				successfulUser.setText("Password must have numbers and letters");
				successfulUser.setVisible(true);
			}
		});

		BorderPane mainPane = new BorderPane();
		mainPane.setCenter(topPane);
		mainPane.setBottom(successfulUser);
		mainPane.setAlignment(successfulUser, Pos.CENTER);

		Scene scene = new Scene(mainPane);
		scene.getStylesheets().addAll(this.getClass().getResource("../resources/css/style.css").toString());
		primaryStage.setTitle("User Registration");
		return scene;
	}

	private class CancelHandler implements EventHandler<ActionEvent> {
		User currentUser;
		Stage primaryStage;

		public CancelHandler(User u, Stage p) {
			currentUser = u;
			primaryStage = p;
		}

		public void handle(ActionEvent e) {
			primaryStage.setScene((new MainMenu(this.currentUser)).exec(primaryStage));
		}
	}

}
