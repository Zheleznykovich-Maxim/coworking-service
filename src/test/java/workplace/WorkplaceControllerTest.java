package workplace;

import org.example.coworking.controller.WorkplaceController;
import org.example.coworking.domain.dto.request.WorkplaceRequestDto;
import org.example.coworking.domain.dto.response.WorkplaceResponseDto;
import org.example.coworking.service.WorkplaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Collection;
import java.util.Collections;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class WorkplaceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private WorkplaceService workplaceService;

    @InjectMocks
    private WorkplaceController workplaceController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(workplaceController).build();
    }

    @Test
    public void testGetWorkplaces() throws Exception {
        WorkplaceResponseDto workplaceResponseDto = new WorkplaceResponseDto(
                1, "workplace1", true);

        Collection<WorkplaceResponseDto> allWorkplaces = Collections.singletonList(workplaceResponseDto);

        when(workplaceService.getWorkplaces()).thenReturn(allWorkplaces);

        mockMvc.perform(get("/workplace")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    assertThat(json).contains("\"id\":1");
                });
    }

    @Test
    public void testGetWorkplaceById() throws Exception {
        WorkplaceResponseDto workplaceResponseDto = new WorkplaceResponseDto(
                1, "workplace1", true);

        when(workplaceService.findWorkplaceById(1)).thenReturn(workplaceResponseDto);

        mockMvc.perform(get("/workplace/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    assertThat(json).contains("\"id\":1");
                });
    }

    @Test
    public void testCreateWorkplace() throws Exception {
        WorkplaceRequestDto workplaceRequestDto = new WorkplaceRequestDto(
                "workplace1", true);
        WorkplaceResponseDto workplaceResponseDto = new WorkplaceResponseDto(
                1, "workplace1", true);

        when(workplaceService.addWorkplace(workplaceRequestDto)).thenReturn(workplaceResponseDto);

        String requestJson = "{ \"name\": \"workplace1\", \"isAvailable\": true }";

        mockMvc.perform(post("/workplace")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    assertThat(json).contains("\"id\":1");
                });
    }

    @Test
    public void testUpdateWorkplace() throws Exception {
        WorkplaceRequestDto workplaceRequestDto = new WorkplaceRequestDto(
                "workplace1", true);
        WorkplaceResponseDto workplaceResponseDto = new WorkplaceResponseDto(
                1, "workplace1", true);

        when(workplaceService.updateWorkplace(1, workplaceRequestDto)).thenReturn(workplaceResponseDto);

        String requestJson = "{ \"name\": \"workplace1\", \"isAvailable\": true }";

        mockMvc.perform(put("/workplace/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    assertThat(json).contains("\"id\":1");
                });
    }

    @Test
    public void testDeleteWorkplace() throws Exception {
        WorkplaceResponseDto workplaceResponseDto = new WorkplaceResponseDto(
                1, "workplace1", true);

        when(workplaceService.deleteWorkplace(1)).thenReturn(workplaceResponseDto);

        mockMvc.perform(delete("/workplace/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    assertThat(json).contains("\"id\":1");
                });
    }
}
