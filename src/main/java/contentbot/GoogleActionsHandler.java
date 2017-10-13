package contentbot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import contentbot.dto.ApiGatewayRequest;
import contentbot.dto.ApiGatewayResponse;
import contentbot.repo.FrankRepo;
import contentbot.repo.PapyrusRepo;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.stream.Collectors;

@Component
public class GoogleActionsHandler {

    private final ObjectMapper objectMapper;
    private final PapyrusRepo papyrusRepo;
    private final FrankRepo frankRepo;

    GoogleActionsHandler(final ObjectMapper objectMapper,
                         final PapyrusRepo papyrusRepo,
                         final FrankRepo frankRepo) {
        this.objectMapper = objectMapper;
        this.papyrusRepo = papyrusRepo;
        this.frankRepo = frankRepo;
    }

    ApiGatewayResponse handle(final ApiGatewayRequest apiGatewayRequest) throws IOException {
        final JsonNode inputJsonNode = objectMapper.readTree(apiGatewayRequest.getBody());
        final String contentQuery = inputJsonNode.get("result").get("parameters").get("content").asText();

        return new ApiGatewayResponse("{\"speech\" : \"hello\", \"contextOut\":[],\"data\":{\"google\":{\"expectUserResponse\":false,\"isSsml\":false,\"noInputPrompts\":[]}}}");
    }

    private String fetchContent(final String contentQuery) {
        return frankRepo.fetchContentSnippet(papyrusRepo.fetchIds(contentQuery)).stream().collect(Collectors.joining(""));
    }

}
