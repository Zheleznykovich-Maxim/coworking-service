import org.example.coworking.model.ConferenceHall;
import org.example.coworking.model.Workplace;
import org.example.coworking.repository.ConferenceHallRepository;
import org.example.coworking.repository.WorkplaceRepository;
import org.example.coworking.service.CoworkingSpaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CoworkingSpaceServiceTest {

    @Mock
    private ConferenceHallRepository conferenceHallRepository;

    @Mock
    private WorkplaceRepository workplaceRepository;

    @InjectMocks
    private CoworkingSpaceService coworkingSpaceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Получение всех рабочих мест")
    public void testGetWorkplaces() {
        Workplace workplace1 = new Workplace(1, "Desk 1", true);
        Workplace workplace2 = new Workplace(2, "Desk 2", false);
        when(workplaceRepository.getAllWorkplaces()).thenReturn(Arrays.asList(workplace1, workplace2));

        Collection<Workplace> workplaces = coworkingSpaceService.getWorkplaces();

        assertThat(workplaces).containsExactlyInAnyOrder(workplace1, workplace2);
        verify(workplaceRepository, times(1)).getAllWorkplaces();
    }

    @Test
    @DisplayName("Получение всех конференц-залов")
    public void testGetConferenceHalls() {
        ConferenceHall hall1 = new ConferenceHall(1, "Main Hall", true);
        ConferenceHall hall2 = new ConferenceHall(2, "Small Hall", false);
        when(conferenceHallRepository.getAllConferenceHalls()).thenReturn(Arrays.asList(hall1, hall2));

        Collection<ConferenceHall> halls = coworkingSpaceService.getConferenceHalls();

        assertThat(halls).containsExactlyInAnyOrder(hall1, hall2);
        verify(conferenceHallRepository, times(1)).getAllConferenceHalls();
    }

    @Test
    @DisplayName("Добавление рабочего места")
    public void testAddWorkplace() {
        Workplace workplace = new Workplace(1, "Desk 1", true);
        coworkingSpaceService.addWorkplace(workplace);

        verify(workplaceRepository, times(1)).addWorkplace(workplace);
    }

    @Test
    @DisplayName("Добавление конференц-зала")
    public void testAddConferenceHall() throws IOException {
        ConferenceHall conferenceHall = new ConferenceHall(1, "Main Hall", true);
        coworkingSpaceService.addConferenceHall(conferenceHall);

        verify(conferenceHallRepository, times(1)).addConferenceHall(conferenceHall);
    }

    @Test
    @DisplayName("Получение всех доступных рабочих мест")
    public void testGetAvailableWorkplaces() {
        Workplace workplace = new Workplace(1, "Desk 1", true);
        when(workplaceRepository.getAvailableWorkplaces()).thenReturn(Arrays.asList(workplace));

        Collection<Workplace> availableWorkplaces = coworkingSpaceService.getAvailableWorkplaces();

        assertThat(availableWorkplaces).containsExactly(workplace);
        verify(workplaceRepository, times(1)).getAvailableWorkplaces();
    }

    @Test
    @DisplayName("Получение всех доступных конференц-залов")
    public void testGetAvailableConferenceHalls() {
        ConferenceHall conferenceHall = new ConferenceHall(1, "Main Hall", true);
        when(conferenceHallRepository.getAvailableConferenceHalls()).thenReturn(Arrays.asList(conferenceHall));

        Collection<ConferenceHall> availableConferenceHalls = coworkingSpaceService.getAvailableConferenceHalls();

        assertThat(availableConferenceHalls).containsExactly(conferenceHall);
        verify(conferenceHallRepository, times(1)).getAvailableConferenceHalls();
    }

    @Test
    @DisplayName("Обновление рабочего места")
    public void testUpdateWorkplace() {
        Workplace workplace = new Workplace(1, "Desk 1", true);
        coworkingSpaceService.updateWorkplace(workplace);

        verify(workplaceRepository, times(1)).updateWorkplace(workplace);
    }

    @Test
    @DisplayName("Обновление конференц-зала")
    public void testUpdateConferenceHall() {
        ConferenceHall conferenceHall = new ConferenceHall(1, "Main Hall", true);
        coworkingSpaceService.updateConferenceHall(conferenceHall);

        verify(conferenceHallRepository, times(1)).updateConferenceHall(conferenceHall);
    }

    @Test
    @DisplayName("Удаление рабочего места по ID")
    public void testDeleteWorkplace() {
        int id = 1;
        coworkingSpaceService.deleteWorkplace(id);

        verify(workplaceRepository, times(1)).removeWorkplaceById(id);
    }

    @Test
    @DisplayName("Удаление конференц-зала по ID")
    public void testDeleteConferenceHall() {
        int id = 1;
        coworkingSpaceService.deleteConferenceHall(id);

        verify(conferenceHallRepository, times(1)).removeConferenceHallById(id);
    }

    @Test
    @DisplayName("Поиск рабочего места по ID")
    public void testFindWorkplaceById() {
        int id = 1;
        Workplace workplace = new Workplace(id, "Desk 1", true);
        when(workplaceRepository.findWorkplaceById(id)).thenReturn(Optional.of(workplace));

        Workplace foundWorkplace = coworkingSpaceService.findWorkplaceById(id);

        assertThat(foundWorkplace).isEqualTo(workplace);
        verify(workplaceRepository, times(1)).findWorkplaceById(id);
    }

    @Test
    @DisplayName("Поиск конференц-зала по ID")
    public void testFindConferenceHallById() {
        int id = 1;
        ConferenceHall conferenceHall = new ConferenceHall(id, "Main Hall", true);
        when(conferenceHallRepository.findConferenceById(id)).thenReturn(Optional.of(conferenceHall));

        ConferenceHall foundConferenceHall = coworkingSpaceService.findConferenceHallById(id);

        assertThat(foundConferenceHall).isEqualTo(conferenceHall);
        verify(conferenceHallRepository, times(1)).findConferenceById(id);
    }
}
