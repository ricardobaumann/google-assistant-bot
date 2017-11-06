package bot.service;

import bot.Loggable;
import bot.config.AudioFileProperties;
import bot.config.AudioLambdaProperties;
import bot.dto.AudioLambdaInput;
import bot.dto.ContentSnippet;
import com.amazonaws.services.lambda.AWSLambdaAsync;
import com.amazonaws.services.lambda.model.InvocationType;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AudioFileService implements Loggable {

    private static final int LIMIT = 3;
    private final ContentSnippetService contentSnippetService;
    private final SsmlTranslationService ssmlTranslationService;
    private final AWSLambdaAsync lambda;
    private final AudioLambdaProperties audioLambdaProperties;
    private final AudioFileProperties audioFileProperties;
    private final ObjectMapper objectMapper;

    AudioFileService(final ContentSnippetService contentSnippetService,
                     final SsmlTranslationService ssmlTranslationService,
                     final AWSLambdaAsync lambda,
                     final AudioLambdaProperties audioLambdaProperties,
                     final AudioFileProperties audioFileProperties,
                     final ObjectMapper objectMapper) {
        this.contentSnippetService = contentSnippetService;
        this.ssmlTranslationService = ssmlTranslationService;
        this.lambda = lambda;
        this.audioLambdaProperties = audioLambdaProperties;
        this.audioFileProperties = audioFileProperties;
        this.objectMapper = objectMapper;
    }

    public void generateAudioFile() {
        final Set<ContentSnippet> snippets = contentSnippetService.getContentSnippets(LIMIT);
        final String ssml = ssmlTranslationService.asSSML(snippets);
        try {
            final InvokeRequest invokeRequest = new InvokeRequest()
                    .withFunctionName(audioLambdaProperties.getFunctionName())
                    .withInvocationType(InvocationType.RequestResponse)
                    .withPayload(objectMapper.writeValueAsString(new AudioLambdaInput(ssml, audioFileProperties.getTargetS3Bucket(), "audio.mp3", audioFileProperties.getRegion())));

            final InvokeResult result = lambda.invoke(invokeRequest);
            logger().info("Lambda invoked with result status {} and payload {}", result.getStatusCode(), new String(result.getPayload().array()));

        } catch (final JsonProcessingException e) {
            logger().error("Failed to generate audio file", e);
        }

    }

}
