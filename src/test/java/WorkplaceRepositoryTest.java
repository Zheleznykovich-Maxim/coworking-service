import org.example.coworking.model.Workplace;
import org.example.coworking.repository.WorkplaceRepository;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.io.IOException;
import java.sql.*;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class WorkplaceRepositoryTest {

    // Создание контейнера PostgreSQL
    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    private static Connection connection;
    private WorkplaceRepository workplaceRepository;

    // Инициализация контейнера и подключение к базе данных перед всеми тестами
    @BeforeAll
    static void init() throws SQLException {
        // Запуск контейнера (если не запущен)
        if (!postgresContainer.isRunning()) {
            postgresContainer.start();
        }

        // Получение URL для подключения к PostgreSQL
        String jdbcUrl = postgresContainer.getJdbcUrl();
        String username = postgresContainer.getUsername();
        String password = postgresContainer.getPassword();

        // Установка соединения с PostgreSQL
        connection = DriverManager.getConnection(jdbcUrl, username, password);

        // Инициализация базы данных, создание таблиц и т.д. (если нужно)
        initializeDatabase();
    }

    // Метод для инициализации базы данных (например, создание таблиц)
    private static void initializeDatabase() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE workplaces (" +
                    "id SERIAL PRIMARY KEY," +
                    "name VARCHAR(255) UNIQUE," +
                    "is_available BOOLEAN" +
                    ")");
        }
    }

    // Перед каждым тестом создаем новый экземпляр репозитория
    @BeforeEach
    void setUp() {
        workplaceRepository = new WorkplaceRepository();
    }

    // Тест для добавления и поиска рабочего места по идентификатору
    @Test
    @DisplayName("Test addWorkplace and findWorkplaceById methods")
    void testAddWorkplaceAndFindById() throws SQLException, IOException {
        // Создаем тестовое рабочее место
        Workplace workplace = new Workplace();
        workplace.setName("Workplace A");
        workplace.setAvailable(true);

        // Добавляем рабочее место
        workplaceRepository.addWorkplace(workplace);

        // Проверяем, что рабочее место было успешно добавлено
        Workplace retrievedWorkplace = workplaceRepository.findWorkplaceById(workplace.getId());
        assertThat(retrievedWorkplace).isNotNull();
        assertThat(retrievedWorkplace.getName()).isEqualTo(workplace.getName());
        assertThat(retrievedWorkplace.isAvailable()).isEqualTo(workplace.isAvailable());
    }

    // Закрытие соединения с базой данных после всех тестов
    @AfterAll
    static void tearDown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
