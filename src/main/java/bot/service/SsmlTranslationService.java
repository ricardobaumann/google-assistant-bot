package bot.service;

import bot.config.AudioFileProperties;
import bot.dto.ContentSnippet;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SsmlTranslationService {

    private static final String EMPTY_STRING = "";
    private final AudioFileProperties audioFileProperties;

    SsmlTranslationService(final AudioFileProperties audioFileProperties) {
        this.audioFileProperties = audioFileProperties;
    }

    private static final String SSML_HEADER = "<speak xmlns=\"http://www.w3.org/2001/10/synthesis\"\n" +
            "       xmlns:dc=\"http://purl.org/dc/elements/1.1/\"\n" +
            "       version=\"1.0\">\n" +
            "  <metadata>\n" +
            "    <dc:title xml:lang=\"en\">Content qcu summary</dc:title>\n" +
            "  </metadata>\n";

    private static final String SSML_FOOTER = "</speak>";

    private static final String SSML_PARAGRAPH_TEMPLATE =
            "\n" +
                    "  <p>\n" +
                    "    <s xml:lang=\"de-DE\">\n" +
                    "        <emphasis>%s</emphasis> <break time=\"2s\" /> %s <break time=\"2s\" /> %s %s\n" +
                    "    </s>\n" +
                    "  </p>\n" +
                    "\n";

    public String asSSML(final ContentSnippet contentSnippet, String question) {
            return String.format(SSML_HEADER + SSML_PARAGRAPH_TEMPLATE + SSML_FOOTER, contentSnippet.getTopic(), contentSnippet.getIntro(), contentSnippet.getSummary(), question);
    }

    String asSSML(final Set<ContentSnippet> contentSnippets) {
        return SSML_HEADER
                + (contentSnippets.stream()
                .map(contentSnippet -> String.format(SSML_PARAGRAPH_TEMPLATE, contentSnippet.getTopic(), contentSnippet.getIntro(), contentSnippet.getSummary(), EMPTY_STRING))
                .collect(Collectors.joining()))
                + SSML_FOOTER;
    }

    public String getFullAudioSsmlResponse() {
        return SSML_HEADER + String.format("<audio src=\"%s\">This is the full audio file</audio>", getAudioSrcUrl()) + SSML_FOOTER;
    }

    private String getAudioSrcUrl() {
        return String.format("https://s3-%s.amazonaws.com/%s/%s", audioFileProperties.getRegion(), audioFileProperties.getTargetS3Bucket(), audioFileProperties.getFileName());
    }
}
