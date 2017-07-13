package tn.com.cellcom.cellcomevertek.entities;

/**
 * Created by farouk on 10/07/2017.
 */

public class Answer {
    String answer;
    String question;

    public Answer(String answer, String question) {
        this.answer = answer;
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
