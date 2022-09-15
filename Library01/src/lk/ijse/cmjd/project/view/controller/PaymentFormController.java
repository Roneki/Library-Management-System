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

public class PaymentFormController  implements Initializable {

    @FXML
    private AnchorPane root2;

    @FXML
    private Label lblPayments;

    @FXML
    private Button btnBack;

    @FXML
    private Label lblSelect1;

    @FXML
    private Label lblDescription1;

    @FXML
    private ImageView imageMembershipFeePay;

    @FXML
    private ImageView imageLostPay;

    @FXML
    private ImageView imageFinePay;

    @FXML
    void navigate(MouseEvent event) throws IOException {
        if (event.getSource()instanceof ImageView){
            ImageView icon= (ImageView) event.getSource();

            Parent root=null;

            switch (icon.getId()){
                case "imageFinePay":
                    root= FXMLLoader.load(this.getClass().getResource("/lk/ijse/cmjd/project/view/FinePaymentForm.fxml"));
                    break;
                case "imageLostPay":
                    root=FXMLLoader.load(this.getClass().getResource("/lk/ijse/cmjd/project/view/LostPaymentForm.fxml"));
                    break;
                case "imageMembershipFeePay":
                    root=FXMLLoader.load(this.getClass().getResource("/lk/ijse/cmjd/project/view/MembershipFeeForm.fxml"));
                    break;
            }
            if (root!=null) {
                Scene subScene = new Scene(root);
                Stage primaryStage = (Stage) this.root2.getScene().getWindow();
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
                case "imageFinePay":
                    lblSelect1.setText("Fine Payments");
                    lblDescription1.setText("Click to add or get fine payment details");
                    break;
                case "imageLostPay":
                    lblSelect1.setText("Lost Book Payments");
                    lblDescription1.setText("Click to add or get lost book payment details");
                    break;
                case "imageMembershipFeePay":
                    lblSelect1.setText("Membership Fee Payments");
                    lblDescription1.setText("Click to add or get membership fee payments");
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
            lblSelect1.setText("Select ");
            lblDescription1.setText("Please click one of the above operations to proceed");
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition fadeIn=new FadeTransition(Duration.millis(2000),root2);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

    }
    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/cmjd/project/view/MainForm.fxml"));

        Scene subScene = new Scene(root);
        Stage primaryStage = (Stage) this.root2.getScene().getWindow();
        primaryStage.setScene(subScene);
        primaryStage.centerOnScreen();

        TranslateTransition tt = new TranslateTransition(Duration.millis(200), subScene.getRoot());
        tt.setFromX(-subScene.getWidth());
        tt.setToX(0);
        tt.play();


    }

}
