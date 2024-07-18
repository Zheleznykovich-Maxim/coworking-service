package conferenceHall;

import org.example.coworking.controller.ConferenceHallController;
import org.example.coworking.domain.dto.request.ConferenceHallRequestDto;
import org.example.coworking.domain.dto.response.ConferenceHallResponseDto;
import org.example.coworking.service.ConferenceHallService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
@DisplayName("Tests for ConferenceHallController")
public class ConferenceHallControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ConferenceHallService conferenceHallService;

    @InjectMocks
    private ConferenceHallController conferenceHallController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(conferenceHallController).build();
    }

    @Test
    @DisplayName("Test get all conferenceHalls")
    public void testGetConferenceHalls() throws Exception {

        ConferenceHallResponseDto conferenceHallResponseDto = new ConferenceHallResponseDto(
                1, "conferenceHall1", true);

        Collection<ConferenceHallResponseDto> allConferenceHalls = Collections.singletonList(conferenceHallResponseDto);

        when(conferenceHallService.getConferenceHalls()).thenReturn(allConferenceHalls);

        mockMvc.perform(get("/coworking/conferenceHall")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    assertThat(json).contains("\"id\":1");
                });
    }

    @Test
    @DisplayName("Test get conferenceHall by id")
    public void testGetConferenceHallById() throws Exception {

        ConferenceHallResponseDto conferenceHallResponseDto = new ConferenceHallResponseDto(
                1, "conferenceHall1", true);

        when(conferenceHallService.findConferenceHallById(1)).thenReturn(conferenceHallResponseDto);

        mockMvc.perform(get("/coworking/conferenceHall/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    assertThat(json).contains("\"id\":1");
                });
    }

    @Test
    @DisplayName("Test create conferenceHall")
    public void testCreateConferenceHall() throws Exception {

        ConferenceHallRequestDto conferenceHallRequestDto = new ConferenceHallRequestDto(
                "conferenceHall1", true);
        ConferenceHallResponseDto conferenceHallResponseDto = new ConferenceHallResponseDto(
                1, "conferenceHall1", true);

        when(conferenceHallService.addConferenceHall(conferenceHallRequestDto)).thenReturn(conferenceHallResponseDto);

        String requestJson = "{ \"name\": \"conferenceHall1\", \"isAvailable\": true }";

        mockMvc.perform(post("/coworking/conferenceHall")
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
    @DisplayName("Test update conferenceHall")
    public void testUpdateConferenceHall() throws Exception {

        ConferenceHallRequestDto conferenceHallRequestDto = new ConferenceHallRequestDto(
                "conferenceHall1", true);
        ConferenceHallResponseDto conferenceHallResponseDto = new ConferenceHallResponseDto(
                1, "conferenceHall1", true);

        when(conferenceHallService.updateConferenceHall(1, conferenceHallRequestDto)).thenReturn(conferenceHallResponseDto);

        String requestJson = "{ \"name\": \"conferenceHall1\", \"isAvailable\": true }";

        mockMvc.perform(put("/coworking/conferenceHall/1")
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
    @DisplayName("Test delete conferenceHall")
    public void testDeleteConferenceHall() throws Exception {
        ConferenceHallResponseDto conferenceHallResponseDto = new ConferenceHallResponseDto(
                1, "conferenceHall1", true);

        when(conferenceHallService.deleteConferenceHall(1)).thenReturn(conferenceHallResponseDto);

        mockMvc.perform(delete("/coworking/conferenceHall/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    assertThat(json).contains("\"id\":1");
                });
    }
}
