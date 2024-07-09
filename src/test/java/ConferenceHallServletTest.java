import org.example.coworking.dto.response.ConferenceHallResponseDto;
import org.example.coworking.mapper.ConferenceHallMapper;
import org.example.coworking.model.ConferenceHall;
import org.example.coworking.servlet.ConferenceHallServlet;
import org.example.coworking.service.CoworkingSpaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.*;

public class ConferenceHallServletTest {

    @InjectMocks
    private ConferenceHallServlet conferenceHallServlet;

    @Mock
    private CoworkingSpaceService coworkingSpaceService;

    @Mock
    private ConferenceHallMapper conferenceHallMapper;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDoGetWithNoPath() throws Exception {
        when(request.getPathInfo()).thenReturn(null);

        Collection<ConferenceHall> conferenceHalls = Arrays.asList(new ConferenceHall(), new ConferenceHall(    ));
        when(coworkingSpaceService.getConferenceHalls()).thenReturn(conferenceHalls);

        Collection<ConferenceHallResponseDto> workplaceResponseDtos = conferenceHallMapper.conferenceHallsToConferenceHallResponseDtos(conferenceHalls);
        when(conferenceHallMapper.conferenceHallsToConferenceHallResponseDtos(conferenceHalls)).thenReturn(workplaceResponseDtos);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(response.getOutputStream()).thenReturn(new ServletOutputStreamWrapper(outputStream));

        conferenceHallServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void testDoGetWithValidPath() throws Exception {
        when(request.getPathInfo()).thenReturn("/1");

        ConferenceHall conferenceHall = new ConferenceHall();
        when(coworkingSpaceService.findConferenceHallById(1)).thenReturn(conferenceHall);

        ConferenceHallResponseDto conferenceHallResponseDto = conferenceHallMapper.conferenceHallToConferenceHallResponseDto(conferenceHall);
        when(conferenceHallMapper.conferenceHallToConferenceHallResponseDto(conferenceHall)).thenReturn(conferenceHallResponseDto);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(response.getOutputStream()).thenReturn(new ServletOutputStreamWrapper(outputStream));

        conferenceHallServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void testDoGetWithInvalidPath() throws Exception {
        when(request.getPathInfo()).thenReturn("/invalid");

        conferenceHallServlet.doGet(request, response);

        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный путь запроса!");
    }

    private class ServletOutputStreamWrapper extends jakarta.servlet.ServletOutputStream {
        private ByteArrayOutputStream outputStream;

        public ServletOutputStreamWrapper(ByteArrayOutputStream outputStream) {
            this.outputStream = outputStream;
        }

        @Override
        public void write(int b) {
            outputStream.write(b);
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(jakarta.servlet.WriteListener writeListener) {
        }
    }
}
