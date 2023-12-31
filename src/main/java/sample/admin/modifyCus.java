package sample.admin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import sample.admin.util.CustomerUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class modifyCus implements Initializable {

    @FXML
    private Button back;
    @FXML
    private Button dlt;
    @FXML
    private Label id;
    @FXML
    private Label fname;
    @FXML
    private Label lname;
    @FXML
    private Label addr;
    @FXML
    private Label age;
    @FXML
    private Label email;
    @FXML
    private Label mobile;
    @FXML
    private Label username;
    @FXML
    private Label close;

    private static final SessionFactory SESSION_FACTORY = CustomerUtil.getSessionFactory();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }

    public void load()
    {
        databaseConnection c = new databaseConnection();
        Connection con = c.getConnection();
        try {
            Statement s = con.createStatement();
            ResultSet val = s.executeQuery("select * from temp");
            while (val.next()) {
                String b_id = val.getString("bid");

                Session session = SESSION_FACTORY.openSession();
                Transaction tx = session.beginTransaction();
                customerTable cus = session.get(customerTable.class, b_id);
                id.setText(cus.getId());
                fname.setText(cus.getFname());
                lname.setText(cus.getLname());
                addr.setText(cus.getAddr());
                email.setText(cus.getEmail());
                age.setText(cus.getAge());
                mobile.setText(cus.getMobile());
                username.setText(cus.getUname());

                tx.commit();
                session.close();



                /*
                Statement ss = con.createStatement();
                ResultSet val1 = ss.executeQuery("select * from customer where customerID ='" + b_id + "'");
                while (val1.next()) {
                    String Cid = val1.getString("customerID");
                    String sfname = val1.getString("firstName");
                    String slname = val1.getString("lastName");
                    String saddr = val1.getString("address");
                    String semail = val1.getString("email");
                    String sage = val1.getString("age");
                    String smobile = val1.getString("mobile");
                    String suser = val1.getString("username");


                    id.setText(Cid);
                    fname.setText(sfname);
                    lname.setText(slname);
                    addr.setText(saddr);
                    email.setText(semail);
                    age.setText(sage);
                    mobile.setText(smobile);
                    username.setText(suser);
                }
                */
                Statement ss = con.createStatement();
                ss.executeUpdate("delete from temp where bid ='"+b_id+"'");

            }

        }catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void GoBack() throws IOException {
        Main m = new Main();
        m.changeScene("customer.fxml");
    }

    public void closeWindow()
    {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    public void deleteCus()
    {

        try {
            Session session = SESSION_FACTORY.openSession();
            Transaction tx = session.beginTransaction();
            customerTable cus = session.get(customerTable.class, id.getText());
            session.delete(cus);
            tx.commit();
            session.close();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Customer has been deleted Successfully!!!");

            Main m = new Main();
            m.changeScene("customer.fxml");

        }catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }


}

