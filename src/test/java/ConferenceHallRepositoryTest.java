import org.example.coworking.model.ConferenceHall;
import org.example.coworking.repository.ConferenceHallRepository;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.io.IOException;
import java.sql.*;


import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class ConferenceHallRepositoryTest {

    // Создание контейнера PostgreSQL
    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    private static Connection connection;
    private ConferenceHallRepository conferenceHallRepository;

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
            statement.execute("CREATE TABLE conference_halls (" +
                    "id SERIAL PRIMARY KEY," +
                    "name VARCHAR(255)," +
                    "is_available BOOLEAN" +
                    ")");
        }
    }

    // Перед каждым тестом создаем новый экземпляр репозитория
    @BeforeEach
    void setUp() {
        conferenceHallRepository = new ConferenceHallRepository();
    }

    // Тест для добавления и поиска конференц-зала
    @Test
    @DisplayName("Test addConferenceHall and findConferenceById methods")
    void testAddAndFindConferenceHall() throws SQLException, IOException {
        // Создаем тестовый конференц-зал
        ConferenceHall conferenceHall = new ConferenceHall();
        conferenceHall.setName("Conference Room 1");
        conferenceHall.setAvailable(true);

        // Добавляем конференц-зал в репозиторий
        conferenceHallRepository.addConferenceHall(conferenceHall);

        // Проверяем, что конференц-зал был успешно добавлен
        ConferenceHall retrievedConferenceHall = conferenceHallRepository.findConferenceById(conferenceHall.getId());
        assertThat(retrievedConferenceHall).isNotNull();
        assertThat(retrievedConferenceHall.getName()).isEqualTo(conferenceHall.getName());
        assertThat(retrievedConferenceHall.isAvailable()).isEqualTo(conferenceHall.isAvailable());
    }

    // Закрытие соединения с базой данных после всех тестов
    @AfterAll
    static void tearDown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
