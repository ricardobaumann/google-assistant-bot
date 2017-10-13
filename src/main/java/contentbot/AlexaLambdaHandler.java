package contentbot;

import contentbot.domain.FulfillmentState;
import contentbot.domain.LexRequest;
import contentbot.domain.LexResponse;

import java.util.Map;


public class AlexaLambdaHandler extends AbstractLexRequestHandler {


    @Override
    public LexResponse handleRequest(final LexRequest lexRequest, final Map<String, String> sessionAttributes) {
        return createCloseDialogActionResponse(FulfillmentState.Failed, "Sorry, I'm having a problem fulfilling your request.  Please try again later.");
    }
}
