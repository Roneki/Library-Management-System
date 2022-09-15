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
import lk.ijse.cmjd.project.business.ManageReturnBusiness;
import lk.ijse.cmjd.project.dto.ReturnDTO;
import lk.ijse.cmjd.project.entity.ReturnPK;
import lk.ijse.cmjd.project.view.util.ReturnTM;
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

public class ReturnFormController {
    @FXML
    private AnchorPane root;


    @FXML
    private Label lblReturnForm;

    @FXML
    private JFXComboBox<?> cmbMemberId;

    @FXML
    private Label lblMemberId;

    @FXML
    private Label lblBookId;

    @FXML
    private JFXComboBox<?> cmbBookId;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReport;

    @FXML
    private TableView<ReturnTM> tblReturn;

    @FXML
    private JFXTextField txtFineNeededOrNot;

    @FXML
    private JFXDatePicker txtReturnDate;

    @FXML
    private JFXDatePicker txtDueDate;
    public ReturnFormController() throws SQLException {
        List<String> bookIdList=new ArrayList<>();
        bookIdList= ManageBookBusiness.bookIdList();
        cmbBookId.setItems((ObservableList) bookIdList);

        List<String> memberIdList=new ArrayList<>();
        memberIdList= ManageMemberBusiness.memberIdList();
        cmbMemberId.setItems((ObservableList) memberIdList);
    }

    public void initialize() throws SQLException {
       tblReturn.getColumns().get(0).setStyle("-app-alignment:center");
        tblReturn.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("memberId"));
        tblReturn.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("bookId"));
        tblReturn.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        tblReturn.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        tblReturn.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("fineNeeded"));


        List<ReturnDTO> returnsDB = ManageReturnBusiness.getReturns();
        ObservableList<ReturnDTO> returnDTOs = FXCollections.observableArrayList(returnsDB);
        ObservableList<ReturnTM> tblItems = FXCollections.observableArrayList();
        for (ReturnDTO returnDTO : returnDTOs) {
            tblItems.add(new ReturnTM(returnDTO.getMemberId(),returnDTO.getBookId(),returnDTO.getDueDate(),returnDTO.getReturnDate(),returnDTO.isFineNeeded()));
        }
        tblReturn.setItems(tblItems);

        tblReturn.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ReturnTM>() {
            @Override
            public void changed(ObservableValue<? extends ReturnTM> observable, ReturnTM oldValue, ReturnTM selectedReturn) {

                if (selectedReturn == null) {
                    // Clear Selecti
                    return;
                }
                cmbMemberId.setId(selectedReturn.getMemberId());
                cmbBookId.setId(selectedReturn.getBookId());
                txtDueDate.setAccessibleText(String.valueOf(selectedReturn.getDueDate()));
                txtReturnDate.setAccessibleText(String.valueOf(selectedReturn.getReturnDate()));



            }
        });
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
private void reset(){
cmbMemberId.getSelectionModel().clearSelection();
cmbBookId.getSelectionModel().clearSelection();
txtDueDate.setValue(null);
txtReturnDate.setValue(null);
txtFineNeededOrNot.clear();
btnSave.setDisable(false);
btnBack.setDisable(false);
btnDelete.setDisable(true);
btnReport.setDisable(true);
tblReturn.getSelectionModel().clearSelection();

}
    public void btnReportOnAction(ActionEvent actionEvent) throws JRException {
        File file = new File("/lk/ijse/cmjd/project/report/ReturnDetails.jasper");
        JasperReport compiledReport = (JasperReport) loadObject(file);
        JasperPrint filledReport = JasperFillManager
                .fillReport(compiledReport,
                        new HashMap<>(),
                        new JREmptyDataSource()
                );
        JasperViewer.viewReport(filledReport,false);


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
        } else if (txtReturnDate.getEditor().getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, " Return Date is Empty", ButtonType.OK).showAndWait();

            return;

        }
        if (tblReturn.getSelectionModel().isEmpty()) {


            ObservableList<ReturnTM> items = tblReturn.getItems();



            ReturnTM returnTM= new ReturnTM(cmbMemberId.getId(),cmbBookId.getId(),txtDueDate.getValue(),txtReturnDate.getValue(),Boolean.getBoolean(txtFineNeededOrNot.getText()));
            tblReturn.getItems().add(returnTM);
            ReturnDTO returnDTO = new ReturnDTO(cmbMemberId.getId(),cmbBookId.getId(),txtDueDate.getValue(),txtReturnDate.getValue(),Boolean.getBoolean(txtFineNeededOrNot.getText()));
            boolean result = ManageReturnBusiness.addReturn(returnDTO);

            if (result) {
                new Alert(Alert.AlertType.INFORMATION,
                        "Return details has been saved successfully", ButtonType.OK).showAndWait();
                tblReturn.scrollTo(returnTM);
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save the return details", ButtonType.OK).showAndWait();
            }

        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save the return details", ButtonType.OK).showAndWait();
        }

        reset();

    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        Alert confirmMsg = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete this detail?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = confirmMsg.showAndWait();

        if (buttonType.get() == ButtonType.YES) {
            String memberId = tblReturn.getSelectionModel().getSelectedItem().getMemberId();
            String bookId = tblReturn.getSelectionModel().getSelectedItem().getBookId();
            Date dueDate = java.sql.Date.valueOf(tblReturn.getSelectionModel().getSelectedItem().getDueDate());
            ReturnPK returnPK=new ReturnPK(memberId,bookId,dueDate);
            tblReturn.getItems().remove(tblReturn.getSelectionModel().getSelectedItem());
            boolean result = ManageReturnBusiness.deleteReturn(returnPK);
            if (!result) {
                new Alert(Alert.AlertType.ERROR, "Failed to delete the details", ButtonType.OK).showAndWait();
            } else {
                reset();
            }

        }
    }



}
