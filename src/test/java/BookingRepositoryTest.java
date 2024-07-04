import org.example.coworking.model.Booking;
import org.example.coworking.model.enums.ResourceType;
import org.example.coworking.repository.BookingRepository;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class BookingRepositoryTest {

    // Создание контейнера PostgreSQL
    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    private static Connection connection;

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
            statement.execute("CREATE TABLE bookings (" +
                    "id SERIAL PRIMARY KEY," +
                    "user_id INT," +
                    "resource_id INT," +
                    "resource_name VARCHAR(255)," +
                    "start_time TIMESTAMP," +
                    "end_time TIMESTAMP," +
                    "resource_type VARCHAR(50)," +
                    "is_available BOOLEAN" +
                    ")");
        }
    }

    // Тест для BookingRepository
    @Test
    @DisplayName("Test addBooking method")
    void testAddBooking() throws SQLException, IOException {
        BookingRepository bookingRepository = new BookingRepository();

        // Добавление тестового бронирования
        Booking booking = new Booking();
        booking.setUserId(1);
        booking.setResourceId(1);
        booking.setResourceName("Meeting Room A");
        booking.setStartTime(LocalDateTime.now());
        booking.setEndTime(LocalDateTime.now().plusHours(1));
        booking.setResourceType(ResourceType.CONFERENCEHALL);
        booking.setAvailable(true);

        bookingRepository.addBooking(booking);

        // Проверка успешности добавления
        Optional<Booking> retrievedBooking = bookingRepository.findBookingById(booking.getId());
        assertThat(retrievedBooking).isPresent();
        assertThat(retrievedBooking.get().getUserId()).isEqualTo(booking.getUserId());
        assertThat(retrievedBooking.get().getResourceId()).isEqualTo(booking.getResourceId());
    }

    // Закрытие соединения с базой данных после всех тестов
    @AfterAll
    static void tearDown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
