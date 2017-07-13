package tn.com.cellcom.cellcomevertek.entities;

import java.util.List;

/**
 * Created by farouk on 10/07/2017.
 */

public class Report {

    String ref;
    String date;
    User user;
    List<Answer> answers;
    Form form;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    @Override
    public String toString() {
        return "Report{" +
                "ref='" + ref + '\'' +
                ", date='" + date + '\'' +
                ", user=" + user +
                ", answers=" + answers +
                ", form=" + form +
                '}';
    }
}
