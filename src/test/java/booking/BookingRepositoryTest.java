package booking;

import config.TestContainerManager;
import org.example.coworking.MainApplication;
import org.example.coworking.domain.model.Booking;
import org.example.coworking.domain.model.enums.ResourceType;
import org.example.coworking.repository.BookingRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = MainApplication.class)
@Testcontainers
@DisplayName("Tests for BookingRepository")
class BookingRepositoryTest {

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = TestContainerManager.getPostgresContainer();

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Autowired
    private BookingRepository bookingRepository;


    @BeforeAll
    static void init() {
        postgreSQLContainer.start();
        TestContainerManager.establishConnection();
    }

    @Test
    @DisplayName("Test add booking")
    void testAddBooking() {
        LocalDateTime startTime = LocalDateTime.of(2024, 6, 25, 9, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 6, 25, 11, 0);
        Booking booking = new Booking(3, 1, 1, startTime, endTime, ResourceType.WORKPLACE, true);
        bookingRepository.addBooking(booking);

        Optional<Booking> retrievedBooking = bookingRepository.findBookingById(booking.getId());
        assertThat(retrievedBooking).isPresent();
        assertThat(retrievedBooking.get()).isEqualTo(booking);
    }

    @Test
    @DisplayName("Tests deleting booking by id")
    void testRemoveBookingById() {
        LocalDateTime startTime = LocalDateTime.of(2024, 6, 25, 9, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 6, 25, 11, 0);
        Booking booking = new Booking(1, 2, 1, startTime, endTime, ResourceType.WORKPLACE, true);
        bookingRepository.addBooking(booking);

        bookingRepository.removeBookingById(booking.getId());

        Optional<Booking> retrievedBooking = bookingRepository.findBookingById(booking.getId());
        assertThat(retrievedBooking).isEmpty();
    }

    @AfterAll
    public static void teardown() {
        postgreSQLContainer.stop();
    }
}
