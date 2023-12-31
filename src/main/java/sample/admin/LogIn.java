package sample.admin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LogIn {

    public LogIn() {

    }

    @FXML
    private Button button;
    @FXML
    private Label wrongLogIn;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label close;


    public void userLogIn(ActionEvent event) throws IOException {
        checkLogin();

    }

    public void closeLogin()
    {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    private void checkLogin() throws IOException {
         if(username.getText().isEmpty() && password.getText().isEmpty()) {
            wrongLogIn.setText("ERROR: Please enter your data");
        }
        else {
            validate_login();

        }
    }

    public void validate_login()
    {
        databaseConnection c = new databaseConnection();
        Connection connectDB = c.getConnection();
        try{
            Statement s = connectDB.createStatement();
            ResultSet rs = s.executeQuery("select * from admin_login");

            while(rs.next())
            {
                String u = rs.getString("username");
                String p = rs.getString("password");

                if(username.getText().equals(u))
                {
                    if(password.getText().equals(p))
                    {
                        Main m = new Main();
                        m.changeScene("afterLogin.fxml");
                    }
                    else
                    {
                        wrongLogIn.setText("ERROR: Invalid LogIn");
                    }
                }
                else
                {
                    wrongLogIn.setText("ERROR: User not found");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        clearFields();
    }

    public void clearFields()
    {
        username.setText("");
        password.setText("");
    }

}