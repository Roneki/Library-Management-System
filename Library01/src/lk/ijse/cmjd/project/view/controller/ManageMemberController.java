package lk.ijse.cmjd.project.view.controller;

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
import lk.ijse.cmjd.project.dto.MemberDTO;
import lk.ijse.cmjd.project.entity.Member;
import lk.ijse.cmjd.project.view.util.MemberTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.commons.collections.functors.FalsePredicate;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import static net.sf.jasperreports.engine.util.JRLoader.loadObject;


public class ManageMemberController {

    @FXML
    private AnchorPane root;

    @FXML
    private Label lblManageMemberDetails;

    @FXML
    private JFXTextField txtMemberId;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtMemberType;

    @FXML
    private Button btnAddNewMember;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReport;

    @FXML
    private TableView<MemberTM> tblMember;

    @FXML
    private JFXDatePicker txtMembershipDate;

    @FXML
    private Button btnBack;

    public ManageMemberController() throws SQLException {
    }

    public void initialize() throws SQLException {
        tblMember.getColumns().get(0).setStyle("-app-alignment:center");
        tblMember.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("memberId"));
        tblMember.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblMember.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblMember.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("memberType"));
        tblMember.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("membershipDate"));


        List<MemberDTO> membersDB = ManageMemberBusiness.getMembers();
        ObservableList<MemberDTO> memberDTOs = FXCollections.observableArrayList(membersDB);
        ObservableList<MemberTM> tblItems = FXCollections.observableArrayList();
        for (MemberDTO memberDTO : memberDTOs) {
            tblItems.add(new MemberTM(memberDTO.getMemberId(), memberDTO.getName(), memberDTO.getAddress(), memberDTO.getMemberType(), memberDTO.getMembershipDate()));
        }
        tblMember.setItems(tblItems);

        tblMember.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MemberTM>() {
            @Override
            public void changed(ObservableValue<? extends MemberTM> observable, MemberTM oldValue, MemberTM selectedMember) {

                if (selectedMember == null) {
                    // Clear Selection
                    return;
                }

                txtMemberId.setText(selectedMember.getMemberId());
                txtName.setText(selectedMember.getName());
                txtAddress.setText(selectedMember.getAddress());
                txtMemberType.setText(selectedMember.getMemberType());
                txtMembershipDate.setAccessibleText(String.valueOf(selectedMember.getMembershipDate()));
                txtMemberId.setEditable(false);


            }
        });
    }


    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/cmjd/project/view/MainForm.fxml"));

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
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        Alert confirmMsg = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete this member?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = confirmMsg.showAndWait();

        if (buttonType.get() == ButtonType.YES) {
            String selectedMember = tblMember.getSelectionModel().getSelectedItem().getMemberId();

            tblMember.getItems().remove(tblMember.getSelectionModel().getSelectedItem());
            boolean result = ManageMemberBusiness.deleteMember(selectedMember);
            if (!result) {
                new Alert(Alert.AlertType.ERROR, "Failed to delete the customer", ButtonType.OK).showAndWait();
            } else {
                reset();
            }

        }
    }

    @FXML
    void btnReportOnAction(ActionEvent event) throws SQLException, JRException {
        File file = new File("report/MemberDetails.jasper");
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
        if (txtMemberId.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Member Id is Empty", ButtonType.OK).showAndWait();
            txtMemberId.requestFocus();
            return;
        } else if (txtName.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Member name is Empty", ButtonType.OK).showAndWait();
            txtName.requestFocus();
            return;
        } else if (txtAddress.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Member address is Empty", ButtonType.OK).showAndWait();
            txtAddress.requestFocus();
            return;
        } else if (txtMemberType.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Member type is Empty", ButtonType.OK).showAndWait();
            txtMemberType.requestFocus();
            return;
        } else if (txtMembershipDate.getEditor().getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Membership Date is Empty", ButtonType.OK).showAndWait();
            return;
        }
        if (tblMember.getSelectionModel().isEmpty()) {


            ObservableList<MemberTM> items = tblMember.getItems();
            for (MemberTM memberTM : items) {
                if (memberTM.getMemberId().equals(txtMemberId.getText())) {
                    new Alert(Alert.AlertType.ERROR, "Duplicate Member IDs are not allowed").showAndWait();
                    txtMemberId.requestFocus();
                    return;
                }
            }

            MemberTM memberTM = new MemberTM(txtMemberId.getText(), txtName.getText(), txtAddress.getText(), txtMemberType.getText(), txtMembershipDate.getValue());
            tblMember.getItems().add(memberTM);
            MemberDTO memberDTO = new MemberDTO(txtMemberId.getText(), txtName.getText(), txtAddress.getText(), txtMemberType.getText(), txtMembershipDate.getValue());
            boolean result = ManageMemberBusiness.addMember(memberDTO);

            if (result) {
                new Alert(Alert.AlertType.INFORMATION,
                        "Member has been saved successfully", ButtonType.OK).showAndWait();
                tblMember.scrollTo(memberTM);
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save the member", ButtonType.OK).showAndWait();
            }

        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save the member", ButtonType.OK).showAndWait();
        }

        reset();
    }


    @FXML
    void btnSearchOnAction(ActionEvent event) throws SQLException {
        String memberId =txtMemberId.getText();
        MemberDTO dto=ManageMemberBusiness.searchMember(memberId);
        if (dto!=null){
            txtMemberId.requestFocus();
            txtMemberId.setEditable(false);
            txtMemberId.setText(dto.getMemberId());
            txtName.setText(dto.getName());
            txtAddress.setText(dto.getAddress());
            txtMemberType.setText(dto.getMemberType());
            txtMembershipDate.setValue(dto.getMembershipDate());

        }else {
            new Alert(Alert.AlertType.ERROR, "NO member found", ButtonType.OK).showAndWait();
        }



    }

    @FXML
    public void btnUpdateOnAction(ActionEvent event) throws SQLException {
        if (txtMemberId.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Member Id is Empty", ButtonType.OK).showAndWait();
            txtMemberId.requestFocus();
            return;
        } else if (txtName.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Member name is Empty", ButtonType.OK).showAndWait();
            txtName.requestFocus();
            return;
        } else if (txtAddress.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Member address is Empty", ButtonType.OK).showAndWait();
            txtAddress.requestFocus();
            return;
        } else if (txtMemberType.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Member type is Empty", ButtonType.OK).showAndWait();
            txtMemberType.requestFocus();
            return;
        } else if (txtMembershipDate.getEditor().getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Membership Date is Empty", ButtonType.OK).showAndWait();
            return;
        }







    boolean result = ManageMemberBusiness.updateMember(new MemberDTO(txtMemberId.getText(), txtName.getText(), txtAddress.getText(), txtMemberType.getText(), txtMembershipDate.getValue()));

   if(result){

        new Alert(Alert.AlertType.INFORMATION, "Member has been updated successfully", ButtonType.OK).showAndWait();



    } else

    {
        new Alert(Alert.AlertType.ERROR, "Failed to update the member", ButtonType.OK).showAndWait();
    }

    reset();
}

@FXML

public void btnAddNewMemberOnAction(ActionEvent actionEvent) {
    reset();
}
    private void reset(){
        txtMemberId.clear();
        txtName.clear();
        txtAddress.clear();
        txtMemberType.clear();
        txtMembershipDate.setValue(null);
        txtMemberId.requestFocus();
        txtMemberId.setEditable(true);
        tblMember.getSelectionModel().clearSelection();
    }

    public void txtMemberIdOnAction(ActionEvent actionEvent) {


    }
}

