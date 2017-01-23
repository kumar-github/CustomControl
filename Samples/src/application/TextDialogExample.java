package application;

import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TextDialogExample
extends Application {

	private TextInputDialog dialog;
	private Text actionStatus;
	private final String defaultVal = "Default text";
	private static final String titleTxt = "JavaFX Dialogs Example";

	public static void main(final String [] args) {

		Application.launch(args);
	}

	@Override
	public void start(final Stage primaryStage) {

		primaryStage.setTitle(titleTxt);

		// Window label
		final Label label = new Label("Text Input Dialog");
		label.setTextFill(Color.DARKBLUE);
		label.setFont(Font.font("Calibri", FontWeight.BOLD, 36));
		final HBox labelHb = new HBox();
		labelHb.setAlignment(Pos.CENTER);
		labelHb.getChildren().add(label);

		// Button
		final Button textbtn = new Button("Get Text");
		textbtn.setOnAction(new TextButtonListener());
		final HBox buttonHb = new HBox(10);
		buttonHb.setAlignment(Pos.CENTER);
		buttonHb.getChildren().addAll(textbtn);

		// Status message text
		this.actionStatus = new Text();
		this.actionStatus.setFont(Font.font("Calibri", FontWeight.NORMAL, 20));
		this.actionStatus.setFill(Color.FIREBRICK);

		// Vbox
		final VBox vbox = new VBox(30);
		vbox.setPadding(new Insets(25, 25, 25, 25));;
		vbox.getChildren().addAll(labelHb, buttonHb, this.actionStatus);

		// Scene
		final Scene scene = new Scene(vbox, 500, 250); // w x h
		primaryStage.setScene(scene);
		primaryStage.show();

		// Initial dialog
		this.displayTextDialog();
	}

	private void displayTextDialog() {

		this.dialog = new TextInputDialog(this.defaultVal);
		this.dialog.setTitle(titleTxt);
		this.dialog.setHeaderText("Enter some text, or use default value.");

		final Optional<String> result = this.dialog.showAndWait();
		String entered = "none.";

		if (result.isPresent()) {

			entered = result.get();
		}

		this.actionStatus.setText("Text entered: " + entered);
	}

	private class TextButtonListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(final ActionEvent e) {

			TextDialogExample.this.actionStatus.setText("");
			TextDialogExample.this.displayTextDialog();
		}
	}
}