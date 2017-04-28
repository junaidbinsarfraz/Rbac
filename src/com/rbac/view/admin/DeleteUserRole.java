package com.rbac.view.admin;

import java.util.List;

import com.rbac.common.Common;
import com.rbac.model.UserRole;
import com.rbac.util.Constants;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DeleteUserRole {

	public void initialize(Stage primaryStage) {

		Label errorLB = new Label();
		errorLB.setTextFill(Color.web("#d1433e"));

		List<UserRole> userRoles = Common.userController.getAllUserRoles();

		Label userRoleLB = new Label("User Roles");

		final ComboBox<UserRole> userRoleComboBox = new ComboBox<UserRole>();
		userRoleComboBox.getItems().addAll(userRoles);
		userRoleComboBox.setPromptText("Select User Role");

		HBox userRoleHB = new HBox(10);
		userRoleHB.setAlignment(Pos.CENTER);
		userRoleHB.getChildren().addAll(userRoleLB, userRoleComboBox);
		
		Button cancelBT = new Button("Cancel");
		cancelBT.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				primaryStage.hide();
				new AdminPanel().initialize(new Stage());
			}
		});

		Button deleteBT = new Button("Delete");
		deleteBT.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				UserRole userRole = userRoleComboBox.getSelectionModel().getSelectedItem();

				if (userRole == null) {
					errorLB.setText("Select user role from list");
					return;
				}
				
				userRole = Common.userController.getUserRoleById(userRole.getId());
				
				if (userRole == null) {
					errorLB.setText("Unable to delete user role");
				}

				Common.userController.deleteUserRole(userRole);
				
				primaryStage.hide();
				new AdminPanel().initialize(new Stage());
			}
		});

		HBox buttonHB = new HBox(10);
		buttonHB.setAlignment(Pos.CENTER);
		buttonHB.getChildren().addAll(deleteBT, cancelBT);

		VBox vBox = new VBox(5);
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(errorLB, userRoleHB, buttonHB);

		StackPane root = new StackPane();
		root.getChildren().addAll(vBox);

		Scene scene = new Scene(root, Constants.WIDTH, Constants.HEIGHT);

		primaryStage.setTitle("Admin | Delete User Role");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}
