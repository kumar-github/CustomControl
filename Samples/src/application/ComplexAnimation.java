package application;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ComplexAnimation extends Application {
    @Override
    public void start(final Stage stage) {
        final Group root = new Group();

        stage.setScene(new Scene(root, 400, 250));
        stage.show();

        this.startAnimation(root);
    }

    private void startAnimation(final Group root) {
        final Rectangle rect = new Rectangle(50, 50, 100, 75);
        rect.setFill(Color.RED);
        rect.setOpacity(0.0);

        final FadeTransition fadein
            = new FadeTransition(new Duration(500));
        fadein.setNode(rect);
        fadein.setToValue(1.0);

        final TranslateTransition translate
            = new TranslateTransition(new Duration(1000));
        translate.setNode(rect);
        translate.setFromX(0.0);
        translate.setToX(200.0);
        translate.setAutoReverse(true);
        translate.setCycleCount(5);

        final Timeline colorchange = new Timeline(
            new KeyFrame(new Duration(600),
                         new KeyValue(rect.fillProperty(), Color.GREEN))
        );
        colorchange.setAutoReverse(true);
        colorchange.setCycleCount(4);

        final Circle circ = new Circle(150, 150, 50);
        circ.setFill(Color.CYAN);
        circ.setOpacity(0.0);

        final FadeTransition fadein2
            = new FadeTransition(new Duration(500));
        fadein2.setNode(circ);
        fadein2.setToValue(1.0);

        final ScaleTransition scale
           = new ScaleTransition(new Duration(1000));
        scale.setNode(circ);
        scale.setFromX(1.0); scale.setFromY(1.0);
        scale.setToX(2.0); scale.setToY(0.75);
        scale.setAutoReverse(true);
        scale.setCycleCount(2);

        final FadeTransition fadeout
            = new FadeTransition(new Duration(500));
        fadeout.setNode(circ);
        fadeout.setToValue(0.0);
        fadeout.setOnFinished(event -> root.getChildren().remove(circ));

        final Timeline timeline = new Timeline(
            new KeyFrame(
                new Duration(500),
                event -> {
				    root.getChildren().add(rect);
				    fadein.play();
				}
            ),
            new KeyFrame(
                new Duration(1000),
                event -> translate.play()
            ),
            new KeyFrame(
                new Duration(1500),
                event -> colorchange.play()
            ),
            new KeyFrame(
                new Duration(2000),
                event -> {
				    root.getChildren().add(circ);
				    fadein2.play();
				}
            ),
            new KeyFrame(
                new Duration(2500),
                event -> scale.play()
            ),
            new KeyFrame(
                new Duration(5000),
                event -> fadeout.play()
            )
        );
        timeline.play();
    }

    public static void main(final String... args) {
        launch(args);
    }
}
