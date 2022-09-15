package lk.ijse.cmjd.project.view.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
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
import lk.ijse.cmjd.project.business.ManageMemberBusiness;
import lk.ijse.cmjd.project.business.ManageMembershipFeeBusiness;
import lk.ijse.cmjd.project.dto.MembershipFeeDTO;
import lk.ijse.cmjd.project.view.util.MembershipFeeTM;
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

public class MembershipFeeFormController {
    @FXML
    private AnchorPane root;


    @FXML
    private Label lblMembershipFeePayment;

    @FXML
    private JFXComboBox cmbMemberId;



    @FXML
    private Label lblMemberId;

    @FXML
    private JFXTextField txtYearlyAmount;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReport;

    @FXML
    private TableView<MembershipFeeTM> tblMembershipFee;

    @FXML
    private JFXDatePicker txtDateOfPayment;
    public MembershipFeeFormController() throws SQLException
    {

        List<String> memberIdList=new ArrayList<>();
        memberIdList= ManageMemberBusiness.memberIdList();
        cmbMemberId.setItems((ObservableList) memberIdList);
    }

    public void initialize() throws SQLException {
        tblMembershipFee.getColumns().get(0).setStyle("-app-alignment:center");
        tblMembershipFee.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("memberId"));
        tblMembershipFee.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("yearlyAmount"));
        tblMembershipFee.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("dateOfPayment"));

        List<MembershipFeeDTO> membershipDB = ManageMembershipFeeBusiness.getMembershipFees();
        ObservableList<MembershipFeeDTO> memberDTOs = FXCollections.observableArrayList(membershipDB);
        ObservableList<MembershipFeeTM> tblItems = FXCollections.observableArrayList();
        for (MembershipFeeDTO membershipFeeDTO:memberDTOs) {
            tblItems.add(new MembershipFeeTM(membershipFeeDTO.getMemberId(),membershipFeeDTO.getYearlyAmount(),membershipFeeDTO.getDateOfPayment()));
        }
         tblMembershipFee.setItems(tblItems);

        tblMembershipFee.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MembershipFeeTM>() {
            @Override
            public void changed(ObservableValue<? extends MembershipFeeTM> observable, MembershipFeeTM oldValue, MembershipFeeTM selectedMemberFee) {

                if (selectedMemberFee == null) {
                    // Clear Selection
                    return;
                }

                cmbMemberId.setId(selectedMemberFee.getMemberId());
             txtYearlyAmount.setText(Double.toString(selectedMemberFee.getYearlyAmount()));
                txtDateOfPayment.setAccessibleText(String.valueOf(selectedMemberFee.getDateOfPayment()));



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
      cmbMemberId.getSelectionModel().clearSelection();
        txtYearlyAmount.clear();
        txtDateOfPayment.setValue(null);
        tblMembershipFee.getSelectionModel().clearSelection();
}
    public void btnReportOnAction(ActionEvent actionEvent) throws JRException {
        File file = new File("/lk/ijse/cmjd/project/report/MembershipFee.jasper");
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
            String selectedMembership= tblMembershipFee.getSelectionModel().getSelectedItem().getMemberId();

            tblMembershipFee.getItems().remove(tblMembershipFee.getSelectionModel().getSelectedItem());
            boolean result = ManageMembershipFeeBusiness.deleteMembershipFee(Integer.toString(Integer.parseInt(selectedMembership)));
            if (!result) {
                new Alert(Alert.AlertType.ERROR, "Failed to delete the detail", ButtonType.OK).showAndWait();
            } else {
                reset();
            }

        }
        }
        @FXML
        void btnSaveOnAction(ActionEvent event) throws SQLException {


             if (cmbMemberId.getEditor().getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Member Id is Empty", ButtonType.OK).showAndWait();

                return;
            } else if (txtYearlyAmount.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Yearly Amount  is Empty", ButtonType.OK).showAndWait();
                txtYearlyAmount.requestFocus();
                return;
            } else if (txtDateOfPayment.getEditor().getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Payment date is Empty", ButtonType.OK).showAndWait();

                return;
            }
            if (tblMembershipFee.getSelectionModel().isEmpty()) {


            }}

    }
