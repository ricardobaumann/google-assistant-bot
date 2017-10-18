package contentbot;

import ai.api.model.Fulfillment;
import ai.api.model.GoogleAssistantResponseMessages;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import contentbot.dto.ApiGatewayRequest;
import contentbot.dto.ApiGatewayResponse;
import contentbot.dto.ContentSnippet;
import contentbot.repo.FrankRepo;
import contentbot.repo.PapyrusRepo;
import contentbot.repo.SessionNewstickerStepRepo;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class NewstickerGoogleActionsHandler implements Loggable {

    private static final String SSML_TEMPLATE = "<speak xmlns=\"http://www.w3.org/2001/10/synthesis\"\n" +
            "       xmlns:dc=\"http://purl.org/dc/elements/1.1/\"\n" +
            "       version=\"1.0\">\n" +
            "  <metadata>\n" +
            "    <dc:title xml:lang=\"en\">Content qcu summary</dc:title>\n" +
            "  </metadata>\n" +
            "\n" +
            "  <p>\n" +
            "    <s xml:lang=\"de-DE\">\n" +
            "      <voice name=\"David\" gender=\"male\" age=\"25\">\n" +
            "        <emphasis>%s</emphasis> <break time=\"2s\" /> %s <break time=\"2s\" /> %s\n" +
            "      </voice>\n" +
            "    </s>\n" +
            "  </p>\n" +
            "\n" +
            "</speak>";

    private final PapyrusRepo papyrusRepo;
    private final FrankRepo frankRepo;
    private final SessionNewstickerStepRepo sessionNewstickerStepRepo;
    private final Gson gson;

    NewstickerGoogleActionsHandler(final PapyrusRepo papyrusRepo,
                                   final FrankRepo frankRepo,
                                   final SessionNewstickerStepRepo sessionNewstickerStepRepo,
                                   final Gson gson) {
        this.papyrusRepo = papyrusRepo;
        this.frankRepo = frankRepo;
        this.sessionNewstickerStepRepo = sessionNewstickerStepRepo;
        this.gson = gson;
    }

    ApiGatewayResponse handle(final ApiGatewayRequest apiGatewayRequest) throws IOException {
        final JsonElement jsonElement = gson.fromJson(apiGatewayRequest.getBody(), JsonElement.class);
        final String sessionId = jsonElement.getAsJsonObject().get("sessionId").getAsString();
        final Fulfillment fulfillment = new Fulfillment();
        final GoogleAssistantResponseMessages.ResponseChatBubble chatBubble = new GoogleAssistantResponseMessages.ResponseChatBubble();
        chatBubble.setCustomizeAudio(true);
        final Set<ContentSnippet> snippets = fetchContent();
        final Optional<ContentSnippet> contentSnippetOptional = snippets
                .stream()
                .filter(cs -> !sessionNewstickerStepRepo.getReadIds(sessionId).contains(cs.getId())).findFirst();

        if (contentSnippetOptional.isPresent()) {
            final ContentSnippet contentSnippet = contentSnippetOptional.get();
            logger().info("Delivering snippet: {}", contentSnippet.getId());
            final GoogleAssistantResponseMessages.ResponseChatBubble.Item item = new GoogleAssistantResponseMessages.ResponseChatBubble.Item();
            item.setSsml(String.format(SSML_TEMPLATE,
                    contentSnippet.getTopic(), contentSnippet.getIntro(), contentSnippet.getSummary()));
            chatBubble.setItems(Collections.singletonList(item));

            final GoogleAssistantResponseMessages.ResponseBasicCard responseBasicCard = new GoogleAssistantResponseMessages.ResponseBasicCard();
            responseBasicCard.setTitle(contentSnippet.getTopic());
            responseBasicCard.setSubtitle(contentSnippet.getIntro());
            responseBasicCard.setFormattedText(contentSnippet.getSummary());
            final GoogleAssistantResponseMessages.ResponseBasicCard.Button button = new GoogleAssistantResponseMessages.ResponseBasicCard.Button();
            button.setTitle("Check it on welt");
            final GoogleAssistantResponseMessages.ResponseBasicCard.OpenUrlAction action = new GoogleAssistantResponseMessages.ResponseBasicCard.OpenUrlAction();
            action.setUrl(contentSnippet.getUrl());
            button.setOpenUrlAction(action);
            responseBasicCard.setButtons(Collections.singletonList(button));
            fulfillment.setMessages(Arrays.asList(chatBubble, responseBasicCard));
            sessionNewstickerStepRepo.markAsRead(sessionId, contentSnippet.getId());

        } else {
            final GoogleAssistantResponseMessages.ResponseChatBubble.Item item = new GoogleAssistantResponseMessages.ResponseChatBubble.Item();
            item.setTextToSpeech("I do not have more content. Try again later");
            final Map<String, Boolean> map = Collections.singletonMap("expectUserResponse", false);
            if (fulfillment.getData() == null) {
                fulfillment.setData(new HashMap<>());
            }
            fulfillment.getData().put("google", gson.toJsonTree(map));
            chatBubble.setItems(Collections.singletonList(item));

            fulfillment.setMessages(Collections.singletonList(chatBubble));
        }

        return new ApiGatewayResponse(gson.toJson(fulfillment));
    }


    private Set<ContentSnippet> fetchContent() {
        return frankRepo.fetchContentSnippet(papyrusRepo.fetchIds());
    }

}
