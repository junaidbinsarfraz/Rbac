package com.rbac.view.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.rbac.common.Common;
import com.rbac.model.AcessType;
import com.rbac.model.Permission;
import com.rbac.model.Resource;
import com.rbac.util.Constants;
import com.rbac.util.FileUtil;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CreateFile {
	
	public void initialize(Stage primaryStage) {
		
		Label errorLB = new Label();
		errorLB.setTextFill(Color.web("#d1433e"));

		Label fileNameLB = new Label("File Name");
		TextField fileNameTF = new TextField();

		HBox fileNameHB = new HBox(10);
		fileNameHB.setAlignment(Pos.CENTER);
		fileNameHB.getChildren().addAll(fileNameLB, fileNameTF);
		
		Button cancelBT = new Button("Cancel");
		cancelBT.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				primaryStage.hide();
				new UserPanel().initialize(new Stage());
			}
		});

		Button createBT = new Button("Create");
		createBT.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				String fileName = fileNameTF.getText();
				
				if(fileName == null || "".equals(fileName)) {
					errorLB.setText("Enter file name");
					return;
				}
				
				try {
					Boolean isFileCreated = FileUtil.writeIntoFile(fileName, "");
					
					if(Boolean.FALSE.equals(isFileCreated)) {
						errorLB.setText("Unable to create file");
						return;
					}
				} catch (IOException e) {
					errorLB.setText("Unable to create file");
					return;
				}
				
				Resource resource = new Resource();

				resource.setName(fileName);
				resource.setStatus(Boolean.TRUE);

				Common.permissionController.saveResource(resource);

				// Permission permission = new Permission();

				resource = Common.permissionController.getResources(resource) != null ? Common.permissionController.getResources(resource).get(0)
						: null;

				if (resource == null) {
					System.out.println("Unable to create file");
					return;
				}

				List<AcessType> acessTypes = Common.permissionController.getAllAcessTypes();

				List<Permission> permissions = new ArrayList<Permission>();

				for (AcessType acessType : acessTypes) {

					if (!(Constants.ACCESS_TYPE_CREATE.equals(acessType.getName()) || Constants.ACCESS_TYPE_DELETE.equals(acessType.getName())
							|| Constants.ACCESS_TYPE_UPDATE.equals(acessType.getName())
							|| Constants.ACCESS_TYPE_VIEW.equals(acessType.getName()))) {
						continue;
					}

					Permission newPermission = new Permission();

					newPermission.setAccesstypeid(acessType.getId());
					newPermission.setResourceid(resource.getId());
					newPermission.setStatus(Boolean.TRUE);

					permissions.add(newPermission);
				}

				for (Permission newPermission : permissions) {
					Common.permissionController.savePermission(newPermission);
				}

				primaryStage.hide();
				new UserPanel().initialize(new Stage());
			}
		});

		HBox buttonHB = new HBox(10);
		buttonHB.setAlignment(Pos.CENTER);
		buttonHB.getChildren().addAll(cancelBT, createBT);
		
		VBox loginVB = new VBox(20);
		loginVB.setAlignment(Pos.CENTER);
		loginVB.getChildren().addAll(errorLB, fileNameHB, buttonHB);

		StackPane root = new StackPane();
		root.getChildren().addAll(loginVB);

		Scene scene = new Scene(root, 300, 300);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Create File");
		primaryStage.show();
		
	}
	
}
