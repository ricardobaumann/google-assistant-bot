package contentbot.repo;

import com.google.common.collect.Sets;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class FrankRepoTest {

    @Autowired
    private FrankRepo frankRepo;

    @Test
    public void fetchQcus() throws Exception {
        final Set<String> qcus = frankRepo.fetchContentSnippet(Sets.newHashSet("169580633"));
        assertThat(qcus).isNotEmpty();
    }

}