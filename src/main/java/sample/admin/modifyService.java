package sample.admin;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import sample.admin.util.serviceUtil;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class modifyService implements Initializable {

    @FXML
    private Button back;
    @FXML
    private Button clear;
    @FXML
    private Button update;
    @FXML
    private Button search;
    @FXML
    private Button delete;
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
    @FXML
    private ImageView pic;
    private static final SessionFactory SESSION_FACTORY = serviceUtil.getSessionFactory();

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
        imgPath.setText("");
        category.setValue("");
        lab.setText("");
        pic.setImage(null);
    }

    public void search_service()
    {
        databaseConnection c = new databaseConnection();
        Connection con = c.getConnection();
        try {
            Statement st = con.createStatement();
            ResultSet val = st.executeQuery("select ServiceID from ServiceNew");
            int found = -1;
            while(val.next())
            {
                String sid = val.getString("ServiceID");

                if(id.getText().equals(sid))
                {
                    found++;
                }
            }
            st.close();

            if(found != -1) {

                Session session = SESSION_FACTORY.openSession();
                Transaction tx = session.beginTransaction();
                serviceTable s = session.get(serviceTable.class, id.getText());
                name.setText(s.getName());
                price.setText(s.getPrice());
                category.setValue(s.getCategory());
                imgPath.setText(s.getImage());

                Image image = new Image(s.getImage());
                pic.setImage(image);


                tx.commit();
                session.close();
                /*
                Statement s = con.createStatement();
                ResultSet val1 = s.executeQuery("select * from ServiceNew where ServiceID ='" + id.getText() + "'");
                while (val1.next()) {
                    String sid = val1.getString("ServiceID");
                    String sname = val1.getString("Service_Name");
                    String sprice = val1.getString("price");
                    String scategory = val1.getString("category");
                    String img = val1.getString("image");

                    id.setText(sid);
                    name.setText(sname);
                    price.setText(sprice);
                    category.setValue(scategory);
                    imgPath.setText(img);


                    //to retrive pics, pic is imageviewver ID
                    Image image = new Image(img);
                    pic.setImage(image);

                }
                lab.setText("");
                */
            }
            else
            {
                lab.setText("Error: Service not found!");
                name.setText("");
                price.setText("");
                imgPath.setText("");
                category.setValue("");
                pic.setImage(null);
            }
        }catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }

    public void delete_service()
    {
        try {

            databaseConnection c = new databaseConnection();
            Connection con = c.getConnection();
            Statement st = con.createStatement();
            ResultSet val = st.executeQuery("select ServiceID from ServiceNew");
            int count = 0;
            while(val.next())
            {
                String sid = val.getString("ServiceID");

                if(id.getText().equals(sid))
                {
                    count++;
                }
            }

            if(count != 0)
            {
                Session session = SESSION_FACTORY.openSession();
                Transaction tx = session.beginTransaction();
                serviceTable s = session.get(serviceTable.class, id.getText());
                session.delete(s);
                tx.commit();
                session.close();
                lab.setText("DATA DELETED SUCCESSFULLY!!!");
                clearText();
            }
            else
            {
                lab.setText("Error: Service not found!");
                name.setText("");
                price.setText("");
                imgPath.setText("");
                category.setValue("");
                pic.setImage(null);
            }



        }catch (Exception e){
            e.printStackTrace();
        }
        /*
        databaseConnection c = new databaseConnection();
        Connection con = c.getConnection();
        try {
            Statement st = con.createStatement();
            ResultSet val = st.executeQuery("select ServiceID from ServiceNew");
            int found = -1;
            while(val.next())
            {
                String sid = val.getString("ServiceID");

                if(id.getText().equals(sid))
                {
                    found++;
                }
            }
            st.close();
            Statement s = con.createStatement();
            if(found != -1)
            {
                int val1 = s.executeUpdate("delete from ServiceNew where ServiceID = '"+id.getText()+"'");
                lab.setText("DATA DELETED SUCCESSFULLY!!!");
                clearText();
            }
            else
            {
                lab.setText("Error: Service not found!");
                name.setText("");
                price.setText("");
                imgPath.setText("");
                category.setValue("");
                pic.setImage(null);
            }
        }catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        */
    }

    public void update_service()
    {
        try {

            databaseConnection c = new databaseConnection();
            Connection con = c.getConnection();
            Statement st = con.createStatement();
            ResultSet val = st.executeQuery("select ServiceID from ServiceNew");
            int count = 0;
            while(val.next())
            {
                String sid = val.getString("ServiceID");

                if(id.getText().equals(sid))
                {
                    count++;
                }
            }

            addservices as = new addservices();
            boolean a = as.isDouble(price);

            if(a == false)
            {
                lab.setText("Error: Invalid Price!!!");
            }
            else if(id.getText().isEmpty() || name.getText().isEmpty() || price.getText().isEmpty() || category.getValue().isEmpty() || imgPath.getText().isEmpty())
            {
                lab.setText("Error: Fields Can't be empty!");
            }
            else if(count != 0)
            {
                Session session = SESSION_FACTORY.openSession();
                Transaction tx = session.beginTransaction();
                serviceTable s = session.get(serviceTable.class, id.getText());
                s.setName(name.getText());
                s.setPrice(price.getText());
                s.setCategory(category.getValue());
                s.setImage(imgPath.getText());

                session.update(s);
                tx.commit();
                session.close();
                lab.setText("DATA UPDATED SUCCESSFULLY!!!");
                clearText();
            }
            else
            {
                lab.setText("Error: Service not found!");
                name.setText("");
                price.setText("");
                category.setValue("");
                imgPath.setText("");
                pic.setImage(null);
            }



        }catch (Exception e){
            e.printStackTrace();
        }


       /*
        databaseConnection c = new databaseConnection();
        Connection con = c.getConnection();
        try {
            Statement st = con.createStatement();
            ResultSet val = st.executeQuery("select ServiceID from ServiceNew");
            int found = -1;
            while(val.next())
            {
                String sid = val.getString("ServiceID");

                if(id.getText().equals(sid))
                {
                    found++;
                }
            }
            st.close();
            Statement s = con.createStatement();
            if(found != -1)
            {
                if(!(id.getText().isEmpty() || name.getText().isEmpty() || price.getText().isEmpty() || category.getValue().isEmpty() || imgPath.getText().isEmpty()))
                {
                    int value = s.executeUpdate("update ServiceNew set Service_Name = '" + name.getText() + "', price = '" + price.getText() + "', category = '" + category.getValue() + "', image = '" + imgPath.getText() + "' where ServiceID = '"+id.getText()+"'");

                    st.close();
                    lab.setText("DATA UPDATED SUCCESSFULLY!!!");
                    clearText();
                }
                else
                {
                    lab.setText("Error: Fields Can't be empty!");
                }
            }
            else
            {
                lab.setText("Error: Service not found!");
                name.setText("");
                price.setText("");
                category.setValue("");
                imgPath.setText("");
                pic.setImage(null);
            }
        }catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        */
    }

    public void clearText()
    {
        id.setText("");
        name.setText("");
        price.setText("");
        category.setValue("");
        imgPath.setText("");
        pic.setImage(null);

    }

}

