import config.DatabaseTestContainer;
import org.example.coworking.model.ConferenceHall;
import org.example.coworking.repository.ConferenceHallRepository;
import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class ConferenceHallRepositoryTest {

    private ConferenceHallRepository conferenceHallRepository;

    @BeforeAll
    static void init() throws SQLException {
        DatabaseTestContainer.startContainer();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS conference_halls (" +
                "id SERIAL PRIMARY KEY," +
                "name VARCHAR(255)," +
                "is_available BOOLEAN" +
                ")";
        DatabaseTestContainer.initializeDatabase(createTableSQL);
    }

    @BeforeEach
    void setUp() {
        conferenceHallRepository = new ConferenceHallRepository();
    }

    @Test
    @DisplayName("Test addConferenceHall and findConferenceById methods")
    void testAddAndFindConferenceHall() throws SQLException, IOException {
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
    static void tearDown() throws SQLException {
        DatabaseTestContainer.stopContainer();
    }
}
