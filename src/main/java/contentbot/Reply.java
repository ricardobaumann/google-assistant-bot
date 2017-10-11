package contentbot;


import java.util.Optional;

public interface Reply {
    Optional<String> replyTo(String question);

    String description();

    default int order() {
        return 0;
    }
}
