package application;

import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AlertDialogExample
extends Application {

	private TextField textFld;
	private Text actionStatus;
	private static final String titleTxt = "JavaFX Dialogs Example";

	public static void main(final String [] args) {

		Application.launch(args);
	}

	@Override
	public void start(final Stage primaryStage) {

		primaryStage.setTitle(titleTxt);

		// Window label
		final Label label = new Label("Alert Dialogs");
		label.setTextFill(Color.DARKBLUE);
		label.setFont(Font.font("Calibri", FontWeight.BOLD, 36));
		final HBox labelHb = new HBox();
		labelHb.setAlignment(Pos.CENTER);
		labelHb.getChildren().add(label);

		// Text field
		final Label txtLbl = new Label("A Text field:");
		txtLbl.setFont(Font.font("Calibri", FontWeight.NORMAL, 20));
		this.textFld = new TextField();
		this.textFld.setMinHeight(30.0);
		this.textFld.setPromptText("Enter some text and save.");
		this.textFld.setPrefColumnCount(15);
		final HBox hbox = new HBox();
		hbox.setSpacing(10);
		hbox.getChildren().addAll(txtLbl, this.textFld);

		// Buttons
		final Button infobtn = new Button("Info");
		infobtn.setOnAction(new InfoButtonListener());
		final Button savebtn = new Button("Save");
		savebtn.setOnAction(new SaveButtonListener());
		final Button clearbtn = new Button("Clear");
		clearbtn.setOnAction(new ClearButtonListener());
		final HBox buttonHb = new HBox(10);
		buttonHb.setAlignment(Pos.CENTER);
		buttonHb.getChildren().addAll(infobtn, savebtn, clearbtn);

		// Status message text
		this.actionStatus = new Text();
		this.actionStatus.setFont(Font.font("Calibri", FontWeight.NORMAL, 20));
		this.actionStatus.setFill(Color.FIREBRICK);

		// Vbox
		final VBox vbox = new VBox(30);
		vbox.setPadding(new Insets(25, 25, 25, 25));;
		vbox.getChildren().addAll(labelHb, hbox, buttonHb, this.actionStatus);

		// Scene
		final Scene scene = new Scene(vbox, 500, 300); // w x h
		primaryStage.setScene(scene);
		primaryStage.show();

		// Initial
		this.actionStatus.setText("An example of Alert Dialogs. Enter some text and save.");
		infobtn.requestFocus();
	}

	private class InfoButtonListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(final ActionEvent e) {

			// Show info alert dialog

			final Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(titleTxt);
			alert.setHeaderText("Information Alert");
			final String s ="This is an example of JavaFX 8 Dialogs. " +
			"This is an Alert Dialog of Alert type - INFORMATION." + " \n \n" +
			"Other Alert types are: CONFIRMATION, ERROR, NONE and WARNING.";
			alert.setContentText(s);

			alert.show();
		}
	}

	private class SaveButtonListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(final ActionEvent e) {

			// Show error alert dialog

			final String txt = AlertDialogExample.this.textFld.getText().trim();
			String msg = "Text saved: ";
			boolean valid = true;

			if ((txt.isEmpty()) || (txt.length() < 5)) {

				valid = false;
				final Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle(titleTxt);
				final String s = "Text should be at least 5 characters long. " +
				"Enter valid text and save. ";
				alert.setContentText(s);

				alert.showAndWait();
				msg = "Invalid text entered: ";
			}

			AlertDialogExample.this.actionStatus.setText(msg + txt);

			if (! valid) {

				AlertDialogExample.this.textFld.requestFocus();
			}
		}
	}

	private class ClearButtonListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(final ActionEvent e) {

			// Show confirm alert dialog

			final Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle(titleTxt);
			final String s = "Confirm to clear text in text field ! ";
			alert.setContentText(s);

			final Optional<ButtonType> result = alert.showAndWait();

			if ((result.isPresent()) && (result.get() == ButtonType.OK)) {

				AlertDialogExample.this.textFld.setText("");
				AlertDialogExample.this.actionStatus.setText("An example of Alert Dialogs. Enter some text and save.");
				AlertDialogExample.this.textFld.requestFocus();
			}
		}
	}
}