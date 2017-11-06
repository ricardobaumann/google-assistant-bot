package contentbot.repo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import contentbot.Loggable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class PapyrusRepo implements Loggable {

    private final RestTemplate restTemplate;

    PapyrusRepo(@Qualifier("papyrusRestTemplate") final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Set<String> fetchIds(final int limit) {

        try {
            final JsonNode responseNode = restTemplate.getForObject("/{source}", JsonNode.class, mapInput());
            final ArrayNode articlesArray = (ArrayNode) responseNode.get(0).get("articles");

            return StreamSupport.stream(articlesArray.spliterator(), false)
                    .map(jsonNode -> jsonNode.get("id").asText())
                    .limit(limit)
                    .collect(Collectors.toSet());
        } catch (final Exception e) {
            logger().error("Failed to fetch content from papyrus", e);
            return Collections.emptySet();
        }
    }

    private Object mapInput() {
        return "newsticker";
    }
}
