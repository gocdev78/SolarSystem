import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.InputStream;

public class TextureTest extends Application {
    @Override
    public void start(Stage primaryStage) {
        InputStream inputStream = getClass().getResourceAsStream("/earth.jpg");
        Image texture = new Image(inputStream);
        ImageView imageView = new ImageView(texture);
        StackPane root = new StackPane(imageView);
        Scene scene = new Scene(root, texture.getWidth(), texture.getHeight());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
