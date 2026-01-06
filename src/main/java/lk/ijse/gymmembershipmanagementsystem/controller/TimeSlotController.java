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
import lk.ijse.gymmembershipmanagementsystem.dto.TimeSlotDTO;
import lk.ijse.gymmembershipmanagementsystem.model.TimeSlotModel;

public class TimeSlotController implements Initializable {
    
    @FXML
    private TextField idField;
    @FXML
    private TextField timeInField;
    @FXML
    private TextField timeOutField;
    @FXML
    private DatePicker dateCombo;
    @FXML
    private TableView tableTimeSlot;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn timeInColumn;
    @FXML
    private TableColumn timeOutColumn;
    @FXML
    private TableColumn dateColumn;
    
    private final String TIME_REGEX = "^(?:[01]\\d|2[0-3]):(?:[0-5]\\d):(?:[0-5]\\d)$";
    
    private final TimeSlotModel slotModel = new TimeSlotModel();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Time Slot FXML is loaded!");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("slotId"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeInColumn.setCellValueFactory(new PropertyValueFactory<>("timeIn"));
        timeOutColumn.setCellValueFactory(new PropertyValueFactory<>("timeOut"));
        loadTimeSlotTable();

        tableTimeSlot.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        setTimeSlotDetails((TimeSlotDTO) newVal);
                    }
                }
        );
    }    
    
    @FXML
    private void save() {
        String timeIn = timeInField.getText().trim();
        String timeOut = timeOutField.getText().trim();
        LocalDate date = dateCombo.getValue();

        if(!timeIn.matches(TIME_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Time Slot Management");
            alert.setHeaderText("Invalid time in!");
            alert.show();
        } else if(!timeOut.matches(TIME_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Time Slot Management");
            alert.setHeaderText("Invalid time out!");
            alert.show();
        } else {
            try {
                TimeSlotDTO slotDTO = new TimeSlotDTO(date, timeIn, timeOut);
                boolean result = slotModel.save(slotDTO);
                if (result) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Time Slot Management");
                    alert.setHeaderText("Time Slot saved successfully!");
                    alert.show();
                    cleanFileds();
                    loadTimeSlotTable();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Time Slot Management");
                    alert.setHeaderText("Something went a wrong!");
                    alert.show();
                }
            } catch(Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Time Slot Management");
                alert.setHeaderText("Something went a wrong!");
                alert.show();
            }
        }
    }
    
    @FXML
    private void update() {
        String id = idField.getText().trim();
        String timeIn = timeInField.getText().trim();
        String timeOut = timeOutField.getText().trim();
        LocalDate date = dateCombo.getValue();

        if(!timeIn.matches(TIME_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Time Slot Management");
            alert.setHeaderText("Invalid time in!");
            alert.show();
        } else if(!timeOut.matches(TIME_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Time Slot Management");
            alert.setHeaderText("Invalid time out!");
            alert.show();
        } else {

            try {
                TimeSlotDTO slotDTO = new TimeSlotDTO(Integer.parseInt(id), date, timeIn, timeOut);
                boolean result = slotModel.update(slotDTO);
                if (result) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Time Slot Update");
                    alert.setHeaderText("Time slot updated successfully!");
                    alert.show();
                    cleanFileds();
                    loadTimeSlotTable();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Time Slot Update ");
                    alert.setHeaderText("Time slot not found!");
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
    private void delete() {
        String id = idField.getText().trim();
        
        try {
            boolean result = slotModel.delete(id);
            if (result) {
                new Alert(Alert.AlertType.INFORMATION, "Time slot deleted successfully!").show();
                cleanFileds();
                loadTimeSlotTable();
            } else {
                new Alert(Alert.AlertType.WARNING, "Time slot not found!").show();
            } 
        } catch(Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Delete Error: " + e.getMessage());
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
        timeInField.setText("");
        timeOutField.setText("");
    }
    
    @FXML
    private void handleSearchTimeSlot(KeyEvent event) throws SQLException {
        
        if (event.getCode() == KeyCode.ENTER) {
            try {
                String id = idField.getText();
                
                TimeSlotDTO slotDTO = slotModel.search(id);
                
                if(slotDTO!=null) {
                    dateCombo.setValue(slotDTO.getDate());
                    timeInField.setText(slotDTO.getTimeIn());
                    timeOutField.setText(slotDTO.getTimeOut());
                } else {
                    cleanFileds();
                    new Alert(Alert.AlertType.ERROR, "Time slot not found!").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            }
        }
    }
    
    @FXML
    public void loadTimeSlotTable() {
        try {
            
            List<TimeSlotDTO> slotList = slotModel.getAllTimeSlot();
            
            ObservableList<TimeSlotDTO> obList = FXCollections.observableArrayList();
            
            for (TimeSlotDTO slotDTO : slotList) {
                obList.add(slotDTO);
            }
            
            tableTimeSlot.setItems(obList);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void setTimeSlotDetails(TimeSlotDTO newVal) {
        System.out.println(newVal.getSlotId());

        idField.setText(String.valueOf(newVal.getSlotId()));
        dateCombo.setValue(newVal.getDate());
        timeInField.setText(newVal.getTimeIn());
        timeOutField.setText(newVal.getTimeOut());
    }
}
