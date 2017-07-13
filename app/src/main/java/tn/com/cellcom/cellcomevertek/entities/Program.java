package tn.com.cellcom.cellcomevertek.entities;

import java.util.List;

/**
 * Created by farouk on 08/07/2017.
 */

public class Program {
    private String id;
    private String user;
    private List<Visit> visits;
    private String date;

    @Override
    public String toString() {
        return "Program{" +
                "id='" + id + '\'' +
                ", user='" + user + '\'' +
                ", visits=" + visits +
                ", date='" + date + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
