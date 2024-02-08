package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.CD;
import model.CDManager;
import model.User;

public class ListCDs {

	private User currentUser;
	private Boolean selling;

	public ListCDs(User currentUser, Boolean selling) {
		this.currentUser = currentUser;
		this.selling = selling;
	}

	public Scene exec(Stage primaryStage) {
		CDManager cdsFile = new CDManager();

		ObservableList<CD> cdList = FXCollections.observableArrayList(cdsFile.getCDs());

		TableView cdTable = new TableView();

		cdTable.setEditable(true);

		TableColumn titleC = new TableColumn("Title");
		titleC.setCellValueFactory(new PropertyValueFactory<>("title"));

		TableColumn genreC = new TableColumn("Genre");
		genreC.setCellValueFactory(new PropertyValueFactory<>("genre"));

		TableColumn priceC = new TableColumn("Price");
		priceC.setCellValueFactory(new PropertyValueFactory<>("price"));

		TableColumn singerC = new TableColumn("Singer");
		singerC.setCellValueFactory(new PropertyValueFactory<>("singer"));

		TableColumn quantityC = new TableColumn("Quantity");
		quantityC.setCellValueFactory(new PropertyValueFactory<>("quantity"));

		cdTable.setItems(cdList);
		cdTable.getColumns().addAll(titleC, genreC, priceC, singerC, quantityC);

		Button cancel = new Button("Cancel");
		Button checkout = new Button("Checkout");
		Button addCd = new Button("Add CD");
		Button editCd = new Button("Edit CD");
		Button deleteCd = new Button("Delete");

		cancel.setOnAction(e -> {
			primaryStage.setScene((new MainMenu(this.currentUser)).exec(primaryStage));
		});

		checkout.setOnAction(e -> {

			CD cd = (CD) cdTable.getSelectionModel().getSelectedItem();
			primaryStage.setScene(new CreateBill(this.currentUser, cd).exec(primaryStage));

		});

		addCd.setOnAction(e -> {
			CD cd = (CD) cdTable.getSelectionModel().getSelectedItem();
			primaryStage.setScene(new AddCD(this.currentUser, cd, false, selling).exec(primaryStage));
		});

		editCd.setOnAction(e -> {
			CD cd = (CD) cdTable.getSelectionModel().getSelectedItem();
			primaryStage.setScene(new AddCD(this.currentUser, cd, true, selling).exec(primaryStage));

		});

		deleteCd.setOnAction(e -> {
			CDManager manager = new CDManager();

			CD cd = (CD) cdTable.getSelectionModel().getSelectedItem();
			manager.remove(cd.getId());
			primaryStage.setScene(new ListCDs(this.currentUser, selling).exec(primaryStage));

		});

		BorderPane bp = new BorderPane();
		HBox hb = new HBox();

		System.out.println(currentUser.getLevel());

		if (selling) {
			hb.getChildren().addAll(checkout, cancel);
		} else {
			hb.getChildren().addAll(deleteCd, editCd, addCd, cancel);
		}

		hb.setMargin(checkout, new Insets(0, 10, 0, 0));
		hb.setMargin(deleteCd, new Insets(0, 10, 0, 0));
		hb.setMargin(editCd, new Insets(0, 10, 0, 0));
		hb.setMargin(addCd, new Insets(0, 10, 0, 0));
		hb.setAlignment(Pos.CENTER);

		bp.setCenter(cdTable);
		bp.setBottom(hb);

		Scene scene = new Scene(bp);
		scene.getStylesheets().addAll(this.getClass().getResource("../resources/css/style.css").toString());

		primaryStage.setTitle("List of CDs");

		return scene;
	}

}
