package bot.repo;

import com.google.common.collect.Sets;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Repository
public class SessionNewstickerStepRepo {

    private final Map<String, Set<String>> sessions = new HashMap<>();

    public void markAsRead(final String sessionId, final String contentId) {
        sessions.putIfAbsent(sessionId, Sets.newHashSet());
        sessions.get(sessionId).add(contentId);
    }

    public Set<String> getReadIds(final String sessionId) {
        return sessions.getOrDefault(sessionId, Collections.emptySet());
    }
}
