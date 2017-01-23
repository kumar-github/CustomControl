package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TestClass_OLD extends Application
{
	@Override
	public void start(Stage primaryStage)
	{
		try
		{
			HBox root = FXMLLoader.load(this.getClass().getResource("TestView.fxml"));
			//TableView<Person> root = FXMLLoader.load(this.getClass().getResource("/control/CommodityReferenceDataTableView.fxml"));

			//SearchableTextField<Person> ab = new SearchableTextField<Person>();
			//ab.setReferenceDataControlNameToLoad("/view/CommodityReferenceDataTableView");
			//root.getChildren().add(ab);

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}