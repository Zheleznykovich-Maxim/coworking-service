package user;

import org.example.coworking.domain.dto.request.user.UserRegisterRequestDto;
import org.example.coworking.domain.dto.response.UserResponseDto;
import org.example.coworking.domain.model.User;
import org.example.coworking.domain.model.enums.UserRole;
import org.example.coworking.mapper.UserMapper;
import org.example.coworking.repository.UserRepository;
import org.example.coworking.service.impl.UserServiceImpl;
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

@DisplayName("Tests for UserService")
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    UserServiceImpl userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test adding a user")
    public void testAddUser() {

        UserRegisterRequestDto requestDto = new UserRegisterRequestDto(
                "user5", "user5", UserRole.USER);

        User user = new User();
        UserResponseDto responseDto = new UserResponseDto(
                1, "user5", UserRole.USER
        );
        when(userMapper.userRegisterRequestDtotoUser(requestDto)).thenReturn(user);
        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.empty());
        doNothing().when(userRepository).registerUser(user);
        when(userMapper.userToUserResponseDto(user)).thenReturn(responseDto);

        UserResponseDto result = userService.register(requestDto);

        assertThat(result).isEqualTo(responseDto);
        verify(userMapper, times(1)).userRegisterRequestDtotoUser(requestDto);
        verify(userRepository, times(1)).findUserByUsername(user.getUsername());
        verify(userRepository, times(1)).registerUser(user);
        verify(userMapper, times(1)).userToUserResponseDto(user);
    }

    @Test
    @DisplayName("Test deleting a user")
    public void testDeleteUser() {

        int userId = 1;
        User user = new User();
        user.setId(userId);

        UserResponseDto userResponseDto = new UserResponseDto(
                1, "user1", UserRole.USER);

        when(userRepository.findUserById(userId)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).removeUserById(userId);
        when(userMapper.userToUserResponseDto(user)).thenReturn(userResponseDto);

        UserResponseDto result = userService.removeUserById(userId);

        assertThat(result).isEqualTo(userResponseDto);
        verify(userRepository, times(1)).findUserById(userId);
        verify(userRepository, times(1)).removeUserById(userId);
        verify(userMapper, times(1)).userToUserResponseDto(user);
    }

    @Test
    @DisplayName("Test getting all users")
    public void testGetAllUsers() {

        User user1 = new User(1, "user1", "user1", UserRole.USER);
        User user2 = new User(2, "user2", "user2", UserRole.USER);
        Collection<User> users = Arrays.asList(
                user1,
                user2
        );
        Collection<UserResponseDto> userResponseDtos = Arrays.asList(
                userMapper.userToUserResponseDto(user1),
                userMapper.userToUserResponseDto(user2)
        );

        when(userRepository.getAllUsers()).thenReturn(users);
        when(userMapper.usersToUserResponseDtos(users)).thenReturn(userResponseDtos);

        Collection<UserResponseDto> result = userService.getUsers();

        assertThat(result).isEqualTo(userResponseDtos);
        verify(userRepository, times(1)).getAllUsers();
        verify(userMapper, times(1)).usersToUserResponseDtos(users);
    }

    @Test
    @DisplayName("Test finding user by id")
    public void testGetUserById() {

        int userId = 1;
        User user = new User();
        user.setId(userId);

        UserResponseDto userResponseDto = new UserResponseDto(1, "workplace1", UserRole.USER);

        when(userRepository.findUserById(userId)).thenReturn(Optional.of(user));
        when(userMapper.userToUserResponseDto(user)).thenReturn(userResponseDto);

        UserResponseDto result = userService.findUserById(userId);

        assertThat(result).isEqualTo(userResponseDto);
        verify(userRepository, times(1)).findUserById(userId);
        verify(userMapper, times(1)).userToUserResponseDto(user);
    }
}


