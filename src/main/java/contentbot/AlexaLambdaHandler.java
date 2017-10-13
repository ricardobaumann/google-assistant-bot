package contentbot;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import lex.response.DialogAction;
import lex.response.LexResponse;
import lex.response.Message;

public class AlexaLambdaHandler implements RequestHandler<LexRequest, LexResponse> {

    @Override
    public LexResponse handleRequest(final LexRequest input, final Context context) {

        final String example = "{\n" +
                "  \"version\": \"1.0\",\n" +
                //"  \"sessionAttributes\": {\n" +
                //"    \"string\": \"<object>\"\n" +
                //"  },\n" +
                "  \"response\": {\n" +
                "    \"outputSpeech\": {\n" +
                "      \"type\": \"PlainText\",\n" +
                "      \"text\": \"this is my text output speech\"\n" +
                //"      \"ssml\": \"string\"\n" +
                "    },\n" +
                //"    \"card\": {\n" +
                //"      \"type\": \"string\",\n" +
                //"      \"title\": \"string\",\n" +
                //"      \"content\": \"string\",\n" +
                //"      \"text\": \"string\",\n" +
                //"      \"image\": {\n" +
                //"        \"smallImageUrl\": \"string\",\n" +
                //"        \"largeImageUrl\": \"string\"\n" +
                //"      }\n" +
                //"    },\n" +
                //"    \"reprompt\": {\n" +
                //"      \"outputSpeech\": {\n" +
                //"        \"type\": \"string\",\n" +
                //"        \"text\": \"string\",\n" +
                //"        \"ssml\": \"string\"\n" +
                //"      }\n" +
                //"    },\n" +
                //"    \"directives\": [\n" +
                //"      {\n" +
                //"        \"type\": \"Display.RenderTemplate\",\n" +
                //"        \"template\": {\n" +
                //"          \"type\": \"string\"\n" +
                //"\t\t  ...\n" +
                //"        }\n" +
                //"      },\n" +
                // "      {\n" +
                // "        \"type\": \"AudioPlayer\",\n" +
                //"        \"playBehavior\": \"string\",\n" +
                //"        \"audioItem\": {\n" +
                //"          \"stream\": {\n" +
                //"            \"token\": \"string\",\n" +
                //"            \"url\": \"string\",\n" +
                //"            \"offsetInMilliseconds\": 0\n" +
                //"          }\n" +
                //"        }\n" +
                //"      },\n" +
                //"      {\n" +
                //"        \"general\": {\n" +
                //"          \"type\": \"VideoApp.Launch\",\n" +
                //"          \"videoItem\": {\n" +
                //"            \"source\": \"string\",\n" +
                //"            \"metadata\": {\n" +
                //"              \"title\": \"string\",\n" +
                //"              \"subtitle\": \"string\"\n" +
                //"            }\n" +
                //"          }\n" +
                //"        }\n" +
                //"      }\n" +
                //"    ],\n" +
                "    \"shouldEndSession\": true\n" +
                "  }\n" +
                "}\n";
        final DialogAction dialogAction = new DialogAction("ConfirmIntent", DialogAction.FULFILLMENT_STATE_FULFILLED, new Message(Message.CONTENT_TYPE_PLAIN_TEXT, "my content"));
        dialogAction.setIntentName("CheckNews");
        return new LexResponse(dialogAction);
    }
}
