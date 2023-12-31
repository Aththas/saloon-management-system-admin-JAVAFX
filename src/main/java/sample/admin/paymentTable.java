package sample.admin;

public class paymentTable {
    String bno,cid,bid,billAmount;

    public paymentTable(String bno, String cid, String bid, String billAmount) {
        this.bno = bno;
        this.cid = cid;
        this.bid = bid;
        this.billAmount = billAmount;
    }

    public String getBno() {
        return bno;
    }

    public void setBno(String bno) {
        this.bno = bno;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(String billAmount) {
        this.billAmount = billAmount;
    }
}
