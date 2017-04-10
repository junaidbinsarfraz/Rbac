package com.rbac.view;

import java.util.List;

import com.rbac.common.Common;
import com.rbac.model.Role;
import com.rbac.model.User;
import com.rbac.model.UserRole;

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

public class CreateUserRole {

	public void initialize(Stage primaryStage) {

		Label errorLB = new Label();
		errorLB.setTextFill(Color.web("#d1433e"));

		List<User> users = Common.userController.getAllUser();

		Label userLB = new Label("Users");

		final ComboBox<User> userComboBox = new ComboBox<User>();
		userComboBox.getItems().addAll(users);
		userComboBox.setPromptText("Select User");

		HBox userHB = new HBox(10);
		userHB.setAlignment(Pos.CENTER);
		userHB.getChildren().addAll(userLB, userComboBox);
		
		List<Role> roles = Common.roleController.getAllRoles();

		Label roleLB = new Label("Users");

		final ComboBox<Role> roleComboBox = new ComboBox<Role>();
		roleComboBox.getItems().addAll(roles);
		roleComboBox.setPromptText("Select Role");

		HBox roleHB = new HBox(10);
		roleHB.setAlignment(Pos.CENTER);
		roleHB.getChildren().addAll(roleLB, roleComboBox);

		Button cancelBT = new Button("Cancel");
		cancelBT.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				primaryStage.hide();
				new AdminPanel().initialize(new Stage());
			}
		});

		Button createBT = new Button("Create");
		createBT.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				User user = userComboBox.getSelectionModel().getSelectedItem();

				if (user == null) {
					errorLB.setText("Select user from list");
					return;
				}
				
				Role role = roleComboBox.getSelectionModel().getSelectedItem();

				if (role== null) {
					errorLB.setText("Select role from list");
					return;
				}

				user = Common.userController.getUserById(user.getId());

				if (user == null) {
					errorLB.setText("Unable to delete user");
					return;
				}
				
				role = Common.roleController.getRoleById(role.getId());

				// Assign user role

				UserRole userRole = new UserRole();

				userRole.setStatus(Boolean.TRUE);
				userRole.setRole(role);
				userRole.setUser(user);

				Common.userController.assignUserRole(userRole);

				primaryStage.hide();
				new AdminPanel().initialize(new Stage());
			}
		});

		HBox buttonHB = new HBox(10);
		buttonHB.setAlignment(Pos.CENTER);
		buttonHB.getChildren().addAll(cancelBT, createBT);

		VBox vBox = new VBox(5);
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(errorLB, userHB, roleHB, buttonHB);

		StackPane root = new StackPane();
		root.getChildren().addAll(vBox);

		Scene scene = new Scene(root, 300, 300);

		primaryStage.setTitle("Create User Role");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}
