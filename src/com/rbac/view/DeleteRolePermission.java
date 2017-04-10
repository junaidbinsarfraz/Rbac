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

public class DeleteRolePermission {

	public void initialize(Stage primaryStage) {

		Label errorLB = new Label();
		errorLB.setTextFill(Color.web("#d1433e"));

		List<RolePermission> rolePermissions = Common.roleController.getAllRolePermissoins();
		List<String> rolePermissionsDescription = new ArrayList<>();
		
		for (RolePermission rolePermission : rolePermissions) {

			Permission permission = rolePermission.getPermission();

			AcessType acessType = Common.permissionController.getAcessTypeById(permission.getAccesstypeid());
			Resource resource = Common.permissionController.getResourceById(permission.getResourceid());

			rolePermissionsDescription.add(rolePermission.getId() + ") Role " + rolePermission.getRole().getName() + " can " + acessType.getName()
					+ " " + resource.getName());

		}

		Label rolePermissionLB = new Label("Role Permissions");

		final ComboBox<String> rolePermissionComboBox = new ComboBox<String>();
		rolePermissionComboBox.getItems().addAll(rolePermissionsDescription);
		rolePermissionComboBox.setPromptText("Select Role Permission");

		HBox rolePermissionHB = new HBox(10);
		rolePermissionHB.setAlignment(Pos.CENTER);
		rolePermissionHB.getChildren().addAll(rolePermissionLB, rolePermissionComboBox);
		
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
				String rolePermissionDescription = rolePermissionComboBox.getSelectionModel().getSelectedItem();
				
				if(rolePermissionDescription == null || Boolean.FALSE.equals(rolePermissionDescription.contains(")"))) {
					errorLB.setText("Select role permission from drop down");
					return;
				}
				
				Integer rolePermissionId = Integer.parseInt(rolePermissionDescription.split(")")[0]);
				
				RolePermission rolePermission = Common.roleController.getRolePermissionById(rolePermissionId);

				if (rolePermission == null) {
					errorLB.setText("Unable to delete role permission");
					return;
				}
				
				Common.roleController.deleteRolePermission(rolePermission);
				
				primaryStage.hide();
				new AdminPanel().initialize(new Stage());
			}
		});

		HBox buttonHB = new HBox(10);
		buttonHB.setAlignment(Pos.CENTER);
		buttonHB.getChildren().addAll(cancelBT, deleteBT);

		VBox vBox = new VBox(5);
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(errorLB, rolePermissionHB, buttonHB);

		StackPane root = new StackPane();
		root.getChildren().addAll(vBox);

		Scene scene = new Scene(root, 300, 300);

		primaryStage.setTitle("Delete Role Permission");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}
