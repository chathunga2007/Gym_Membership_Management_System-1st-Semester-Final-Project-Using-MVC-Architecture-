package lk.ijse.gymmembershipmanagementsystem.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.ijse.gymmembershipmanagementsystem.dto.TrainerDTO;
import lk.ijse.gymmembershipmanagementsystem.model.TrainerModel;

public class TrainerController implements Initializable {
    
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField nicField;
    @FXML
    private TextField contactField;
    @FXML
    private TextField emailField;
    @FXML
    private TableView tableTrainer;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn ageColumn;
    @FXML
    private TableColumn nicColumn;
    @FXML
    private TableColumn contactColumn;
    @FXML
    private TableColumn emailColumn;
    
    private final String NAME_REGEX = "^[A-Za-z ]{3,}$";
    private final String AGE_REGEX = "^[0-9 ]{2}$";
    private final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private final String NIC_REGEX = "^([0-9]{9}[VvXx]|[0-9]{12})$";
    private final String CONTACT_REGEX = "^0[0-9]{9}$";
    
    private final TrainerModel trainerModel = new TrainerModel();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       System.out.println("Trainer FXML is loaded!");
       idColumn.setCellValueFactory(new PropertyValueFactory<>("trainerId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        nicColumn.setCellValueFactory(new PropertyValueFactory<>("nic"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        loadTrainerTable();

        tableTrainer.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        setTrainerDetails((TrainerDTO) newVal);
                    }
                }
        );
    }    
    
    @FXML
    private void save() {

        String name = nameField.getText().trim();
        String age = ageField.getText().trim();
        String nic = nicField.getText().trim();
        String contact = contactField.getText().trim();
        String email = emailField.getText();
        
        if(!name.matches(NAME_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Trainer Management");
                    alert.setHeaderText("Invalid trainer name!");
                    alert.show();
        } else if(!age.matches(AGE_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Trainer Management");
                    alert.setHeaderText("Invalid trainer age!");
                    alert.show();
        } else if(!nic.matches(NIC_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Trainer Management");
                    alert.setHeaderText("Invalid trainer NIC!");
                    alert.show();
        } else if (!email.matches(EMAIL_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Trainer Management");
                    alert.setHeaderText("Invalid trainer email!");
                    alert.show();
        } else if (!contact.matches(CONTACT_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Trainer Management");
                    alert.setHeaderText("Invalid trainer contact number!");
                    alert.show();
        } else {
            
            try {
            TrainerDTO trainerDTO = new TrainerDTO(name, Integer.parseInt(age), nic, contact, email);
            boolean result = trainerModel.save(trainerDTO);
                if (result) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Trainer Management");
                    alert.setHeaderText("Trainer saved successfully!");
                    alert.show();
                    cleanFileds();
                    loadTrainerTable();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Trainer Management");
                    alert.setHeaderText("Something went a wrong!");
                    alert.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Trainer Management");
                        alert.setHeaderText("Something went a wrong!");
                        alert.show();
            }
        }
    }
    
    @FXML
    private void update() {

        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String age = ageField.getText().trim();
        String nic = nicField.getText().trim();
        String contact = contactField.getText().trim();
        String email = emailField.getText();

        if(!name.matches(NAME_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Trainer Management");
            alert.setHeaderText("Invalid trainer name!");
            alert.show();
        } else if(!age.matches(AGE_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Trainer Management");
            alert.setHeaderText("Invalid trainer age!");
            alert.show();
        } else if(!nic.matches(NIC_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Trainer Management");
            alert.setHeaderText("Invalid trainer NIC!");
            alert.show();
        } else if (!email.matches(EMAIL_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Trainer Management");
            alert.setHeaderText("Invalid trainer email!");
            alert.show();
        } else if (!contact.matches(CONTACT_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Trainer Management");
            alert.setHeaderText("Invalid trainer contact number!");
            alert.show();
        } else {

            try {
                TrainerDTO trainerDTO = new TrainerDTO(Integer.parseInt(id), name, Integer.parseInt(age), nic, contact, email);
                boolean result = trainerModel.update(trainerDTO);

                if (result) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Trainer Update");
                    alert.setHeaderText("Trainer updated successfully!");
                    alert.show();
                    cleanFileds();
                    loadTrainerTable();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Trainer Update ");
                    alert.setHeaderText("Trainer not found!");
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
            boolean result = trainerModel.delete( id);

            if (result) {
                new Alert(Alert.AlertType.INFORMATION, "Trainer deleted successfully!").show();
                cleanFileds();
                loadTrainerTable();
            } else {
                new Alert(Alert.AlertType.WARNING, "Trainer not found!").show();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Delete Error: " + e.getMessage());
            alert.show();
        }
    }
    
    @FXML
    private void handleSearchTrainer(KeyEvent event) throws SQLException {
        
        if (event.getCode() == KeyCode.ENTER) {
            try {
                String id = idField.getText();
                
                TrainerDTO trainerDTO = trainerModel.search(id);
                
                if(trainerDTO!=null) {
                    nameField.setText(trainerDTO.getName());
                    ageField.setText(String.valueOf(trainerDTO.getAge()));
                    nicField.setText(trainerDTO.getNic());
                    contactField.setText(trainerDTO.getContact());
                    emailField.setText(trainerDTO.getEmail()); 
                } else {
                    cleanFileds();
                    new Alert(Alert.AlertType.ERROR, "Trainer not found!").show();
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
        ageField.setText("");
        emailField.setText("");
        nicField.setText("");
        contactField.setText("");
    }
    
    @FXML
    public void loadTrainerTable() {
        try {
            
            List<TrainerDTO> trainerList = trainerModel.getAllTrainer();
            
            ObservableList<TrainerDTO> obList = FXCollections.observableArrayList();
            
            for (TrainerDTO trainerDTO : trainerList) {
                obList.add(trainerDTO);
            }
            
            tableTrainer.setItems(obList);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void setTrainerDetails(TrainerDTO newVal) {
        System.out.println(newVal.getTrainerId());

        idField.setText(String.valueOf(newVal.getTrainerId()));
        nameField.setText(newVal.getName());
        ageField.setText(String.valueOf(newVal.getAge()));
        emailField.setText(newVal.getEmail());
        nicField.setText(newVal.getNic());
        contactField.setText(newVal.getContact());
    }
}
