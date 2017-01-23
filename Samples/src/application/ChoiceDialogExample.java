package application;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChoiceDialogExample
extends Application {

	private ChoiceDialog<String> dialog;
	private Text actionStatus;
	private final String [] arrayData = {"First", "Second", "Third", "Fourth"};
	private List<String> dialogData;
	private static final String titleTxt = "JavaFX Dialogs Example";

	public static void main(final String [] args) {

		Application.launch(args);
	}

	@Override
	public void start(final Stage primaryStage) {

		primaryStage.setTitle(titleTxt);

		// Window label
		final Label label = new Label("Choice Dialog");
		label.setTextFill(Color.DARKBLUE);
		label.setFont(Font.font("Calibri", FontWeight.BOLD, 36));
		final HBox labelHb = new HBox();
		labelHb.setAlignment(Pos.CENTER);
		labelHb.getChildren().add(label);

		// Button
		final Button choicebtn = new Button("Get Choice");
		choicebtn.setOnAction(new ChoiceButtonListener());
		final HBox buttonHb = new HBox(10);
		buttonHb.setAlignment(Pos.CENTER);
		buttonHb.getChildren().addAll(choicebtn);

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
		this.dialogData = Arrays.asList(this.arrayData);
		this.displayChoiceDialog();
	}

	private void displayChoiceDialog() {

		this.dialog = new ChoiceDialog<>(this.dialogData.get(0), this.dialogData);
		this.dialog.setTitle(titleTxt);
		this.dialog.setHeaderText("Select your choice");

		final Optional<String> result = this.dialog.showAndWait();
		String selected = "cancelled.";

		if (result.isPresent()) {

			selected = result.get();
		}

		this.actionStatus.setText("Selection: " + selected);
	}

	private class ChoiceButtonListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(final ActionEvent e) {

			ChoiceDialogExample.this.actionStatus.setText("");
			ChoiceDialogExample.this.displayChoiceDialog();
		}
	}
}