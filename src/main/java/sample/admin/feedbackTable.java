package sample.admin;

public class feedbackTable {
    String fno,cid,sid,feedback,rating;

    public feedbackTable(String fno, String cid, String sid, String feedback, String rating) {
        this.fno = fno;
        this.cid = cid;
        this.sid = sid;
        this.feedback = feedback;
        this.rating = rating;
    }

    public String getFno() {
        return fno;
    }

    public void setFno(String fno) {
        this.fno = fno;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
