package contentbot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriTemplateHandler;

@Configuration
public class ContentBotConfig {

    @Bean(name = "frankRestTemplate")
    RestTemplate frankRestTemplate() {
        return createFrom("", "", "https://frank-ecs-production.up.welt.de");
    }

    @Bean(name = "stasiRestTemplate")
    RestTemplate stasiRestTemplate() {
        return createFrom("", "", "https://stasi-ecs-production.up.welt.de");
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
