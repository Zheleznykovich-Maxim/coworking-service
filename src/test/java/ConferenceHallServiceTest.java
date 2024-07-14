
import org.example.coworking.domain.dto.request.ConferenceHallRequestDto;
import org.example.coworking.domain.dto.response.ConferenceHallResponseDto;
import org.example.coworking.domain.model.ConferenceHall;
import org.example.coworking.mapper.ConferenceHallMapper;
import org.example.coworking.repository.ConferenceHallRepository;
import org.example.coworking.service.impl.ConferenceHallServiceImpl;
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

@DisplayName("Tests for ConferenceHallService")
public class ConferenceHallServiceTest {

    @Mock
    private ConferenceHallRepository conferenceHallRepository;

    @Mock
    private ConferenceHallMapper conferenceHallMapper;

    @InjectMocks
    ConferenceHallServiceImpl conferenceHallService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test adding a conferenceHall")
    public void testAddConferenceHall() {

        ConferenceHallRequestDto requestDto = new ConferenceHallRequestDto(
                "conferenceHall1", true);

        conferenceHallService.addConferenceHall(requestDto);
        verify(conferenceHallRepository, times(1)).addConferenceHall(conferenceHallMapper.conferenceHallRequestDtotoConferenceHall(requestDto));

    }

    @Test
    @DisplayName("Test deleting a conferenceHall")
    public void testDeleteConferenceHall() {

        int conferenceHallId = 1;
        ConferenceHall conferenceHall = new ConferenceHall();
        conferenceHall.setId(conferenceHallId);

        ConferenceHallResponseDto conferenceHallResponseDto = new ConferenceHallResponseDto(
                1, "conferenceHall1", true);

        when(conferenceHallRepository.findConferenceById(conferenceHallId)).thenReturn(Optional.of(conferenceHall));
        doNothing().when(conferenceHallRepository).removeConferenceHallById(conferenceHallId);
        when(conferenceHallMapper.conferenceHallToConferenceHallResponseDto(conferenceHall)).thenReturn(conferenceHallResponseDto);

        ConferenceHallResponseDto result = conferenceHallService.deleteConferenceHall(conferenceHallId);

        assertThat(result).isEqualTo(conferenceHallResponseDto);
        verify(conferenceHallRepository, times(1)).findConferenceById(conferenceHallId);
        verify(conferenceHallRepository, times(1)).removeConferenceHallById(conferenceHallId);
        verify(conferenceHallMapper, times(1)).conferenceHallToConferenceHallResponseDto(conferenceHall);
    }

    @Test
    @DisplayName("Test getting all conferenceHalls")
    public void testGetAllConferenceHalls() {

        ConferenceHall conferenceHall1 = new ConferenceHall(1, "conferenceHall1", true);
        ConferenceHall conferenceHall2 = new ConferenceHall(2, "conferenceHall2", true);
        Collection<ConferenceHall> conferenceHalls = Arrays.asList(
                conferenceHall1,
                conferenceHall2
        );
        Collection<ConferenceHallResponseDto> conferenceHallResponseDtos = Arrays.asList(
                conferenceHallMapper.conferenceHallToConferenceHallResponseDto(conferenceHall1),
                conferenceHallMapper.conferenceHallToConferenceHallResponseDto(conferenceHall2)
        );

        when(conferenceHallRepository.getAllConferenceHalls()).thenReturn(conferenceHalls);
        when(conferenceHallMapper.conferenceHallsToConferenceHallResponseDtos(conferenceHalls)).thenReturn(conferenceHallResponseDtos);

        Collection<ConferenceHallResponseDto> result = conferenceHallService.getConferenceHalls();

        assertThat(result).isEqualTo(conferenceHallResponseDtos);
        verify(conferenceHallRepository, times(1)).getAllConferenceHalls();
        verify(conferenceHallMapper, times(1)).conferenceHallsToConferenceHallResponseDtos(conferenceHalls);
    }

    @Test
    @DisplayName("Test finding conferenceHall by id")
    public void testGetConferenceHallById() {

        int conferenceHallId = 1;
        ConferenceHall conferenceHall = new ConferenceHall();
        conferenceHall.setId(conferenceHallId);

        ConferenceHallResponseDto conferenceHallResponseDto = new ConferenceHallResponseDto(1, "conferenceHall1", true);

        when(conferenceHallRepository.findConferenceById(conferenceHallId)).thenReturn(Optional.of(conferenceHall));
        when(conferenceHallMapper.conferenceHallToConferenceHallResponseDto(conferenceHall)).thenReturn(conferenceHallResponseDto);

        ConferenceHallResponseDto result = conferenceHallService.findConferenceHallById(conferenceHallId);

        assertThat(result).isEqualTo(conferenceHallResponseDto);
        verify(conferenceHallRepository, times(1)).findConferenceById(conferenceHallId);
        verify(conferenceHallMapper, times(1)).conferenceHallToConferenceHallResponseDto(conferenceHall);
    }
}
