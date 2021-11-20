package usyd.elec5619.group42.backend.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import usyd.elec5619.group42.backend.exception.EntityNotFoundException;
import usyd.elec5619.group42.backend.pojo.User;
import usyd.elec5619.group42.backend.service.UserService;
import usyd.elec5619.group42.backend.utils.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles(value = "test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TestUserController {
    private static int userId = 1;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalServerPort
    private int port;

    private String token;
    private volatile boolean registered = false;
    private User authorisedUser;

    static final Object lock = new Object();

    @BeforeEach
    public void setupUserToken() {
        if(token != null) {
            return;
        }

        String username = "testuser1" + userId++;
        String password = "testuser";
        authorisedUser = new User();
        authorisedUser.setUsername(username);
        authorisedUser.setPassword(password);

        ResponseEntity<ResponseBody> response = testRestTemplate.postForEntity(
                "http://localhost:" + port + "/auth/" + (registered ? "login" : "register"),
                new ResponseBody()
                        .put("username", username)
                        .put("password", password),
                ResponseBody.class
        );
        ResponseBody content = response.getBody();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(content);
        assertTrue(content.containsKey("token"));
        this.token = "Bearer " + content.get("token");

        if (!registered) {
            registered = true;
        }
    }


    @AfterEach
    public void clearToken() {
        if(this.token == null) {
            return;
        }

        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        header.put("Authorization", List.of(this.token));

        var response = testRestTemplate.postForEntity(
                "http://localhost:" + port + "/auth/logout",
                new HttpEntity<>(header),
                ResponseBody.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        this.token = null;
        this.authorisedUser = null;
    }

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

    @Test
    public void getUser() throws Exception {
        User expectedUser = produceUserEntity("getUser");
        Mockito.when(userService.get(expectedUser.getUserId())).thenReturn(expectedUser);

        mockMvc.perform(get("/user/view/" + expectedUser.getUserId())
                        .header("Authorization", this.token)
                        .accept(APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("\"user\"")))
                        .andExpect(content().string(containsString(String.format("\"username\":\"%s\"", expectedUser.getUsername()))));

        Mockito.verify(userService, Mockito.times(1)).get(expectedUser.getUserId());
    }

    @Test
    public void getUserInvalidId() throws Exception {
        int invalidUserId = 1;

        Mockito.when(userService.get(Mockito.anyInt())).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(get("/user/view/" + invalidUserId)
                .header("Authorization", this.token)
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
        Mockito.verify(userService, Mockito.times(1)).get(invalidUserId);
    }

    @Test
    public void getUserList() throws Exception {
        int listSize = 5;
        List<User> expectedUserList = new ArrayList<>();
        for(int i = 0; i < listSize; i++) {
            expectedUserList.add(produceUserEntity("getUserList" + i));
        }

        Mockito.when(userService.getAll()).thenReturn(expectedUserList);

        mockMvc.perform(get("/user/view")
                .header("Authorization", this.token)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user_list.length()", equalTo(listSize)))
                .andExpect(jsonPath("$.user_list[0].userId", equalTo(expectedUserList.get(0).getUserId())))
                .andExpect(jsonPath("$.user_list[1].userId", equalTo(expectedUserList.get(1).getUserId())))
                .andExpect(jsonPath("$.user_list[2].userId", equalTo(expectedUserList.get(2).getUserId())))
                .andExpect(jsonPath("$.user_list[3].userId", equalTo(expectedUserList.get(3).getUserId())))
                .andExpect(jsonPath("$.user_list[4].userId", equalTo(expectedUserList.get(4).getUserId())))
                .andExpect(jsonPath("$.user_list[0].username", equalTo(expectedUserList.get(0).getUsername())))
                .andExpect(jsonPath("$.user_list[1].username", equalTo(expectedUserList.get(1).getUsername())))
                .andExpect(jsonPath("$.user_list[2].username", equalTo(expectedUserList.get(2).getUsername())))
                .andExpect(jsonPath("$.user_list[3].username", equalTo(expectedUserList.get(3).getUsername())))
                .andExpect(jsonPath("$.user_list[4].username", equalTo(expectedUserList.get(4).getUsername())));
        Mockito.verify(userService, Mockito.times(1)).getAll();
    }

    @Test
    public void changePassword() throws Exception {
        User origin = produceUserEntity(authorisedUser.getUsername());
        String oldPassword = authorisedUser.getPassword();
        String newPassword = "new_testuser";

        Mockito.when(userService.changePassword(authorisedUser.getUsername(), oldPassword, newPassword)).thenReturn(
                new User(
                        origin.getUserId(),
                        origin.getUsername(),
                        newPassword,
                        origin.getFirstName(),
                        origin.getLastName(),
                        origin.getLocation(),
                        origin.getEmail(),
                        origin.getPhone(),
                        origin.getPortrait(),
                        origin.getFavoriteList(),
                        origin.getPosts(),
                        origin.getBookmarkedPosts()
                )
        );

        mockMvc.perform(put("/user/change_password")
                .content("{" +
                        "\"oldPassword\":\"" + oldPassword + "\"," +
                        "\"newPassword\":\"" + newPassword + "\"" +
                        "}")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .header("Authorization", this.token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", equalTo("ok")))
                .andExpect(jsonPath("$.user.userId", equalTo(origin.getUserId())))
                .andExpect(jsonPath("$.user.username", equalTo(origin.getUsername())));
        Mockito.verify(userService, Mockito.times(1)).changePassword(origin.getUsername(), oldPassword, newPassword);
    }

    @Test
    public void requestUnauthorised() throws Exception {
        Mockito.when(userService.get(Mockito.anyInt())).thenReturn(null);

        mockMvc.perform(get("/user/view/1")
                .accept(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
        Mockito.verify(userService, Mockito.never()).get(Mockito.anyInt());
    }

    @Test
    public void updateUserInfo() throws Exception {
        String firstName = "First";
        String lastName = "Last";
        String email = "22345@gmail.com";
        String phone = "0987654321";
        String location = "University of Sydney";
        String portrait = "This is a test user's portrait";
        User userInfo = new User();
        userInfo.setFirstName(firstName);
        userInfo.setLastName(lastName);
        userInfo.setEmail(email);
        userInfo.setPhone(phone);
        userInfo.setPortrait(portrait);
        userInfo.setLocation(location);

        Mockito.when(userService.update(authorisedUser.getUsername(), userInfo)).thenReturn(
                new User(
                        authorisedUser.getUserId(),
                        authorisedUser.getUsername(),
                        authorisedUser.getPassword(),
                        userInfo.getFirstName(),
                        userInfo.getLastName(),
                        userInfo.getLocation(),
                        userInfo.getEmail(),
                        userInfo.getPhone(),
                        userInfo.getPortrait(),
                        userInfo.getFavoriteList(),
                        userInfo.getPosts(),
                        userInfo.getBookmarkedPosts()
                )
        );

        mockMvc.perform(put("/user/update")
                .content("{" +
                        "\"firstName\":\"" + userInfo.getFirstName() + "\"," +
                        "\"lastName\":\"" + userInfo.getLastName() + "\"," +
                        "\"email\":\"" + userInfo.getEmail() + "\"," +
                        "\"phone\":\"" + userInfo.getPhone() + "\"," +
                        "\"portrait\":\"" + userInfo.getPortrait() + "\"," +
                        "\"location\":\"" + userInfo.getLocation() + "\"" +
                        "}")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .header("Authorization", this.token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", equalTo("ok")))
                .andExpect(jsonPath("$.user", notNullValue()))
                .andExpect(jsonPath("$.user.username", equalTo(authorisedUser.getUsername())))
                .andExpect(jsonPath("$.user.firstName", equalTo(userInfo.getFirstName())))
                .andExpect(jsonPath("$.user.lastName", equalTo(userInfo.getLastName())))
                .andExpect(jsonPath("$.user.email", equalTo(userInfo.getEmail())))
                .andExpect(jsonPath("$.user.phone", equalTo(userInfo.getPhone())))
                .andExpect(jsonPath("$.user.portrait", equalTo(userInfo.getPortrait())))
                .andExpect(jsonPath("$.user.location", equalTo(userInfo.getLocation())));
        Mockito.verify(userService, Mockito.times(1)).update(authorisedUser.getUsername(), userInfo);
    }
}
