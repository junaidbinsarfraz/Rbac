package com.rbac.view;

import java.util.ArrayList;
import java.util.List;

import com.rbac.common.Common;
import com.rbac.model.AcessType;
import com.rbac.model.Permission;
import com.rbac.model.Resource;
import com.rbac.model.RolePermission;

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

public class DeletePermission {

	public void initialize(Stage primaryStage) {

		Label errorLB = new Label();
		errorLB.setTextFill(Color.web("#d1433e"));

		List<Permission> allPermissions = Common.permissionController.getAllPermissions();
		List<String> permissionsDescription = new ArrayList<>();
		
		for (Permission permission : allPermissions) {

			AcessType acessType = Common.permissionController.getAcessTypeById(permission.getId());
			Resource resource = Common.permissionController.getResourceById(permission.getResourceid());

			permissionsDescription.add(permission.getId() + ") Can " + acessType.getName() + " " + resource.getName());
		}

		Label permissionLB = new Label("Permissions");

		final ComboBox<String> permissionComboBox = new ComboBox<String>();
		permissionComboBox.getItems().addAll(permissionsDescription);
		permissionComboBox.setPromptText("Select Permission");

		HBox permissionHB = new HBox(10);
		permissionHB.setAlignment(Pos.CENTER);
		permissionHB.getChildren().addAll(permissionLB, permissionComboBox);
		
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

				// Get permission by id
				String permissionDescription = permissionComboBox.getSelectionModel().getSelectedItem();
				
				if(permissionDescription == null || Boolean.FALSE.equals(permissionDescription.contains(")"))) {
					errorLB.setText("Select permission from drop down");
					return;
				}
				
				Integer permissionId = Integer.parseInt(permissionDescription.split(")")[0]);
				
				Permission selectedPermission = Common.permissionController.getPermissionById(permissionId);

				if (selectedPermission == null) {
					errorLB.setText("Unable to delete permission");
					return;
				}
				
				RolePermission rolePermission = new RolePermission();

				rolePermission.setPermission(selectedPermission);

				List<RolePermission> rolePermissions = Common.roleController.getRolePermissoins(rolePermission);

				for (RolePermission myRolePermission : rolePermissions) {
					if (selectedPermission.equals(myRolePermission.getPermission())) {
						Common.roleController.deleteRolePermission(myRolePermission);
					}
				}

				Common.permissionController.deletePermission(selectedPermission);
				
				primaryStage.hide();
				new AdminPanel().initialize(new Stage());
			}
		});

		HBox buttonHB = new HBox(10);
		buttonHB.setAlignment(Pos.CENTER);
		buttonHB.getChildren().addAll(cancelBT, deleteBT);

		VBox vBox = new VBox(5);
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(errorLB, permissionHB, buttonHB);

		StackPane root = new StackPane();
		root.getChildren().addAll(vBox);

		Scene scene = new Scene(root, 300, 300);

		primaryStage.setTitle("Delete Permission");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}
