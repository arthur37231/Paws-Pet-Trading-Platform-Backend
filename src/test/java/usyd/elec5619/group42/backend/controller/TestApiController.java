
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
public class TestApiController {
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

        String username = "testuser" + userId++;
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
    @Test
    public void getSydneyWeather()throws Exception{
        mockMvc.perform(get("/weather/Sydney/")
                .header("Authorization", this.token)
                .accept(APPLICATION_JSON))
                .andExpect(status().is(200));

    }
    @Test
    public void getMelbourneWeather()throws Exception{
        mockMvc.perform(get("/weather/Melbourne/")
                .header("Authorization", this.token)
                .accept(APPLICATION_JSON))
                .andExpect(status().is(200));

    }
    @Test
    public void getCanberraWeather()throws Exception{
        mockMvc.perform(get("/weather/Canberra/")
                .header("Authorization", this.token)
                .accept(APPLICATION_JSON))
                .andExpect(status().is(200));

    }


}
