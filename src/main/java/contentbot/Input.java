package contentbot;

public class Input {
    private String body;

    public Input(final String body) {
        this.body = body;
    }

    public Input() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Input{" +
                "body='" + body + '\'' +
                '}';
    }
}
