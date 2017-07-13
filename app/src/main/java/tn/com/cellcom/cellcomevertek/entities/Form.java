package tn.com.cellcom.cellcomevertek.entities;

import java.util.List;

/**
 * Created by farouk on 07/04/2017.
 */

public class Form {
    private String id;
    private String ref;
    private Boolean isActive;
    private String title;
    private List<Question> questions;

    @Override
    public String toString() {
        return "Form{" +
                "id='" + id + '\'' +
                ", ref='" + ref + '\'' +
                ", isActive=" + isActive +
                ", title='" + title + '\'' +
                ", questions=" + questions +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
