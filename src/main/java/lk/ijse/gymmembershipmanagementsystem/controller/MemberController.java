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
import lk.ijse.gymmembershipmanagementsystem.model.MemberModel;

public class MemberController implements Initializable {
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private DatePicker dobField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField nicField;
    @FXML
    private TextField contactField;
    @FXML
    private DatePicker joinedDateField;
    @FXML
    private ComboBox<String> statusComboBox;
    @FXML
    private TableView tableMember;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn dobColumn;
    @FXML
    private TableColumn ageColumn;
    @FXML
    private TableColumn emailColumn;
    @FXML
    private TableColumn nicColumn;
    @FXML
    private TableColumn contactColumn;
    @FXML
    private TableColumn joinedDateColumn;
    @FXML
    private TableColumn statusColumn;
    
    private final String NAME_REGEX = "^[A-Za-z ]{3,}$";
    private final String AGE_REGEX = "^[0-9 ]{2}$";
    private final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private final String NIC_REGEX = "^([0-9]{9}[VvXx]|[0-9]{12})$";
    private final String CONTACT_REGEX = "^0[0-9]{9}$";
    
    private final MemberModel memberModel = new MemberModel();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Member FXML is loaded!");
        statusComboBox.getItems().addAll("ACTIVE", "INACTIVE");
        statusComboBox.setValue("ACTIVE");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("MemberID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        nicColumn.setCellValueFactory(new PropertyValueFactory<>("nic"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        joinedDateColumn.setCellValueFactory(new PropertyValueFactory<>("joinedDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        loadMemberTable();

        tableMember.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        setMemberDetails((MemberDTO) newVal);
                    }
                }
        );
    }
    
    @FXML
    private void save() {

        String name = nameField.getText().trim();
        LocalDate dob = dobField.getValue();
        String age = ageField.getText().trim();
        String email = emailField.getText();
        String nic = nicField.getText().trim();
        String contact = contactField.getText().trim();
        LocalDate joinedDate = joinedDateField.getValue();
        String status = statusComboBox.getValue();
        
        if(!name.matches(NAME_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Member Management");
            alert.setHeaderText("Invalid member name!");
            alert.show();
        } else if(!age.matches(AGE_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Member Management");
            alert.setHeaderText("Invalid member age!");
            alert.show();
        } else if(!nic.matches(NIC_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Member Management");
            alert.setHeaderText("Invalid member NIC!");
            alert.show();
        } else if (!email.matches(EMAIL_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Member Management");
            alert.setHeaderText("Invalid member email!");
            alert.show();
        } else if (!contact.matches(CONTACT_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Member Management");
            alert.setHeaderText("Invalid member contact number!");
            alert.show();
        } else {
            try {
            MemberDTO memDTO = new MemberDTO(name, dob, Integer.parseInt(age), email, nic, contact, joinedDate, status);
            boolean result = memberModel.save(memDTO);
                if (result) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Member Management");
                    alert.setHeaderText("Member saved successfully!");
                    alert.show();
                    cleanFileds();
                    loadMemberTable();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Member Management");
                    alert.setHeaderText("Something went a wrong!");
                    alert.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Member Management");
                alert.setHeaderText("Something went a wrong!");
                alert.show();
            }
        }
    }
        
    @FXML
    private void update() {

        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        LocalDate dob = dobField.getValue();
        String age = ageField.getText().trim();
        String email = emailField.getText();
        String nic = nicField.getText().trim();
        String contact = contactField.getText().trim();
        LocalDate joinedDate = joinedDateField.getValue();
        String status = statusComboBox.getValue();

        if(!name.matches(NAME_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Member Management");
            alert.setHeaderText("Invalid member name!");
            alert.show();
        } else if(!age.matches(AGE_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Member Management");
            alert.setHeaderText("Invalid member age!");
            alert.show();
        } else if(!nic.matches(NIC_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Member Management");
            alert.setHeaderText("Invalid member NIC!");
            alert.show();
        } else if (!email.matches(EMAIL_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Member Management");
            alert.setHeaderText("Invalid member email!");
            alert.show();
        } else if (!contact.matches(CONTACT_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Member Management");
            alert.setHeaderText("Invalid member contact number!");
            alert.show();
        } else {

            try {
                MemberDTO memDTO = new MemberDTO(Integer.parseInt(id), name, dob, Integer.parseInt(age), email, nic, contact, joinedDate, status);
                boolean result = memberModel.update(memDTO);

                if (result) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Member Update");
                    alert.setHeaderText("Member updated successfully!");
                    alert.show();
                    cleanFileds();
                    loadMemberTable();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Member Update ");
                    alert.setHeaderText("Member not found!");
                    alert.show();
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Update Error: " + e.getMessage());
                alert.show();
            }
        }
    }
    
    @FXML
    private void delete() {

        String id = idField.getText().trim();

        try {
            boolean result = memberModel.delete(id);

            if (result) {
                new Alert(Alert.AlertType.INFORMATION, "Member deleted successfully!").show();
                cleanFileds();
                loadMemberTable();
            } else {
                new Alert(Alert.AlertType.WARNING, "Member not found!").show();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Delete Error: " + e.getMessage());
            alert.show();
        }
    }
    
    @FXML
    private void handleSearchMember(KeyEvent event) throws SQLException {
        
        if (event.getCode() == KeyCode.ENTER) {
            try {
                String id = idField.getText();
                
                MemberDTO memDTO = memberModel.search(id);
                
                if(memDTO!=null) {
                    nameField.setText(memDTO.getName());
                    dobField.setValue(memDTO.getDob());
                    ageField.setText(String.valueOf(memDTO.getAge()));
                    emailField.setText(memDTO.getEmail());
                    nicField.setText(memDTO.getNic());
                    contactField.setText(memDTO.getContact());
                    joinedDateField.setValue(memDTO.getJoinedDate());
                } else {
                    cleanFileds();
                    new Alert(Alert.AlertType.ERROR, "Member not found!").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            }
        }
    }
    
    @FXML
    private void reset() {
        cleanFileds();
    }
    
    private void cleanFileds() {
        idField.setText("");
        nameField.setText("");
        dobField.setValue(null);
        ageField.setText("");
        emailField.setText("");
        nicField.setText("");
        contactField.setText("");
        joinedDateField.setValue(null);
        statusComboBox.setValue(null);
    }
    
    @FXML
    public void loadMemberTable() {
        try {
            
            List<MemberDTO> memberList = memberModel.getAllMember();
            
            ObservableList<MemberDTO> obList = FXCollections.observableArrayList();
            
            for (MemberDTO memberDTO : memberList) {
                obList.add(memberDTO);
            }
            
            tableMember.setItems(obList);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void setMemberDetails(MemberDTO newVal) {
        System.out.println(newVal.getMemberID());

        idField.setText(String.valueOf(newVal.getMemberID()));
        nameField.setText(newVal.getName());
        dobField.setValue(newVal.getDob());
        ageField.setText(String.valueOf(newVal.getAge()));
        emailField.setText(newVal.getEmail());
        nicField.setText(newVal.getNic());
        contactField.setText(newVal.getContact());
        joinedDateField.setValue(newVal.getJoinedDate());
        statusComboBox.setValue(newVal.getStatus());
    }
}
