import config.DatabaseTestContainer;
import org.example.coworking.model.ConferenceHall;
import org.example.coworking.repository.ConferenceHallRepository;
import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.io.IOException;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class ConferenceHallRepositoryTest {

    private ConferenceHallRepository conferenceHallRepository;

    @BeforeAll
    static void init() {
        DatabaseTestContainer.startContainer();
    }

    @BeforeEach
    void setUp() {
        conferenceHallRepository = new ConferenceHallRepository(DatabaseTestContainer.getDatabaseConnection());
    }

    @Test
    @DisplayName("Test addConferenceHall and findConferenceById methods")
    void testAddAndFindConferenceHall() throws IOException {
        ConferenceHall conferenceHall = new ConferenceHall();
        conferenceHall.setName("Conference Room 1");
        conferenceHall.setAvailable(true);

        conferenceHallRepository.addConferenceHall(conferenceHall);

        Optional<ConferenceHall> retrievedConferenceHall = conferenceHallRepository.findConferenceById(conferenceHall.getId());
        assertThat(retrievedConferenceHall).isPresent();
        assertThat(retrievedConferenceHall.get().getName()).isEqualTo(conferenceHall.getName());
        assertThat(retrievedConferenceHall.get().isAvailable()).isEqualTo(conferenceHall.isAvailable());
    }

    @AfterAll
    static void tearDown() {
        DatabaseTestContainer.stopContainer();
    }
}
