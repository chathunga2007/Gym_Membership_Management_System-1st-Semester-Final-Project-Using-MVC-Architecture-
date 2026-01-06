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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.ijse.gymmembershipmanagementsystem.dto.MemberDTO;
import lk.ijse.gymmembershipmanagementsystem.dto.MembershipDTO;
import lk.ijse.gymmembershipmanagementsystem.model.MembershipModel;

public class MembershipController implements Initializable {
    
    @FXML
    private TextField idField;
    @FXML
    private ComboBox<String> membershipTypeComboBox;
    @FXML
    private DatePicker issuedDateField;
    @FXML
    private DatePicker expiryDateField;
    @FXML
    private ComboBox<MemberDTO> cmbMemberId;
    @FXML
    private TableView tableMembership;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn membershipTypeColumn;
    @FXML
    private TableColumn issuedDateColumn;
    @FXML
    private TableColumn expirDateColumn;
    @FXML
    private TableColumn memberNameColumn;
    
    private final MembershipModel membershipModel = new MembershipModel();

    private int selectedMemberId;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Membership FXML is loaded!");
        loadMemberID();
        membershipTypeComboBox.getItems().addAll("GOLD", "SILVER", "PLATINUM");

        cmbMemberId.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
            if (newVal != null) {
                selectedMemberId = newVal.getMemberID();
                System.out.println("Selected Member ID = " + selectedMemberId);
            }
        });

        idColumn.setCellValueFactory(new PropertyValueFactory<>("membershipId"));
        membershipTypeColumn.setCellValueFactory(new PropertyValueFactory<>("membershipType"));
        issuedDateColumn.setCellValueFactory(new PropertyValueFactory<>("issuedDate"));
        expirDateColumn.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
        memberNameColumn.setCellValueFactory(new PropertyValueFactory<>("memberName"));

        loadMembershipTable();

        tableMembership.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        setMembershipDetails((MembershipDTO) newVal);
                    }
                }
        );
    }
    
    @FXML
    private void save() {
        String membershipType = membershipTypeComboBox.getValue();
        LocalDate issuedDate = issuedDateField.getValue();
        LocalDate expiryDate = expiryDateField.getValue();
        try{
            MembershipDTO membershipDTO = new MembershipDTO(Integer.parseInt(String.valueOf(selectedMemberId)), membershipType, issuedDate, expiryDate);
            boolean result = membershipModel.save(membershipDTO);
            if (result) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Membership Management");
                alert.setHeaderText("Membership saved successfully!");
                alert.show();
                cleanFileds();
                loadMembershipTable();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Membership Management");
                alert.setHeaderText("Something went a wrong!");
                alert.show();
            }
        } catch(Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Membership Management");
            alert.setHeaderText("Something went a wrong!");
            alert.show();
        }
    }

    @FXML
    private void update() {
        String id = idField.getText().trim();
        String membershipType = membershipTypeComboBox.getValue();
        LocalDate issuedDate = issuedDateField.getValue();
        LocalDate expiryDate = expiryDateField.getValue();
        try {
            MembershipDTO membershipDTO = new MembershipDTO(Integer.parseInt(id), selectedMemberId, membershipType, issuedDate, expiryDate);
            boolean result = membershipModel.update(membershipDTO);
            if (result) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Membership Update");
                alert.setHeaderText("Membership updated successfully!");
                alert.show();
                cleanFileds();
                loadMembershipTable();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Membership Update ");
                alert.setHeaderText("Membership not found!");
                alert.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Update Error: " + e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void delete() {
        String id = idField.getText().trim();

        try {
            boolean result = membershipModel.delete( id);
            if (result) {
                new Alert(Alert.AlertType.INFORMATION, "Membership deleted successfully!").show();
                cleanFileds();
                loadMembershipTable();
            } else {
                new Alert(Alert.AlertType.WARNING, "Membership not found!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Update Error: " + e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void handleSearchMemberships(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                String id = idField.getText();

                MembershipDTO membershipDTO = membershipModel.search(id);

                if(membershipDTO!=null) {
                    membershipTypeComboBox.setValue(membershipDTO.getMembershipType());
                    issuedDateField.setValue(membershipDTO.getIssuedDate());
                    expiryDateField.setValue(membershipDTO.getExpiryDate());
                    for (MemberDTO mem : cmbMemberId.getItems()) {
                        if (mem.getMemberID() == membershipDTO.getMemberId()) {
                            cmbMemberId.setValue(mem);
                            break;
                        }
                    }
                } else {
                    cleanFileds();
                    new Alert(Alert.AlertType.ERROR, "Membership not found!").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            }
        }
    }

    private void loadMemberID(){
        try {
            ObservableList<MemberDTO> idList = membershipModel.loadMemberID();
            cmbMemberId.setItems(idList);
            cmbMemberId.setCellFactory(cb -> new ListCell<MemberDTO>() {
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
            cmbMemberId.setButtonCell(new ListCell<MemberDTO>() {
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

    @FXML
    private void reset() {
        cleanFileds();
    }

    private void cleanFileds() {
        idField.setText("");
        cmbMemberId.setValue(null);
        issuedDateField.setValue(null);
        expiryDateField.setValue(null);
        membershipTypeComboBox.setValue(null);
    }

    @FXML
    public void loadMembershipTable() {
        try {

            List<MembershipDTO> membershipList = membershipModel.getAllMembership();

            ObservableList<MembershipDTO> obList = FXCollections.observableArrayList();

            for (MembershipDTO membershipDTO : membershipList) {
                obList.add(membershipDTO);
            }

            tableMembership.setItems(obList);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void setMembershipDetails(MembershipDTO newVal) {
        System.out.println(newVal.getMembershipId());
        for (MemberDTO mem : cmbMemberId.getItems()) {
            if (mem.getMemberID() == newVal.getMemberId()) {
                cmbMemberId.setValue(mem);
                break;
            }
        }
        idField.setText(String.valueOf(newVal.getMembershipId()));
        membershipTypeComboBox.setValue(newVal.getMembershipType());
        issuedDateField.setValue(newVal.getIssuedDate());
        expiryDateField.setValue(newVal.getExpiryDate());
    }
}
