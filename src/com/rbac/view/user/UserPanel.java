package com.rbac.view.user;

import com.rbac.common.Common;
import com.rbac.util.Constants;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserPanel {
	
	public void initialize(Stage primaryStage) {
		
		Button createFileBT = new Button("Create File");
		Button viewFileBT = new Button("View File");
		Button updateFileBT = new Button("Update File");
		Button deleteFileBT = new Button("Delete File");
		
		createFileBT.setPrefWidth(150);
		viewFileBT.setPrefWidth(150);
		updateFileBT.setPrefWidth(150);
		deleteFileBT.setPrefWidth(150);
		
		createFileBT.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				primaryStage.hide();
				new CreateFile().initialize(new Stage());
			}
		});

		viewFileBT.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				primaryStage.hide();
				new ViewFile().initialize(new Stage());
			}
		});

		updateFileBT.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				primaryStage.hide();
				new UpdateFile().initialize(new Stage());
			}
		});

		deleteFileBT.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				primaryStage.hide();
				new DeleteFile().initialize(new Stage());
			}
		});

		VBox vBox = new VBox(5);
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(createFileBT, viewFileBT, updateFileBT, deleteFileBT);

		StackPane root = new StackPane();
		root.getChildren().addAll(vBox);

		Scene scene = new Scene(root, Constants.WIDTH, Constants.HEIGHT);

		primaryStage.setTitle((Common.user != null ? Common.user.getName() + " | " : "") + "Main Screen");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
}
