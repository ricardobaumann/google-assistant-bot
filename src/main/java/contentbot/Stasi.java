package contentbot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class Stasi implements Reply {

    private static final Pattern PATTERN = Pattern.compile("about '(.*?)'");

    private final RestTemplate stasiRestTemplate;
    private final RestTemplate frankRestTemplate;
    private Map<String, String> reportMap;

    Stasi(@Qualifier("stasiRestTemplate") final RestTemplate stasiRestTemplate,
          @Qualifier("frankRestTemplate") final RestTemplate frankRestTemplate) {
        this.stasiRestTemplate = stasiRestTemplate;
        this.frankRestTemplate = frankRestTemplate;
    }

    @PostConstruct
    void init() {
        final ObjectNode node = stasiRestTemplate.getForObject("/info", ObjectNode.class);
        final ArrayNode reportsArray = (ArrayNode) node.get("cache.reportNames");

        final Set<String> reports = StreamSupport.stream(reportsArray.spliterator(), false)
                .map(JsonNode::asText)
                .collect(Collectors.toSet());

        reportMap = reports.stream().collect(Collectors.toMap(this::friendlyName, Function.identity()));
    }

    @Override
    public Optional<String> replyTo(final String answer) {

        final Matcher matcher = PATTERN.matcher(answer);
        if (matcher.find()) {
            final String tag = matcher.group(1);
            if (reportMap.containsKey(tag)) {
                return fetchReportData(reportMap.get(tag));
            }
        }

        return Optional.empty();
    }

    private Optional<String> fetchReportData(final String reportName) {

        final Set<String> ids = stasiRestTemplate.getForObject("/reports/{reportName}", Set.class, reportName);
        return Optional.of("Take a look: \n" + ids.stream().map(this::fetchUrl).collect(Collectors.joining("\n")));
    }

    private String fetchUrl(final String id) {
        final ObjectNode objectNode = frankRestTemplate.getForObject("/content/{id}", ObjectNode.class, id);

        return "https://welt.de" + objectNode.get("content").get("webUrl").asText();
    }

    @Override
    public String description() {

        final String response = reportMap.keySet().stream().collect(Collectors.joining("\n"));
        return "I have some content groups with these funny names:\n" + response + " \nYou can ask me about any of these. Just type : I wanna know about '" + reportMap.keySet().iterator().next() + "'";
    }

    private String friendlyName(final String reportName) {
        return reportName.replaceAll("api_", "").replaceAll("_", " ");
    }
}
