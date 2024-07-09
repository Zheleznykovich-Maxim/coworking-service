import org.example.coworking.servlet.WorkplaceServlet;
import org.example.coworking.service.CoworkingSpaceService;
import org.example.coworking.model.Workplace;
import org.example.coworking.mapper.WorkplaceMapper;
import org.example.coworking.dto.response.WorkplaceResponseDto;
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

public class WorkplaceServletTest {

    @InjectMocks
    private WorkplaceServlet workplaceServlet;

    @Mock
    private CoworkingSpaceService coworkingSpaceService;

    @Mock
    private WorkplaceMapper workplaceMapper;

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

        Collection<Workplace> workplaces = Arrays.asList(new Workplace(), new Workplace());
        when(coworkingSpaceService.getWorkplaces()).thenReturn(workplaces);

        Collection<WorkplaceResponseDto> workplaceResponseDtos = workplaceMapper.worplacesToWorkplaceResponseDtos(workplaces);
        when(workplaceMapper.worplacesToWorkplaceResponseDtos(workplaces)).thenReturn(workplaceResponseDtos);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(response.getOutputStream()).thenReturn(new ServletOutputStreamWrapper(outputStream));

        workplaceServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void testDoGetWithValidPath() throws Exception {
        when(request.getPathInfo()).thenReturn("/1");

        Workplace workplace = new Workplace();
        when(coworkingSpaceService.findWorkplaceById(1)).thenReturn(workplace);

        WorkplaceResponseDto workplaceResponseDto = workplaceMapper.workplaceToWorkplaceResponseDto(workplace);
        when(workplaceMapper.workplaceToWorkplaceResponseDto(workplace)).thenReturn(workplaceResponseDto);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(response.getOutputStream()).thenReturn(new ServletOutputStreamWrapper(outputStream));

        workplaceServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void testDoGetWithInvalidPath() throws Exception {
        when(request.getPathInfo()).thenReturn("/invalid");

        workplaceServlet.doGet(request, response);

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
