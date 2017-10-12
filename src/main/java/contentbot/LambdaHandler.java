package contentbot;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import contentbot.dto.ApiGatewayResponse;
import contentbot.dto.Input;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class LambdaHandler implements RequestHandler<Input, ApiGatewayResponse>, Loggable {

    private static ConfigurableApplicationContext applicationContext;

    @Override
    public ApiGatewayResponse handleRequest(final Input input, final Context context) {

        logger().info("Handling {}", input);
        try {
            return getApplicationContext().getBean(GoogleActionsHandler.class).handle(input);
        } catch (final IOException e) {
            return new ApiGatewayResponse("{\"message\" : \"error\"}");
        }

    }

    private static ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            applicationContext = SpringApplication.run(LambdaHandler.class);
        }
        return applicationContext;
    }

    public static void main(final String[] args) throws InterruptedException, IOException {
        getApplicationContext();
    }

}
