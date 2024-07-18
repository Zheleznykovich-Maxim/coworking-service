package user;

import org.example.coworking.controller.UserController;
import org.example.coworking.domain.dto.request.user.UserLoginRequestDto;
import org.example.coworking.domain.dto.request.user.UserRegisterRequestDto;
import org.example.coworking.domain.dto.response.UserResponseDto;
import org.example.coworking.domain.model.enums.UserRole;
import org.example.coworking.service.UserService;
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
@DisplayName("Tests for UserController")
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @DisplayName("Test get all users")
    public void testGetUsers() throws Exception {
        UserResponseDto userResponseDto = new UserResponseDto(1, "user1", UserRole.USER);

        Collection<UserResponseDto> allUsers = Collections.singletonList(userResponseDto);

        when(userService.getUsers()).thenReturn(allUsers);

        mockMvc.perform(get("/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    assertThat(json).contains("\"id\":1");
                });
    }

    @Test
    @DisplayName("Test get user by id")
    public void testGetUserById() throws Exception {
        UserResponseDto userResponseDto = new UserResponseDto(1, "user1", UserRole.USER);

        when(userService.findUserById(1)).thenReturn(userResponseDto);

        mockMvc.perform(get("/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    assertThat(json).contains("\"id\":1");
                });
    }

    @Test
    @DisplayName("Test get user by name")
    public void testGetUserByUsername() throws Exception {
        UserResponseDto userResponseDto = new UserResponseDto(1, "user1", UserRole.USER);

        when(userService.findUserByName("user1")).thenReturn(userResponseDto);

        mockMvc.perform(get("/user/username")
                        .param("username", "user1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    assertThat(json).contains("\"id\":1");
                });
    }

    @Test
    @DisplayName("Test register user")
    public void testRegisterUser() throws Exception {
        UserRegisterRequestDto userRegisterRequestDto = new UserRegisterRequestDto("user1", "password", UserRole.USER);
        UserResponseDto userResponseDto = new UserResponseDto(1, "user1", UserRole.USER);

        when(userService.register(userRegisterRequestDto)).thenReturn(userResponseDto);

        String requestJson = "{ \"username\": \"user1\", \"password\": \"password\", \"role\": \"USER\" }";

        mockMvc.perform(post("/user/register")
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
    @DisplayName("Test login user")
    public void testLoginUser() throws Exception {
        UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto("user1", "password");
        UserResponseDto userResponseDto = new UserResponseDto(1, "user1", UserRole.USER);

        when(userService.login(userLoginRequestDto)).thenReturn(userResponseDto);

        String requestJson = "{ \"username\": \"user1\", \"password\": \"password\" }";

        mockMvc.perform(post("/user/login")
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
    @DisplayName("Test update user")
    public void testUpdateUser() throws Exception {
        UserRegisterRequestDto userRegisterRequestDto = new UserRegisterRequestDto("user1", "password", UserRole.USER);
        UserResponseDto userResponseDto = new UserResponseDto(1, "user1", UserRole.USER);

        when(userService.updateUser(1, userRegisterRequestDto)).thenReturn(userResponseDto);

        String requestJson = "{ \"username\": \"user1\", \"password\": \"password\", \"role\": \"USER\" }";

        mockMvc.perform(put("/user/1")
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
    @DisplayName("Test delete user")
    public void testDeleteUser() throws Exception {
        UserResponseDto userResponseDto = new UserResponseDto(1, "user1", UserRole.USER);

        when(userService.removeUserById(1)).thenReturn(userResponseDto);

        mockMvc.perform(delete("/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    assertThat(json).contains("\"id\":1");
                });
    }
}
