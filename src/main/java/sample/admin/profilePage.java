package sample.admin;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class profilePage {
    @FXML
    private Label close;
    @FXML
    private Label validate;
    @FXML
    private TextField username;
    @FXML
    private TextField currentPwd;
    @FXML
    private TextField newPwd;
    @FXML
    private TextField rePwd;


    public void loadpage() {
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


    public void closeWindow()
    {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    public void GoBack() throws IOException {
        Main m = new Main();
        m.changeScene("afterLogin.fxml");
    }

    public void clearFields()
    {
        currentPwd.setText(""); newPwd.setText(""); rePwd.setText(""); validate.setText("");
    }

    public void clearText()
    {
        currentPwd.setText(""); newPwd.setText(""); rePwd.setText("");
    }

    public void changePassword()
    {
        databaseConnection c = new databaseConnection();
        Connection connectDB = c.getConnection();
        try {
            Statement s = connectDB.createStatement();
            ResultSet rs = s.executeQuery("select * from admin_login");

            while (rs.next()) {
                String u = rs.getString("username");
                String p = rs.getString("password");
                username.setText(u);
                if(currentPwd.getText().equals(p))
                {
                    if(newPwd.getText().equals(rePwd.getText()))
                    {
                        Statement sUpdate = connectDB.createStatement();
                        int rsUpdate = sUpdate.executeUpdate("update admin_login set password = '"+newPwd.getText()+"' where username = '"+u+"'  ");
                        validate.setText("Password changed successfully!!!");
                        clearText();
                    }
                    else
                    {
                        validate.setText("Error : New password not matched");
                        clearText();
                    }
                }
                else
                {
                    validate.setText("Error : Invalid Password");
                    clearText();
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
