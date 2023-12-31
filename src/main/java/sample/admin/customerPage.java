package sample.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import sample.admin.util.CustomerUtil;

public class customerPage implements Initializable{
    @FXML
    private Label close;
    @FXML
    private TableView<customerTable> cusTable;
    @FXML
    private TableColumn<customerTable, String> idCol;
    @FXML
    private TableColumn<customerTable, String> fnameCol;
    @FXML
    private TableColumn<customerTable, String> lnameCol;
    @FXML
    private TableColumn<customerTable, String> emailCol;
    @FXML
    private TableColumn<customerTable, String> ageCol;
    @FXML
    private TableColumn<customerTable, String> mobileCol;
    @FXML
    private TextField id;
    @FXML
    private Label validate;

    private static final SessionFactory SESSION_FACTORY = CustomerUtil.getSessionFactory();
    ObservableList<customerTable> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseConnection c = new databaseConnection();
        Connection con = c.getConnection();
        try {
            Statement st = con.createStatement();
            ResultSet val = st.executeQuery("select * from customer");
            while(val.next())
            {
                oblist.add(new customerTable(
                        val.getString("customerID"),
                        val.getString("firstName"),
                        val.getString("lastName"),
                        val.getString("address"),
                        val.getString("email"),
                        val.getString("age"),
                        val.getString("mobile"),
                        val.getString("username")));

            }

            st.close();

        }catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        fnameCol.setCellValueFactory(new PropertyValueFactory<>("fname"));
        lnameCol.setCellValueFactory(new PropertyValueFactory<>("lname"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        mobileCol.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        cusTable.setItems(oblist);

        FilteredList<customerTable> filteredData = new FilteredList<>(oblist, b ->true);
        id.textProperty().addListener((observable, oldValue, newValue) ->{

            filteredData.setPredicate(customerTable -> {

              if(newValue.isEmpty() || newValue.isBlank() || newValue == null)
              {
                  return true;
              }

              String searchKeyword = newValue.toLowerCase();

              if(customerTable.getId().toLowerCase().indexOf(searchKeyword) > -1)
              {
                  return true;
              }
              else if(customerTable.getFname().toLowerCase().indexOf(searchKeyword) > -1)
              {
                  return true;
              }
              else if(customerTable.getLname().toLowerCase().indexOf(searchKeyword) > -1)
              {
                  return true;
              }
              else if(customerTable.getEmail().toLowerCase().indexOf(searchKeyword) > -1)
              {
                  return true;
              }
              else if(customerTable.getAge().toLowerCase().indexOf(searchKeyword) > -1)
              {
                  return true;
              }
              else if(customerTable.getMobile().toLowerCase().indexOf(searchKeyword) > -1)
              {
                  return true;
              }
              else
              {
                  return false;
              }

            });
        });

        SortedList<customerTable> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(cusTable.comparatorProperty());

        cusTable.setItems(sortedData);

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

    public void refresh() throws IOException {
        Main m = new Main();
        m.changeScene("customer.fxml");
    }

    public void searchCustomer() throws IOException {

        databaseConnection c = new databaseConnection();
        Connection connectDB = c.getConnection();
        try {
            Statement s = connectDB.createStatement();
            int found = -1;
            if(!id.getText().isEmpty())
            {
                ResultSet rs = s.executeQuery("select customerID from customer");

                while (rs.next())
                {
                    String bid = rs.getString("customerID");
                    if(id.getText().equals(bid))
                    {
                        found++;
                    }
                }
                if(found != -1)
                {
                    Main m = new Main();
                    m.changeScene("modifyCustomer.fxml");
                    s.executeUpdate("insert into temp values('"+id.getText()+"')");
                }
                else
                {
                    validate.setText("Error: Customer not found!");
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

    public void deleteCus()
    {
        databaseConnection c = new databaseConnection();
        Connection connectDB = c.getConnection();
        try {
            Statement s = connectDB.createStatement();
            int found = -1;
            if(!id.getText().isEmpty())
            {
                ResultSet rs = s.executeQuery("select customerID from customer");

                while (rs.next())
                {
                    String bid = rs.getString("customerID");
                    if(id.getText().equals(bid))
                    {
                        found++;
                    }
                }
                if(found != -1)
                {
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
                }
                else
                {
                    validate.setText("Error: Customer not found!");
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
