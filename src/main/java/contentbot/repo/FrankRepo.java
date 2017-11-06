package contentbot.repo;

import com.fasterxml.jackson.databind.JsonNode;
import contentbot.Loggable;
import contentbot.dto.ContentSnippet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@Repository
public class FrankRepo implements Loggable {

    private final RestTemplate restTemplate;
    private final ExecutorService executorService;

    FrankRepo(@Qualifier("frankRestTemplate") final RestTemplate restTemplate,
              final ExecutorService executorService) {
        this.restTemplate = restTemplate;
        this.executorService = executorService;
    }

    public Set<ContentSnippet> fetchContentSnippet(final Set<String> ids) {
        return ids.stream().map(this::getContentSnippet)
                .map(CompletableFuture::join)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    private CompletableFuture<Optional<ContentSnippet>> getContentSnippet(final String id) {
        return CompletableFuture.supplyAsync(() -> fetchContentSnippet(id), executorService);
    }

    private ContentSnippet buildSnippet(final JsonNode responseJsonNode) {
        final JsonNode fields = responseJsonNode.get("content").get("fields");
        return new ContentSnippet(fields.get("topic").asText(),
                fields.get("intro").asText(),
                fields.get("qcuSummary").asText(),
                String.format("https://welt.de/%s",
                        responseJsonNode.get("content").get("webUrl").asText()),
                responseJsonNode.get("content").get("id").asText());
    }

    public Optional<ContentSnippet> fetchContentSnippet(final String id) {
        try {
            final JsonNode responseJsonNode = restTemplate.getForObject("/content/{id}", JsonNode.class, id);
            return Optional.of(buildSnippet(responseJsonNode));
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            logger().error("Failed to retrieve data from content api", e);
            return Optional.empty();
        }
    }
}
