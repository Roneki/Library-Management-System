package lk.ijse.cmjd.project.view.controller;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

public class MainFormController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private ImageView imgLibraryManagementSystem;

    @FXML
    private ImageView imgBooks;

    @FXML
    private ImageView imgBookTransactions;

    @FXML
    private ImageView imgMembers;

    @FXML
    private ImageView imgPayments;

    @FXML
    private Label lblWelcome;

    @FXML
    private Label lblDescription;

    @FXML
    void navigate(MouseEvent event) throws IOException {
if (event.getSource()instanceof ImageView){
    ImageView icon= (ImageView) event.getSource();

    Parent root=null;

switch (icon.getId()){
    case "imgBooks":
        root=FXMLLoader.load(this.getClass().getResource("/lk/ijse/cmjd/project/view/ManageBook.fxml"));
        break;
    case "imgMembers":
        root=FXMLLoader.load(this.getClass().getResource("/lk/ijse/cmjd/project/view/ManageMember.fxml"));
        break;
    case "imgBookTransactions":
        root=FXMLLoader.load(this.getClass().getResource("/lk/ijse/cmjd/project/view/BookTransactionsForm.fxml"));
        break;
    case "imgPayments":
        root=FXMLLoader.load(this.getClass().getResource("/lk/ijse/cmjd/project/view/PaymentForm.fxml"));
        break;
}
if (root!=null){
    Scene subScene=new Scene(root);
    Stage primaryStage= (Stage) this.root.getScene().getWindow();
primaryStage.setScene(subScene);
primaryStage.centerOnScreen();

    TranslateTransition tt=new TranslateTransition(Duration.millis(350),subScene.getRoot());
tt.setFromX(-subScene.getWidth());
tt.setToX(0);
tt.play();

}

}

    }

    @FXML
    void playMouseEnterAnimation(MouseEvent event) {
        if (event.getSource()instanceof ImageView){
ImageView icon= (ImageView) event.getSource();

switch (icon.getId()){
    case "imgBooks":
lblWelcome.setText("Manage Book Details");
lblDescription.setText("Click to add,save,search,delete,update and get reports of books");
break;
    case "imgMembers":
        lblWelcome.setText("Manage Member Details");
        lblDescription.setText("Click to add,save,search,delete,update and get reports of members");
        break;
    case "imgBookTransactions":
        lblWelcome.setText("Book Transactions");
        lblDescription.setText("Click to borrow,return and renew books");
        break;
    case "imgPayments":
        lblWelcome.setText("Payments");
        lblDescription.setText("Click to make fine,lost book and membershipfee payments");
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
    void playMouseExitAnimation(MouseEvent event) throws IOException {
if (event.getSource()instanceof ImageView){
ImageView icon= (ImageView) event.getSource();
ScaleTransition scaleT=new ScaleTransition(Duration.millis(200),icon);
scaleT.setToX(1);
scaleT.setToY(1);
scaleT.play();

icon.setEffect(null);
lblWelcome.setText("Welcome");
lblDescription.setText("Please click one of the above operations to proceed");
}


        }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition fadeIn=new FadeTransition(Duration.millis(2000),root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

    }
}
