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
import lk.ijse.cmjd.project.business.ManageBorrowBusiness;
import lk.ijse.cmjd.project.business.ManageMemberBusiness;
import lk.ijse.cmjd.project.dto.BorrowDTO;
import lk.ijse.cmjd.project.dto.MemberDTO;
import lk.ijse.cmjd.project.entity.BorrowPK;
import lk.ijse.cmjd.project.view.util.BookTM;
import lk.ijse.cmjd.project.view.util.BorrowTM;
import lk.ijse.cmjd.project.view.util.MemberTM;
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

public class BorrowFormController {
    @FXML
    private AnchorPane root;


    @FXML
    private Label lblBorrowingForm;

    @FXML
    private JFXComboBox cmbMemberId;
    @FXML
    private Label lblMemberId;
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
    private TableView<BorrowTM> tblBorrow;

    @FXML
    private JFXDatePicker txtIssuedDate;

    @FXML
    private JFXDatePicker txtDueDate;
    public BorrowFormController() throws SQLException {
//        List<String> bookIdList=new ArrayList<>();
//        bookIdList= ManageBookBusiness.bookIdList();
//        cmbBookId.setItems((ObservableList) bookIdList);
//
//        List<String> memberIdList=new ArrayList<>();
//        memberIdList= ManageMemberBusiness.memberIdList();
//        cmbMemberId.setItems((ObservableList) memberIdList);
    }

    public void initialize() throws SQLException {
        tblBorrow.getColumns().get(0).setStyle("-app-alignment:center");
        tblBorrow.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("memberId"));
        tblBorrow.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("bookId"));
        tblBorrow.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        tblBorrow.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("dueDate"));



        List<BorrowDTO> borrowsDB = ManageBorrowBusiness.getBorrows();
        ObservableList<BorrowDTO> borrowDTOS = FXCollections.observableArrayList(borrowsDB);
        ObservableList<BorrowTM> tblItems = FXCollections.observableArrayList();
        for (BorrowDTO borrowDTO : borrowDTOS) {
            tblItems.add(new BorrowTM(borrowDTO.getMemberId(),borrowDTO.getBookId(),borrowDTO.getIssueDate(),borrowDTO.getDueDate()));
        }
        tblBorrow.setItems(tblItems);

        tblBorrow.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BorrowTM>() {
            @Override
            public void changed(ObservableValue<? extends BorrowTM> observable, BorrowTM oldValue, BorrowTM selectedBorrow) {

                if (selectedBorrow == null) {
                    // Clear Selecti
                    return;
                }
                cmbMemberId.setId(selectedBorrow.getMemberId());
                cmbBookId.setId(selectedBorrow.getBookId());
                txtDueDate.setAccessibleText(String.valueOf(selectedBorrow.getDueDate()));
                txtIssuedDate.setAccessibleText(String.valueOf(selectedBorrow.getIssueDate()));



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
        txtIssuedDate.setValue(null);
        txtDueDate.setValue(null);
        tblBorrow.getSelectionModel().clearSelection();
}

    public void btnReportOnAction(ActionEvent actionEvent) throws JRException {
        File file = new File("/lk/ijse/cmjd/project/report/BorrowDetails.jasper");
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
           String memberId = tblBorrow.getSelectionModel().getSelectedItem().getMemberId();
            String bookId = tblBorrow.getSelectionModel().getSelectedItem().getBookId();
          Date issueDate = java.sql.Date.valueOf(tblBorrow.getSelectionModel().getSelectedItem().getIssueDate());
          BorrowPK borrowPK=new BorrowPK(memberId,bookId,issueDate);
          tblBorrow.getItems().remove(tblBorrow.getSelectionModel().getSelectedItem());
            boolean result = ManageBorrowBusiness.deleteBorrow(borrowPK);
            if (!result) {
                new Alert(Alert.AlertType.ERROR, "Failed to delete the details", ButtonType.OK).showAndWait();
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
        } else if (cmbBookId.getEditor().getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Book Id is Empty", ButtonType.OK).showAndWait();

            return;
        } else if (txtDueDate.getEditor().getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Due Date is Empty", ButtonType.OK).showAndWait();

            return;
        } else if (txtIssuedDate.getEditor().getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Issued date is Empty", ButtonType.OK).showAndWait();

            return;

        }
        if (tblBorrow.getSelectionModel().isEmpty()) {


            ObservableList<BorrowTM> items = tblBorrow.getItems();



         BorrowTM borrowTM= new BorrowTM(cmbMemberId.getId(),cmbBookId.getId(),txtIssuedDate.getValue(),txtDueDate.getValue());
            tblBorrow.getItems().add(borrowTM);
            BorrowDTO borrowDTO = new BorrowDTO(cmbMemberId.getId(),cmbBookId.getId(),txtIssuedDate.getValue(),txtDueDate.getValue());
            boolean result = ManageBorrowBusiness.addBorrow(borrowDTO);

            if (result) {
                new Alert(Alert.AlertType.INFORMATION,
                        "Borrow details has been saved successfully", ButtonType.OK).showAndWait();
                tblBorrow.scrollTo(borrowTM);
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save the borrow details", ButtonType.OK).showAndWait();
            }

        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save the borrow details", ButtonType.OK).showAndWait();
        }

        reset();

    }
}
