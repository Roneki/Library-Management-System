package lk.ijse.cmjd.project.view.controller;


import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BookTransactionsFormController  implements Initializable {

    @FXML
    private AnchorPane root1;

    @FXML
    private Label lblBookTransactions;

    @FXML
    private ImageView imageBurrow;

    @FXML
    private ImageView imageReturn;

    @FXML
    private ImageView imageRenew;

    @FXML
    private Button btnBack;

    @FXML
    private Label lblSelect;

    @FXML
    private Label lblDescription;

    @FXML
    void navigate(MouseEvent event) throws IOException {
        if (event.getSource()instanceof ImageView){
            ImageView icon= (ImageView) event.getSource();

            Parent root=null;

            switch (icon.getId()){
                case "imageBurrow":
                    root= FXMLLoader.load(this.getClass().getResource("/lk/ijse/cmjd/project/view/BorrowForm.fxml"));
                    break;
                case "imageReturn":
                    root=FXMLLoader.load(this.getClass().getResource("/lk/ijse/cmjd/project/view/ReturnForm.fxml"));
                    break;
                case "imageRenew":
                    root=FXMLLoader.load(this.getClass().getResource("/lk/ijse/cmjd/project/view/RenewForm.fxml"));
                    break;
            }
            if (root!=null) {
                Scene subScene = new Scene(root);
                Stage primaryStage = (Stage) this.root1.getScene().getWindow();
                primaryStage.setScene(subScene);
                primaryStage.centerOnScreen();

                TranslateTransition tt = new TranslateTransition(Duration.millis(350), subScene.getRoot());
                tt.setFromX(-subScene.getWidth());
                tt.setToX(0);
                tt.play();


            }}
            }

    @FXML
    void playMouseEnterAnimation(MouseEvent event) {
        if (event.getSource()instanceof ImageView){
            ImageView icon= (ImageView) event.getSource();

            switch (icon.getId()){
                case "imageBurrow":
                   lblSelect.setText("Burrow Books");
                    lblDescription.setText("Click to add and get book burrowing details ");
                    break;
                case "imageReturn":
                    lblSelect.setText("Return  Books");
                    lblDescription.setText("Click to add and get book returning details");
                    break;
                case "imageRenew":
                    lblSelect.setText("Renew Books");
                    lblDescription.setText("Click to add and get book renewing details");
                    break;

            }
            ScaleTransition scaleT=new ScaleTransition(Duration.millis(200),icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow=new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }
    }

    @FXML
    void playMouseExitAnimation(MouseEvent event) {
        if (event.getSource()instanceof ImageView){
            ImageView icon= (ImageView) event.getSource();
            ScaleTransition scaleT=new ScaleTransition(Duration.millis(200),icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();

            icon.setEffect(null);
            lblSelect.setText("Select");
            lblDescription.setText("Please click one of the above operations to proceed");
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition fadeIn=new FadeTransition(Duration.millis(2000),root1);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

    }
    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/cmjd/project/view/MainForm.fxml"));

        Scene subScene = new Scene(root);
        Stage primaryStage = (Stage) this.root1.getScene().getWindow();
        primaryStage.setScene(subScene);
        primaryStage.centerOnScreen();

        TranslateTransition tt = new TranslateTransition(Duration.millis(200), subScene.getRoot());
        tt.setFromX(-subScene.getWidth());
        tt.setToX(0);
        tt.play();


    }

}
