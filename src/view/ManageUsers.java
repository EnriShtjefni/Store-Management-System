package view;

import model.AccessUsers;
import model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ManageUsers {

	private User currentUser;

	public ManageUsers(User currentUser) {
		this.currentUser = currentUser;
	}

	public Scene exec(Stage primaryStage) {
		AccessUsers au = new AccessUsers();

		ObservableList<User> userList = FXCollections.observableArrayList(au.getUsers());

		TableView userTable = new TableView();
		userTable.setEditable(true);
		TableColumn<User, String> uNameC = new TableColumn("Username");
		uNameC.setCellValueFactory(new PropertyValueFactory<>("user"));

		uNameC.setCellFactory(TextFieldTableCell.<User>forTableColumn());

		uNameC.setOnEditCommit(new EventHandler<CellEditEvent<User, String>>() {
			@Override
			public void handle(CellEditEvent<User, String> t) {

				User oldData = (User) userTable.getSelectionModel().getSelectedItem();

				((User) t.getTableView().getItems().get(t.getTablePosition().getRow())).setUser(t.getNewValue());

				User newData = (User) userTable.getSelectionModel().getSelectedItem();
				System.out.println(oldData);

				AccessUsers au = new AccessUsers();
				au.editUser(userTable.getSelectionModel().getSelectedIndex(), newData);

			}
		});

		TableColumn<User, String> passC = new TableColumn("Password");
		passC.setCellValueFactory(new PropertyValueFactory<>("pass"));

		passC.setCellFactory(TextFieldTableCell.<User>forTableColumn());

		passC.setOnEditCommit(new EventHandler<CellEditEvent<User, String>>() {
			@Override
			public void handle(CellEditEvent<User, String> t) {

				User oldData = (User) userTable.getSelectionModel().getSelectedItem();

				((User) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPass(t.getNewValue());

				User newData = (User) userTable.getSelectionModel().getSelectedItem();
				System.out.println(newData);

				AccessUsers au = new AccessUsers();
				au.editUser(userTable.getSelectionModel().getSelectedIndex(), newData);
			}
		});

		TableColumn<User, Integer> typeC = new TableColumn("Type");
		typeC.setCellValueFactory(new PropertyValueFactory<>("level"));

		ObservableList<Integer> cbValues = FXCollections.observableArrayList(1, 2, 3);

		typeC.setCellFactory(ComboBoxTableCell.forTableColumn(cbValues));

		typeC.setOnEditCommit(new EventHandler<CellEditEvent<User, Integer>>() {
			@Override
			public void handle(CellEditEvent<User, Integer> t) {

				User oldData = (User) userTable.getSelectionModel().getSelectedItem();

				((User) t.getTableView().getItems().get(t.getTablePosition().getRow())).setLevel(t.getNewValue());

				User newData = (User) userTable.getSelectionModel().getSelectedItem();
				System.out.println(newData);

				AccessUsers au = new AccessUsers();
				au.editUser(userTable.getSelectionModel().getSelectedIndex(), newData);
			}
		});

		userTable.setItems(userList);
		userTable.getColumns().addAll(uNameC, passC, typeC);

		Button cancel = new Button("Cancel");
		Button delete = new Button("Delete");

		cancel.setOnAction(e -> {
			primaryStage.setScene((new MainMenu(this.currentUser)).exec(primaryStage));
		});

		delete.setOnAction(e -> {
			User user = (User) userTable.getSelectionModel().getSelectedItem();
			au.remove(user.getid());

			primaryStage.setScene(new ManageUsers(this.currentUser).exec(primaryStage));

		});

		BorderPane bp = new BorderPane();
		HBox hb = new HBox();
		hb.setMargin(delete, new Insets(0, 10, 0, 0));
		hb.setMargin(cancel, new Insets(0, 10, 0, 0));
		hb.getChildren().addAll(userTable, delete, cancel);
		hb.setAlignment(Pos.CENTER);

		bp.setCenter(userTable);
		bp.setBottom(hb);

		Scene scene = new Scene(bp);

		primaryStage.setTitle("List of Users");

		return scene;
	}

}
