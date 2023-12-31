package sample.admin;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class viewFeedback {
    @FXML
    private Button back;
    @FXML
    private Label close;
    @FXML
    private Label fno;
    @FXML
    private Label cname;
    @FXML
    private Label uname;
    @FXML
    private Label sid;
    @FXML
    private Label cid;
    @FXML
    private Label sname;
    @FXML
    private Label rating;
    @FXML
    private Label feedback;



    public void GoBack() throws IOException {
        Main m = new Main();
        m.changeScene("feedback.fxml");
    }

    public void closeWindow()
    {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }


    public void load()
    {
        databaseConnection c = new databaseConnection();
        Connection con = c.getConnection();
        try {
            Statement s = con.createStatement();
            Statement ss = con.createStatement();
            ResultSet val = s.executeQuery("select * from temp");
            while (val.next()) {
                String b_id = val.getString("bid");


                ResultSet val1 = ss.executeQuery("select * from feedback where feedback_no ='" + b_id + "'");
                while (val1.next()) {
                    String Fno = val1.getString("feedback_no");
                    String Cid = val1.getString("customerID");
                    String Sid = val1.getString("serviceID");
                    String Ffeedback = val1.getString("feedback");
                    String Fratings = val1.getString("ratings");

                    fno.setText(Fno);
                    cid.setText(Cid);
                    sid.setText(Sid);
                    feedback.setText(Ffeedback);
                    rating.setText(Fratings + "/5");
                }
                ResultSet val2 = ss.executeQuery("select * from customer where customerID ='" + cid.getText() + "'");
                while (val2.next()) {
                    String sfname = val2.getString("firstName");
                    String slname = val2.getString("lastName");
                    String suser = val2.getString("username");


                    cname.setText(sfname+" "+slname);
                    uname.setText(suser);
                }
                ResultSet val3 = ss.executeQuery("select * from ServiceNew where ServiceID ='" + sid.getText() + "'");
                while (val3.next()) {
                    String s_name = val3.getString("Service_Name");
                    sname.setText(s_name);
                }
                ss.executeUpdate("delete from temp where bid ='"+b_id+"'");
            }



        }catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }

}
