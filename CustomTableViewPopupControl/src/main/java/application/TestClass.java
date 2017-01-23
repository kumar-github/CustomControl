package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TestClass extends Application
{
	public TestClass()
	{
	}

	public static void main(final String[] args)
	{
		launch(args);
	}

	@Override
	public void init()
	{
		CayenneHelper.initializeCayenneServerRuntime();
		CayenneReferenceDataCache.fetchAllReferenceData();
	}

	@Override
	public void start(final Stage primaryStage)
	{
		try
		{
			final HBox root = FXMLLoader.load(this.getClass().getResource("TestView.fxml"));
			final Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch(final Exception ex)
		{
			Platform.exit();
		}
		finally
		{
		}
	}

	@Override
	public void stop() throws Exception
	{
		super.stop();
		Platform.exit();
		System.exit(0);
	}
}