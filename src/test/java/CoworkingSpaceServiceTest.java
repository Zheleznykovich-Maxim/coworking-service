import org.example.coworking.models.ConferenceHall;
import org.example.coworking.models.Workplace;
import org.example.coworking.services.CoworkingSpaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.assertj.core.api.Assertions.assertThat;

public class CoworkingSpaceServiceTest {

    private CoworkingSpaceService coworkingSpaceService;
    private ArrayList<Workplace> workplaces;
    private ArrayList<ConferenceHall> conferenceHalls;

    @BeforeEach
    public void setUp() {
        workplaces = new ArrayList<>();
        conferenceHalls = new ArrayList<>();
        coworkingSpaceService = new CoworkingSpaceService(workplaces, conferenceHalls);
    }

    @Test
    public void testAddWorkplace() {
        // Arrange
        Workplace workplace = new Workplace(1, "Workplace 1", true);

        // Act
        coworkingSpaceService.addWorkplace(workplace);

        // Assert
        assertThat(workplaces).hasSize(1);
        assertThat(workplaces.get(0)).isEqualTo(workplace);
    }

    @Test
    public void testAddConferenceHall() {
        // Arrange
        ConferenceHall conferenceHall = new ConferenceHall(1, "Conference Hall 1", true);

        // Act
        coworkingSpaceService.addConferenceHall(conferenceHall);

        // Assert
        assertThat(conferenceHalls).hasSize(1);
        assertThat(conferenceHalls.get(0)).isEqualTo(conferenceHall);
    }

    @Test
    public void testGetAvailableWorkplaces() {
        // Arrange
        Workplace availableWorkplace = new Workplace(1, "Workplace 1", true);
        Workplace bookedWorkplace = new Workplace(2, "Workplace 2", false);
        workplaces.add(availableWorkplace);
        workplaces.add(bookedWorkplace);

        // Act
        ArrayList<Workplace> availableWorkplaces = coworkingSpaceService.getAvailableWorkplaces();

        // Assert
        assertThat(availableWorkplaces).hasSize(1);
        assertThat(availableWorkplaces.get(0)).isEqualTo(availableWorkplace);
    }

    @Test
    public void testGetAvailableConferenceHalls() {
        // Arrange
        ConferenceHall availableConferenceHall = new ConferenceHall(1, "Conference Hall 1", true);
        ConferenceHall bookedConferenceHall = new ConferenceHall(2, "Conference Hall 2", false);
        conferenceHalls.add(availableConferenceHall);
        conferenceHalls.add(bookedConferenceHall);

        // Act
        ArrayList<ConferenceHall> availableConferenceHalls = coworkingSpaceService.getAvailableConferenceHalls();

        // Assert
        assertThat(availableConferenceHalls).hasSize(1);
        assertThat(availableConferenceHalls.get(0)).isEqualTo(availableConferenceHall);
    }

    @Test
    public void testUpdateConferenceHall() {
        // Arrange
        ConferenceHall conferenceHall = new ConferenceHall(1, "Conference Hall 1", true);
        conferenceHalls.add(conferenceHall);

        // Act
        coworkingSpaceService.updateConferenceHall(1, "Updated Conference Hall", false);

        // Assert
        assertThat(conferenceHall.getName()).isEqualTo("Updated Conference Hall");
        assertThat(conferenceHall.isAvailable()).isFalse();
    }

    @Test
    public void testUpdateWorkplace() {
        // Arrange
        Workplace workplace = new Workplace(1, "Workplace 1", true);
        workplaces.add(workplace);

        // Act
        coworkingSpaceService.updateWorkplace(1, "Updated Workplace", false);

        // Assert
        assertThat(workplace.getName()).isEqualTo("Updated Workplace");
        assertThat(workplace.isAvailable()).isFalse();
    }

    @Test
    public void testDeleteConferenceHall() {
        // Arrange
        ConferenceHall conferenceHall = new ConferenceHall(1, "Conference Hall 1", true);
        conferenceHalls.add(conferenceHall);

        // Act
        coworkingSpaceService.deleteConferenceHall(1);

        // Assert
        assertThat(conferenceHalls).isEmpty();
    }

    @Test
    public void testDeleteWorkplace() {
        // Arrange
        Workplace workplace = new Workplace(1, "Workplace 1", true);
        workplaces.add(workplace);

        // Act
        coworkingSpaceService.deleteWorkplace(1);

        // Assert
        assertThat(workplaces).isEmpty();
    }

    @Test
    public void testFindWorkplaceById() {
        // Arrange
        Workplace workplace = new Workplace(1, "Workplace 1", true);
        workplaces.add(workplace);

        // Act
        Workplace foundWorkplace = coworkingSpaceService.findWorkplaceById(1);

        // Assert
        assertThat(foundWorkplace).isEqualTo(workplace);
    }

    @Test
    public void testFindConferenceHallById() {
        // Arrange
        ConferenceHall conferenceHall = new ConferenceHall(1, "Conference Hall 1", true);
        conferenceHalls.add(conferenceHall);

        // Act
        ConferenceHall foundConferenceHall = coworkingSpaceService.findConferenceHallById(1);

        // Assert
        assertThat(foundConferenceHall).isEqualTo(conferenceHall);
    }
}
