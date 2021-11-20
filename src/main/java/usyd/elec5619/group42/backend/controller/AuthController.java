package usyd.elec5619.group42.backend.controller;

import io.swagger.annotations.*;
import io.swagger.models.Response;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import usyd.elec5619.group42.backend.exception.UnAuthorisedException;
import usyd.elec5619.group42.backend.pojo.User;
import usyd.elec5619.group42.backend.service.AuthService;
import usyd.elec5619.group42.backend.service.UserService;
import usyd.elec5619.group42.backend.utils.AuthUtils;
import usyd.elec5619.group42.backend.utils.Constants;
import usyd.elec5619.group42.backend.utils.ResponseBody;
import usyd.elec5619.group42.backend.utils.ServletUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static usyd.elec5619.group42.backend.utils.SwaggerResponseExample.*;

@Api(value = "User authentication and authorisation related APIs")
@RestController
@RequestMapping(path = "/auth", produces = APPLICATION_JSON_VALUE)
public class AuthController {
    @Resource
    private UserService userService;
    @Resource
    private AuthService authService;

    @Operation(summary = "User sign in", description = "Sign in user's account with username and password to request user's info and access token")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "username", required = true, value = "User's account username", dataTypeClass = String.class),
            @ApiImplicitParam(name = "password", required = true, value = "User's account password", dataTypeClass = String.class)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",
                    examples = @Example(value = {@ExampleProperty(mediaType = APPLICATION_JSON_VALUE, value = LOGIN_OK)}),
                    response = Response.class)
    })
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(HttpServletRequest request) {
        var user = userService.getSimpleUserByUsername((String) request.getAttribute("username"));

        String token = (String) request.getAttribute("token");
        authService.login(token, user.getUserId());

        return ResponseEntity.ok(
                new ResponseBody()
                        .put("token", token)
                        .put("user", user)
        );
    }

    @Operation(summary = "User sign out", description = "Sign out user's account with access token in request header. After sign out, the access token will be expired immediately.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",
                    examples = @Example(value = {@ExampleProperty(mediaType = APPLICATION_JSON_VALUE, value = LOGOUT_OK)}),
                    response = Response.class)
    })
    @PostMapping("/logout")
    public ResponseEntity<ResponseBody> logout(HttpServletRequest request) {
        authService.logout(AuthUtils.extractToken(request));
        return ResponseEntity.ok(
                new ResponseBody()
                        .put("message", "Sign out successfully")
        );
    }

    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "username", value = "User entity's username attribute", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "password", value = "User entity's password attribute", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "firstName", value = "User entity's first name attribute", dataTypeClass = String.class),
            @ApiImplicitParam(name = "lastName", value = "User entity's last name attribute", dataTypeClass = String.class),
            @ApiImplicitParam(name = "location", value = "Registering user's location in String format", dataTypeClass = String.class),
            @ApiImplicitParam(name = "email", value = "Registering user's email", dataTypeClass = String.class),
            @ApiImplicitParam(name = "phone", value = "Registering user's phone number in String format", dataTypeClass = String.class),
            @ApiImplicitParam(name = "portrait", value = "User's profile portrait. Upload the image to the server first, then include its URI here", dataTypeClass = String.class)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",
                    examples = @Example(value = {@ExampleProperty(mediaType = APPLICATION_JSON_VALUE, value = LOGIN_OK)}),
                    response = Response.class)
    })
    @PostMapping("/register")
    public ResponseEntity<ResponseBody> register(HttpServletRequest request) {
        ServletUtils.jsonToAttribute(request);
        ServletUtils.checkContainsAttributes(request, "username", "password");

        User user = authService.addUser(
                (String) request.getAttribute("username"),
                (String) request.getAttribute("password"),
                (String) request.getAttribute("firstName"),
                (String) request.getAttribute("lastName"),
                (String) request.getAttribute("location"),
                (String) request.getAttribute("email"),
                (String) request.getAttribute("phone"),
                (String) request.getAttribute("portrait"),
                new HashSet<>()
        );

        String token = AuthUtils.generateToken(
                Constants.USER_TOKEN_FLAG,
                request.getRequestURL().toString(),
                user.getUsername()
        );
        authService.login(token, user.getUserId());

        return ResponseEntity.ok().body(
                new ResponseBody()
                        .put("user", user)
                        .put("token", token)
        );
    }

    @GetMapping("/ping")
    public ResponseEntity<ResponseBody> checkToken(HttpServletRequest request) {
        if(authService.isValidToken(AuthUtils.extractToken(request))) {
            return ResponseEntity.ok(
                    new ResponseBody()
                            .put("message", "pong")
            );
        }

        return ResponseEntity.status(401)
                .body(new ResponseBody(new UnAuthorisedException()));
    }
}
