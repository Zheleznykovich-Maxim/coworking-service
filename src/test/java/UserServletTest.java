import org.example.coworking.model.enums.UserRole;
import org.example.coworking.servlet.UserServlet;
import org.example.coworking.service.UserService;
import org.example.coworking.model.User;
import org.example.coworking.mapper.UserMapper;
import org.example.coworking.dto.request.user.UserLoginRequestDto;
import org.example.coworking.dto.request.user.UserRegisterRequestDto;
import org.example.coworking.dto.response.UserResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.*;

public class UserServletTest {

    @InjectMocks
    private UserServlet userServlet;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDoPostRegister() throws Exception {
        when(request.getPathInfo()).thenReturn("/register");

        String jsonRequest = "{\"username\":\"testuser\",\"password\":\"password123\",\"role\":\"USER\"}";
        InputStream inputStream = new ByteArrayInputStream(jsonRequest.getBytes(StandardCharsets.UTF_8));
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        when(request.getReader()).thenReturn(reader);

        UserRegisterRequestDto userRegisterRequestDto = new UserRegisterRequestDto(
                "testuser",
                "password123",
                UserRole.USER
        );

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setRole(UserRole.USER);

        when(userMapper.userRegisterRequestDtotoUser(any(UserRegisterRequestDto.class))).thenReturn(user);
        when(userService.checkUsernameExists("testuser")).thenReturn(false);

        doNothing().when(userService).register(user);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(response.getOutputStream()).thenReturn(new ServletOutputStreamWrapper(outputStream));

        userServlet.doPost(request, response);
        verify(response).setStatus(HttpServletResponse.SC_CREATED);
        verify(response).setContentType("application/json");
    }

    @Test
    public void testDoPostLogin() throws Exception {
        when(request.getPathInfo()).thenReturn("/login");

        String jsonRequest = "{\"username\":\"testuser\",\"password\":\"password123\"}";
        when(request.getReader()).thenReturn(new UserServletTest.DummyReader(jsonRequest));

        UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto("testuser", "password123");

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");

        when(userMapper.userLoginRequestDtotoUser(userLoginRequestDto)).thenReturn(user);
        when(userService.login(user)).thenReturn(true);
        when(userService.findUserByName("testuser")).thenReturn(user);

        UserResponseDto userResponseDto = userMapper.userToUserResponseDto(user, "Авторизация прошла успешна!");
        when(userMapper.userToUserResponseDto(user, "Авторизация прошла успешна!")).thenReturn(userResponseDto);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(response.getOutputStream()).thenReturn(new ServletOutputStreamWrapper(outputStream));

        userServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response).setContentType("application/json");
    }

    @Test
    public void testDoPostLoginInvalidCredentials() throws Exception {
        when(request.getPathInfo()).thenReturn("/login");

        String jsonRequest = "{\"username\":\"testuser\",\"password\":\"wrongpassword\"}";
        when(request.getReader()).thenReturn(new UserServletTest.DummyReader(jsonRequest));

        UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto("testuser", "wrongpassword");

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("wrongpassword");

        when(userMapper.userLoginRequestDtotoUser(userLoginRequestDto)).thenReturn(user);
        when(userService.login(user)).thenReturn(false);

        userServlet.doPost(request, response);

        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid username or password");
    }

    @Test
    public void testDoPostInvalidPath() throws Exception {
        when(request.getPathInfo()).thenReturn("/invalid");

        userServlet.doPost(request, response);

        verify(response).sendError(HttpServletResponse.SC_NOT_FOUND, "Url not found");
    }

    @Test
    public void testDoPostNullPath() throws Exception {
        when(request.getPathInfo()).thenReturn(null);

        userServlet.doPost(request, response);

        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request");
    }

    private static class ServletOutputStreamWrapper extends jakarta.servlet.ServletOutputStream {
        private final ByteArrayOutputStream outputStream;

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

    // DummyReader class to mock request reader
    private class DummyReader extends java.io.BufferedReader {
        public DummyReader(String data) {
            super(new java.io.StringReader(data));
        }
    }
}
