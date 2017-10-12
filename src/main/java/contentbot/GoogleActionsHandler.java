package contentbot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import contentbot.dto.ApiGatewayResponse;
import contentbot.dto.Input;
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

    ApiGatewayResponse handle(final Input input) throws IOException {
        final JsonNode inputJsonNode = objectMapper.readTree(input.getBody());
        final String contentQuery = inputJsonNode.get("result").get("parameters").get("content").asText();

        return new ApiGatewayResponse(String.format("{\"speech\":\"%s\",\"contextOut\":[],\"data\":{\"google\":{\"expectUserResponse\":false,\"isSsml\":false,\"noInputPrompts\":[]}}}", fetchContent(contentQuery)));
    }

    private String fetchContent(final String contentQuery) {
        return frankRepo.fetchQcus(papyrusRepo.fetchIds(contentQuery)).stream().collect(Collectors.joining(""));
    }

}
