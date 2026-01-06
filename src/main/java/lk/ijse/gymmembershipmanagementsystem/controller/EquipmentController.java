package lk.ijse.gymmembershipmanagementsystem.controller;

import java.net.URL;
import java.sql.SQLException;
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
import lk.ijse.gymmembershipmanagementsystem.dto.EquipmentDTO;
import lk.ijse.gymmembershipmanagementsystem.model.EquipmentModel;

public class EquipmentController implements Initializable {
    
    @FXML
    private TextField idField;
    @FXML
    private ComboBox<String> nameCombo;
    @FXML
    private TextField qtyField;
    @FXML
    private ComboBox<String> availabilityField;
    @FXML
    private TableView tableEquipment;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn qtyColumn;
    @FXML
    private TableColumn availabilityColumn;

    private final String QTY_REGEX = "^[0-9 ]{1,}$";
    
    private final EquipmentModel equipmentModel = new EquipmentModel();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Equipments FXML is loaded!");
        nameCombo.getItems().addAll(
                "Exercise Bike (Stationary Bike)",
                "Elliptical Trainer",
                "Rowing Machine",
                "Power Rack / Squat Rack",
                "Bench Press Bench",
                "Adjustable Bench",
                "Lat Pulldown Machine",
                "Seated Row Machine",
                "Chest Fly / Pec Deck Machine",
                "Cable Crossover Machine",
                "Leg Extension Machine",
                "Leg Curl Machine",
                "Shoulder Press Machine",
                "Dumbbells",
                "Barbells",
                "Weight Plates",
                "Smith Machine",
                "Leg Press Machine",
                "Preacher Curl Bench",
                "Treadmills",
                "Punching Bags",
                "Kettlebell"
        );
        availabilityField.getItems().addAll("AVAILABLE", "IN_USE", "UNDER_MAINTENANCE");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("equipmentsId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        qtyColumn.setCellValueFactory(new PropertyValueFactory<>("qty"));
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));
        loadEquipmentTable();

        tableEquipment.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        setEquipmentDetails((EquipmentDTO) newVal);
                    }
                }
        );
    }    
    
    @FXML
    private void save() {

        String name = nameCombo.getValue();
        String qty = qtyField.getText().trim();
        String availability = availabilityField.getValue();

        if (!qty.matches(QTY_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Equipment Management");
            alert.setHeaderText("Invalid equipment qty!");
            alert.show();
        } else {
            try {
            EquipmentDTO equipmentDTO = new EquipmentDTO(name, Integer.parseInt(qty), availability);
            boolean result = equipmentModel.save(equipmentDTO);
                if (result) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Equipment Management");
                    alert.setHeaderText("Equipment saved successfully!");
                    alert.show();
                    cleanFileds();
                    loadEquipmentTable();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Equipment Management");
                    alert.setHeaderText("Something went a wrong!");
                    alert.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Equipment Management");
                alert.setHeaderText("Something went a wrong!");
                alert.show();
            }
        }
    }
    
    @FXML
    private void update() {

        String id = idField.getText().trim();
        String name = nameCombo.getValue();
        String qty = qtyField.getText().trim();
        String availability = availabilityField.getValue();

        if (!qty.matches(QTY_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Equipment Management");
            alert.setHeaderText("Invalid equipment qty!");
            alert.show();
        } else {

            try {
                EquipmentDTO equipmentDTO = new EquipmentDTO(Integer.parseInt(id), name, Integer.parseInt(qty), availability);
                boolean result = equipmentModel.update(equipmentDTO);

                if (result) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Equipment Update");
                    alert.setHeaderText("Equipment updated successfully!");
                    alert.show();
                    cleanFileds();
                    loadEquipmentTable();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Equipment Update ");
                    alert.setHeaderText("Equipment not found!");
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
            boolean result = equipmentModel.delete( id);

            if (result) {
                new Alert(Alert.AlertType.INFORMATION, "Equipment deleted successfully!").show();
                cleanFileds();
                loadEquipmentTable();
            } else {
                new Alert(Alert.AlertType.WARNING, "Equipment not found!").show();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Delete Error: " + e.getMessage());
            alert.show();
        }
    }
    
    @FXML
    private void handleSearchEquipment(KeyEvent event) throws SQLException {
        
        if (event.getCode() == KeyCode.ENTER) {
            try {
                String id = idField.getText();
                
                EquipmentDTO equipmentDTO = equipmentModel.search(id);
                
                if(equipmentDTO!=null) {
                    nameCombo.setValue(equipmentDTO.getName());
                    qtyField.setText(String.valueOf(equipmentDTO.getQty()));
                    availabilityField.setValue(equipmentDTO.getAvailability());
                } else {
                    cleanFileds();
                    new Alert(Alert.AlertType.ERROR, "Equipment not found!").show();
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
        nameCombo.setValue(null);
        qtyField.setText("");
        availabilityField.setValue(null);
    }
    
    @FXML
    public void loadEquipmentTable() {
        try {
            
            List<EquipmentDTO> equipmentList = equipmentModel.getAllEquipment();
            
            ObservableList<EquipmentDTO> obList = FXCollections.observableArrayList();
            
            for (EquipmentDTO equipmentDTO : equipmentList) {
                obList.add(equipmentDTO);
            }
            
            tableEquipment.setItems(obList);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void setEquipmentDetails(EquipmentDTO newVal) {
        System.out.println(newVal.getEquipmentsId());

        idField.setText(String.valueOf(newVal.getEquipmentsId()));
        nameCombo.setValue(newVal.getName());
        qtyField.setText(String.valueOf(newVal.getQty()));
        availabilityField.setValue(newVal.getAvailability());
    }
}
