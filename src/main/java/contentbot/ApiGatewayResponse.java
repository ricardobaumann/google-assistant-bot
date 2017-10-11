package contentbot;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class ApiGatewayResponse {

    private final String body;
    private final Map<String, String> headers;
    private final int statusCode;
    private final boolean base64Encoded;

    private ApiGatewayResponse(final String body, final Map<String, String> headers,
                               final int statusCode, final boolean base64Encoded) {
        this.statusCode = statusCode;
        this.body = body;
        this.headers = Collections.unmodifiableMap(new HashMap<>(headers));
        this.base64Encoded = base64Encoded;
    }

    ApiGatewayResponse(final String answer) {
        this(answer, Collections.singletonMap("Content-Type", "application/json"), 200, false);
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public int getStatusCode() {
        return statusCode;
    }

    // APIGW expects the property to be called "isBase64Encoded"
    public boolean isIsBase64Encoded() {
        return base64Encoded;
    }

}
