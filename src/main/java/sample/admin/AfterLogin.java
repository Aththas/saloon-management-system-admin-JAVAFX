package sample.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AfterLogin {

    @FXML
    private Button product;
    @FXML
    private Button customer;
    @FXML
    private Button booking;
    @FXML
    private Button feedback;
    @FXML
    private Button profile;
    @FXML
    private Label close;
    @FXML
    private Label username;

    public void user()
    {
        databaseConnection c = new databaseConnection();
        Connection connectDB = c.getConnection();
        try {
            Statement s = connectDB.createStatement();
            ResultSet rs = s.executeQuery("select * from admin_login");

            while (rs.next()) {
                String u = rs.getString("username");
                username.setText(u);
            }
        }catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

        public void logout() throws IOException {
            Main m = new Main();
            m.changeScene("sample.fxml");
        }

        public void closeWindow()
        {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
        }

        public void loadProduct() throws IOException {
            Main m = new Main();
            m.changeScene("product.fxml");
        }

        public void loadCustomer() throws IOException {
            Main m = new Main();
            m.changeScene("customer.fxml");
        }

        public void loadBooking() throws IOException {
            Main m = new Main();
            m.changeScene("booking.fxml");
        }

        public void loadFeedback() throws IOException {
            Main m = new Main();
            m.changeScene("feedback.fxml");
        }

        public void loadPayment() throws IOException {
            Main m = new Main();
            m.changeScene("payment.fxml");
        }

        public void loadProfile() throws IOException {
            Main m = new Main();
            m.changeScene("profile.fxml");
        }





}
