package view;

import java.util.ArrayList;

import model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu {

	User currentUser;
	Stage primaryStage;

	public MainMenu(User currentUser) {
		this.currentUser = currentUser;
	}

	public Scene exec(Stage ps) {
		primaryStage = ps;

		BorderPane mainPane = new BorderPane();
		Accordion panes = new Accordion();
		panes.getStyleClass().add("titledpane");

		MenuBar menuBar = new MenuBar();

		ArrayList<TitledPane> tipanes = new ArrayList<TitledPane>();
		ArrayList<Menu> items = new ArrayList<Menu>();

		VBox userButtons = new VBox();
		userButtons.getStyleClass().add("bluebox");

		Button viewCDs = new Button("View CDs");

		userButtons.getChildren().addAll(viewCDs);

		viewCDs.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				primaryStage.setScene((new ListCDs(currentUser, true)).exec(primaryStage));

			}
		});

		userButtons.setAlignment(Pos.CENTER);
		TitledPane userWorks = new TitledPane("Cashier", userButtons);

		tipanes.add(userWorks);

		if (currentUser.getLevel() <= 2) {
			VBox editorButtons = new VBox();
			editorButtons.getStyleClass().add("greenbox");
			Button manageCDs = new Button("Manage CDs");
			editorButtons.getChildren().addAll(manageCDs);

			editorButtons.setAlignment(Pos.CENTER);
			VBox.setMargin(manageCDs, new Insets(0, 0, 10, 0));

			manageCDs.setOnAction(e -> {
				primaryStage.setScene((new ListCDs(currentUser, false)).exec(primaryStage));
			});

			TitledPane editorWorks = new TitledPane("Manager", editorButtons);
			tipanes.add(editorWorks);
		}

		if (currentUser.getLevel() < 2) {
			VBox adminButtons = new VBox();
			adminButtons.getStyleClass().add("yellowbox");
			Button addUser = new Button("Add User");
			Button userManage = new Button("Manage Users");
			adminButtons.getChildren().addAll(addUser, userManage);

			adminButtons.setAlignment(Pos.CENTER);
			VBox.setMargin(addUser, new Insets(0, 0, 10, 0));

			TitledPane adminWorks = new TitledPane("Administrator", adminButtons);
			tipanes.add(adminWorks);

			addUser.setOnAction(e -> {
				primaryStage.setScene((new AddUser(this.currentUser)).exec(primaryStage));
			});

			userManage.setOnAction(e -> {
				primaryStage.setScene((new ManageUsers(this.currentUser)).exec(primaryStage));
			});
		}

		panes.getPanes().addAll(tipanes);

		panes.setExpandedPane(userWorks);

		Label logoutLab = new Label("Log Out");

		Menu logout = new Menu("", logoutLab);

		menuBar.getMenus().add(logout);

		mainPane.setCenter(panes);
		mainPane.setTop(menuBar);

		Scene scene = new Scene(mainPane, 600, 400);

		scene.getStylesheets().addAll(this.getClass().getResource("../resources/css/style.css").toString());

		primaryStage.setTitle("Main Menu - " + currentUser.getUser());

		logoutLab.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {

				primaryStage.setScene((new Login()).exec(primaryStage));

			}
		});

		return scene;
	}

}
