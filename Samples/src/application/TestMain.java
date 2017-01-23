package application;

import java.security.MessageDigest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TestMain extends Application
{
	@Override
	public void start(final Stage primaryStage)
	{
		try
		{
			String ss = "welcome";

			System.out.println(ss instanceof String);
			System.out.println(ss instanceof Object);
			ss = null;
			System.out.println(ss instanceof String);
			System.out.println(ss instanceof Object);


			//List<String> homeCompanies = Arrays.asList("GH", "CD", "BC", "EF", "FE", "AB");
			final List<String> homeCompanies = Arrays.asList("GH", "CD", "BC", "EF", "FE", "BA");
			final List<String> x = Arrays.asList("AB", "BC", "CD", "DE", "EF", "GH");
			//System.out.println(x.stream().findFirst());
			System.out.println(x.stream().filter(anX -> homeCompanies.contains(anX)).findFirst());

			//MessageDigest md = MessageDigest.getInstance("");
			final MessageDigest md = MessageDigest.getInstance("SHA-1");
			final byte[] digestBytes = md.digest("admin".getBytes("UTF-8"));
			//new String(Base64.encodeBase64(digestBytes), "ASCII");
			final String s = new String(Base64.getEncoder().encode(digestBytes), "ASCII");
			System.out.println(s);

			final LocalTime startTime = LocalTime.of(0, 0, 0);
			final LocalTime endTime = LocalTime.of(23, 0, 0);

			final LocalDate startDate = LocalDate.now();
			final LocalDate endDate = LocalDate.now();

			final LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
			final LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

			DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss").format(startDateTime);
			DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss").format(endDateTime);
			System.out.println(startDateTime);
			System.out.println(endDateTime);

			//VBox root = FXMLLoader.load(this.getClass().getResource("LoginView.fxml"));
			//FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tc/app/exchangemonitor/view/fxml/LoginView.fxml"));
			final FXMLLoader loader = new FXMLLoader(this.getClass().getResource("LoginView.fxml"));
			final Scene scene = new Scene(new StackPane());
			scene.setRoot((Parent) loader.load());
			//Scene scene = new Scene(root);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			//LoginManager loginManager = new LoginManager(scene);
			//loginManager.showLoginScreen();

			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch(final Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(final String[] args)
	{
		launch(args);
	}
}