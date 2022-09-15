package lk.ijse.cmjd.project.view.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.cmjd.project.business.ManageBookBusiness;
import lk.ijse.cmjd.project.business.ManageLostPaymentBusiness;
import lk.ijse.cmjd.project.business.ManageMemberBusiness;
import lk.ijse.cmjd.project.dto.LostPaymentDTO;
import lk.ijse.cmjd.project.view.util.LostPaymentTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static net.sf.jasperreports.engine.util.JRLoader.loadObject;

public class LostPaymentFormController {
    @FXML
    private AnchorPane root;


    @FXML
    private Label lblLostBookPayment;

    @FXML
    private JFXTextField txtNo;

    @FXML
    private JFXComboBox cmbBookId;
    @FXML
    private Label lblBookId;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReport;

    @FXML
    private TableView<LostPaymentTM> tblLostPayment;

    @FXML
    private JFXComboBox cmbMemberId;
    @FXML
    private Label lblMemberId;

    @FXML
    private JFXTextField txtPayment;

    @FXML
    private JFXTextField txtBookPrice;
    public LostPaymentFormController() throws SQLException {
        List<String> bookIdList=new ArrayList<>();
        bookIdList= ManageBookBusiness.bookIdList();
        cmbBookId.setItems((ObservableList) bookIdList);

        List<String> memberIdList=new ArrayList<>();
        memberIdList= ManageMemberBusiness.memberIdList();
        cmbMemberId.setItems((ObservableList) memberIdList);
    }

    public void initialize() throws SQLException {
        tblLostPayment.getColumns().get(0).setStyle("-app-alignment:center");
        tblLostPayment.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("number"));
        tblLostPayment.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("bookId"));
        tblLostPayment.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("memberId"));
        tblLostPayment.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("bookPrice"));
        tblLostPayment.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("payment"));


        List<LostPaymentDTO> lostPaymentDB = ManageLostPaymentBusiness.getLostPaymentDTOList();
        ObservableList<LostPaymentDTO> lostPaymentDTOS = FXCollections.observableArrayList(lostPaymentDB);
        ObservableList<LostPaymentTM> tblItems = FXCollections.observableArrayList();
        for (LostPaymentDTO lostPaymentDTO:lostPaymentDTOS) {
            tblItems.add(new LostPaymentTM(lostPaymentDTO.getNumber(),lostPaymentDTO.getBookId(),lostPaymentDTO.getMemberId(),lostPaymentDTO.getBookPrice(),lostPaymentDTO.getPayment()));
        }
        tblLostPayment.setItems(tblItems);

        tblLostPayment.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<LostPaymentTM>() {
            @Override
            public void changed(ObservableValue<? extends LostPaymentTM> observable, LostPaymentTM oldValue, LostPaymentTM selectedLost) {

                if (selectedLost == null) {
                    // Clear Selection
                    return;
                }
                txtNo.setText(Integer.toString(selectedLost.getNumber()));
                cmbBookId.setId(selectedLost.getBookId());
                cmbMemberId.setId(selectedLost.getMemberId());
                txtBookPrice.setText(Double.toString(selectedLost.getBookPrice()));
                txtPayment.setText(Double.toString(selectedLost.getPayment()));




            }
        });
    }




    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/cmjd/project/view/PaymentForm.fxml"));

        Scene subScene = new Scene(root);
        Stage primaryStage = (Stage) this.root.getScene().getWindow();
        primaryStage.setScene(subScene);
        primaryStage.centerOnScreen();

        TranslateTransition tt = new TranslateTransition(Duration.millis(200), subScene.getRoot());
        tt.setFromX(-subScene.getWidth());
        tt.setToX(0);
        tt.play();

    }
    @FXML
    void btnNewFormOnAction(ActionEvent event) {
reset();

    }
private void reset(){
        txtNo.clear();
        cmbMemberId.getSelectionModel().clearSelection();
        cmbBookId.getSelectionModel().clearSelection();
        txtBookPrice.clear();
        txtPayment.clear();
        txtNo.requestFocus();
        txtNo.setEditable(true);
        tblLostPayment.getSelectionModel().clearSelection();

}
    public void btnReportOnAction(ActionEvent actionEvent) throws JRException {
        File file = new File("/lk/ijse/cmjd/project/report/LostPayment.jasper");
        JasperReport compiledReport = (JasperReport) loadObject(file);
        JasperPrint filledReport = JasperFillManager
                .fillReport(compiledReport,
                        new HashMap<>(),
                        new JREmptyDataSource()
                );
        JasperViewer.viewReport(filledReport,false);


    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        Alert confirmMsg = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete this detail?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = confirmMsg.showAndWait();

        if (buttonType.get() == ButtonType.YES) {
            int selectedLost = tblLostPayment.getSelectionModel().getSelectedItem().getNumber();

            tblLostPayment.getItems().remove(tblLostPayment.getSelectionModel().getSelectedItem());
            boolean result = ManageLostPaymentBusiness.deleteLostPayment(Integer.toString(selectedLost));
            if (!result) {
                new Alert(Alert.AlertType.ERROR, "Failed to delete the detail", ButtonType.OK).showAndWait();
            } else {
                reset();
            }

        }
    }
    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        if (txtNo.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Number is Empty", ButtonType.OK).showAndWait();
            txtNo.requestFocus();
            return;
        } else if (cmbBookId.getEditor().getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Book Id is Empty", ButtonType.OK).showAndWait();

            return;
        } else if (cmbMemberId.getEditor().getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Member Id is Empty", ButtonType.OK).showAndWait();

            return;
        } else if (txtBookPrice.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Book Price  is Empty", ButtonType.OK).showAndWait();
            txtBookPrice.requestFocus();
            return;
        } else if (txtPayment.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Payment is Empty", ButtonType.OK).showAndWait();
            txtPayment.requestFocus();
            return;
        }
        if (tblLostPayment.getSelectionModel().isEmpty()) {


            ObservableList<LostPaymentTM> items = tblLostPayment.getItems();
            for (LostPaymentTM lostPaymentTM: items) {
                if (Integer.toString(lostPaymentTM.getNumber()).equals(cmbMemberId.getEditor().getText())) {
                    new Alert(Alert.AlertType.ERROR, "Duplicate  Numbers are not allowed").showAndWait();

                    return;
                }
            }

            LostPaymentTM lostPaymentTM=new LostPaymentTM(Integer.parseInt(txtNo.getText()),cmbBookId.getEditor().getText(),cmbMemberId.getEditor().getText(),Double.parseDouble(txtBookPrice.getText()),Double.parseDouble(txtPayment.getText()));
            tblLostPayment.getItems().add(lostPaymentTM);
            LostPaymentDTO lostPaymentDTO=new LostPaymentDTO(Integer.parseInt(txtNo.getText()),cmbBookId.getEditor().getText(),cmbMemberId.getEditor().getText(),Double.parseDouble(txtBookPrice.getText()),Double.parseDouble(txtPayment.getText()));
            boolean result = ManageLostPaymentBusiness.addLostPayment(lostPaymentDTO);

            if (result) {
                new Alert(Alert.AlertType.INFORMATION,
                        "Detail has been saved successfully", ButtonType.OK).showAndWait();
                tblLostPayment.scrollTo(lostPaymentTM);
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save the detail", ButtonType.OK).showAndWait();
            }

        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save the detail", ButtonType.OK).showAndWait();
        }

        reset();
    }
}
