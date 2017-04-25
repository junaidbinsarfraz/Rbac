package com.rbac.view.user;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.rbac.common.Common;
import com.rbac.crypto.common.CryptoCommon;
import com.rbac.crypto.util.BitsUtil;
import com.rbac.crypto.util.CryptoConstants;
import com.rbac.crypto.util.KeyStoreUtil;
import com.rbac.model.AcessType;
import com.rbac.model.Resource;
import com.rbac.model.UserRole;
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

public class UpdateFile {
	
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
		
		Label textLB = new Label("Text");
		
		TextArea textTA = new TextArea();
		textTA.setEditable(Boolean.TRUE);
		
		HBox textHB = new HBox(10);
		textHB.setAlignment(Pos.CENTER);
		textHB.getChildren().addAll(textLB, textTA);
		
		Button cancelBT = new Button("Cancel");
		cancelBT.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				primaryStage.hide();
				new UserPanel().initialize(new Stage());
			}
		});

		Button appendBT = new Button("Append");
		appendBT.setOnAction(new EventHandler<ActionEvent>() {

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

				acessType.setName(Constants.ACCESS_TYPE_UPDATE);
				
				Common.user = Common.userController.getUserById(Common.user.getId());
				
				Boolean isPermitted = Common.permissionController.isPermitted(Common.user, resource, acessType);

				if (Boolean.TRUE.equals(isPermitted)) {

					byte[] content = textTA.getText().getBytes();

					try {
						
						byte[] fileContent = FileUtil.readFile(filename);
						
						CryptoCommon.lastFileName = filename;
						
						if(fileContent != null && fileContent.length > 0) {
							try {
								
								UserRole updatedUserRole = new UserRole();
								
								updatedUserRole.setUser(Common.user);
								
								List<UserRole> userRoles = Common.userController.getUserRoles(updatedUserRole);
								
								Integer bytes = BitsUtil.getBytesFromUserRoles(userRoles);
								
								fileContent = CryptoCommon.encryptionController.decrypt(KeyStoreUtil.deserializeSecretKey(new FileInputStream(CryptoConstants.KEY_DIRECTORY + Common.user.getUsername() + ".txt"), 
										CryptoCommon.paring, BitsUtil.get32BitString(Integer.toBinaryString(bytes))), fileContent);
								
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						
						byte[] destination = new byte[fileContent.length + content.length];
						
						System.arraycopy(fileContent, 0, destination, 0, fileContent.length);
						
						System.arraycopy(content, 0, destination, fileContent.length, content.length);
						
//						FileUtil.appendIntoFile(filename, content);
						
						FileUtil.writeIntoFile(filename, CryptoCommon.encryptionController.encrypt(destination), Boolean.TRUE);
						
					} catch (IOException e) {
						errorLB.setText(e.getMessage());
						return;
					}

				} else {
					errorLB.setText("Not Permitted");
					return;
				}
				
				textTA.setText("");
				
				primaryStage.hide();
				new UserPanel().initialize(new Stage());
			}
		});
		
		HBox buttonHB = new HBox(10);
		buttonHB.setAlignment(Pos.CENTER);
		buttonHB.getChildren().addAll(cancelBT, appendBT);
		
		VBox loginVB = new VBox(20);
		loginVB.setAlignment(Pos.CENTER);
		loginVB.getChildren().addAll(errorLB, fileHB, textHB, buttonHB);

		StackPane root = new StackPane();
		root.getChildren().addAll(loginVB);

		Scene scene = new Scene(root, 300, 300);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Append Into File");
		primaryStage.show();
		
	}
	
}
