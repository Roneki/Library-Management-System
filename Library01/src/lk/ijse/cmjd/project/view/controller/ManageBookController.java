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
import lk.ijse.cmjd.project.business.ManageMemberBusiness;
import lk.ijse.cmjd.project.business.ManagePublisherDetailsBusiness;
import lk.ijse.cmjd.project.dto.BookDTO;
import lk.ijse.cmjd.project.dto.MemberDTO;
import lk.ijse.cmjd.project.view.util.BookTM;
import lk.ijse.cmjd.project.view.util.MemberTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.io.IOException;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static net.sf.jasperreports.engine.util.JRLoader.loadObject;

public class ManageBookController {
    public ManageBookController() throws Exception {
//        List<String> publisherIdList=new ArrayList<>();
        List<String> publisherIdList = ManagePublisherDetailsBusiness.publisherIdList();
//        cmbPublisherId.getItems().clear();
        System.out.println("Working "+publisherIdList);
        for (String id : publisherIdList) {
            System.out.println("working 2 "+id);
            cmbPublisherId.getItems().add(id);
        }
    }

    @FXML
    private AnchorPane root;


    @FXML
    private Label lblManageBookDetails;

    @FXML
    private JFXTextField txtBookId;

    @FXML
    private JFXTextField txtSubject;

    @FXML
    private JFXTextField txtTitle;

    @FXML
    private JFXTextField txtQuantityAvailable;

    @FXML
    private JFXTextField txtFullQuantity;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private JFXTextField txtAuthor;

    @FXML
    private JFXComboBox<String> cmbPublisherId;
    @FXML
    private Label lblPublisherId;

    @FXML
    private Button btnAddNewBook;

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
    private TableView<BookTM> tblBook;


    @FXML
    private Button btnGetPublisherDetails;
    public void initialize() throws SQLException {
        tblBook.getColumns().get(0).setStyle("-app-alignment:center");
        tblBook.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("bookId"));
        tblBook.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("title"));
      tblBook.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("subject"));
        tblBook.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("author"));
        tblBook.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("price"));
        tblBook.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("fullQty"));
        tblBook.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        tblBook.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("publisherId"));


        List<BookDTO> booksDB = ManageBookBusiness.getBooks();
        ObservableList<BookDTO> bookDTOS = FXCollections.observableArrayList(booksDB);
        ObservableList<BookTM> tblItems = FXCollections.observableArrayList();
        for (BookDTO bookDTO : bookDTOS) {
            tblItems.add(new BookTM(bookDTO.getBookId(),bookDTO.getTitle(),bookDTO.getSubject(),bookDTO.getAuthor(),bookDTO.getPrice(),bookDTO.getFullQty(),bookDTO.getQtyOnHand(),bookDTO.getPublisherId()));
        }
        tblBook.setItems(tblItems);

       tblBook.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BookTM>() {
            @Override
            public void changed(ObservableValue<? extends BookTM> observable, BookTM oldValue, BookTM selectedBook) {

                if (selectedBook == null) {
                    // Clear Selection
                    return;
                }

                txtBookId.setText(selectedBook.getBookId());
              txtTitle.setText(selectedBook.getTitle());
                txtSubject.setText(selectedBook.getSubject());
                txtAuthor.setText(selectedBook.getAuthor());
                txtPrice.setText( Double.toString(selectedBook.getPrice()));
                txtFullQuantity.setText(Integer.toString(selectedBook.getFullQty()));
                txtQuantityAvailable.setText(Integer.toString(selectedBook.getQtyOnHand()));
                cmbPublisherId.setAccessibleText(selectedBook.getPublisherId());

                txtBookId.setEditable(false);


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
    void btnAddNewBookOnAction(ActionEvent event) {
reset();
    }
private void reset(){
        txtBookId.clear();
        txtTitle.clear();
        txtSubject.clear();
        txtAuthor.clear();
        txtPrice.clear();
        txtFullQuantity.clear();
        txtQuantityAvailable.clear();
        cmbPublisherId.getSelectionModel().clearSelection();
        txtBookId.requestFocus();
        txtBookId.setEditable(true);
        tblBook.getSelectionModel().clearSelection();
}

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        Alert confirmMsg = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete this book?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = confirmMsg.showAndWait();

        if (buttonType.get() == ButtonType.YES) {
            String selectedBook = tblBook.getSelectionModel().getSelectedItem().getBookId();

            tblBook.getItems().remove(tblBook.getSelectionModel().getSelectedItem());
            boolean result = ManageBookBusiness.deleteBook(selectedBook);
            if (!result) {
                new Alert(Alert.AlertType.ERROR, "Failed to delete the book", ButtonType.OK).showAndWait();
            } else {
                reset();
            }

        }

    }

    @FXML
    void btnGetPublisherDetailsOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/cmjd/project/view/PublisherDetailsForm.fxml"));

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
    void btnReportOnAction(ActionEvent event) throws JRException {
        File file = new File("/lk/ijse/cmjd/project/report/BookDetails.jasper");
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
        if (txtBookId.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Book Id is Empty", ButtonType.OK).showAndWait();
            txtBookId.requestFocus();
            return;
        } else if (txtTitle.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Book Title is Empty", ButtonType.OK).showAndWait();
            txtTitle.requestFocus();
            return;
        } else if (txtSubject.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Subject is Empty", ButtonType.OK).showAndWait();
            txtSubject.requestFocus();
            return;
        } else if (txtAuthor.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Author type is Empty", ButtonType.OK).showAndWait();
            txtAuthor.requestFocus();
            return;
        } else if (txtPrice.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Price is Empty", ButtonType.OK).showAndWait();
            txtPrice.requestFocus();
            return;
        }else if (txtFullQuantity.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Full Quantity is Empty", ButtonType.OK).showAndWait();
            txtFullQuantity.requestFocus();
            return;
        }else if (txtQuantityAvailable.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Quantity Available is Empty", ButtonType.OK).showAndWait();
            txtQuantityAvailable.requestFocus();
            return;
        }else if (cmbPublisherId.getId().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Publisher Id is Empty", ButtonType.OK).showAndWait();
            return;
        }
        if (tblBook.getSelectionModel().isEmpty()) {


            ObservableList<BookTM> items = tblBook.getItems();
            for (BookTM bookTM : items) {
                if (bookTM.getBookId().equals(txtBookId.getText())) {
                    new Alert(Alert.AlertType.ERROR, "Duplicate Duplicate IDs are not allowed").showAndWait();
                    txtBookId.requestFocus();
                    return;
                }
            }

          BookTM bookTM = new BookTM(txtBookId.getText(),txtTitle.getText(),txtSubject.getText(),txtAuthor.getText(),Double.parseDouble(txtPrice.getText()),Integer.parseInt(txtFullQuantity.getText()),Integer.parseInt(txtQuantityAvailable.getText()),cmbPublisherId.getId());
            tblBook.getItems().add(bookTM);
            BookDTO bookDTO = new BookDTO(txtBookId.getText(),txtTitle.getText(),txtSubject.getText(),txtAuthor.getText(),Double.parseDouble(txtPrice.getText()),Integer.parseInt(txtFullQuantity.getText()),Integer.parseInt(txtQuantityAvailable.getText()),cmbPublisherId.getId());

            boolean result = ManageBookBusiness.addBook(bookDTO);

            if (result) {
                new Alert(Alert.AlertType.INFORMATION,
                        "Book Details has been saved successfully", ButtonType.OK).showAndWait();
                tblBook.scrollTo(bookTM);
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save the book details", ButtonType.OK).showAndWait();
            }

        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save the book details", ButtonType.OK).showAndWait();
        }

        reset();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) throws SQLException {
        String bookId =txtBookId.getText();
        BookDTO dto=ManageBookBusiness.searchBook(bookId);
        if (dto!=null){
            txtBookId.requestFocus();
            txtBookId.setEditable(false);
            txtBookId.setText(dto.getBookId());
            txtTitle.setText(dto.getTitle());
            txtSubject.setText(dto.getSubject());
            txtAuthor.setText(dto.getAuthor());
            txtPrice.setText(Double.toString(dto.getPrice()));
            txtFullQuantity.setText(Integer.toString(dto.getFullQty()));
            txtQuantityAvailable.setText(Integer.toString(dto.getQtyOnHand()));
            cmbPublisherId.setId(dto.getPublisherId());

        }else {
            new Alert(Alert.AlertType.ERROR, "NO member found", ButtonType.OK).showAndWait();
        }




    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        if (txtBookId.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Book Id is Empty", ButtonType.OK).showAndWait();
            txtBookId.requestFocus();
            return;
        } else if (txtTitle.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Book Title is Empty", ButtonType.OK).showAndWait();
            txtTitle.requestFocus();
            return;
        } else if (txtSubject.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Subject is Empty", ButtonType.OK).showAndWait();
            txtSubject.requestFocus();
            return;
        } else if (txtAuthor.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Author type is Empty", ButtonType.OK).showAndWait();
            txtAuthor.requestFocus();
            return;
        } else if (txtPrice.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Price is Empty", ButtonType.OK).showAndWait();
            txtPrice.requestFocus();
            return;
        }else if (txtFullQuantity.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Full Quantity is Empty", ButtonType.OK).showAndWait();
            txtFullQuantity.requestFocus();
            return;
        }else if (txtQuantityAvailable.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Quantity Available is Empty", ButtonType.OK).showAndWait();
            txtQuantityAvailable.requestFocus();
            return;
        }else if (cmbPublisherId.getId().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Publisher Id is Empty", ButtonType.OK).showAndWait();
            return;
        }
        boolean result = ManageBookBusiness.updateBook(new BookDTO(txtBookId.getText(),txtTitle.getText(),txtSubject.getText(),txtAuthor.getText(),Double.parseDouble(txtPrice.getText()),Integer.parseInt(txtFullQuantity.getText()),Integer.parseInt(txtQuantityAvailable.getText()),cmbPublisherId.getId()));

        if(result){

            new Alert(Alert.AlertType.INFORMATION, "Book details has been updated successfully", ButtonType.OK).showAndWait();



        } else

        {
            new Alert(Alert.AlertType.ERROR, "Failed to update the book details", ButtonType.OK).showAndWait();
        }

        reset();
    }



}
