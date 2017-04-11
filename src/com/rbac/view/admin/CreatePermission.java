package com.rbac.view.admin;

import java.util.List;

import com.rbac.common.Common;
import com.rbac.model.AcessType;
import com.rbac.model.Permission;
import com.rbac.model.Resource;

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

public class CreatePermission {

	public void initialize(Stage primaryStage) {

		Label errorLB = new Label();
		errorLB.setTextFill(Color.web("#d1433e"));

		List<AcessType> acessTypes = Common.permissionController.getAllAcessTypes();

		Label acessTypeLB = new Label("Acess Types");

		final ComboBox<AcessType> acessTypeComboBox = new ComboBox<AcessType>();
		acessTypeComboBox.getItems().addAll(acessTypes);
		acessTypeComboBox.setPromptText("Select Acess Type");

		HBox acessTypeHB = new HBox(10);
		acessTypeHB.setAlignment(Pos.CENTER);
		acessTypeHB.getChildren().addAll(acessTypeLB, acessTypeComboBox);
		
		List<Resource> resources = Common.permissionController.getAllResources();

		Label resourceLB = new Label("Resources");

		final ComboBox<Resource> resourceComboBox = new ComboBox<Resource>();
		resourceComboBox.getItems().addAll(resources);
		resourceComboBox.setPromptText("Select resourcR");

		HBox resourceHB = new HBox(10);
		resourceHB.setAlignment(Pos.CENTER);
		resourceHB.getChildren().addAll(resourceLB, resourceComboBox);

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

				AcessType acessType = acessTypeComboBox.getSelectionModel().getSelectedItem();

				if (acessType == null) {
					errorLB.setText("Select access type from list");
					return;
				}
				
				Resource resource = resourceComboBox.getSelectionModel().getSelectedItem();

				if (resource== null) {
					errorLB.setText("Select resource from list");
					return;
				}

				acessType = Common.permissionController.getAcessTypeById(acessType.getId());
				
				resource = Common.permissionController.getResourceById(resource.getId());
				
				if (acessType == null || resource == null) {
					errorLB.setText("Unable to create permission");
					return;
				}

				Permission permission = new Permission();

				permission.setAccesstypeid(acessType.getId());
				permission.setResourceid(resource.getId());
				
				// if permission already exists 
				List<Permission> permissions = Common.permissionController.getPerissions(permission);
				
				if(!(permissions == null || permissions.isEmpty())) {
					permission = permissions.get(0);
					permission.setStatus(Boolean.TRUE);
				}
				
				Common.permissionController.savePermission(permission);
				
				primaryStage.hide();
				new AdminPanel().initialize(new Stage());
			}
		});

		HBox buttonHB = new HBox(10);
		buttonHB.setAlignment(Pos.CENTER);
		buttonHB.getChildren().addAll(cancelBT, createBT);

		VBox vBox = new VBox(5);
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(errorLB, acessTypeHB, resourceHB, buttonHB);

		StackPane root = new StackPane();
		root.getChildren().addAll(vBox);

		Scene scene = new Scene(root, 300, 300);

		primaryStage.setTitle("Create Permission");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}
