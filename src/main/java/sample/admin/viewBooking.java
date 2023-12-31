package sample.admin;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class viewBooking {
    @FXML
    private Button back;
    @FXML
    private Label close;
    @FXML
    private Label bid;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private Label cname;
    @FXML
    private Label addr;
    @FXML
    private Label email;
    @FXML
    private Label age;
    @FXML
    private Label mobile;
    @FXML
    private Label uname;
    @FXML
    private Label sid;
    @FXML
    private Label cid;
    @FXML
    private Label status;
    @FXML
    private Label sname;
    @FXML
    private Label price;
    @FXML
    private Label category;


    public void GoBack() throws IOException {
        Main m = new Main();
        m.changeScene("booking.fxml");
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


                ResultSet val1 = ss.executeQuery("select * from booking where BookingID ='" + b_id + "'");
                while (val1.next()) {
                    String Bid = val1.getString("BookingID");
                    String Cid = val1.getString("CustomerID");
                    String Sid = val1.getString("ServiceID");
                    String Bdate = val1.getString("Date");
                    String Btime = val1.getString("Time_slot");
                    String Bstatus = val1.getString("status");


                    bid.setText(Bid);
                    date.setText(Bdate);
                    time.setText(Btime);
                    status.setText(Bstatus);
                    cid.setText(Cid);
                    sid.setText(Sid);
                }
                ResultSet val2 = ss.executeQuery("select * from customer where customerID ='" + cid.getText() + "'");
                while (val2.next()) {
                    String sfname = val2.getString("firstName");
                    String slname = val2.getString("lastName");
                    String saddr = val2.getString("address");
                    String semail = val2.getString("email");
                    String sage = val2.getString("age");
                    String smobile = val2.getString("mobile");
                    String suser = val2.getString("username");


                    cname.setText(sfname+" "+slname);
                    addr.setText(saddr);
                    email.setText(semail);
                    age.setText(sage);
                    mobile.setText(smobile);
                    uname.setText(suser);
                }
                ResultSet val3 = ss.executeQuery("select * from ServiceNew where ServiceID ='" + sid.getText() + "'");
                while (val3.next()) {
                    String s_name = val3.getString("Service_Name");
                    String sprice = val3.getString("price");
                    String scategory = val3.getString("category");

                    sname.setText(s_name);
                    price.setText(sprice);
                    category.setText(scategory);
                }
                ss.executeUpdate("delete from temp where bid ='"+b_id+"'");
            }



        }catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }

}
