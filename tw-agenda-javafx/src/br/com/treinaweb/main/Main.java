package br.com.treinaweb.main;

import br.com.treinaweb.agenda.entidades.Contato;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            Contato contato = new Contato();
            VBox root = (VBox) FXMLLoader.load(getClass().getResource("Main.fxml"));
            Scene scene = new Scene(root, 600, 400);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Minha agenda");
            primaryStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
       
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

}
