package contentbot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GoogleActionsHandler {

    private final ObjectMapper objectMapper;

    GoogleActionsHandler(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    ApiGatewayResponse handle(final Input input) throws IOException {
        final JsonNode inputJsonNode = objectMapper.readTree(input.getBody());
        final String contentQuery = inputJsonNode.get("originalRequest").get("data").get("inputs").get(0).get("arguments").get(0).get("textValue").asText();
        return new ApiGatewayResponse(String.format("{\"speech\":\"you said %s\",\"contextOut\":[],\"data\":{\"google\":{\"expectUserResponse\":false,\"isSsml\":false,\"noInputPrompts\":[]}}}", contentQuery));
    }

}
