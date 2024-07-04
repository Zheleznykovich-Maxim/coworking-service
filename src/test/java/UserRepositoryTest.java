import org.example.coworking.model.User;
import org.example.coworking.model.enums.UserRole;
import org.example.coworking.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.io.IOException;
import java.sql.*;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class UserRepositoryTest {

    // Создание контейнера PostgreSQL
    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    private static Connection connection;
    private UserRepository userRepository;

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
            statement.execute("CREATE TABLE users (" +
                    "id SERIAL PRIMARY KEY," +
                    "username VARCHAR(255) UNIQUE," +
                    "password VARCHAR(255)," +
                    "role VARCHAR(50)" +
                    ")");
        }
    }

    // Перед каждым тестом создаем новый экземпляр репозитория
    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
    }

    // Тест для регистрации и поиска пользователя по имени пользователя
    @Test
    @DisplayName("Test registerUser and findUserByUsername methods")
    void testRegisterAndFindUserByUsername() throws SQLException, IOException {
        // Создаем тестового пользователя
        User user = new User();
        user.setUsername("testuser1");
        user.setPassword("password");
        user.setRole(UserRole.USER);

        // Регистрируем пользователя
        userRepository.registerUser(user);

        // Проверяем, что пользователь был успешно зарегистрирован
        Optional<User> retrievedUser = userRepository.findUserByUsername(user.getUsername());
        User foundUser = retrievedUser.get();
        assertThat(retrievedUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(foundUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(foundUser.getRole()).isEqualTo(user.getRole());
    }

    // Закрытие соединения с базой данных после всех тестов
    @AfterAll
    static void tearDown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
