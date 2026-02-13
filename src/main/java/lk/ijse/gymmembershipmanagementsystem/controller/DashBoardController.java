package lk.ijse.gymmembershipmanagementsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import lk.ijse.gymmembershipmanagementsystem.App;

public class DashBoardController implements Initializable {

    @FXML
    public StackPane mainContent;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Dash Board is loaded!");
        try {
            clickDashBoard();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void logout() throws IOException {
        App.setRoot("Login" );
    }
    
    @FXML
    private void clickDashBoard() throws IOException {
        Parent mainContentDashBoard = App.loadFXML("MainContentDashBoard");
        mainContent.getChildren().setAll(mainContentDashBoard);
    }

    @FXML
    private void clickMember() throws IOException {
        Parent memberFXML = App.loadFXML("Member");
        mainContent.getChildren().setAll(memberFXML);
    }

    @FXML
    private void clickMembership() throws IOException {
        Parent membershipFXML = App.loadFXML("Membership");
        mainContent.getChildren().setAll(membershipFXML);
    }

    @FXML
    private void clickSession() throws IOException {
        Parent sessionFXML = App.loadFXML("Session");
        mainContent.getChildren().setAll(sessionFXML);
    }

    @FXML
    private void clickPrivateSession() throws IOException {
        Parent privateSessionFXML = App.loadFXML("PrivateSession");
        mainContent.getChildren().setAll(privateSessionFXML);
    }

    @FXML
    private void clickTrainer() throws IOException {
        Parent trainerFXML = App.loadFXML("Trainer");
        mainContent.getChildren().setAll(trainerFXML);
    }

    @FXML
    private void clickTimeSlot() throws IOException {
        Parent timeSlotFXML = App.loadFXML("TimeSlot");
        mainContent.getChildren().setAll(timeSlotFXML);
    }

    @FXML
    private void clickEquipment() throws IOException {
        Parent equipmentFXML = App.loadFXML("Equipment");
        mainContent.getChildren().setAll(equipmentFXML);
    }

    @FXML
    private void clickPayment() throws IOException {
        Parent paymentFXML = App.loadFXML("Payment");
        mainContent.getChildren().setAll(paymentFXML);
    }

    @FXML
    private void clickSupplements() throws IOException {
        Parent supplementsFXML = App.loadFXML("Supplement");
        mainContent.getChildren().setAll(supplementsFXML);
    }

    @FXML
    private void clickOrders() throws IOException {
        Parent ordersFXML = App.loadFXML("Order");
        mainContent.getChildren().setAll(ordersFXML);
        System.out.println("Hello");
    }
}
