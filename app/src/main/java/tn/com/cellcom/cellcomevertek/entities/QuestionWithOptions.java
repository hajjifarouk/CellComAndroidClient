package tn.com.cellcom.cellcomevertek.entities;

import java.util.List;

/**
 * Created by farouk on 10/04/2017.
 */

public class QuestionWithOptions extends Question {
    private List<Option> options;
    private Boolean multiple;

    public QuestionWithOptions() {
    }

    public QuestionWithOptions(String body, String type, List<Option> options, Boolean multiple) {
        super(body, type);
        this.options = options;
        this.multiple = multiple;
    }

    public QuestionWithOptions(String body, String type) {
        super(body, type);
    }


    public QuestionWithOptions(String body, String id, String type, List<Option> options, Boolean multiple) {
        super(body, id, type);
        this.options = options;
        this.multiple = multiple;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public Boolean getMultiple() {
        return multiple;
    }

    public void setMultiple(Boolean multiple) {
        this.multiple = multiple;
    }
}
