import config.DatabaseTestContainer;
import org.example.coworking.model.Workplace;
import org.example.coworking.repository.WorkplaceRepository;
import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class WorkplaceRepositoryTest {

    private WorkplaceRepository workplaceRepository;

    @BeforeAll
    static void init() throws SQLException {
        DatabaseTestContainer.startContainer();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS workplaces (" +
                "id SERIAL PRIMARY KEY," +
                "name VARCHAR(255) UNIQUE," +
                "is_available BOOLEAN" +
                ")";
        DatabaseTestContainer.initializeDatabase(createTableSQL);
    }

    @BeforeEach
    void setUp() {
        workplaceRepository = new WorkplaceRepository();
    }

    @Test
    @DisplayName("Test addWorkplace and findWorkplaceById methods")
    void testAddWorkplaceAndFindById() throws SQLException, IOException {
        Workplace workplace = new Workplace();
        workplace.setName("Workplace A");
        workplace.setAvailable(true);

        workplaceRepository.addWorkplace(workplace);

        Optional<Workplace> retrievedWorkplace = workplaceRepository.findWorkplaceById(workplace.getId());
        assertThat(retrievedWorkplace).isPresent();
        assertThat(retrievedWorkplace.get().getName()).isEqualTo(workplace.getName());
        assertThat(retrievedWorkplace.get().isAvailable()).isEqualTo(workplace.isAvailable());
    }

    @AfterAll
    static void tearDown() {
        DatabaseTestContainer.stopContainer();
    }
}
