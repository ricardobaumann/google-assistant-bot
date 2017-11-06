package bot.config;

import ai.api.GsonFactory;
import com.amazonaws.services.lambda.AWSLambdaAsync;
import com.amazonaws.services.lambda.AWSLambdaAsyncClientBuilder;
import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriTemplateHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ContentBotConfig {

    @Bean(name = "frankRestTemplate")
    RestTemplate frankRestTemplate(final FrankProperties frankProperties) {
        return createFrom(frankProperties.getUsername(), frankProperties.getPassword(), frankProperties.getBaseUrl());
    }

    @Bean(name = "papyrusRestTemplate")
    RestTemplate papyrusRestTemplate(final PapyrusProperties papyrusProperties) {
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("x-api-key", papyrusProperties.getApiKey());
            return execution.execute(request, body);
        });
        final DefaultUriTemplateHandler uriTemplateHandler = new DefaultUriTemplateHandler();
        uriTemplateHandler.setBaseUrl(papyrusProperties.getBaseUrl());
        restTemplate.setUriTemplateHandler(uriTemplateHandler);
        return restTemplate;
    }

    @Bean
    Gson gson() {
        return GsonFactory.getDefaultFactory().getGson();
    }

    @Bean
    ExecutorService executorService() {
        return Executors.newFixedThreadPool(10);
    }

    @Bean
    AWSLambdaAsync lambda() {
        return AWSLambdaAsyncClientBuilder.standard().build();
    }

    private RestTemplate createFrom(final String username, final String password, final String baseUrl) {
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(username, password));
        final DefaultUriTemplateHandler uriTemplateHandler = new DefaultUriTemplateHandler();
        uriTemplateHandler.setBaseUrl(baseUrl);
        restTemplate.setUriTemplateHandler(uriTemplateHandler);
        return restTemplate;
    }
}
