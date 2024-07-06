import config.DatabaseTestContainer;
import org.example.coworking.model.Workplace;
import org.example.coworking.repository.WorkplaceRepository;
import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class WorkplaceRepositoryTest {

    private WorkplaceRepository workplaceRepository;

    @BeforeAll
    static void init() {
        DatabaseTestContainer.startContainer();
    }

    @BeforeEach
    void setUp() {
        workplaceRepository = new WorkplaceRepository(DatabaseTestContainer.getDatabaseConnection());
    }

    @Test
    @DisplayName("Test addWorkplace and findWorkplaceById methods")
    void testAddWorkplaceAndFindById() {
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
