package lk.ijse.cmjd.project.main;



import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class AppInitializer extends Application {

    public static void navigateToHome(Node node, Stage mainStage) throws IOException {

        Parent root = FXMLLoader.load(AppInitializer.class.getResource("/lk/ijse/cmjd/project/view/MainForm.fxml"));


        Scene mainScene = new Scene(root);
        mainStage.setScene(mainScene);

        TranslateTransition tt1 = new TranslateTransition(Duration.millis(300), root.lookup("AnchorPane"));
        tt1.setToX(0);
        tt1.setFromX(-mainScene.getWidth());
        tt1.play();

        mainStage.centerOnScreen();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
try {
    Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/cmjd/project/view/MainForm.fxml"));
    Scene mainScene = new Scene(root);
    primaryStage.setTitle("LMS SoftWare");
    primaryStage.setScene(mainScene);
    primaryStage.setResizable(false);

    primaryStage.show();

}catch (Exception e){
    new Alert(Alert.AlertType.ERROR, String.valueOf(e), ButtonType.OK).showAndWait();
}

    }

}
