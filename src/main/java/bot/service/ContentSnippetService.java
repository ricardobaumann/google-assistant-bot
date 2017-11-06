package bot.service;

import bot.dto.ContentSnippet;
import bot.repo.FrankRepo;
import bot.repo.PapyrusRepo;
import bot.repo.SessionNewstickerStepRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ContentSnippetService {

    private final FrankRepo frankRepo;
    private final PapyrusRepo papyrusRepo;
    private final SessionNewstickerStepRepo sessionNewstickerStepRepo;

    ContentSnippetService(final FrankRepo frankRepo,
                          final PapyrusRepo papyrusRepo,
                          final SessionNewstickerStepRepo sessionNewstickerStepRepo) {
        this.frankRepo = frankRepo;
        this.papyrusRepo = papyrusRepo;
        this.sessionNewstickerStepRepo = sessionNewstickerStepRepo;
    }

    Set<ContentSnippet> getContentSnippets(final int limit) {
        return frankRepo.fetchContentSnippet(papyrusRepo.fetchIds(limit));
    }

    public Optional<ContentSnippet> getNextSnippet(final String sessionId) {
        final Set<String> read = sessionNewstickerStepRepo.getReadIds(sessionId);
        final Optional<ContentSnippet> result = papyrusRepo.fetchIds(Integer.MAX_VALUE)
                .stream().filter(s -> !read.contains(s)).findFirst()
                .flatMap(frankRepo::fetchContentSnippet);
        result.ifPresent(contentSnippet -> sessionNewstickerStepRepo.markAsRead(sessionId, contentSnippet.getId()));
        return result;
    }
}
