import org.example.coworking.model.ConferenceHall;
import org.example.coworking.model.Workplace;
import org.example.coworking.repository.ConferenceHallRepository;
import org.example.coworking.repository.WorkplaceRepository;
import org.example.coworking.service.CoworkingSpaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests for CoworkingSpaceService class")
public class CoworkingSpaceServiceTest {

    private CoworkingSpaceService coworkingSpaceService;
    private Map<Integer, Workplace> workplaceMap;
    private Map<Integer, ConferenceHall> conferenceHallMap;

    @BeforeEach
    public void setUp() {
        workplaceMap = new HashMap<>();
        conferenceHallMap = new HashMap<>();
        WorkplaceRepository workplaceRepository = new WorkplaceRepository(workplaceMap);
        ConferenceHallRepository conferenceHallRepository = new ConferenceHallRepository(conferenceHallMap);
        coworkingSpaceService = new CoworkingSpaceService(conferenceHallRepository, workplaceRepository);
    }

    @Test
    @DisplayName("Test adding a Workplace")
    public void testAddWorkplace() {
        // Arrange
        Workplace workplace = new Workplace(1, "Workplace 1", true);

        // Act
        coworkingSpaceService.addWorkplace(workplace);

        // Assert
        assertThat(workplaceMap).hasSize(1);
        assertThat(workplaceMap.get(1)).isEqualTo(workplace);
    }

    @Test
    @DisplayName("Test adding a ConferenceHall")
    public void testAddConferenceHall() {
        // Arrange
        ConferenceHall conferenceHall = new ConferenceHall(1, "Conference Hall 1", true);

        // Act
        coworkingSpaceService.addConferenceHall(conferenceHall);

        // Assert
        assertThat(conferenceHallMap).hasSize(1);
        assertThat(conferenceHallMap.get(1)).isEqualTo(conferenceHall);
    }

    @Test
    @DisplayName("Test retrieving available Workplaces")
    public void testGetAvailableWorkplaces() {
        // Arrange
        Workplace availableWorkplace = new Workplace(1, "Workplace 1", true);
        Workplace bookedWorkplace = new Workplace(2, "Workplace 2", false);
        workplaceMap.put(availableWorkplace.getId(), availableWorkplace);
        workplaceMap.put(bookedWorkplace.getId(), bookedWorkplace);

        // Act
        Collection<Workplace> availableWorkplaces = coworkingSpaceService.getAvailableWorkplaces();

        // Assert
        assertThat(availableWorkplaces).hasSize(1);
        assertThat(availableWorkplaces.iterator().next()).isEqualTo(availableWorkplace);
    }

    @Test
    @DisplayName("Test retrieving available ConferenceHalls")
    public void testGetAvailableConferenceHalls() {
        // Arrange
        ConferenceHall availableConferenceHall = new ConferenceHall(1, "Conference Hall 1", true);
        ConferenceHall bookedConferenceHall = new ConferenceHall(2, "Conference Hall 2", false);
        conferenceHallMap.put(availableConferenceHall.getId(), availableConferenceHall);
        conferenceHallMap.put(bookedConferenceHall.getId(), bookedConferenceHall);

        // Act
        Collection<ConferenceHall> availableConferenceHalls = coworkingSpaceService.getAvailableConferenceHalls();

        // Assert
        assertThat(availableConferenceHalls).hasSize(1);
        assertThat(availableConferenceHalls.iterator().next()).isEqualTo(availableConferenceHall);
    }

    @Test
    @DisplayName("Test updating a ConferenceHall")
    public void testUpdateConferenceHall() {
        // Arrange
        ConferenceHall conferenceHall = new ConferenceHall(1, "Conference Hall 1", true);
        conferenceHallMap.put(conferenceHall.getId(), conferenceHall);

        // Act
        ConferenceHall updatedConferenceHall = new ConferenceHall(1, "Updated Conference Hall", false);
        coworkingSpaceService.updateConferenceHall(updatedConferenceHall);

        // Assert
        assertThat(conferenceHallMap.get(conferenceHall.getId())).isEqualTo(updatedConferenceHall);
        assertThat(conferenceHallMap.get(conferenceHall.getId()).isAvailable()).isFalse();
    }

    @Test
    @DisplayName("Test updating a Workplace")
    public void testUpdateWorkplace() {
        // Arrange
        Workplace workplace = new Workplace(1, "Workplace 1", true);
        workplaceMap.put(workplace.getId(), workplace);

        // Act
        Workplace updatedWorkplace = new Workplace(1, "Updated Workplace", false);
        coworkingSpaceService.updateWorkplace(updatedWorkplace);

        // Assert
        assertThat(workplaceMap.get(workplace.getId())).isEqualTo(updatedWorkplace);
        assertThat(workplaceMap.get(workplace.getId()).isAvailable()).isFalse();
    }

    @Test
    @DisplayName("Test deleting a ConferenceHall")
    public void testDeleteConferenceHall() {
        // Arrange
        ConferenceHall conferenceHall = new ConferenceHall(1, "Conference Hall 1", true);
        conferenceHallMap.put(conferenceHall.getId(), conferenceHall);

        // Act
        coworkingSpaceService.deleteConferenceHall(1);

        // Assert
        assertThat(conferenceHallMap).isEmpty();
    }

    @Test
    @DisplayName("Test deleting a Workplace")
    public void testDeleteWorkplace() {
        // Arrange
        Workplace workplace = new Workplace(1, "Workplace 1", true);
        workplaceMap.put(workplace.getId(), workplace);

        // Act
        coworkingSpaceService.deleteWorkplace(1);

        // Assert
        assertThat(workplaceMap).isEmpty();
    }

    @Test
    @DisplayName("Test finding a Workplace by ID")
    public void testFindWorkplaceById() {
        // Arrange
        Workplace workplace = new Workplace(1, "Workplace 1", true);
        workplaceMap.put(workplace.getId(), workplace);

        // Act
        Workplace foundWorkplace = coworkingSpaceService.findWorkplaceById(1);

        // Assert
        assertThat(foundWorkplace).isEqualTo(workplace);
    }

    @Test
    @DisplayName("Test finding a ConferenceHall by ID")
    public void testFindConferenceHallById() {
        // Arrange
        ConferenceHall conferenceHall = new ConferenceHall(1, "Conference Hall 1", true);
        conferenceHallMap.put(conferenceHall.getId(), conferenceHall);

        // Act
        ConferenceHall foundConferenceHall = coworkingSpaceService.findConferenceHallById(1);

        // Assert
        assertThat(foundConferenceHall).isEqualTo(conferenceHall);
    }
}
