package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            //AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("fxml_files/MainPage.fxml"));
            FXMLLoader root = new FXMLLoader(Main.class.getResource("fxml_files/LoginPage.fxml"));
            Scene scene = new Scene(root.load(),1024,768);
            scene.getStylesheets().add(getClass().getResource("css_files/LoginPage.css").toExternalForm());
            primaryStage.resizableProperty().setValue(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
