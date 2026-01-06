package lk.ijse.gymmembershipmanagementsystem.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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
import lk.ijse.gymmembershipmanagementsystem.dto.*;
import lk.ijse.gymmembershipmanagementsystem.model.EquipmentModel;
import lk.ijse.gymmembershipmanagementsystem.model.SessionModel;

public class SessionController implements Initializable {
    
    @FXML
    private TextField sessionIDField;
    @FXML
    private ComboBox<String> sessionTypeCombo;
    @FXML
    private TextField durationField;
    @FXML
    private ComboBox<TrainerDTO> trainerIDComboBox;
    @FXML
    private ComboBox<TimeSlotDTO> slotIDComboBox;
    @FXML
    private TableView tableSession;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn sessionTypeColumn;
    @FXML
    private TableColumn durationColumn;
    @FXML
    private TableColumn trainerNameColumn;
    @FXML
    private TableColumn slotDateColumn;
    @FXML
    private ListView<EquipmentDTO> equipmentListView;
    
    SessionModel sessionModel = new SessionModel();

    private final String DURATION_REGEX = "^[1-9][0-9]{0,3}$";
    
    private int selectTrainerID;
    private int selectSlotID;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Session FXML is loaded!");
        loadTrainerID();
        loadSlotID();
        loadEquipment();

        sessionTypeCombo.getItems().addAll(
                "Yoga",
                "Zumba",
                "Cardio",
                "Strength",
                "Full Body Strength",
                "Boxing",
                "BootCamp",
                "HIIT (High Intensity Interval Training)",
                "CrossFit",
                "Functional Training",
                "Core Training",
                "Abs & Core Blast",
                "Weight Loss Program",
                "Muscle Building Session",
                "Circuit Training",
                "Personal Training",
                "Group Training",
                "Spin Class (Indoor Cycling)",
                "Stretching & Mobility",
                "Rehabilitation / Physio Training"
        );

        idColumn.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        sessionTypeColumn.setCellValueFactory(new PropertyValueFactory<>("sessionType"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        trainerNameColumn.setCellValueFactory(new PropertyValueFactory<>("trainerName"));
        slotDateColumn.setCellValueFactory(new PropertyValueFactory<>("slotDate"));
        loadSessionTable();
        
        trainerIDComboBox.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
            if (newVal != null) {
                selectTrainerID = newVal.getTrainerId();
                System.out.println("Selected Member ID = " + selectTrainerID);
            }
        });

        slotIDComboBox.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
            if(newVal != null) {
                selectSlotID = newVal.getSlotId();
                System.out.println("Selected Member ID = " + selectSlotID);
            }
        });

        tableSession.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        setSesstionDetails((SessionDTO) newVal);
                    }
                }
        );
    }    
    
    @FXML
    private void save() {
        String sessionType = sessionTypeCombo.getValue();
        String duration = durationField.getText().trim();
        
        if(!duration.matches(DURATION_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Session Management");
            alert.setHeaderText("Invalid session duration");
            alert.show();
        } else if(selectTrainerID == 0 || selectSlotID == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Session Management");
            alert.setHeaderText("Select Trainer & Slot");
            alert.show();
        } else {
            List<Integer> equipmentIDs = new ArrayList<>();
            for (EquipmentDTO eq : equipmentListView
                    .getSelectionModel().getSelectedItems()) {
                equipmentIDs.add(eq.getEquipmentsId());
            }
            try {

                SessionDTO sessionDTO = new SessionDTO(selectSlotID, selectTrainerID, sessionType, Integer.parseInt(duration));
                boolean result = sessionModel.save(sessionDTO, equipmentIDs);
                
                if (result) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Session Management");
                    alert.setHeaderText("Session saved successfully!");
                    alert.show();
                    cleanFileds();
                    loadSessionTable();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Session Management");
                    alert.setHeaderText("Something went a wrong!");
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
    private void update() throws SQLException {
        String id =  sessionIDField.getText();
        String sessionType = sessionTypeCombo.getValue();
        String duration = durationField.getText();

        try {
            SessionDTO sessionDTO = new SessionDTO(Integer.parseInt(id), selectSlotID, selectTrainerID, sessionType, Integer.parseInt(duration));
            boolean result = sessionModel.update(sessionDTO);
            if (result) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Session Update");
                alert.setHeaderText("Session updated successfully!");
                alert.show();
                cleanFileds();
                loadSessionTable();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Session Update ");
                alert.setHeaderText("Session not found!");
                alert.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Update Error: " + e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void delete() throws SQLException {
        String id = sessionIDField.getText();

        try {
            boolean result = sessionModel.delete(id);
            if (result) {
                new Alert(Alert.AlertType.INFORMATION, "Session deleted successfully!").show();
                cleanFileds();
                loadSessionTable();
            } else {
                new Alert(Alert.AlertType.WARNING, "Session not found!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Update Error: " + e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void handleSearchSession(KeyEvent event) throws SQLException {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                String id = sessionIDField.getText();

                SessionDTO sessionDTO = sessionModel.search(id);

                if(sessionDTO!=null) {
                    sessionTypeCombo.setValue(sessionDTO.getSessionType());
                    durationField.setText(String.valueOf(sessionDTO.getDuration()));
                    for (TrainerDTO trainer : trainerIDComboBox.getItems()) {
                        if (trainer.getTrainerId() == sessionDTO.getTrainerId()) {
                            trainerIDComboBox.setValue(trainer);
                            break;
                        }
                    }

                    for (TimeSlotDTO slot : slotIDComboBox.getItems()) {
                        if (slot.getSlotId() == sessionDTO.getSlotId()) {
                            slotIDComboBox.setValue(slot);
                            break;
                        }
                    }

                } else {
                    cleanFileds();
                    new Alert(Alert.AlertType.ERROR, "Session not found!").show();
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
        sessionIDField.setText("");
        sessionTypeCombo.setValue(null);
        durationField.setText("");
        trainerIDComboBox.setValue(null);
        slotIDComboBox.setValue(null);
        equipmentListView.getSelectionModel().clearSelection();
    }
    
    private void loadTrainerID(){
        try {
            ObservableList<TrainerDTO> idList = sessionModel.loadTrainerID();
            trainerIDComboBox.setItems(idList);
            trainerIDComboBox.setCellFactory(cb -> new ListCell<TrainerDTO>() {
                @Override
                protected void updateItem(TrainerDTO t, boolean empty) {
                    super.updateItem(t, empty);
                    if (empty || t == null) {
                        setText(null);
                    } else {
                        setText(t.getTrainerId() + " - " + t.getName());
                    }
                }
            });
            trainerIDComboBox.setButtonCell(new ListCell<TrainerDTO>() {
                @Override
                protected void updateItem(TrainerDTO t, boolean empty) {
                    super.updateItem(t, empty);
                    if (empty || t == null) {
                        setText(null);
                    } else {
                        setText(t.getTrainerId() + " - " + t.getName());
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void loadSlotID(){
        try {
            ObservableList<TimeSlotDTO> idList = sessionModel.loadSlotID();
            slotIDComboBox.setItems(idList);
            slotIDComboBox.setCellFactory(cb -> new ListCell<TimeSlotDTO>() {
                @Override
                protected void updateItem(TimeSlotDTO t, boolean empty) {
                    super.updateItem(t, empty);
                    if (empty || t == null) {
                        setText(null);
                    } else {
                        setText(t.getSlotId() + " - " + t.getDate());
                    }
                }
            });
            slotIDComboBox.setButtonCell(new ListCell<TimeSlotDTO>() {
                @Override
                protected void updateItem(TimeSlotDTO t, boolean empty) {
                    super.updateItem(t, empty);
                    if (empty || t == null) {
                        setText(null);
                    } else {
                        setText(t.getSlotId() + " - " + t.getDate());
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadSessionTable() {
        try {

            List<SessionDTO> sessionList = sessionModel.getAllSession();

            ObservableList<SessionDTO> obList = FXCollections.observableArrayList();

            for (SessionDTO sessionDTO : sessionList) {
                obList.add(sessionDTO);
            }

            tableSession.setItems(obList);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void setSesstionDetails(SessionDTO newVal) {
        System.out.println(newVal.getSessionId());
        for (TrainerDTO trainer : trainerIDComboBox.getItems()) {
            if (trainer.getTrainerId() == newVal.getTrainerId()) {
                trainerIDComboBox.setValue(trainer);
                break;
            }
        }
        for (TimeSlotDTO slot : slotIDComboBox.getItems()) {
            if (slot.getSlotId() == newVal.getSlotId()) {
                slotIDComboBox.setValue(slot);
                break;
            }
        }

        sessionIDField.setText(String.valueOf(newVal.getSessionId()));
        durationField.setText(String.valueOf(newVal.getDuration()));
        sessionTypeCombo.setValue(newVal.getSessionType());
    }

    private void loadEquipment() {
        try {
            ObservableList<EquipmentDTO> list =
                    FXCollections.observableArrayList(
                            EquipmentModel.getAllEquipment()
                    );
            equipmentListView.setItems(list);
            equipmentListView.getSelectionModel()
                    .setSelectionMode(SelectionMode.MULTIPLE);

            equipmentListView.setCellFactory(param -> new ListCell<EquipmentDTO>() {
                @Override
                protected void updateItem(EquipmentDTO eq, boolean empty) {
                    super.updateItem(eq, empty);
                    if (empty || eq == null) {
                        setText(null);
                    } else {
                        setText(eq.getEquipmentsId() + " - " + eq.getName());
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
