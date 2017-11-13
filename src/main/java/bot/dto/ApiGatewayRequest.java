package bot.dto;

public class ApiGatewayRequest {
    private String body;
    private boolean keepItWarm;

    public ApiGatewayRequest(final String body) {
        this.body = body;
    }

    public ApiGatewayRequest() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public boolean isKeepItWarm() {
        return keepItWarm;
    }

    public void setKeepItWarm(boolean keepItWarm) {
        this.keepItWarm = keepItWarm;
    }

    @Override
    public String toString() {
        return "ApiGatewayRequest{" +
                "body='" + body + '\'' +
                '}';
    }
}
