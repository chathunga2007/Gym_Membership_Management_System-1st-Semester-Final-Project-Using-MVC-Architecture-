package lk.ijse.gymmembershipmanagementsystem.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import lk.ijse.gymmembershipmanagementsystem.db.DBConnection;
import lk.ijse.gymmembershipmanagementsystem.model.DashBoardModel;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class MainContentDashBoardController implements Initializable {
    @FXML
    private Label lblDate;
    @FXML
    private Label lblTime;
    @FXML
    private Label lblTotalMembers;
    @FXML
    private Label lblTotalTrainers;
    @FXML
    private Label lblTodaySessions;
    @FXML
    private Label lblIncome;
    @FXML
    private ListView<String> listTodaySessions;
    @FXML
    private LineChart<String, Number> incomeChart;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Main Content Dash Board is loaded!");
        setDate();
        startClock();
        loadSummaryData();
        loadTodaySessions();
        loadDailyIncomeChart();
    }

    private void setDate() {
        DateTimeFormatter dateFormatter =
                DateTimeFormatter.ofPattern("dd MMM yyyy");

        lblDate.setText(LocalDate.now().format(dateFormatter));
    }

    private void startClock() {
        DateTimeFormatter timeFormatter =
                DateTimeFormatter.ofPattern("hh:mm:ss a");

        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    lblTime.setText(
                            LocalTime.now().format(timeFormatter)
                    );
                });
            }
        }, 0, 1000);
    }

    private void loadSummaryData() {
        try {
            lblTotalMembers.setText(String.valueOf(DashBoardModel.getTotalMembers()));
            lblTotalTrainers.setText(String.valueOf(DashBoardModel.getTotalTrainers()));
            lblTodaySessions.setText(String.valueOf(DashBoardModel.getTodaySessions()));
            lblIncome.setText("Rs. " + DashBoardModel.getTodayIncome());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTodaySessions() {

        listTodaySessions.getItems().clear();

        String sql = """
        
                SELECT
            t.timeIn,
            t.timeOut,
            s.sessionType,
            tr.name AS trainerName
        FROM Session s
        LEFT JOIN Time_Slot t ON s.slotID = t.slotID
        LEFT JOIN Trainer tr ON s.trainerID = tr.trainerID
        WHERE t.date = DATE(CONVERT_TZ(NOW(), 'SYSTEM', '+05:30'))
        ORDER BY t.timeIn;
        """;

        try {
            ResultSet rs = DBConnection.getInstance()
                    .getConnection()
                    .createStatement()
                    .executeQuery(sql);

            boolean hasData = false;

            while (rs.next()) {
                hasData = true;

                String row =
                        rs.getTime("timeIn") + " - " +
                                rs.getTime("timeOut") + " | " +
                                rs.getString("sessionType") +
                                " (Trainer: " + rs.getString("trainerName") + ")";

                listTodaySessions.getItems().add(row);
            }

            if (!hasData) {
                listTodaySessions.getItems().add("No sessions scheduled for today");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDailyIncomeChart() {

        incomeChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Daily Income");

        String sql = """
        SELECT d, SUM(total) AS income
        FROM (
            SELECT date AS d, SUM(amount) AS total
            FROM Payment
            GROUP BY date

            UNION ALL

            SELECT o.date AS d, SUM(oi.price * oi.qty) AS total
            FROM orders o
            JOIN order_items oi ON o.id = oi.order_id
            GROUP BY o.date
        ) x
        GROUP BY d
        ORDER BY d
    """;
        try {
            ResultSet rs = DBConnection.getInstance()
                    .getConnection()
                    .createStatement()
                    .executeQuery(sql);

            boolean hasData = false;

            while (rs.next()) {
                hasData = true;
                series.getData().add(
                        new XYChart.Data<>(
                                rs.getDate("d").toString(),
                                rs.getDouble("income")
                        )
                );
            }

            if (hasData) {
                incomeChart.getData().add(series);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
