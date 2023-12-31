package sample.admin;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class viewPayment {

    @FXML
    private Button back;
    @FXML
    private Label close;
    @FXML
    private Label bno;
    @FXML
    private Label bid;
    @FXML
    private Label cname;
    @FXML
    private Label date;
    @FXML
    private Label sid;
    @FXML
    private Label cid;
    @FXML
    private Label sname;
    @FXML
    private Label time;
    @FXML
    private Label addr;
    @FXML
    private Label mobile;
    @FXML
    private Label email;
    @FXML
    private Label price;
    @FXML
    private Label total;



    public void GoBack() throws IOException {
        Main m = new Main();
        m.changeScene("payment.fxml");
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


                ResultSet val1 = ss.executeQuery("select * from bill_payment where bill_no ='" + b_id + "'");
                while (val1.next()) {
                    String Bno = val1.getString("bill_no");
                    String Cid = val1.getString("customerID");
                    String Bid = val1.getString("bookingID");
                    String Bamount = val1.getString("bill_amount");



                    bno.setText(Bno);
                    cid.setText(Cid);
                    bid.setText(Bid);
                    total.setText(Bamount);
                }
                ResultSet val2 = ss.executeQuery("select * from customer where customerID ='" + cid.getText() + "'");
                while (val2.next()) {
                    String sfname = val2.getString("firstName");
                    String slname = val2.getString("lastName");
                    String saddr = val2.getString("address");
                    String semail = val2.getString("email");
                    String smobile = val2.getString("mobile");


                    cname.setText(sfname+" "+slname);
                    addr.setText(saddr);
                    email.setText(semail);
                    mobile.setText(smobile);

                }
                ResultSet val3 = ss.executeQuery("select * from booking where BookingID ='" + bid.getText() + "'");
                while (val3.next()) {
                    String s_id = val3.getString("ServiceID");
                    String sdate = val3.getString("Date");
                    String stime = val3.getString("Time_slot");
                    sid.setText(s_id);
                    date.setText(sdate);
                    time.setText(stime);
                }
                ResultSet val4 = ss.executeQuery("select * from ServiceNew where ServiceID ='" + sid.getText() + "'");
                while (val4.next()) {
                    String s_name = val4.getString("Service_Name");
                    String sprice = val4.getString("price");
                    sname.setText(s_name);
                    price.setText(sprice);
                }



                ss.executeUpdate("delete from temp where bid ='"+b_id+"'");
            }



        }catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }
}
