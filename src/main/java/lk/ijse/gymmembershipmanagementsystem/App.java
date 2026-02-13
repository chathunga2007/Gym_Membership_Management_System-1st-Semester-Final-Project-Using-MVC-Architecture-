package lk.ijse.gymmembershipmanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {

    private static Scene scene;
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        Image icon = new Image(getClass().getResourceAsStream("/appImage/app_icon.png"));
        stage.getIcons().add(icon);
        scene = new Scene(loadFXML("WelcomePage"));
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Flex Gym Management System");
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static void setRoot(String fxml, double width, double height) throws IOException {
        Parent root = loadFXML(fxml);
        scene = new  Scene(root, width, height);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}