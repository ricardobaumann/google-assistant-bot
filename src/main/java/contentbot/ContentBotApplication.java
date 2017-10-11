package contentbot;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//@SpringBootApplication
@RestController
public class ContentBotApplication {

    private final Replyer replyer;

    ContentBotApplication(final Replyer replyer) {
        this.replyer = replyer;
    }

    @PostMapping(path = "/question", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String reply(@RequestBody final String question) {
        return replyer.reply(question);
    }

}
