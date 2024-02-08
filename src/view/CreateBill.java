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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.BillManager;
import model.CD;
import model.CDManager;
import model.User;

public class CreateBill {

	User currentUser;
	CD currentCD;

	public CreateBill(User cUser, CD cd) {
		currentUser = cUser;
		currentCD = cd;
	}

	public Scene exec(Stage primaryStage) {

		Text successfullBill = new Text("Bill successfuly created!");
		successfullBill.setStroke(Color.RED);
		successfullBill.setVisible(false);

		Label title = new Label("Title: ");
		Label quantity = new Label("Quantity: ");

		Label cdTitle = new Label(currentCD.getTitle());
		TextField quantityField = new TextField();

		Button create = new Button("Create");
		Button cancel = new Button("Cancel");

		HBox buttons = new HBox();
		buttons.getChildren().addAll(create, cancel);
		buttons.setAlignment(Pos.CENTER);
		HBox.setMargin(create, new Insets(0, 10, 0, 0));

		GridPane fieldPane = new GridPane();
		fieldPane.addRow(0, title, cdTitle);
		fieldPane.addRow(1, quantity, quantityField);
		fieldPane.setAlignment(Pos.CENTER);
		fieldPane.setPadding(new Insets(10, 10, 10, 10));
		fieldPane.setHgap(5);
		fieldPane.setVgap(5);

		BorderPane topPane = new BorderPane();
		topPane.setCenter(fieldPane);
		topPane.setBottom(buttons);
		topPane.getStyleClass().add("bluebox");

		cancel.setOnAction(e -> {
			primaryStage.setScene((new ListCDs(this.currentUser, true)).exec(primaryStage));
		});

		create.setOnAction(e -> {
			String titleData = currentCD.getTitle();
			String quantityData = quantityField.getText();
			int quantit = Integer.parseInt(quantityData);
			int currentQuntity = currentCD.getQuantity();

			if (currentQuntity > quantit) {
				currentCD.setQuantity(currentQuntity - quantit);
			} else {
				currentCD.setQuantity(0);
			}

			CDManager cdManager = new CDManager();

			cdManager.editCD(currentCD);

			BillManager manager = new BillManager();

			primaryStage.setScene((new ListCDs(this.currentUser, true)).exec(primaryStage));
		});

		BorderPane mainPane = new BorderPane();
		mainPane.setCenter(topPane);
		mainPane.setBottom(successfullBill);
		BorderPane.setAlignment(successfullBill, Pos.CENTER);

		Scene scene = new Scene(mainPane);
		scene.getStylesheets().addAll(this.getClass().getResource("../resources/css/style.css").toString());
		primaryStage.setTitle("Bill");
		return scene;
	}

}
