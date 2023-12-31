package sample.admin;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import sample.admin.util.serviceUtil;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class addservices implements Initializable {

    @FXML
    private Button back;
    @FXML
    private Button clear;
    @FXML
    private Button addService;
    @FXML
    private Button addImg;
    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField price;
    @FXML
    private ComboBox<String> category;
    @FXML
    private Label close;
    @FXML
    private Label lab;
    @FXML
    private Label imgPath;
    private String imageUrl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        category.setItems(FXCollections.observableArrayList("Hair Cut", "Beard Cut", "Coloring", "Massaage","Facial"));

    }


    final FileChooser fc = new FileChooser();
    public void addImage()
    {
        fc.setTitle("Select a Service Image");

        fc.setInitialDirectory(new File(System.getProperty("user.home")));

        File file = fc.showOpenDialog(null);
        if(file != null)
        {
            try {
                imageUrl = file.toURI().toURL().toExternalForm();
                imgPath.setText(imageUrl);
            } catch (MalformedURLException ex) {
                throw new IllegalStateException(ex);
            }
        }
        else
        {
            imgPath.setText("Invalid File");
        }
    }

    public void GoBack() throws IOException {
        Main m = new Main();
        m.changeScene("product.fxml");
    }

    public void closeWindow()
    {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    public void clearFields()
    {
        id.setText("");
        name.setText("");
        price.setText("");
        lab.setText("");
        imgPath.setText("");
        category.setValue("");
    }
    public boolean isDouble(TextField f)
    {
        try
        {
            Double.parseDouble(f.getText());
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }
    private static final SessionFactory SESSION_FACTORY = serviceUtil.getSessionFactory();
    public void add()
    {
        boolean a = isDouble(price);

        if(a == false)
        {
            lab.setText("Error: Invalid Price!!!");
        }
        else if(!(id.getText().isEmpty() || name.getText().isEmpty() || price.getText().isEmpty() || category.getValue().isEmpty() || imgPath.getText().isEmpty()))
        {
            try {
                int count = 0;

                databaseConnection c = new databaseConnection();
                Connection con = c.getConnection();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select ServiceID from ServiceNew");
                while (rs.next())
                {
                    String sid = rs.getString("ServiceID");
                    if(id.getText().equals(sid))
                    {
                        count++;
                    }
                }

                if(count == 0)
                {
                    Session session = SESSION_FACTORY.openSession();
                    Transaction tx = session.beginTransaction();
                    serviceTable s = new serviceTable();
                    s.setId(id.getText());
                    s.setName(name.getText());
                    s.setPrice(price.getText());
                    s.setCategory(category.getValue());
                    s.setImage(imgPath.getText());

                    session.save(s);
                    tx.commit();
                    session.close();
                    lab.setText("DATA ADDED SUCCESSFULLY!!!");
                    clearTexts();
                }
                else
                {
                    lab.setText("Error: Service ID " +id.getText()+ " is already exist!");
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else
        {
            lab.setText("Error: Fields can't be empty!");
        }


 /*
        databaseConnection c = new databaseConnection();
        Connection con = c.getConnection();
        try {
            Statement st = con.createStatement();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select ServiceID from ServiceNew");
            int found = -1;
                while (rs.next())
                {
                    String sid = rs.getString("ServiceID");
                    if(id.getText().equals(sid))
                    {
                        found++;
                    }
                }
                if(!(id.getText().isEmpty() || name.getText().isEmpty() || price.getText().isEmpty() || category.getValue().isEmpty() || imgPath.getText().isEmpty()))
                {
                    if(found == -1) {
                        int val = st
                                .executeUpdate("INSERT ServiceNew VALUES('" + id.getText() + "','" + name.getText() + "','" + price
                                        .getText() + "','" + category.getValue() + "','" + imgPath.getText() + "')");
                        st.close();
                        lab.setText("DATA ADDED SUCCESSFULLY!!!");
                        clearTexts();
                    }
                    else
                    {
                        lab.setText("Error: Service ID " +id.getText()+ " is already exist!");
                        clearTexts();
                    }
                }
                else
                {
                    lab.setText("Error: Fields can't be empty!");
                }

        }catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
*/
    }

    public void clearTexts()
    {
        id.setText("");
        name.setText("");
        price.setText("");
        imgPath.setText("");
        category.setValue("");
    }

}
