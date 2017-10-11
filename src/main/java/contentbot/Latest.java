package contentbot;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class Latest implements Reply {

    private static final String PARAMS = "/_search?type=-out-of-home,-gallery,-live,-external,-oembed&subType=-advertorial,-external,-oembed,-printimport,-ticker&sectionPath=/politik/|/finanzen/|/sport/|/wirtschaft/|/wissenschaft/|/gesundheit/|/vermischtes/|/kultur/|/icon/|/motor/|/reise/|/regionales/|/debatte/|/satire/|/kmpkt/|/videos/|/geschichte/";
    private final RestTemplate restTemplate;

    Latest(@Qualifier("frankRestTemplate") final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<String> replyTo(final String answer) {
        if (answer.toLowerCase().contains("going on")) {
            return Optional.of("There are always lots of things going on. Check it out: \n" + fetchResponse());
        }
        return Optional.empty();
    }

    private String fetchResponse() {
        final ObjectNode response = restTemplate.getForObject(PARAMS, ObjectNode.class);
        final ArrayNode results = (ArrayNode) response.get("response").get("results");
        return StreamSupport.stream(results.spliterator(), false).map(jsonNode -> jsonNode.get("webUrl").asText()).map(s -> "https://welt.de/".concat(s)).collect(Collectors.joining("\n"));
    }

    @Override
    public String description() {
        return null;
    }
}
