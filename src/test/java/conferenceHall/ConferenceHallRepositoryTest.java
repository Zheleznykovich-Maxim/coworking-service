package conferenceHall;

import config.TestContainerManager;
import org.example.coworking.MainApplication;
import org.example.coworking.domain.model.ConferenceHall;
import org.example.coworking.repository.ConferenceHallRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = MainApplication.class)
@Testcontainers
@DisplayName("Tests for ConferenceHallRepository")
public class ConferenceHallRepositoryTest {

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = TestContainerManager.getPostgresContainer();

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Autowired
    private ConferenceHallRepository conferenceHallRepository;

    @BeforeAll
    static void init() {
        postgreSQLContainer.start();
        TestContainerManager.establishConnection();
    }

    @Test
    @DisplayName("Tests add conferenceHall")
    void testAddConferenceHall() {
        ConferenceHall conferenceHall = new ConferenceHall(3, "conferencehall1", true);
        conferenceHallRepository.addConferenceHall(conferenceHall);

        Optional<ConferenceHall> retrievedConferenceHall = conferenceHallRepository.findConferenceById(conferenceHall.getId());
        assertThat(retrievedConferenceHall).isPresent();
        assertThat(retrievedConferenceHall.get()).isEqualTo(conferenceHall);
    }

    @Test
    @DisplayName("Tests remove conferenceHall by id")
    void testRemoveConferenceHallById() {
        ConferenceHall conferenceHall = new ConferenceHall(3, "conferencehall1", true);
        conferenceHallRepository.addConferenceHall(conferenceHall);

        conferenceHallRepository.removeConferenceHallById(conferenceHall.getId());

        Optional<ConferenceHall> retrievedConferenceHall = conferenceHallRepository.findConferenceById(conferenceHall.getId());
        assertThat(retrievedConferenceHall).isEmpty();
    }

    @AfterAll
    static void tearDown() {
        postgreSQLContainer.stop();
    }
}
