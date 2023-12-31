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

public class feedbackPage implements Initializable {
    @FXML
    private Label close;
    @FXML
    private Label validate;
    @FXML
    private TextField feedbackNo;
    @FXML
    private TableView<feedbackTable> fbTable;
    @FXML
    private TableColumn<feedbackTable, String> f_no;
    @FXML
    private TableColumn<feedbackTable, String> c_id;
    @FXML
    private TableColumn<feedbackTable, String> s_id;
    @FXML
    private TableColumn<feedbackTable, String> feedbackCol;
    @FXML
    private TableColumn<feedbackTable, String> ratingsCol;


    ObservableList<feedbackTable> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseConnection c = new databaseConnection();
        Connection con = c.getConnection();
        try {
            Statement st = con.createStatement();
            ResultSet val = st.executeQuery("select * from feedback");
            while(val.next())
            {
                oblist.add(new feedbackTable(
                        val.getString("feedback_no"),
                        val.getString("customerID"),
                        val.getString("serviceID"),
                        val.getString("feedback"),
                        val.getString("ratings")));

            }

            st.close();

        }catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        f_no.setCellValueFactory(new PropertyValueFactory<>("fno"));
        c_id.setCellValueFactory(new PropertyValueFactory<>("cid"));
        s_id.setCellValueFactory(new PropertyValueFactory<>("sid"));
        feedbackCol.setCellValueFactory(new PropertyValueFactory<>("feedback"));
        ratingsCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
        fbTable.setItems(oblist);

        FilteredList<feedbackTable> filteredData = new FilteredList<>(oblist, b ->true);
        feedbackNo.textProperty().addListener((observable, oldValue, newValue) ->{

            filteredData.setPredicate(feedbackTable -> {

                if(newValue.isEmpty() || newValue.isBlank() || newValue == null)
                {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if(feedbackTable.getFno().toLowerCase().indexOf(searchKeyword) > -1)
                {
                    return true;
                }
                else if(feedbackTable.getCid().toLowerCase().indexOf(searchKeyword) > -1)
                {
                    return true;
                }
                else if(feedbackTable.getSid().toLowerCase().indexOf(searchKeyword) > -1)
                {
                    return true;
                }
                else if(feedbackTable.getFeedback().toLowerCase().indexOf(searchKeyword) > -1)
                {
                    return true;
                }
                else if(feedbackTable.getRating().toLowerCase().indexOf(searchKeyword) > -1)
                {
                    return true;
                }
                else
                {
                    return false;
                }

            });
        });

        SortedList<feedbackTable> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(fbTable.comparatorProperty());

        fbTable.setItems(sortedData);
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
    public void viewFeedback()
    {
        databaseConnection c = new databaseConnection();
        Connection connectDB = c.getConnection();
        try {
            Statement s = connectDB.createStatement();
            int found = -1;
            if(!feedbackNo.getText().isEmpty())
            {
                ResultSet rs = s.executeQuery("select feedback_no from feedback");

                while (rs.next())
                {
                    String bid = rs.getString("feedback_no");
                    if(feedbackNo.getText().equals(bid))
                    {
                        found++;
                    }
                }
                if(found != -1)
                {
                    Main m = new Main();
                    m.changeScene("view_feedback.fxml");
                    s.executeUpdate("insert into temp values('"+feedbackNo.getText()+"')");
                }
                else
                {
                    validate.setText("Error: Feedback not found!");
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
