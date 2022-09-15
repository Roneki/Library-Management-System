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
import lk.ijse.cmjd.project.business.ManageFinePaymentBusiness;
import lk.ijse.cmjd.project.business.ManageMemberBusiness;
import lk.ijse.cmjd.project.dto.FinePaymentDTO;
import lk.ijse.cmjd.project.view.util.FinePaymentTM;
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

public class FinePaymentFormController {
    @FXML
    private AnchorPane root;


    @FXML
    private Label lblFinePayment;

    @FXML
    private JFXTextField txtFineNo;

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
    private TableView<FinePaymentTM> tblFinePayment;

    @FXML
    private JFXComboBox cmbMemberId;
    @FXML
    private Label lblMemberId;

    @FXML
    private JFXTextField txtAmount;

    @FXML
    private JFXTextField txtNoOfDaysPassed;
    public FinePaymentFormController() throws SQLException {
        List<String> bookIdList=new ArrayList<>();
        bookIdList= ManageBookBusiness.bookIdList();
        cmbBookId.setItems((ObservableList) bookIdList);

        List<String> memberIdList=new ArrayList<>();
        memberIdList= ManageMemberBusiness.memberIdList();
        cmbMemberId.setItems((ObservableList) memberIdList);
    }

    public void initialize() throws SQLException {
        tblFinePayment.getColumns().get(0).setStyle("-app-alignment:center");
        tblFinePayment.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("fineNO"));
        tblFinePayment.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("bookId"));
        tblFinePayment.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("memberId"));
        tblFinePayment.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("noOfDaysPassed"));
        tblFinePayment.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("amount"));


        List<FinePaymentDTO> finePaymentsDB = ManageFinePaymentBusiness.getFinePaymentDTOList();
        ObservableList<FinePaymentDTO> finePaymentDTOS = FXCollections.observableArrayList(finePaymentsDB);
        ObservableList<FinePaymentTM> tblItems = FXCollections.observableArrayList();
        for (FinePaymentDTO finePaymentDTO: finePaymentDTOS) {
            tblItems.add(new FinePaymentTM(finePaymentDTO.getFineNo(),finePaymentDTO.getBookId(),finePaymentDTO.getMemberId(),finePaymentDTO.getNoOfDaysPassed(),finePaymentDTO.getAmount()));
        }
        tblFinePayment.setItems(tblItems);

        tblFinePayment.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<FinePaymentTM>() {
            @Override
            public void changed(ObservableValue<? extends FinePaymentTM> observable, FinePaymentTM oldValue, FinePaymentTM selectedFine) {

                if (selectedFine == null) {
                    // Clear Selection
                    return;
                }
                 txtFineNo.setText(Integer.toString(selectedFine.getFineNo()));
                cmbBookId.setId(selectedFine.getBookId());
                cmbMemberId.setId(selectedFine.getMemberId());
                txtNoOfDaysPassed.setText(Integer.toString(selectedFine.getNoOfDaysPassed()));
                txtAmount.setText(Double.toString(selectedFine.getAmount()));




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
txtFineNo.clear();
cmbMemberId.getSelectionModel().clearSelection();
cmbBookId.getSelectionModel().clearSelection();
txtNoOfDaysPassed.clear();
txtAmount.clear();
txtFineNo.requestFocus();
txtFineNo.setEditable(true);
tblFinePayment.getSelectionModel().clearSelection();
}
    public void btnReportOnAction(ActionEvent actionEvent) throws JRException {
        File file = new File("/lk/ijse/cmjd/project/report/FinePayment.jasper");
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
            int selectedFine = tblFinePayment.getSelectionModel().getSelectedItem().getFineNo();

            tblFinePayment.getItems().remove(tblFinePayment.getSelectionModel().getSelectedItem());
            boolean result = ManageFinePaymentBusiness.deleteFinePayment(Integer.toString(selectedFine));
            if (!result) {
                new Alert(Alert.AlertType.ERROR, "Failed to delete the detail", ButtonType.OK).showAndWait();
            } else {
                reset();
            }

        }
    }
    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        if (txtFineNo.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Fine No is Empty", ButtonType.OK).showAndWait();
            txtFineNo.requestFocus();
            return;
        } else if (cmbBookId.getEditor().getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Book Id is Empty", ButtonType.OK).showAndWait();

            return;
        } else if (cmbMemberId.getEditor().getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Member Id is Empty", ButtonType.OK).showAndWait();

            return;
        } else if (txtNoOfDaysPassed.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "No of days passed is Empty", ButtonType.OK).showAndWait();
            txtNoOfDaysPassed.requestFocus();
            return;
        } else if (txtAmount.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Fine amount is Empty", ButtonType.OK).showAndWait();
            txtAmount.requestFocus();
            return;
        }
        if (tblFinePayment.getSelectionModel().isEmpty()) {


            ObservableList<FinePaymentTM> items = tblFinePayment.getItems();
            for (FinePaymentTM  finePaymentTM: items) {
                if (Integer.toString(finePaymentTM.getFineNo()).equals(cmbMemberId.getEditor().getText())) {
                    new Alert(Alert.AlertType.ERROR, "Duplicate Fine Numbers are not allowed").showAndWait();

                    return;
                }
            }

            FinePaymentTM finePaymentTM=new FinePaymentTM(Integer.parseInt(txtFineNo.getText()),cmbBookId.getEditor().getText(),cmbMemberId.getEditor().getText(),Integer.parseInt(txtNoOfDaysPassed.getText()),Double.parseDouble(txtAmount.getText()));
            tblFinePayment.getItems().add(finePaymentTM);
          FinePaymentDTO finePaymentDTO=new FinePaymentDTO(Integer.parseInt(txtFineNo.getText()),cmbBookId.getEditor().getText(),cmbMemberId.getEditor().getText(),Integer.parseInt(txtNoOfDaysPassed.getText()),Double.parseDouble(txtAmount.getText()));
            boolean result = ManageFinePaymentBusiness.addFinePayment(finePaymentDTO);

            if (result) {
                new Alert(Alert.AlertType.INFORMATION,
                        "Detail has been saved successfully", ButtonType.OK).showAndWait();
                tblFinePayment.scrollTo(finePaymentTM);
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save the detail", ButtonType.OK).showAndWait();
            }

        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save the detail", ButtonType.OK).showAndWait();
        }

        reset();
    }


}





