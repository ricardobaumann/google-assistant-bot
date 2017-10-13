package lex.response;

public class Buttons {
    String text;
    String value;

    public Buttons() {
    }

    public Buttons(final String text, final String value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}
