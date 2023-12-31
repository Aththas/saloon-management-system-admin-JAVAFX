package sample.admin;
import javax.persistence.*;

@Entity
@Table(name = "servicenew")
public class serviceTable {
    @Id
    @Column(name = "ServiceID")
    String id;

    @Column(name = "Service_Name")
    String name;

    @Column(name = "price")
    String price;

    @Column(name = "category")
     String category;

    @Column(name = "image")
    String image;
    public serviceTable() {}

    public serviceTable(String id, String name, String price, String category, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
