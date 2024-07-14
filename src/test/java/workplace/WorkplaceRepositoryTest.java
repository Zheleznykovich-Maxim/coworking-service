package workplace;

import config.TestContainerManager;
import org.example.coworking.config.DatabaseConnectionProviderImpl;
import org.example.coworking.domain.model.Workplace;
import org.example.coworking.repository.WorkplaceRepository;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DisplayName("Tests for WorkplaceRepository")
public class WorkplaceRepositoryTest {

    private WorkplaceRepository workplaceRepository = new WorkplaceRepository(
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
    @DisplayName("Test add workplace")
    void testAddWorkplace() {

        Workplace workplace = new Workplace(3, "workplace1", true);
        workplaceRepository.addWorkplace(workplace);

        Optional<Workplace> retrievedWorkplace = workplaceRepository.findWorkplaceById(workplace.getId());
        assertThat(retrievedWorkplace).isPresent();
        assertThat(retrievedWorkplace.get()).isEqualTo(workplace);
    }

    @Test
    @DisplayName("Tests deleting workplace by id")
    void testRemoveWorkplaceById() {

        Workplace workplace = new Workplace(3, "workplace1", true);
        workplaceRepository.addWorkplace(workplace);

        workplaceRepository.removeWorkplaceById(workplace.getId());

        Optional<Workplace> retrievedWorkplace = workplaceRepository.findWorkplaceById(workplace.getId());
        assertThat(retrievedWorkplace).isEmpty();
    }

    @AfterAll
    static void tearDown() {
        postgreSQLContainer.stop();
    }
}
