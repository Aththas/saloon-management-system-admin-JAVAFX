package sample.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class customerTable {
    @Id
    @Column(name = "customerID")
    String id;

    @Column(name = "firstName")
    String fname;

    @Column(name = "lastName")
    String lname;

    @Column(name = "email")
    String email;

    @Column(name = "age")
    String age;

    @Column(name = "mobile")
    String mobile;

    @Column(name = "address")
    String addr;

    @Column(name = "username")
    String uname;

    public customerTable(){}
    public customerTable(String id, String fname, String lname, String addr, String email, String age, String mobile,String uname) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.addr  = addr;
        this.email = email;
        this.age = age;
        this.mobile = mobile;
        this.uname = uname;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
