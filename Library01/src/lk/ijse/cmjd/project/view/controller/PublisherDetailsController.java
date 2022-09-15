package lk.ijse.cmjd.project.view.controller;

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
import lk.ijse.cmjd.project.business.ManagePublisherDetailsBusiness;
import lk.ijse.cmjd.project.dto.PublisherDetailsDTO;
import lk.ijse.cmjd.project.entity.Publisher;
import lk.ijse.cmjd.project.view.util.PublisherTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static net.sf.jasperreports.engine.util.JRLoader.loadObject;

public class PublisherDetailsController {

    @FXML
    private AnchorPane root;

    @FXML
    private Label lblPublisherDetails;

    @FXML
    private JFXTextField txtPublisherId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtTeleNo;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private Button btnAddNewPubisher;

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
    private TableView<PublisherTM> tblPublisher;
    public void initialize() throws SQLException {
        tblPublisher.getColumns().get(0).setStyle("-app-alignment:center");
        tblPublisher.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("memberId"));
        tblPublisher.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblPublisher.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblPublisher.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("teleNo"));



        List<PublisherDetailsDTO> publishersDB = ManagePublisherDetailsBusiness.getPublishers();
        ObservableList<PublisherDetailsDTO> publisherDTOs = FXCollections.observableArrayList(publishersDB);
        ObservableList<PublisherTM> tblItems = FXCollections.observableArrayList();
        for (PublisherDetailsDTO publisherDetailsDTO:publisherDTOs) {
            tblItems.add(new PublisherTM(publisherDetailsDTO.getId(),publisherDetailsDTO.getName(),publisherDetailsDTO.getAddress(),publisherDetailsDTO.getTeleNo()));
        }
        tblPublisher.setItems(tblItems);

        tblPublisher.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PublisherTM>() {
            @Override
            public void changed(ObservableValue<? extends PublisherTM> observable, PublisherTM oldValue, PublisherTM selectedPublisher) {

                if (selectedPublisher == null) {
                    // Clear Selection
                    return;
                }

                txtPublisherId.setText(selectedPublisher.getId());
                txtName.setText(selectedPublisher.getName());
                txtAddress.setText(selectedPublisher.getAddress());
               txtTeleNo.setText(Integer.toString(selectedPublisher.getTeleNo()));

                txtPublisherId.setEditable(false);


            }
        });
    }


    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/cmjd/project/view/ManageBook.fxml"));

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
    void btnAddNewPublisherOnAction(ActionEvent event) {
reset();
    }
private void reset(){
        txtPublisherId.clear();
        txtName.clear();
        txtAddress.clear();
        txtTeleNo.clear();
        txtPublisherId.requestFocus();
        txtPublisherId.setEditable(true);
        tblPublisher.getSelectionModel().clearSelection();
}
    public void btnReportOnAction(ActionEvent actionEvent) throws JRException {
        File file = new File("/lk/ijse/cmjd/project/report/PublisherDetails.jasper");
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
        Alert confirmMsg = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete this publisher?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = confirmMsg.showAndWait();

        if (buttonType.get() == ButtonType.YES) {
            String selectedPublisher = tblPublisher.getSelectionModel().getSelectedItem().getId();

            tblPublisher.getItems().remove(tblPublisher.getSelectionModel().getSelectedItem());
            boolean result = ManagePublisherDetailsBusiness.deletePublisher(selectedPublisher);
            if (!result) {
                new Alert(Alert.AlertType.ERROR, "Failed to delete the publisher", ButtonType.OK).showAndWait();
            } else {
                reset();
            }

        }
    }
    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        if (txtPublisherId.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Publisher Id is Empty", ButtonType.OK).showAndWait();
            txtPublisherId.requestFocus();
            return;
        } else if (txtName.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Publisher name is Empty", ButtonType.OK).showAndWait();
            txtName.requestFocus();
            return;
        } else if (txtAddress.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Publisher address is Empty", ButtonType.OK).showAndWait();
            txtAddress.requestFocus();
            return;
        } else if (txtTeleNo.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "telephone number is Empty", ButtonType.OK).showAndWait();
            txtTeleNo.requestFocus();
            return;
        }
        if (tblPublisher.getSelectionModel().isEmpty()) {


            ObservableList<PublisherTM> items = tblPublisher.getItems();
            for (PublisherTM publisherTM : items) {
                if (publisherTM.getId().equals(txtPublisherId.getText())) {
                    new Alert(Alert.AlertType.ERROR, "Duplicate publisher IDs are not allowed").showAndWait();
                    txtPublisherId.requestFocus();
                    return;
                }
            }

            PublisherTM publisherTM=new PublisherTM(txtPublisherId.getText(), txtName.getText(), txtAddress.getText(),Integer.parseInt(txtTeleNo.getText()));
            tblPublisher.getItems().add(publisherTM);
            PublisherDetailsDTO publisherDetailsDTO=new PublisherDetailsDTO(txtPublisherId.getText(), txtName.getText(), txtAddress.getText(), Integer.parseInt(txtTeleNo.getText()));
            boolean result = ManagePublisherDetailsBusiness.addPublisher(publisherDetailsDTO);

            if (result) {
                new Alert(Alert.AlertType.INFORMATION,
                        "Publisher has been saved successfully", ButtonType.OK).showAndWait();
                tblPublisher.scrollTo(publisherTM);
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save the publisher", ButtonType.OK).showAndWait();
            }

        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save the publisher", ButtonType.OK).showAndWait();
        }

        reset();
    }
    @FXML
    void btnSearchOnAction(ActionEvent event) throws SQLException {
        String memberId =txtPublisherId.getText();
        PublisherDetailsDTO dto=ManagePublisherDetailsBusiness.searchPublisher(memberId);
        if (dto!=null){
            txtPublisherId.requestFocus();
            txtPublisherId.setEditable(false);
            txtPublisherId.setText(dto.getId());
            txtName.setText(dto.getName());
            txtAddress.setText(dto.getAddress());
           txtTeleNo.setText(Integer.toString(dto.getTeleNo()));


        }else {
            new Alert(Alert.AlertType.ERROR, "NO publisher found", ButtonType.OK).showAndWait();
        }



    }

    @FXML
    public void btnUpdateOnAction(ActionEvent event) throws SQLException {
        if (txtPublisherId.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Publisher Id is Empty", ButtonType.OK).showAndWait();
            txtPublisherId.requestFocus();
            return;
        } else if (txtName.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Publisher name is Empty", ButtonType.OK).showAndWait();
            txtName.requestFocus();
            return;
        } else if (txtAddress.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Publisher address is Empty", ButtonType.OK).showAndWait();
            txtAddress.requestFocus();
            return;
        } else if (txtTeleNo.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Telephone number is Empty", ButtonType.OK).showAndWait();
            txtTeleNo.requestFocus();
            return;
        }



        boolean result = ManagePublisherDetailsBusiness.updatePublisher(new PublisherDetailsDTO(txtPublisherId.getText(), txtName.getText(), txtAddress.getText(), Integer.parseInt(txtTeleNo.getText())));

        if(result){

            new Alert(Alert.AlertType.INFORMATION, "Publisher has been updated successfully", ButtonType.OK).showAndWait();



        } else

        {
            new Alert(Alert.AlertType.ERROR, "Failed to update the publisher", ButtonType.OK).showAndWait();
        }

        reset();
    }


}
