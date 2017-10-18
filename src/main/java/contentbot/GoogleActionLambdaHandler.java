package contentbot;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import contentbot.dto.ApiGatewayRequest;
import contentbot.dto.ApiGatewayResponse;

import java.io.IOException;

import static contentbot.ContentBotApplication.getApplicationContext;

public class GoogleActionLambdaHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse>, Loggable {

    @Override
    public ApiGatewayResponse handleRequest(final ApiGatewayRequest apiGatewayRequest, final Context context) {

        logger().info("Handling {}", apiGatewayRequest);
        try {
            return getApplicationContext().getBean(NewstickerGoogleActionsHandler.class).handle(apiGatewayRequest);
        } catch (final IOException e) {
            return new ApiGatewayResponse("{\"message\" : \"error\"}");
        }

    }


}
