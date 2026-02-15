package lk.ijse.gymmembershipmanagementsystem.controller;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.ijse.gymmembershipmanagementsystem.db.DBConnection;
import lk.ijse.gymmembershipmanagementsystem.dto.MemberDTO;
import lk.ijse.gymmembershipmanagementsystem.dto.PaymentDTO;
import lk.ijse.gymmembershipmanagementsystem.model.PaymentModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

public class PaymentController implements Initializable {

    @FXML
    private TextField idField;
    @FXML
    private DatePicker dateCombo;
    @FXML
    private TextField amountField;
    @FXML
    private ComboBox<MemberDTO> memberIDComboBox;
    @FXML
    private TableView tablePayment;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn paymentDateColumn;
    @FXML
    private TableColumn amountColumn;
    @FXML
    private TableColumn memberIDColumn;

    PaymentModel paymentModel =  new PaymentModel();

    private final String AMOUNT_REGEX = "^[0-9]+(\\.[0-9]{1})?$";

    private int selectedMemberID;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        System.out.println("Payment FXML is loaded!");

        loadMemberID();

        memberIDComboBox.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
            if (newVal != null) {
                selectedMemberID = newVal.getMemberID();
                System.out.println("Selected Member ID = " + selectedMemberID);
            }
        });

        loadPaymentTable();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        paymentDateColumn.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        memberIDColumn.setCellValueFactory(new PropertyValueFactory<>("memberId"));

        tablePayment.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
            if (newVal != null) {
                setPaymentDetails((PaymentDTO)  newVal);
            }
        });
    }

    @FXML
    private void save() {
        LocalDate date =  dateCombo.getValue();
        String amount =  amountField.getText().trim();

        if(!amount.matches(AMOUNT_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Payment Management");
            alert.setHeaderText("Invalid payment amount");
            alert.show();
        }  else {
            try {
                PaymentDTO paymentDTO = new PaymentDTO(selectedMemberID, Double.parseDouble(amount), date);
                boolean result = paymentModel.save(paymentDTO);
                if (result) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Payment Management");
                    alert.setHeaderText("Payment saved successfully!");
                    alert.show();
                    cleanFileds();
                    loadPaymentTable();
                    printPaymentBill();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Payment Management");
                    alert.setHeaderText("Something went a wrong!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Update Error: " + e.getMessage());
                alert.show();
            }
        }
    }

    @FXML
    private void update() {
        String id =   idField.getText().trim();
        LocalDate date =  dateCombo.getValue();
        String amount =  amountField.getText().trim();

        if(!amount.matches(AMOUNT_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Payment Management");
            alert.setHeaderText("Invalid payment amount");
            alert.show();
        }   else {
            try {
                PaymentDTO paymentDTO = new PaymentDTO(Integer.parseInt(id), selectedMemberID, Double.parseDouble(amount), date);
                boolean result = paymentModel.update(paymentDTO);
                if (result) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Payment Management");
                    alert.setHeaderText("Payment updated successfully!");
                    alert.show();
                    cleanFileds();
                    loadPaymentTable();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Payment Management");
                    alert.setHeaderText("Something went a wrong!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Update Error: " + e.getMessage());
                alert.show();
            }
        }
    }

    @FXML
    private void delete() {
        String id =   idField.getText().trim();

        try {
            boolean result= paymentModel.delete(id);
            if (result) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Payment Management");
                alert.setHeaderText("Payment deleted successfully!");
                alert.show();
                cleanFileds();
                loadPaymentTable();
            }  else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Payment Management");
                alert.setHeaderText("Something went a wrong!");
                alert.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Update Error: " + e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void reset() {
        cleanFileds();
    }

    private void cleanFileds() {
        idField.setText("");
        dateCombo.setValue(null);
        amountField.setText("");
        memberIDComboBox.setValue(null);
    }

    @FXML
    private void handleSearchPayment(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                String id = idField.getText();

                PaymentDTO paymentDTO = paymentModel.search(id);

                if(paymentDTO!=null) {
                    dateCombo.setValue(paymentDTO.getPaymentDate());
                    amountField.setText(String.valueOf(paymentDTO.getAmount()));
                    for (MemberDTO mem : memberIDComboBox.getItems()) {
                        if (mem.getMemberID() == paymentDTO.getMemberId()) {
                            memberIDComboBox.setValue(mem);
                            break;
                        }
                    }
                } else {
                    cleanFileds();
                    new Alert(Alert.AlertType.ERROR, "Payment not found!").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            }
        }
    }

    @FXML
    private void loadPaymentTable() {
        try {
            List<PaymentDTO> paymentList = paymentModel.getAllPayment();
            ObservableList<PaymentDTO> obList = FXCollections.observableArrayList();

            for (PaymentDTO paymentDTO : paymentList) {
                obList.add(paymentDTO);
            }

            tablePayment.setItems(obList);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    private void loadMemberID(){
        try {
            ObservableList<MemberDTO> idList = paymentModel.loadMemberID();
            memberIDComboBox.setItems(idList);
            memberIDComboBox.setCellFactory(cb -> new ListCell<MemberDTO>() {
                @Override
                protected void updateItem(MemberDTO m, boolean empty) {
                    super.updateItem(m, empty);
                    if (empty || m == null) {
                        setText(null);
                    } else {
                        setText(m.getMemberID() + " - " + m.getName());
                    }
                }
            });
            memberIDComboBox.setButtonCell(new ListCell<MemberDTO>() {
                @Override
                protected void updateItem(MemberDTO m, boolean empty) {
                    super.updateItem(m, empty);
                    if (empty || m == null) {
                        setText(null);
                    } else {
                        setText(m.getMemberID() + " - " + m.getName());
                    }
                }
            });


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setPaymentDetails(PaymentDTO newVal) {
        System.out.println(newVal.getPaymentId());
        for (MemberDTO mem : memberIDComboBox.getItems()) {
            if (mem.getMemberID() == newVal.getMemberId()) {
                memberIDComboBox.setValue(mem);
                break;
            }
        }
        dateCombo.setValue(newVal.getPaymentDate());
        amountField.setText(String.valueOf(newVal.getAmount()));
        idField.setText(String.valueOf(newVal.getPaymentId()));
    }

    public void printPaymentBill() throws Exception {
        Connection conn = DBConnection.getInstance().getConnection();

        InputStream reportObject = getClass().getResourceAsStream("/lk/ijse/GymMembershipManagementSystem/reports/payment_bill.jrxml");

        JasperReport jasperReport = JasperCompileManager.compileReport(reportObject);

        JasperPrint  jasperPrint = JasperFillManager.fillReport(jasperReport, null, conn);

        JasperViewer.viewReport(jasperPrint, false);
    }
}
