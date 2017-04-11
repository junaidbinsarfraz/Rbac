package com.rbac.view.user;

import java.util.List;

import com.rbac.common.Common;
import com.rbac.model.AcessType;
import com.rbac.model.Permission;
import com.rbac.model.Resource;
import com.rbac.model.RolePermission;
import com.rbac.util.Constants;
import com.rbac.util.FileUtil;

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

public class DeleteFile {
	
	public void initialize(Stage primaryStage) {
		
		Label errorLB = new Label();
		errorLB.setTextFill(Color.web("#d1433e"));

		List<String> files = FileUtil.listFiles();

		Label fileLB = new Label("Files");

		final ComboBox<String> fileComboBox = new ComboBox<String>();
		fileComboBox.getItems().addAll(files);
		fileComboBox.setPromptText("Select File");

		HBox fileHB = new HBox(10);
		fileHB.setAlignment(Pos.CENTER);
		fileHB.getChildren().addAll(fileLB, fileComboBox);
		
		Button cancelBT = new Button("Cancel");
		cancelBT.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				primaryStage.hide();
				new UserPanel().initialize(new Stage());
			}
		});

		Button deleteBT = new Button("Delete");
		deleteBT.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				String filename = fileComboBox.getSelectionModel().getSelectedItem();

				if (filename == null || "".equals(filename)) {
					errorLB.setText("Select file from list");
					return;
				}
				
				Resource resource = new Resource();

				resource.setName(filename);

				AcessType acessType = new AcessType();

				acessType.setName(Constants.ACCESS_TYPE_DELETE);

				Boolean isPermitted = Common.permissionController.isPermitted(Common.user, resource, acessType);

				if (Boolean.TRUE.equals(isPermitted)) {

					if (Boolean.TRUE.equals(FileUtil.deleteFile(filename))) {
						
						List<Resource> resources = Common.permissionController.getResources(resource);
						
						if(resources == null || Boolean.TRUE.equals(resources.isEmpty())) {
							errorLB.setText("Unable to delete file");
							return;
						}
						
						resource = resources.get(0);
						
						// Get permission by resource id
						List<Permission> permissions = Common.permissionController.getAllPermissions();
						
						for(Permission permission: permissions) {
							if(resource.getId().equals(permission.getResourceid())) {
								
								// Get role permission by permission id
								List<RolePermission> rolePermissions = Common.roleController.getAllRolePermissoins();
								
								for(RolePermission rolePermission : rolePermissions) {
									if(permission.equals(rolePermission.getPermission())) {
										
										// Delete Role Permission
										Common.roleController.deleteRolePermission(rolePermission);
									}
								}
								// Delete Permission 
								Common.permissionController.deletePermission(permission);
							}
						}
						
						// Delete Resource
						Common.permissionController.deleteResource(resource);
						
						System.out.println("File Deleted");
					} else {
						errorLB.setText("Unable to delete file");
						return;
					}

				} else {
					errorLB.setText("Not Permitted");
					return;
				}
				
				primaryStage.hide();
				new UserPanel().initialize(new Stage());
			}
		});
		
		HBox buttonHB = new HBox(10);
		buttonHB.setAlignment(Pos.CENTER);
		buttonHB.getChildren().addAll(cancelBT, deleteBT);
		
		VBox loginVB = new VBox(20);
		loginVB.setAlignment(Pos.CENTER);
		loginVB.getChildren().addAll(errorLB, fileHB, buttonHB);

		StackPane root = new StackPane();
		root.getChildren().addAll(loginVB);

		Scene scene = new Scene(root, 300, 300);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Delete File");
		primaryStage.show();
		
	}
	
}
