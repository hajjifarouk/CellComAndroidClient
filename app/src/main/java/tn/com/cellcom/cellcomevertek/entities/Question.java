package tn.com.cellcom.cellcomevertek.entities;

/**
 * Created by farouk on 07/04/2017.
 */

public class Question {
    private String body;
    private String id;

    public Question() {
    }

    public Question(String body, String type) {
        this.body = body;
    }

    public Question(String body, String id, String type) {
        this.body = body;
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
