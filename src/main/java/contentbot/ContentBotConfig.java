package contentbot;

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
    RestTemplate frankRestTemplate() {
        return createFrom("biggus", "dickus", "https://frank-ecs-production.up.welt.de");
    }

    @Bean(name = "stasiRestTemplate")
    RestTemplate stasiRestTemplate() {
        return createFrom("biggus", "dickus", "https://stasi-ecs-production.up.welt.de");
    }

    @Bean(name = "papyrusRestTemplate")
    RestTemplate papyrusRestTemplate() {
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("x-api-key", "1yEZZUEyIUXfErUlXlpb8AvDWR4J44a6SHMQMOP8");
            return execution.execute(request, body);
        });
        final DefaultUriTemplateHandler uriTemplateHandler = new DefaultUriTemplateHandler();
        uriTemplateHandler.setBaseUrl("https://api.ep.welt.de/curation/");
        restTemplate.setUriTemplateHandler(uriTemplateHandler);
        return restTemplate;
    }

    @Bean
    ExecutorService executorService() {
        return Executors.newFixedThreadPool(10);
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
