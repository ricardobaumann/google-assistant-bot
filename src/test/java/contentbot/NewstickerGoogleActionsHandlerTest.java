package contentbot;

import com.google.common.collect.Sets;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import contentbot.dto.ApiGatewayRequest;
import contentbot.dto.ApiGatewayResponse;
import contentbot.dto.ContentSnippet;
import contentbot.service.ContentSnippetService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.Charset;
import java.util.Optional;
import java.util.Set;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class NewstickerGoogleActionsHandlerTest {

    @MockBean
    private ContentSnippetService contentSnippetService;

    @Autowired
    private Gson gson;

    @Autowired
    private NewstickerGoogleActionsHandler newstickerGoogleActionsHandler;

    @Value("classpath:apiai_sample_request.json")
    private Resource sampleJsonRequest;

    private String sampleJsonRequestString;

    private final Set<String> ids = Sets.newHashSet("1", "2", "3");

    private final ContentSnippet snippet1 = new ContentSnippet("topic1", "intro1", "summary1", "url1", "1");

    @Before
    public void setUp() throws Exception {
        when(contentSnippetService.getNextSnippet(anyString())).thenReturn(Optional.ofNullable(snippet1));
        sampleJsonRequestString = Resources.toString(sampleJsonRequest.getURL(), Charset.forName("UTF-8"));
    }

    @Test
    public void shouldDeliverNonReadContentSnippetAudioSSml() throws Exception {
        final ApiGatewayResponse apiGatewayResponse = newstickerGoogleActionsHandler.handle(new ApiGatewayRequest(sampleJsonRequestString));
        final JsonElement responseJsonNode = gson.fromJson(apiGatewayResponse.getBody(), JsonElement.class);
        final JsonArray messages = responseJsonNode.getAsJsonObject().get("messages").getAsJsonArray();
        assertThat(messages).isNotEmpty();
        assertThat(StreamSupport.stream(messages.spliterator(), false)
                .anyMatch(jsonNode -> jsonNode.getAsJsonObject().has("ssml"))).isTrue();
    }

    @Test
    public void shouldDeliverNonReadContentSnippetCardInfo() throws Exception {
        final ApiGatewayResponse apiGatewayResponse = newstickerGoogleActionsHandler.handle(new ApiGatewayRequest(sampleJsonRequestString));
        final JsonElement responseJsonNode = gson.fromJson(apiGatewayResponse.getBody(), JsonElement.class);
        final JsonArray messages = responseJsonNode.getAsJsonObject().get("messages").getAsJsonArray();
        assertThat(messages).isNotEmpty();
        assertThat(StreamSupport.stream(messages.spliterator(), false)
                .anyMatch(jsonNode -> jsonNode.getAsJsonObject().has("buttons"))).isTrue();
    }

}