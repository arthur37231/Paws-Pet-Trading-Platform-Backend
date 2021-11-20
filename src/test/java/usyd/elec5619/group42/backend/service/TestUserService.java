package usyd.elec5619.group42.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import usyd.elec5619.group42.backend.exception.EntityNotFoundException;
import usyd.elec5619.group42.backend.exception.InvalidParameterException;
import usyd.elec5619.group42.backend.pojo.User;
import usyd.elec5619.group42.backend.repository.UserRepository;

import javax.annotation.Resource;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(value = "test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestUserService {
    private static Integer userId = 1;

    @Resource
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private User produceUserEntity(String _username) {
        String password = "test_entity";
        String firstName = "Test";
        String lastName = "UserEntity";
        String email = "testing.user@paws.com";
        String phone = "0123456789";
        String portrait = "Portrait of Testing User Entity";

        User user = new User();
        user.setUserId(userId++);
        user.setUsername(_username);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPortrait(portrait);

        return user;
    }

    @BeforeEach
    public void setup() {
        userId = 1;
    }

    @Test
    public void getUser() {
        Integer userId = 1;
        User expectedUser = produceUserEntity("getUser");
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));
        userService.get(userId);
        Mockito.verify(userRepository, Mockito.times(1)).findById(userId);
    }

    @Test
    public void getInvalidUser() {
        Integer userId = 1;
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.get(userId));
        Mockito.verify(userRepository, Mockito.times(1)).findById(userId);
    }

    @Test
    public void getByUsername() {
        String username = "getByUsername";
        String invalidUsername = "invalidUsername";
        User expectedUser = produceUserEntity(username);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(expectedUser);
        Mockito.when(userRepository.findByUsername(invalidUsername)).thenReturn(null);

        assertEquals(expectedUser, userService.getByUsername(username));
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(username);
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.anyString());

        assertNull(userService.getByUsername(invalidUsername));
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(invalidUsername);
        Mockito.verify(userRepository, Mockito.times(2)).findByUsername(Mockito.anyString());
    }

    @Test
    public void changePasswordNormal() {
        String username = "changePasswordNormal";
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";

        User expectedUser = produceUserEntity(username);
        expectedUser.setPassword(oldPassword);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(expectedUser);
        Mockito.when(passwordEncoder.matches(oldPassword, oldPassword)).thenReturn(true);
        Mockito.when(passwordEncoder.encode(newPassword)).thenReturn(newPassword);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(null);

        User actualUser = userService.changePassword(username, oldPassword, newPassword);
        assertEquals(expectedUser, actualUser);
        assertEquals(newPassword, actualUser.getPassword());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(username);
        Mockito.verify(passwordEncoder, Mockito.times(1)).matches(oldPassword, oldPassword);
        Mockito.verify(passwordEncoder, Mockito.times(1)).encode(newPassword);
    }

    @Test
    public void changeSamePassword() {
        String username = "changeSamePassword";
        String oldPassword = "oldPassword";
        String newPassword = oldPassword;

        User expectedUser = produceUserEntity(username);
        expectedUser.setPassword(oldPassword);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(expectedUser);

        User actualUser = userService.changePassword(username, oldPassword, newPassword);
        assertEquals(expectedUser, actualUser);
        assertEquals(oldPassword, actualUser.getPassword());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(username);
        Mockito.verify(passwordEncoder, Mockito.never()).matches(oldPassword, oldPassword);
        Mockito.verify(passwordEncoder, Mockito.never()).encode(newPassword);
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(User.class));
    }

    @Test
    public void changePasswordOldWrong() {
        String username = "changePwdWrongOld";
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";

        User expectedUser = produceUserEntity(username);
        expectedUser.setPassword(oldPassword);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(expectedUser);
        Mockito.when(passwordEncoder.matches(newPassword, oldPassword)).thenReturn(false);
        Mockito.when(passwordEncoder.encode(oldPassword)).thenReturn(oldPassword);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(null);

        assertThrows(InvalidParameterException.class, () -> userService.changePassword(username, newPassword, oldPassword));
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(username);
        Mockito.verify(passwordEncoder, Mockito.times(1)).matches(newPassword, oldPassword);
        Mockito.verify(passwordEncoder, Mockito.never()).encode(oldPassword);
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(User.class));
    }

    @Test
    public void updateUserInfo() {
        String username = "updateUserInfo";
        User expectedUser = produceUserEntity(username);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(expectedUser);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(null);

        String firstName = "Tester";
        String lastName = "Bon";
        String email = "12345@hotmail.com";
        String phone = "None";
        User userInfo = new User();
        userInfo.setFirstName(firstName);
        userInfo.setLastName(lastName);
        userInfo.setEmail(email);
        userInfo.setPhone(phone);

        User result = userService.update(username, userInfo);
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(username);
        assertEquals(username, result.getUsername());
        assertEquals(expectedUser.getUserId(), result.getUserId());
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
        assertEquals(email, result.getEmail());
        assertEquals(phone, result.getPhone());
    }
}
