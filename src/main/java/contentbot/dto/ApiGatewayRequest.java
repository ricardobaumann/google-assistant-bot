package contentbot.dto;

public class ApiGatewayRequest {
    private String body;

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

    @Override
    public String toString() {
        return "ApiGatewayRequest{" +
                "body='" + body + '\'' +
                '}';
    }
}
