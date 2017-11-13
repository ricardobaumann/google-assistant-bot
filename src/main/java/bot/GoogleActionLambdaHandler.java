package bot;

import bot.dto.ApiGatewayRequest;
import bot.dto.ApiGatewayResponse;
import bot.service.NewstickerService;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.io.IOException;

import static bot.ContentBotApplication.getApplicationContext;

public class GoogleActionLambdaHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse>, Loggable {

    @Override
    public ApiGatewayResponse handleRequest(final ApiGatewayRequest apiGatewayRequest, final Context context) {

        logger().info("Handling {}", apiGatewayRequest);
        if (apiGatewayRequest.isKeepItWarm()) {
            return new ApiGatewayResponse("{\"warm\" : \"ok\"}");
        }
        try {
            return getApplicationContext().getBean(NewstickerService.class).handle(apiGatewayRequest);
        } catch (final IOException e) {
            return new ApiGatewayResponse("{\"message\" : \"error\"}");
        }

    }


}