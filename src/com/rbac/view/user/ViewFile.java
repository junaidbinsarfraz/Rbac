package com.rbac.view.user;

import java.util.List;

import com.rbac.common.Common;
import com.rbac.model.AcessType;
import com.rbac.model.Resource;
import com.rbac.util.Constants;
import com.rbac.util.FileUtil;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ViewFile {
	
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
		
		TextArea contentTA = new TextArea();
		contentTA.setEditable(Boolean.FALSE);
		
		Button cancelBT = new Button("Cancel");
		cancelBT.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				primaryStage.hide();
				new UserPanel().initialize(new Stage());
			}
		});

		Button viewBT = new Button("View");
		viewBT.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				contentTA.setText("");
				
				String filename = fileComboBox.getSelectionModel().getSelectedItem();

				if (filename == null || "".equals(filename)) {
					errorLB.setText("Select file from list");
					return;
				}
				
				Resource resource = new Resource();

				resource.setName(filename);

				AcessType acessType = new AcessType();

				acessType.setName(Constants.ACCESS_TYPE_VIEW);

				Boolean isPermitted = Common.permissionController.isPermitted(Common.user, resource, acessType);

				if (Boolean.TRUE.equals(isPermitted)) {

					String fileContent = FileUtil.readFile(filename);

					contentTA.setText(fileContent);

				} else {
					errorLB.setText("Not Permitted");
				}
				
			}
		});
		
		HBox buttonHB = new HBox(10);
		buttonHB.setAlignment(Pos.CENTER);
		buttonHB.getChildren().addAll(cancelBT, viewBT);
		
		VBox loginVB = new VBox(20);
		loginVB.setAlignment(Pos.CENTER);
		loginVB.getChildren().addAll(errorLB, fileHB, buttonHB, contentTA);

		StackPane root = new StackPane();
		root.getChildren().addAll(loginVB);

		Scene scene = new Scene(root, 300, 300);

		primaryStage.setScene(scene);
		primaryStage.setTitle("View File");
		primaryStage.show();
		
	}
	
}
