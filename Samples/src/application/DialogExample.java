package application;

import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DialogExample
extends Application {

	private Text actionStatus;
	private static final String titleTxt = "JavaFX Dialogs Example";

	public static void main(final String [] args) {

		Application.launch(args);
	}

	@Override
	public void start(final Stage primaryStage) {

		primaryStage.setTitle(titleTxt);

		// Window label
		final Label label = new Label("A Dialog");
		label.setTextFill(Color.DARKBLUE);
		label.setFont(Font.font("Calibri", FontWeight.BOLD, 36));
		final HBox labelHb = new HBox();
		labelHb.setAlignment(Pos.CENTER);
		labelHb.getChildren().add(label);

		// Button
		final Button btn = new Button("Click to Show Dialog");
		btn.setOnAction(new DialogButtonListener());
		final HBox buttonHb = new HBox(10);
		buttonHb.setAlignment(Pos.CENTER);
		buttonHb.getChildren().addAll(btn);

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
	}

	private class DialogButtonListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(final ActionEvent e) {

			DialogExample.this.displayDialog();
		}
	}

	private void displayDialog() {

		this.actionStatus.setText("");

		// Custom dialog
		final Dialog<PhoneBook> dialog = new Dialog<>();
		dialog.setTitle(titleTxt);
		dialog.setHeaderText("This is a dialog. Enter info and \n" +
		"press Okay (or click title bar 'X' for cancel).");
		dialog.setResizable(true);

		// Widgets
		final Label label1 = new Label("Name: ");
		final Label label2 = new Label("Phone: ");
		final TextField text1 = new TextField();
		final TextField text2 = new TextField();

		// Create layout and add to dialog
		final GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 35, 20, 35));
		grid.add(label1, 1, 1); // col=1, row=1
		grid.add(text1, 2, 1);
		grid.add(label2, 1, 2); // col=1, row=2
		grid.add(text2, 2, 2);
		dialog.getDialogPane().setContent(grid);

		// Add button to dialog
		final ButtonType buttonTypeOk = new ButtonType("Okay", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk );

		// Result converter for dialog
		dialog.setResultConverter(b -> {

			if (b == buttonTypeOk)
				return new PhoneBook(text1.getText(), text2.getText());

			return null;
		});

		// Show dialog
		final Optional<PhoneBook> result = dialog.showAndWait();

		if (result.isPresent()) {

			this.actionStatus.setText("Result: " + result.get());
		}
	}

	private class PhoneBook {

		private final String name;
		private final String phone;

		PhoneBook(final String s1, final String s2) {

			this.name = s1;
			this.phone = s2;
		}

		@Override
		public String toString() {

			return (this.name + ", " + this.phone);
		}
	}
}