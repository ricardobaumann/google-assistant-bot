package bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class ContentBotApplication {

    private static ConfigurableApplicationContext applicationContext;

    static ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            applicationContext = SpringApplication.run(ContentBotApplication.class);
        }
        return applicationContext;
    }

    public static void main(final String[] args) throws InterruptedException, IOException {
        getApplicationContext();
    }
}
