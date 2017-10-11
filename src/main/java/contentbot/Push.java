package contentbot;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Push implements Reply {
    @Override
    public Optional<String> replyTo(final String answer) {
        if (answer.contains("like push")) {
            return Optional.of("That's great. Visit https://welt.de/push and check it out!!!");
        }
        return Optional.empty();
    }

    @Override
    public String description() {
        return "By the way do you like push-notifications?";
    }

    @Override
    public int order() {
        return 99;
    }
}
