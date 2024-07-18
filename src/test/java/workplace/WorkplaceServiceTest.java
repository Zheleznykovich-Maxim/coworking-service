package workplace;

import org.example.coworking.domain.dto.request.WorkplaceRequestDto;
import org.example.coworking.domain.dto.response.WorkplaceResponseDto;
import org.example.coworking.domain.model.Workplace;
import org.example.coworking.mapper.WorkplaceMapper;
import org.example.coworking.repository.WorkplaceRepository;
import org.example.coworking.service.impl.WorkplaceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Tests for WorkplaceService")
public class WorkplaceServiceTest {

    @Mock
    private WorkplaceRepository workplaceRepository;

    @Mock
    private WorkplaceMapper workplaceMapper;

    @InjectMocks
    WorkplaceServiceImpl workplaceService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test add a workplace")
    public void testAddWorkplace() {

        WorkplaceRequestDto requestDto = new WorkplaceRequestDto(
                "workplace1", true);

        workplaceService.addWorkplace(requestDto);
        verify(workplaceRepository, times(1)).addWorkplace(workplaceMapper.workplaceRequestDtotoWorkplace(requestDto));

    }

    @Test
    @DisplayName("Test deleting a workplace")
    public void testDeleteWorkplace() {

        int workplaceId = 1;
        Workplace workplace = new Workplace();
        workplace.setId(workplaceId);

        WorkplaceResponseDto workplaceResponseDto = new WorkplaceResponseDto(
                1, "workplace1", true);

        when(workplaceRepository.findWorkplaceById(workplaceId)).thenReturn(Optional.of(workplace));
        doNothing().when(workplaceRepository).removeWorkplaceById(workplaceId);
        when(workplaceMapper.workplaceToWorkplaceResponseDto(workplace)).thenReturn(workplaceResponseDto);

        WorkplaceResponseDto result = workplaceService.deleteWorkplace(workplaceId);

        assertThat(result).isEqualTo(workplaceResponseDto);
        verify(workplaceRepository, times(1)).findWorkplaceById(workplaceId);
        verify(workplaceRepository, times(1)).removeWorkplaceById(workplaceId);
        verify(workplaceMapper, times(1)).workplaceToWorkplaceResponseDto(workplace);
    }

    @Test
    @DisplayName("Test getting all workplaces")
    public void testGetAllWorkplaces() {

        Workplace workplace1 = new Workplace(1, "workplace1", true);
        Workplace workplace2 = new Workplace(2, "workplace2", true);
        Collection<Workplace> workplaces = Arrays.asList(
                workplace1,
                workplace2
        );
        Collection<WorkplaceResponseDto> workplaceResponseDtos = Arrays.asList(
                workplaceMapper.workplaceToWorkplaceResponseDto(workplace1),
                workplaceMapper.workplaceToWorkplaceResponseDto(workplace2)
        );

        when(workplaceRepository.getAllWorkplaces()).thenReturn(workplaces);
        when(workplaceMapper.workplacesToWorkplaceResponseDtos(workplaces)).thenReturn(workplaceResponseDtos);

        Collection<WorkplaceResponseDto> result = workplaceService.getWorkplaces();

        assertThat(result).isEqualTo(workplaceResponseDtos);
        verify(workplaceRepository, times(1)).getAllWorkplaces();
        verify(workplaceMapper, times(1)).workplacesToWorkplaceResponseDtos(workplaces);
    }

    @Test
    @DisplayName("Test finding workplace by id")
    public void testGetConferenceHallById() {

        int workplaceId = 1;
        Workplace workplace = new Workplace();
        workplace.setId(workplaceId);

        WorkplaceResponseDto workplaceResponseDto = new WorkplaceResponseDto(1, "workplace1", true);

        when(workplaceRepository.findWorkplaceById(workplaceId)).thenReturn(Optional.of(workplace));
        when(workplaceMapper.workplaceToWorkplaceResponseDto(workplace)).thenReturn(workplaceResponseDto);

        WorkplaceResponseDto result = workplaceService.findWorkplaceById(workplaceId);

        assertThat(result).isEqualTo(workplaceResponseDto);
        verify(workplaceRepository, times(1)).findWorkplaceById(workplaceId);
        verify(workplaceMapper, times(1)).workplaceToWorkplaceResponseDto(workplace);
    }
}


