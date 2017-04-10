package com.rbac.view;

import com.rbac.common.Common;
import com.rbac.model.UserRole;
import com.rbac.util.Constants;

import javafx.application.Application;
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

public class Login extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		Label errorLB = new Label();
		errorLB.setTextFill(Color.web("#d1433e"));

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

		Label emptyLB = new Label("");
		Button loginBT = new Button("Login");
		loginBT.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				String username = usernameTF.getText();
				String password = passwordPF.getText();

				if ("".equals(username) || "".equals(password)) {
					errorLB.setText("All fields are mendatory");
					return;
				}

				Common.user = Common.userController.login(username, password);

				if (Common.user == null) {
					errorLB.setText("Invalid Credentials");
					return;
				}

				for (UserRole userRole : Common.user.getUserRoles()) {
					if (Constants.ROLE_ADMIN.equals(userRole.getRole().getName())) {
						Common.isAdmin = Boolean.TRUE;
						break;
					}
				}
				
				primaryStage.hide();
				
				if (Boolean.TRUE.equals(Common.isAdmin)) {
					// Send to admin panel
					new AdminPanel().initialize(new Stage());
				} else {
					// Send to doctor or nurse panel
					new UserPanel().initialize(new Stage());
				}
			}
		});

		HBox loginHB = new HBox(10);
		loginHB.setAlignment(Pos.CENTER);
		loginHB.getChildren().addAll(emptyLB, loginBT);

		VBox loginVB = new VBox(20);
		loginVB.setAlignment(Pos.CENTER);
		loginVB.getChildren().addAll(errorLB, usernameHB, passwordHB, loginHB);

		StackPane root = new StackPane();
		root.getChildren().addAll(loginVB);

		Scene scene = new Scene(root, 300, 300);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Login");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
