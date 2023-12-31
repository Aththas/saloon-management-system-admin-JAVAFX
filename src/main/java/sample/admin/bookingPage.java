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

public class bookingPage implements Initializable {
    @FXML
    private Label close;
    @FXML
    private Label validate;
    @FXML
    private TextField id;
    @FXML
    private TableView<bookingTable> bookTable;
    @FXML
    private TableColumn<bookingTable, String> B_ID;
    @FXML
    private TableColumn<bookingTable, String> C_ID;
    @FXML
    private TableColumn<bookingTable, String> S_ID;
    @FXML
    private TableColumn<bookingTable, String> dateCol;
    @FXML
    private TableColumn<bookingTable, String> timeCol;
    @FXML
    private TableColumn<bookingTable, String> totalCol;
    @FXML
    private TableColumn<bookingTable, String> statusCol;


    ObservableList<bookingTable> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseConnection c = new databaseConnection();
        Connection con = c.getConnection();
        try {
            Statement st = con.createStatement();
            ResultSet val = st.executeQuery("select * from booking");
            while(val.next())
            {
                oblist.add(new bookingTable(
                        val.getString("BookingID"),
                        val.getString("CustomerID"),
                        val.getString("ServiceID"),
                        val.getString("Date"),
                        val.getString("Time_slot"),
                        val.getString("total"),
                        val.getString("status")));

            }

            st.close();

        }catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        B_ID.setCellValueFactory(new PropertyValueFactory<>("bid"));
        C_ID.setCellValueFactory(new PropertyValueFactory<>("cid"));
        S_ID.setCellValueFactory(new PropertyValueFactory<>("sid"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        bookTable.setItems(oblist);

        FilteredList<bookingTable> filteredData = new FilteredList<>(oblist, b ->true);
        id.textProperty().addListener((observable, oldValue, newValue) ->{

            filteredData.setPredicate(bookingTable -> {

                if(newValue.isEmpty() || newValue.isBlank() || newValue == null)
                {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if(bookingTable.getBid().toLowerCase().indexOf(searchKeyword) > -1)
                {
                    return true;
                }
                else if(bookingTable.getCid().toLowerCase().indexOf(searchKeyword) > -1)
                {
                    return true;
                }
                else if(bookingTable.getSid().toLowerCase().indexOf(searchKeyword) > -1)
                {
                    return true;
                }
                else if(bookingTable.getDate().toLowerCase().indexOf(searchKeyword) > -1)
                {
                    return true;
                }
                else if(bookingTable.getTime().toLowerCase().indexOf(searchKeyword) > -1)
                {
                    return true;
                }
                else if(bookingTable.getTotal().toLowerCase().indexOf(searchKeyword) > -1)
                {
                    return true;
                }
                else if(bookingTable.getStatus().toLowerCase().indexOf(searchKeyword) > -1)
                {
                    return true;
                }
                else
                {
                    return false;
                }

            });
        });

        SortedList<bookingTable> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(bookTable.comparatorProperty());

        bookTable.setItems(sortedData);
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
    public void refreshPage() throws IOException {
        Main m = new Main();
        m.changeScene("booking.fxml");
    }

    public void acceptBooking() {
        databaseConnection c = new databaseConnection();
        Connection connectDB = c.getConnection();
        try {
            Statement ss = connectDB.createStatement();
            Statement s = connectDB.createStatement();
            int found = -1;
            if(!id.getText().isEmpty())
            {
                ResultSet rs = ss.executeQuery("select BookingID from booking");

                while (rs.next())
                {
                    String bid = rs.getString("BookingID");
                    if(id.getText().equals(bid))
                    {
                        found++;
                    }
                }
                if(found != -1)
                {
                    int r = s.executeUpdate("update booking set status = 'Accepted' where BookingID = '" + id.getText() + "' ");
                    validate.setText("Status updated!!!");
                }
                else
                {
                    validate.setText("Error: Booking not found!");
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
        clearFields();
    }

    public void cancelBooking() {
        databaseConnection c = new databaseConnection();
        Connection connectDB = c.getConnection();
        try {
            Statement ss = connectDB.createStatement();
            Statement s = connectDB.createStatement();
            int found = -1;
            if(!id.getText().isEmpty())
            {
                ResultSet rs = ss.executeQuery("select BookingID from booking");

                while (rs.next())
                {
                    String bid = rs.getString("BookingID");
                    if(id.getText().equals(bid))
                    {
                        found++;
                    }
                }
                if(found != -1)
                {
                    int r = s.executeUpdate("update booking set status = 'Rejected' where BookingID = '" + id.getText() + "' ");
                    validate.setText("Status updated!!!");
                }
                else
                {
                    validate.setText("Error: Booking not found!");
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
        clearFields();
    }

    public void viewBooking() {
        databaseConnection c = new databaseConnection();
        Connection connectDB = c.getConnection();
        try {
            Statement s = connectDB.createStatement();
            int found = -1;
            if(!id.getText().isEmpty())
            {
                ResultSet rs = s.executeQuery("select BookingID from booking");

                while (rs.next())
                {
                    String bid = rs.getString("BookingID");
                    if(id.getText().equals(bid))
                    {
                        found++;
                    }
                }
                if(found != -1)
                {
                    Main m = new Main();
                    m.changeScene("view_booking.fxml");
                    s.executeUpdate("insert into temp values('"+id.getText()+"')");
                }
                else
                {
                    validate.setText("Error: Booking not found!");
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

    public void clearFields()
    {
        id.setText("");
    }
}
