package conferenceHall;

import config.TestContainerManager;
import org.example.coworking.config.DatabaseConnectionProviderImpl;
import org.example.coworking.domain.model.ConferenceHall;
import org.example.coworking.repository.ConferenceHallRepository;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DisplayName("Tests for ConferenceHallRepository")
public class ConferenceHallRepositoryTest {

    private ConferenceHallRepository conferenceHallRepository = new ConferenceHallRepository(
            new DatabaseConnectionProviderImpl(
                    postgreSQLContainer.getJdbcUrl(),
                    postgreSQLContainer.getUsername(),
                    postgreSQLContainer.getPassword())
    );

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = TestContainerManager.getPostgresContainer();

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
