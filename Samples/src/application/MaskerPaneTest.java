package application;
import org.controlsfx.control.MaskerPane;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MaskerPaneTest extends Application {
    @Override
    public void start(final Stage primaryStage) throws Exception {

        final MaskerPane progressPane = new MaskerPane();
        progressPane.setVisible(false);

        final Button button = new Button("Show");
        button.setOnAction(event -> {

                final Task task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        progressPane.setVisible(true);
                        Thread.sleep(2000);
                        return null;
                    }
                    @Override
                    protected void succeeded(){
                        super.succeeded();
                        progressPane.setVisible(false);
                    }
                };
                new Thread(task).start();
            });
        final VBox box = new VBox(button, progressPane);
        box.setAlignment(Pos.CENTER);
        final Scene scene = new Scene(box, 200, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

	public static void main(final String[] args)
	{
		Application.launch();
	}
}