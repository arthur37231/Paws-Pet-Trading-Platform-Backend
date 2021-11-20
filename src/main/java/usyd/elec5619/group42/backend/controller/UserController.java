package usyd.elec5619.group42.backend.controller;

import io.swagger.annotations.*;
import io.swagger.models.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import usyd.elec5619.group42.backend.pojo.BuyRequest;
import usyd.elec5619.group42.backend.pojo.User;
import usyd.elec5619.group42.backend.service.BuyRequestService;
import usyd.elec5619.group42.backend.service.UserService;
import usyd.elec5619.group42.backend.utils.ResponseBody;
import usyd.elec5619.group42.backend.utils.ServletUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static usyd.elec5619.group42.backend.utils.SwaggerResponseExample.*;

@Api(value = "APIs related to User entity, e.g. user's profile")
@RestController
@RequestMapping(path = "/user", produces = APPLICATION_JSON_VALUE)
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private BuyRequestService buyRequestService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",
                    examples = @Example(value = {@ExampleProperty(mediaType = APPLICATION_JSON_VALUE, value = GET_USER_OK)}),
                    response = Response.class)
    })
    @GetMapping("/view/{userId}")
    public ResponseEntity<ResponseBody> getUser(@PathVariable Integer userId) {
        User user = userService.get(userId);
        return ResponseEntity.ok(
                new ResponseBody().put("user", user)
        );
    }

    @GetMapping("/view")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",
                    examples = @Example(value = {@ExampleProperty(mediaType = APPLICATION_JSON_VALUE, value = GET_USER_LIST_OK)}),
                    response = Response.class)
    })
    public ResponseEntity<ResponseBody> getUserList() {
        List<User> userList = userService.getAll();
        return ResponseEntity.ok().body(
                new ResponseBody()
                        .put("user_list", userList)
        );
    }

    @PutMapping("/change_password")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "oldPassword", required = true, dataTypeClass = String.class, value = "Password the user currently using"),
            @ApiImplicitParam(name = "newPassword", required = true, dataTypeClass = String.class, value = "New password the user is going to change")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",
                    examples = @Example(value = {@ExampleProperty(mediaType = APPLICATION_JSON_VALUE, value = CHANGE_PASSWORD_OK)}),
                    response = Response.class
            )
    })
    public ResponseEntity<ResponseBody> changePassword(HttpServletRequest request) {
        ServletUtils.jsonToAttribute(request);
        ServletUtils.checkContainsAttributes(request, "oldPassword", "newPassword");
        User user = userService.changePassword(
                (String) request.getAttribute("username"),
                (String) request.getAttribute("oldPassword"),
                (String) request.getAttribute("newPassword")
        );

        return ResponseEntity.ok(
                new ResponseBody()
                        .put("message", "ok")
                        .put("user", user)
        );
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",
                    examples = @Example(value = {@ExampleProperty(mediaType = APPLICATION_JSON_VALUE, value = UPDATE_PROFILE_OK)}),
                    response = Response.class
            )
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseBody> updateProfile(@RequestBody User user, HttpServletRequest request) {
        return ResponseEntity.ok(
                new ResponseBody()
                        .put("message", "ok")
                        .put("user", userService.update((String) request.getAttribute("username"), user))
        );
    }

    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "preferenceList", value = "Array of PetCategory objects, at least including the categoryId attribute", required = true, dataTypeClass = Array.class)
    })

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",
                    examples = @Example(value = {@ExampleProperty(mediaType = APPLICATION_JSON_VALUE, value = UPDATE_PREFERENCE_OK)}),
                    response = Response.class
            )
    })
    @PutMapping("/preference")
    public ResponseEntity<ResponseBody> setPreference(HttpServletRequest request) {
        ServletUtils.jsonToAttribute(request);
        ServletUtils.checkContainsAttributes(request, "preferenceList");
        List<Map<String, Object>> preferenceList = (List<Map<String, Object>>) request.getAttribute("preferenceList");

        return ResponseEntity.ok(
                new ResponseBody()
                        .put("message", "ok")
                        .put("user", userService.setPreference(
                                (String) request.getAttribute("username"),
                                preferenceList
                        ))
        );
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",
                    examples = @Example(value = {@ExampleProperty(mediaType = APPLICATION_JSON_VALUE, value = GET_PREFERENCE_OK)}),
                    response = Response.class
            )
    })
    @GetMapping("/preference")
    public ResponseEntity<ResponseBody> getPreference(HttpServletRequest request) {
        User user = userService.getByUsername((String) request.getAttribute("username"));

        return ResponseEntity.ok(
                new ResponseBody()
                        .put("username", user.getUsername())
                        .put("preferenceList", user.getFavoriteList())
        );
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",
                    examples = @Example(value = {@ExampleProperty(mediaType = APPLICATION_JSON_VALUE, value = GET_BOOKMARKS_OK)}),
                    response = Response.class
            )
    })
    @GetMapping("/bookmark/{id}")
    public ResponseEntity<ResponseBody> getBookmarkHistory(@PathVariable Integer id) {
        return ResponseEntity.ok(
                new ResponseBody()
                        .put("bookmarks", userService.getBookmark(id))
        );
    }

    @GetMapping("/buyRequests/received")
    public ResponseEntity<ResponseBody> getReceivedBuyRequests(HttpServletRequest request) {
        List<BuyRequest> buyRequests =
                buyRequestService.getReceivedRequestList((String) request.getAttribute("username"));

        return ResponseEntity.ok(
                new ResponseBody()
                        .put("message", "ok")
                        .put("buyRequests", buyRequests)
        );
    }

    @GetMapping("/buyRequests/sent")
    public ResponseEntity<ResponseBody> getSentBuyRequests(HttpServletRequest request) {
        List<BuyRequest> buyRequests =
                buyRequestService.getSentRequestList((String) request.getAttribute("username"));

        return ResponseEntity.ok(
                new ResponseBody()
                        .put("message", "ok")
                        .put("buyRequests", buyRequests)
        );
    }
}
