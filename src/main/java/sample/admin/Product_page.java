package sample.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.application.Application.launch;

public class Product_page implements Initializable{

    @FXML
    private Label close;
    @FXML
    private TableView<serviceTable> servicesTable;
    @FXML
    private TableColumn<serviceTable, String> idCol;
    @FXML
    private TableColumn<serviceTable, String> nameCol;
    @FXML
    private TableColumn<serviceTable, String> priceCol;
    @FXML
    private TableColumn<serviceTable, String> categoryCol;
    @FXML
    private TextField id;

    ObservableList<serviceTable> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseConnection c = new databaseConnection();
        Connection con = c.getConnection();
        try {
            Statement st = con.createStatement();
            ResultSet val = st.executeQuery("select * from ServiceNew");
            while(val.next())
            {
                oblist.add(new serviceTable(
                        val.getString("ServiceID"),
                        val.getString("Service_Name"),
                        val.getString("price"),
                        val.getString("category"),
                        val.getString("image")));

            }

            st.close();

        }catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        servicesTable.setItems(oblist);

        FilteredList<serviceTable> filteredData = new FilteredList<>(oblist, b ->true);
        id.textProperty().addListener((observable, oldValue, newValue) ->{

            filteredData.setPredicate(serviceTable -> {

                if(newValue.isEmpty() || newValue.isBlank() || newValue == null)
                {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if(serviceTable.getId().toLowerCase().indexOf(searchKeyword) > -1)
                {
                    return true;
                }
                else if(serviceTable.getName().toLowerCase().indexOf(searchKeyword) > -1)
                {
                    return true;
                }
                else if(serviceTable.getCategory().toLowerCase().indexOf(searchKeyword) > -1)
                {
                    return true;
                }
                else if(serviceTable.getPrice().toLowerCase().indexOf(searchKeyword) > -1)
                {
                    return true;
                }
                else
                {
                    return false;
                }

            });
        });

        SortedList<serviceTable> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(servicesTable.comparatorProperty());

        servicesTable.setItems(sortedData);
    }

    public void GoBack() throws IOException {
        Main m = new Main();
        m.changeScene("afterLogin.fxml");
    }

    public void newService() throws IOException {
        Main m = new Main();
        m.changeScene("new_service.fxml");
    }

    public void searchService() throws IOException {
        Main m = new Main();
        m.changeScene("modify.fxml");
    }
    public void closeWindow()
    {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }




}
