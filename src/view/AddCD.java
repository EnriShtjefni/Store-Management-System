package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.CD;
import model.CDManager;
import model.User;

public class AddCD {

	User currentUser;
	CD cd;
	Boolean editing;
	Boolean selling;

	public AddCD(User user, CD cd, Boolean editing, Boolean selling) {
		currentUser = user;
		this.cd = cd;
		this.editing = editing;
		this.selling = selling;
	}

	public Scene exec(Stage primaryStage) {

		Label title = new Label("Title: ");
		Label quantity = new Label("Quantity: ");
		Label singer = new Label("Singer: ");
		Label genre = new Label("Genre: ");
		Label price = new Label("Price: ");

		TextField titleField = new TextField();
		TextField quantityField = new TextField();
		TextField singerField = new TextField();
		TextField genreField = new TextField();
		TextField priceField = new TextField();

		Button create = new Button("Create");
		Button cancel = new Button("Cancel");

		HBox buttons = new HBox();
		buttons.getChildren().addAll(create, cancel);
		buttons.setAlignment(Pos.CENTER);
		HBox.setMargin(create, new Insets(0, 10, 0, 0));

		GridPane fieldPane = new GridPane();
		fieldPane.addRow(0, title, titleField);
		fieldPane.addRow(1, quantity, quantityField);
		fieldPane.addRow(2, singer, singerField);
		fieldPane.addRow(3, genre, genreField);
		fieldPane.addRow(4, price, priceField);

		fieldPane.setAlignment(Pos.CENTER);
		fieldPane.setPadding(new Insets(10, 10, 10, 10));
		fieldPane.setHgap(5);
		fieldPane.setVgap(5);

		BorderPane topPane = new BorderPane();
		topPane.setCenter(fieldPane);
		topPane.setBottom(buttons);
		topPane.getStyleClass().add("bluebox");

		if (editing) {
			titleField.setText(cd.getTitle());
			singerField.setText(cd.getSinger());
			genreField.setText(cd.getGenre());
			priceField.setText(String.valueOf(cd.getPrice()));
			quantityField.setText(String.valueOf(cd.getQuantity()));
			create.setText("Edit");

		}

		cancel.setOnAction(e -> {
			primaryStage.setScene((new ListCDs(this.currentUser, false)).exec(primaryStage));
		});

		create.setOnAction(e -> {
			String titleData = titleField.getText();
			String quantityData = quantityField.getText();
			int quantityInt = Integer.parseInt(quantityData);
			String singerData = singerField.getText();
			String priceData = priceField.getText();
			int priceInt = Integer.parseInt(priceData);
			String genreData = genreField.getText();

			CDManager manager = new CDManager();

			if (editing) {
				cd.setGenre(genreData);
				cd.setPrice(priceInt);
				cd.setQuantity(quantityInt);
				cd.setSinger(singerData);
				cd.setTitle(titleData);
				manager.editCD(cd);
			} else {
				CD cd = new CD(priceInt, titleData, singerData, genreData, quantityInt);
				manager.addCD(cd);
			}
			primaryStage.setScene((new ListCDs(this.currentUser, selling)).exec(primaryStage));
		});

		BorderPane mainPane = new BorderPane();
		mainPane.setCenter(topPane);

		Scene scene = new Scene(mainPane);
		scene.getStylesheets().addAll(this.getClass().getResource("../resources/css/style.css").toString());
		primaryStage.setTitle("Bill");
		return scene;
	}

}
