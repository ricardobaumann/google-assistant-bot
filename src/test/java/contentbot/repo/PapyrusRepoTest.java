package contentbot.repo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PapyrusRepoTest {

    @Autowired
    private PapyrusRepo papyrusRepo;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void fetchIds() throws Exception {
        final Set<String> ids = papyrusRepo.fetchIds("news");
        assertThat(ids).isNotEmpty();
    }

}