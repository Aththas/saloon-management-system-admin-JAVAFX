package sample.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class paymentPage implements Initializable {
    @FXML
    private Label close;
    @FXML
    private Label validate;
    @FXML
    private TextField bill_no;
    @FXML
    private TableView<paymentTable> payTable;
    @FXML
    private TableColumn<paymentTable, String> b_no;
    @FXML
    private TableColumn<paymentTable, String> c_id;
    @FXML
    private TableColumn<paymentTable, String> b_id;
    @FXML
    private TableColumn<paymentTable, String> billCol;

    ObservableList<paymentTable> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseConnection c = new databaseConnection();
        Connection con = c.getConnection();
        try {
            Statement st = con.createStatement();
            ResultSet val = st.executeQuery("select * from bill_payment");
            while(val.next())
            {
                oblist.add(new paymentTable(
                        val.getString("bill_no"),
                        val.getString("customerID"),
                        val.getString("bookingID"),
                        val.getString("bill_amount")));

            }

            st.close();

        }catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        b_no.setCellValueFactory(new PropertyValueFactory<>("bno"));
        c_id.setCellValueFactory(new PropertyValueFactory<>("cid"));
        b_id.setCellValueFactory(new PropertyValueFactory<>("bid"));
        billCol.setCellValueFactory(new PropertyValueFactory<>("billAmount"));
        payTable.setItems(oblist);

        FilteredList<paymentTable> filteredData = new FilteredList<>(oblist, b ->true);
        bill_no.textProperty().addListener((observable, oldValue, newValue) ->{

            filteredData.setPredicate(paymentTable -> {

                if(newValue.isEmpty() || newValue.isBlank() || newValue == null)
                {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if(paymentTable.getBno().toLowerCase().indexOf(searchKeyword) > -1)
                {
                    return true;
                }
                else if(paymentTable.getCid().toLowerCase().indexOf(searchKeyword) > -1)
                {
                    return true;
                }
                else if(paymentTable.getBid().toLowerCase().indexOf(searchKeyword) > -1)
                {
                    return true;
                }
                else if(paymentTable.getBillAmount().toLowerCase().indexOf(searchKeyword) > -1)
                {
                    return true;
                }
                else
                {
                    return false;
                }

            });
        });

        SortedList<paymentTable> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(payTable.comparatorProperty());

        payTable.setItems(sortedData);
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

    public void viewPayment()
    {
        databaseConnection c = new databaseConnection();
        Connection connectDB = c.getConnection();
        try {
            Statement s = connectDB.createStatement();
            int found = -1;
            if(!bill_no.getText().isEmpty())
            {
                ResultSet rs = s.executeQuery("select bill_no from bill_payment");

                while (rs.next())
                {
                    String bid = rs.getString("bill_no");
                    if(bill_no.getText().equals(bid))
                    {
                        found++;
                    }
                }
                if(found != -1)
                {
                    Main m = new Main();
                    m.changeScene("view_payment.fxml");
                    s.executeUpdate("insert into temp values('"+bill_no.getText()+"')");
                }
                else
                {
                    validate.setText("Error: Payment not found!");
                }
            }
            else
            {
                validate.setText("Error: enter the value!");
            }

        }catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
