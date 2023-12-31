module sample.admin {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires java.desktop;
    requires java.persistence;
    requires java.naming;

    opens sample.admin to javafx.fxml, org.hibernate.orm.core;
    exports sample.admin;

}