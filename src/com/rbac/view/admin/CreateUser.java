package com.rbac.view.admin;

import java.util.List;

import com.rbac.common.Common;
import com.rbac.model.User;
import com.rbac.util.Constants;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CreateUser {
	
	public void initialize(Stage primaryStage) {
		
		Label errorLB = new Label();
		errorLB.setTextFill(Color.web("#d1433e"));

		Label nameLB = new Label("Name");
		TextField nameTF = new TextField();

		HBox nameHB = new HBox(10);
		nameHB.setAlignment(Pos.CENTER);
		nameHB.getChildren().addAll(nameLB, nameTF);
		
		Label usernameLB = new Label("Username");
		TextField usernameTF = new TextField();

		HBox usernameHB = new HBox(10);
		usernameHB.setAlignment(Pos.CENTER);
		usernameHB.getChildren().addAll(usernameLB, usernameTF);

		Label passwordLB = new Label("Password");
		PasswordField passwordPF = new PasswordField();

		HBox passwordHB = new HBox(10);
		passwordHB.setAlignment(Pos.CENTER);
		passwordHB.getChildren().addAll(passwordLB, passwordPF);

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
				
				String name = nameTF.getText();
				String username = usernameTF.getText();
				String password = passwordPF.getText();

				if ("".equals(name) || "".equals(username) || "".equals(password)) {
					errorLB.setText("All fields are mendatory");
					return;
				}
				
				// Check if user already exists
				User user =  new User();
				
				user.setUsername(username);
				
				List<User> users = Common.userController.getUsers(user);
				
				if(users != null && !users.isEmpty()) {
					errorLB.setText("User already exists");
					return;
				}
				
				User newUser = new User();

				newUser.setName(name);
				newUser.setPassword(password);
				newUser.setUsername(username);

				Common.userController.saveUser(newUser);
				
				primaryStage.hide();
				new AdminPanel().initialize(new Stage());
			}
		});

		HBox createUserHB = new HBox(10);
		createUserHB.setAlignment(Pos.CENTER);
		createUserHB.getChildren().addAll(createBT, cancelBT);
		
		VBox vBox = new VBox(5);
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(errorLB, nameHB, usernameHB, passwordHB, createUserHB);
		
		StackPane root = new StackPane();
		root.getChildren().addAll(vBox);

		Scene scene = new Scene(root, Constants.WIDTH, Constants.HEIGHT);

		primaryStage.setTitle("Admin | Create User");
		primaryStage.setScene(scene);
		primaryStage.show();

	}
	
}
