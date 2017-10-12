package contentbot;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
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

    public Set<String> fetchQcus(final Set<String> ids) {

        return ids.stream().map(this::getQcuFuture)
                .map(CompletableFuture::join)
                .filter(Optional::isPresent).map(Optional::get)
                .collect(Collectors.toSet());
    }

    private CompletableFuture<Optional<String>> getQcuFuture(final String id) {
        return CompletableFuture.supplyAsync(() -> {
            final JsonNode responseJsonNode = restTemplate.getForObject("/content/{id}", JsonNode.class, id);
            return Optional.ofNullable(responseJsonNode.get("content").get("fields").get("qcuSummary").asText());
        }, executorService)
                .exceptionally(throwable -> {
                    logger().error("Failed to fetch content for {}", id, throwable);
                    return Optional.empty();
                });
    }
}
