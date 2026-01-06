package lk.ijse.gymmembershipmanagementsystem.controller;

import java.net.URL;
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
import lk.ijse.gymmembershipmanagementsystem.dto.SupplementDTO;
import lk.ijse.gymmembershipmanagementsystem.model.SupplementModel;

public class SupplementController implements Initializable {

    @FXML
    private TextField idTextField;
    @FXML
    private ComboBox<String> nameCombo;
    @FXML
    private TextField qtyTextField;
    @FXML
    private TextField unitPriceTextFiled;
    @FXML
    private TableView tableSupplement;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn qtyColumn;
    @FXML
    private TableColumn unitPriceColumn;

    private SupplementModel supplementModel = new SupplementModel();

    private final String ITEM_QTY_REGEX = "^[0-9]{1,}$";
    private final String ITEM_UNIT_PRICE_REGEX = "^[0-9]+(\\.[0-9]{1,2})?$";


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Supplement FXML is loaded!");

        nameCombo.getItems().addAll(
                "WHEY_PROTEIN",
                "MASS_GAINER",
                "CREATINE",
                "PRE_WORKOUT",
                "BCAA",
                "EAA",
                "FAT_BURNER",
                "L_CARNITINE",
                "MULTIVITAMIN",
                "Vitamin D",
                "Whey Protein",
                "OMEGA_3",
                "ELECTROLYTES",
                "IMMUNITY_BOOSTER",
                "CASEIN_PROTEIN",
                "GLUTAMINE",
                "ZMA",
                "COLLAGEN",
                "TESTOSTERONE_BOOSTER",
                "JOINT_SUPPORT"
        );

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        qtyColumn.setCellValueFactory(new PropertyValueFactory<>("qty"));
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        loadSupplementTable();

        tableSupplement.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        setSupplementDetails((SupplementDTO) newVal);
                    }
                }
        );
    }

    @FXML
    private void save() {
        String name = nameCombo.getValue();
        String qty = qtyTextField.getText().trim();
        String unitPrice = unitPriceTextFiled.getText().trim();

        if(!qty.matches(ITEM_QTY_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Supplement Management");
            alert.setHeaderText("Invalid supplement qty!");
            alert.show();
        } else if(!unitPrice.matches(ITEM_UNIT_PRICE_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Supplement Management");
            alert.setHeaderText("Invalid supplement unitPrice!");
            alert.show();
        } else {

            try {
                SupplementDTO supplementDTO = new SupplementDTO(name, Integer.parseInt(qty), Double.parseDouble(unitPrice));
                boolean result = supplementModel.save( supplementDTO);
                if (result) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Supplement Management");
                    alert.setHeaderText("Supplement saved successfully!");
                    alert.show();
                    cleanFileds();
                    loadSupplementTable();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Supplement Management");
                    alert.setHeaderText("Something went a wrong!");
                    alert.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Supplement Management");
                alert.setHeaderText("Something went a wrong!");
                alert.show();
            }
        }
    }

    @FXML
    private void update() {
        String id = idTextField.getText().trim();
        String name = nameCombo.getValue();
        String qty = qtyTextField.getText().trim();
        String unitPrice = unitPriceTextFiled.getText().trim();

        if(!qty.matches(ITEM_QTY_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Supplement Management");
            alert.setHeaderText("Invalid supplement qty!");
            alert.show();
        } else if(!unitPrice.matches(ITEM_UNIT_PRICE_REGEX)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Supplement Management");
            alert.setHeaderText("Invalid supplement unitPrice!");
            alert.show();
        } else {

            try {
                SupplementDTO supplementDTO = new SupplementDTO(Integer.parseInt(id), name, Integer.parseInt(qty), Double.parseDouble(unitPrice));
                boolean result = supplementModel.update(supplementDTO);

                if (result) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Supplement Update");
                    alert.setHeaderText("Supplement updated successfully!");
                    alert.show();
                    cleanFileds();
                    loadSupplementTable();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Supplement Update ");
                    alert.setHeaderText("Supplement not found!");
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

        String id = idTextField.getText().trim();

        try {
            boolean result = supplementModel.delete( id);

            if (result) {
                new Alert(Alert.AlertType.INFORMATION, "Supplement deleted successfully!").show();
                cleanFileds();
                loadSupplementTable();
            } else {
                new Alert(Alert.AlertType.WARNING, "Supplement not found!").show();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Delete Error: " + e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void reset() {
        cleanFileds();
    }

    private void cleanFileds() {
        nameCombo.setValue(null);
        qtyTextField.setText("");
        idTextField.setText("");
        unitPriceTextFiled.setText("");
    }

    @FXML
    private void handleSearchSupplement(KeyEvent event) {

        if (event.getCode() == KeyCode.ENTER) {
            try {
                String id = idTextField.getText();

                SupplementDTO supplementDTO = supplementModel.search(id);

                if(supplementDTO!=null) {
                    nameCombo.setValue(supplementDTO.getName());
                    qtyTextField.setText(String.valueOf(supplementDTO.getQty()));
                    unitPriceTextFiled.setText(String.valueOf(supplementDTO.getUnitPrice()));
                } else {
                    cleanFileds();
                    new Alert(Alert.AlertType.ERROR, "Supplement not found!").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            }
        }
    }

    @FXML
    public void loadSupplementTable() {
        try {

            List<SupplementDTO> supplementDTOList = supplementModel.getAllIds();

            ObservableList<SupplementDTO> obList = FXCollections.observableArrayList();

            for (SupplementDTO supplementDTO : supplementDTOList) {
                obList.add(supplementDTO);
            }

            tableSupplement.setItems(obList);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void setSupplementDetails(SupplementDTO newVal) {
        System.out.println(newVal.getId());

        idTextField.setText(String.valueOf(newVal.getId()));
        nameCombo.setValue(newVal.getName());
        unitPriceTextFiled.setText(String.valueOf(newVal.getUnitPrice()));
        qtyTextField.setText(String.valueOf(newVal.getQty()));
    }
}
