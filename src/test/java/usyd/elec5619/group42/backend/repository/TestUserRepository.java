package usyd.elec5619.group42.backend.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import usyd.elec5619.group42.backend.pojo.User;

import javax.annotation.Resource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(value = "test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class TestUserRepository {
    @Resource
    private UserRepository userRepository;

    @AfterEach
    public void clearDB() {
        userRepository.deleteAll();
    }

    private User insertTestEntity(String _username) {
        String password = "test_entity";
        String firstName = "Test";
        String lastName = "UserEntity";
        String email = "testing.user@paws.com";
        String phone = "0123456789";
        String portrait = "Portrait of Testing User Entity";

        User user = new User();
        user.setUsername(_username);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPortrait(portrait);
        userRepository.save(user);

        return user;
    }

    @Test
    public void createUser() {
        String username = "test_user";
        String password = "test_user";
        String firstName = "Test";
        String lastName = "User";
        String email = "testing@paws.com";
        String phone = "0123456789";
        String portrait = "nothing here";

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPortrait(portrait);
        userRepository.save(user);

        assertNotNull(user.getUserId());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getEmail());
        assertEquals(phone, user.getPhone());
        assertEquals(portrait, user.getPortrait());
    }

    @Test
    public void getUserById() {
        User expectedUser = insertTestEntity("getUserById");
        Optional<User> actualUser = userRepository.findById(expectedUser.getUserId());
        assertTrue(actualUser.isPresent());

        User user = actualUser.get();
        assertEquals(expectedUser.getUsername(), user.getUsername());
        assertEquals(expectedUser.getPassword(), user.getPassword());
        assertEquals(expectedUser.getFirstName(), user.getFirstName());
        assertEquals(expectedUser.getLastName(), user.getLastName());
        assertEquals(expectedUser.getEmail(), user.getEmail());
        assertEquals(expectedUser.getPhone(), user.getPhone());
        assertEquals(expectedUser.getPortrait(), user.getPortrait());
    }

    @Test
    public void getUserList() {
        User user1 = insertTestEntity("getUserList1");
        User user2 = insertTestEntity("getUserList2");
        List<User> expectedUserList = Arrays.asList(user1, user2);

        List<User> userList = userRepository.findAll();
        assertEquals(2, userList.size());

        for(int i = 0; i < 2; i++) {
            assertEquals(expectedUserList.get(i).getUserId(), userList.get(i).getUserId());
            assertEquals(expectedUserList.get(i).getUsername(), userList.get(i).getUsername());
            assertEquals(expectedUserList.get(i).getEmail(), userList.get(i).getEmail());
            assertEquals(expectedUserList.get(i).getUserId(), userList.get(i).getUserId());
        }
    }

    @Test
    public void changeUserInformation() {
        User initUser = insertTestEntity("changeUserInfo");

        Optional<User> tmpUser = userRepository.findById(initUser.getUserId());
        assertTrue(tmpUser.isPresent());

        User user = tmpUser.get();

        String password = "newPassword";
        String email = "testing@gmail.com";
        String phone = "0000000000";
        String portrait = "Updated User Portrait";

        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPortrait(portrait);

        userRepository.save(user);

        tmpUser = userRepository.findById(initUser.getUserId());
        assertTrue(tmpUser.isPresent());

        user = tmpUser.get();
        assertEquals(password, user.getPassword());
        assertEquals(email, user.getEmail());
        assertEquals(phone, user.getPhone());
        assertEquals(portrait, user.getPortrait());
    }

    @Test
    public void findUserByUsername() {
        String username = "findUserByUsername";
        User insertedUser = insertTestEntity(username);

        User foundUser = userRepository.findByUsername(username);
        assertNotNull(foundUser);
        assertEquals(username, foundUser.getUsername());
    }
}
