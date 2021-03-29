package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {

    public Parent root;

    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {

        //FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/login.fxml"));
        root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/login.fxml"));


        Scene scene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Game Zone Zogu Zi");
        primaryStage.getIcons().add(new Image("/images/logo_icon.png"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}