package lk.ijse.gymmembershipmanagementsystem.controller;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.ijse.gymmembershipmanagementsystem.dto.*;
import lk.ijse.gymmembershipmanagementsystem.model.PrivateSessionModel;

public class PrivateSessionController implements Initializable {

    @FXML
    private TextField privateSessionField;
    @FXML
    private TextField timeInField;
    @FXML
    private TextField timeOutField;
    @FXML
    private TextField extraChargesField;
    @FXML
    private ComboBox<MemberDTO> memberIDComboBox;
    @FXML
    private TableView tablePrivateSession;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn memberNameColumn;
    @FXML
    private TableColumn timeInColumn;
    @FXML
    private TableColumn timeOutColumn;
    @FXML
    private TableColumn extraChargesColumn;

    PrivateSessionModel privateSessionModel =  new PrivateSessionModel();

    private final String TIME_IN_REGEX = "^(?:[01]\\d|2[0-3]):(?:[0-5]\\d):(?:[0-5]\\d)$";
    private final String TIME_OUT_REGEX = "^(?:[01]\\d|2[0-3]):(?:[0-5]\\d):(?:[0-5]\\d)$";
    private final String EXTRA_CHARGES_REGEX = "^[0-9]+(\\.[0-9]{1})?$";

    private int selectMemberID;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        loadMemberID();

        System.out.println("Private Session FXML is loaded!");

        memberIDComboBox.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
            if (newVal != null) {
                selectMemberID = newVal.getMemberID();
                System.out.println("Selected Member ID = " + selectMemberID);
            }
        });

        loadPrivateSessionTable();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("privateSessionId"));
        memberNameColumn.setCellValueFactory(new PropertyValueFactory<>("memberName"));
        timeInColumn.setCellValueFactory(new PropertyValueFactory<>("timeIn"));
        timeOutColumn.setCellValueFactory(new PropertyValueFactory<>("timeOut"));
        extraChargesColumn.setCellValueFactory(new PropertyValueFactory<>("extraCharges"));

         tablePrivateSession.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
             if (newVal != null) {
                 setPrivateSessionDetails((PrivateSessionDTO)  newVal);
             }
         });
    }


    @FXML
    private void save() {
        String timeIn = timeInField.getText().trim();
        String timeOut = timeOutField.getText().trim();
        String extraCharges = extraChargesField.getText().trim();

        if(!timeIn.matches(TIME_IN_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Private Session Management");
            alert.setHeaderText("Invalid private session time in");
            alert.show();
        } else if (!timeOut.matches(TIME_OUT_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Private Session Management");
            alert.setHeaderText("Invalid private session time out");
        } else if (!extraCharges.matches(EXTRA_CHARGES_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Private Session Management");
            alert.setHeaderText("Invalid extra charges");
        } else {
            try {
                LocalDate date = java.time.LocalDate.now();
                PrivateSessionDTO privateSessionDTO = new PrivateSessionDTO(selectMemberID, timeIn, timeOut, Double.parseDouble(extraCharges));
                PaymentDTO paymentDTO = new PaymentDTO(selectMemberID, Double.parseDouble(extraCharges), date);
                boolean result = privateSessionModel.saveWithPayment(privateSessionDTO, paymentDTO);
                if (result) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Private Session Management");
                    alert.setHeaderText("Private Session saved successfully!");
                    alert.show();
                    cleanFileds();
                    loadPrivateSessionTable();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Private Session Management");
                    alert.setHeaderText("Something went a wrong!");
                    alert.show();
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
        String id = privateSessionField.getText().trim();
        String timeIn = timeInField.getText().trim();
        String timeOut = timeOutField.getText().trim();
        String extraCharges = extraChargesField.getText().trim();

        if(!timeIn.matches(TIME_IN_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Private Session Management");
            alert.setHeaderText("Invalid private session time in");
            alert.show();
        }  else if (!timeOut.matches(TIME_OUT_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Private Session Management");
            alert.setHeaderText("Invalid private session time out");
            alert.show();
        }   else if (!extraCharges.matches(EXTRA_CHARGES_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Private Session Management");
            alert.setHeaderText("Invalid extra charges");
            alert.show();
        } else {
            try {
                PrivateSessionDTO privateSessionDTO = new PrivateSessionDTO(Integer.parseInt(id), selectMemberID, timeIn, timeOut, Double.parseDouble(extraCharges));
                boolean result = privateSessionModel.update(privateSessionDTO);
                if (result) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Private Session Update");
                    alert.setHeaderText("Private Session updated successfully!");
                    alert.show();
                    cleanFileds();
                    loadPrivateSessionTable();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Private Session Update ");
                    alert.setHeaderText("Session not found!");
                    alert.show();
                }
            } catch(Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Update Error: " + e.getMessage());
                alert.show();
            }
        }
    }

    @FXML
    private void delete() {
        String id = privateSessionField.getText().trim();

        try {
            boolean result = privateSessionModel.delete(id);
            if (result) {
                new Alert(Alert.AlertType.INFORMATION, "Private Session deleted successfully!").show();
                cleanFileds();
                loadPrivateSessionTable();
            } else {
                new Alert(Alert.AlertType.WARNING, "Private Session not found!").show();
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
        privateSessionField.setText("");
        timeInField.setText("");
        timeOutField.setText("");
        extraChargesField.setText("");
        memberIDComboBox.setValue(null);
    }

    @FXML
    private void handleSearchSession(KeyEvent event) throws SQLException {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                String id = privateSessionField.getText();

                PrivateSessionDTO privateSessionDTO = privateSessionModel.search(id);

                if(privateSessionDTO!=null) {
                    timeInField.setText(privateSessionDTO.getTimeIn());
                    timeOutField.setText(privateSessionDTO.getTimeOut());
                    extraChargesField.setText(String.valueOf(privateSessionDTO.getExtraCharges()));
                    for (MemberDTO mem : memberIDComboBox.getItems()) {
                        if (mem.getMemberID() == privateSessionDTO.getMemberId()) {
                            memberIDComboBox.setValue(mem);
                            break;
                        }
                    }
                } else {
                    cleanFileds();
                    new Alert(Alert.AlertType.ERROR, "Private Session not found!").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            }
        }
    }

    @FXML
    private void loadPrivateSessionTable() {
        try {
            List<PrivateSessionDTO> privateSessionList = privateSessionModel.getAllPrivateSession();
            ObservableList<PrivateSessionDTO> obList = FXCollections.observableArrayList();

            for (PrivateSessionDTO privateSessionDTO : privateSessionList) {
                obList.add(privateSessionDTO);
            }

            tablePrivateSession.setItems(obList);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    private void loadMemberID(){
        try {
            ObservableList<MemberDTO> idList = privateSessionModel.loadMemberID();
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

    private void setPrivateSessionDetails(PrivateSessionDTO newVal) {
        System.out.println(newVal.getPrivateSessionId());
        for (MemberDTO mem : memberIDComboBox.getItems()) {
            if (mem.getMemberID() == newVal.getMemberId()) {
                memberIDComboBox.setValue(mem);
                break;
            }
        }
        privateSessionField.setText(String.valueOf(newVal.getPrivateSessionId()));
        timeInField.setText(String.valueOf(newVal.getTimeIn()));
        timeOutField.setText(String.valueOf(newVal.getTimeOut()));
        extraChargesField.setText(String.valueOf(newVal.getExtraCharges()));
    }
}
