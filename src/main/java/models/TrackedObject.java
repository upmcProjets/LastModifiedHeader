package models;


import javax.persistence.*;
import java.util.Date;

public class TrackedObject {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String etag;
    private Date date ;

    public TrackedObject() {
    }


    public String getEtag() {
        return etag;
    }
    public void setEtag(String etag) {
        this.etag = etag;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
