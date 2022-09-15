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
import lk.ijse.cmjd.project.business.ManageBookBusiness;
import lk.ijse.cmjd.project.business.ManageMemberBusiness;
import lk.ijse.cmjd.project.business.ManageRenewBusiness;
import lk.ijse.cmjd.project.dto.RenewDTO;
import lk.ijse.cmjd.project.entity.RenewPK;
import lk.ijse.cmjd.project.view.util.RenewTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static net.sf.jasperreports.engine.util.JRLoader.loadObject;

public class RenewFormController {
    @FXML
    private AnchorPane root;


    @FXML
    private Label lblRenewForm;

    @FXML
    private JFXComboBox<String> cmbMemberId;

    @FXML
    private JFXComboBox cmbBookId;
    @FXML
    private Label lblMemberId;
    @FXML
    private Label lblBookId;


    @FXML
    private JFXTextField txtBookId;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReport;

    @FXML
    private TableView<RenewTM> tblRenew;

    @FXML
    private JFXDatePicker txtDate;

    @FXML
    private JFXDatePicker txtDueDate;
    public RenewFormController() throws SQLException {
        List<String> bookIdList=new ArrayList <>();
        bookIdList= ManageBookBusiness.bookIdList();
        cmbBookId.setItems((ObservableList) bookIdList);
        List<String> memberIdList=new ArrayList<>();
        memberIdList= ManageMemberBusiness.memberIdList();
        cmbMemberId.setItems((ObservableList) memberIdList);
    }


    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/cmjd/project/view/BookTransactionsForm.fxml"));

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
    public void btnReportOnAction(ActionEvent actionEvent) throws JRException {
        File file = new File("/lk/ijse/cmjd/project/report/RenewDetails.jasper");
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
            String memberId = tblRenew.getSelectionModel().getSelectedItem().getMemberId();
            String bookId = tblRenew.getSelectionModel().getSelectedItem().getBookId();
            Date issueDate = java.sql.Date.valueOf(tblRenew.getSelectionModel().getSelectedItem().getDate());
            RenewPK renewPK=new RenewPK(memberId,bookId,issueDate);
            tblRenew.getItems().remove(tblRenew.getSelectionModel().getSelectedItem());
            boolean result = ManageRenewBusiness.deleteRenew(renewPK);
            if (!result) {
                new Alert(Alert.AlertType.ERROR, "Failed to delete the details", ButtonType.OK).showAndWait();
            } else {
                reset();
            }

        }
    }


    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        if (cmbMemberId.getId().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Member Id is Empty", ButtonType.OK).showAndWait();

            return;
        } else if (cmbBookId.getId().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Book Id is Empty", ButtonType.OK).showAndWait();

            return;
        } else if (txtDueDate.getEditor().getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Due Date is Empty", ButtonType.OK).showAndWait();

            return;
        } else if (txtDate.getEditor().getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Date is Empty", ButtonType.OK).showAndWait();

            return;

        }
        if (tblRenew.getSelectionModel().isEmpty()) {


            ObservableList<RenewTM> items = tblRenew.getItems();



            RenewTM renewTM= new RenewTM(cmbMemberId.getId(),cmbBookId.getId(),txtDate.getValue(),txtDueDate.getValue());
            tblRenew.getItems().add(renewTM);
            RenewDTO renewDTO = new RenewDTO(cmbMemberId.getId(),cmbBookId.getId(),txtDate.getValue(),txtDueDate.getValue());
            boolean result = ManageRenewBusiness.addRenew(renewDTO);

            if (result) {
                new Alert(Alert.AlertType.INFORMATION,
                        "Renew details has been saved successfully", ButtonType.OK).showAndWait();
                tblRenew.scrollTo(renewTM);
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save the renew details", ButtonType.OK).showAndWait();
            }

        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save the renew details", ButtonType.OK).showAndWait();
        }

        reset();

    }

    public void initialize() throws SQLException {
        tblRenew.getColumns().get(0).setStyle("-app-alignment:center");
        tblRenew.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("memberId"));
        tblRenew.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("bookId"));
        tblRenew.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("Date"));
        tblRenew.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("dueDate"));



        List<RenewDTO> renewsDB = ManageRenewBusiness.getRenews();
        ObservableList<RenewDTO> renewDTOs = FXCollections.observableArrayList(renewsDB);
        ObservableList<RenewTM> tblItems = FXCollections.observableArrayList();
        for (RenewDTO renewDTO: renewDTOs) {
            tblItems.add(new RenewTM(renewDTO.getMemberId(),renewDTO.getBookId(),renewDTO.getDate(),renewDTO.getDueDate()));
        }
        tblRenew.setItems(tblItems);

        tblRenew.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<RenewTM>() {
            @Override
            public void changed(ObservableValue<? extends RenewTM > observable, RenewTM  oldValue, RenewTM selectedRenew) {

                if (selectedRenew == null) {
                    // Clear Selecti
                    return;
                }
                cmbMemberId.setId(selectedRenew.getMemberId());
                cmbBookId.setId(selectedRenew.getBookId());
                txtDueDate.setAccessibleText(String.valueOf(selectedRenew.getDueDate()));
                txtDate.setAccessibleText(String.valueOf(selectedRenew.getDate()));



            }
        });
    }
    private void reset(){
        cmbMemberId.getSelectionModel().clearSelection();
        cmbBookId.getSelectionModel().clearSelection();
        txtDate.setValue(null);
        txtDueDate.setValue(null);
        tblRenew.getSelectionModel().clearSelection();
    }

}
