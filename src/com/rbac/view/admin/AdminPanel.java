package com.rbac.view.admin;

import com.rbac.util.Constants;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminPanel {

	/**
	 * The initialize() method is use to create admin gui.
	 * 
	 * @param primaryStage
	 */
	public void initialize(Stage primaryStage) {

		Button createUserBT = new Button("Create User");
		Button deleteUserBT = new Button("Delete User");
		Button createUserRoleBT = new Button("Create User Role");
		Button deleteUserRoleBT = new Button("Delete User Role");
		Button createPermissionBT = new Button("Create Permission");
		Button deletePermissionBT = new Button("Delete Permission");
		Button createRolePermissionBT = new Button("Create Role Permission");
		Button deleteRolePermissionBT = new Button("Delete Role Permission");
		
		createUserBT.setPrefWidth(200);
		deleteUserBT.setPrefWidth(200);
		createUserRoleBT.setPrefWidth(200);
		deleteUserRoleBT.setPrefWidth(200);
		createPermissionBT.setPrefWidth(200);
		deletePermissionBT.setPrefWidth(200);
		createRolePermissionBT.setPrefWidth(200);
		deleteRolePermissionBT.setPrefWidth(200);
		
		createUserBT.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				primaryStage.hide();
				new CreateUser().initialize(new Stage());
			}
		});

		deleteUserBT.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				primaryStage.hide();
				new DeleteUser().initialize(new Stage());
			}
		});

		createUserRoleBT.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				primaryStage.hide();
				new CreateUserRole().initialize(new Stage());
			}
		});

		deleteUserRoleBT.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				primaryStage.hide();
				new DeleteUserRole().initialize(new Stage());
			}
		});

		createPermissionBT.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				primaryStage.hide();
				new CreatePermission().initialize(new Stage());
			}
		});

		deletePermissionBT.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				primaryStage.hide();
				new DeletePermission().initialize(new Stage());
			}
		});

		createRolePermissionBT.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				primaryStage.hide();
				new CreateRolePermission().initialize(new Stage());
			}
		});

		deleteRolePermissionBT.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				primaryStage.hide();
				new DeleteRolePermission().initialize(new Stage());
			}
		});
		
		VBox vBox = new VBox(5);
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(createUserBT, deleteUserBT, createUserRoleBT, deleteUserRoleBT, createPermissionBT, deletePermissionBT,
				createRolePermissionBT, deleteRolePermissionBT);

		StackPane root = new StackPane();
		root.getChildren().addAll(vBox);

		Scene scene = new Scene(root, Constants.WIDTH, Constants.HEIGHT);

		primaryStage.setTitle("Admin | Main Screen");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}
