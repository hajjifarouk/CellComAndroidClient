package tn.com.cellcom.cellcomevertek.entities;

import android.graphics.Bitmap;

/**
 * Created by farouk on 10/04/2017.
 */

public class Option {
    private String id;
    private String text;
    private String Value;
    private Bitmap img;

    public Option() {
    }

    public Option(String text, String value, Bitmap img) {
        this.text = text;
        Value = value;
        this.img = img;
    }

    public Option(String id, String text, String value, Bitmap img) {
        this.id = id;
        this.text = text;
        Value = value;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }
}
