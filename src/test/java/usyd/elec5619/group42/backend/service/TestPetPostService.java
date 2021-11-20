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
import org.springframework.transaction.annotation.Transactional;
import usyd.elec5619.group42.backend.exception.EntityNotFoundException;
import usyd.elec5619.group42.backend.exception.InvalidParameterException;
import usyd.elec5619.group42.backend.pojo.*;
import usyd.elec5619.group42.backend.repository.BookmarkRepository;
import usyd.elec5619.group42.backend.repository.PetPostRepository;
import usyd.elec5619.group42.backend.repository.UserRepository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(value = "test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestPetPostService {
    private static Integer userId = 1;

    private static Integer petId = 1;

    @Resource
    private PetPostService petpostService;

    @MockBean
    private PetPostRepository petpostRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BookmarkRepository bookmarkRepository;

    @Resource
    private UserService userService;



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
    private PetPost producePetPost(){
        String title = "testPetPost";
        String description = "testPetPost";
        BigDecimal price = BigDecimal.valueOf(1);
        String location = "testPetPost";
        User seller = null;
        Picture thumbnail = null;
        Set<Picture> pictures = null;  // Should be a LinkedHashSet
        PetCategory category = null;
        Boolean sold = false;
        BigDecimal weight = BigDecimal.valueOf(1);
        String gender = "male";
        Integer ageYears = 1;
        Integer ageMonths = 1;
        Boolean desexed = false;
        Boolean microchip = false;
        Boolean vaccinated = false;
        Boolean wormed = false;
        PetPost petPost = new PetPost();
        petPost.setTitle(title);
        petPost.setDescription(description);
        petPost.setPrice(price);
        petPost.setLocation(location);
        petPost.setSeller(seller);
        petPost.setThumbnail(thumbnail);
        return petPost;
    }
    @BeforeEach
    public void setup() {
        userId = 1;
    }

    @Test
    public void getPetPost(){
        Integer petId = 1;
        PetPost expectedPetPost = producePetPost();
        Mockito.when(petpostRepository.findById(petId)).thenReturn(Optional.of(expectedPetPost));
        petpostService.get(petId);
        Mockito.verify(petpostRepository, Mockito.times(1)).findById(petId);
    }

    @Test
    public void getInvalidPetPost() {
        Integer petPostId = 1;
        Mockito.when(petpostRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> petpostService.get(petPostId));
        Mockito.verify(petpostRepository, Mockito.times(1)).findById(petPostId);
    }

    @Test
    public void bookmarkTest(){
        Integer petId = 1;
        String username = "bookmarkUser";
        User expectedUser = produceUserEntity(username);
        PetPost expectedPetPost = producePetPost();
        Bookmark bookmark = new Bookmark();
        bookmark.setUser(expectedUser);
        bookmark.setBookmarkedPost(expectedPetPost);

        Mockito.when(petpostRepository.findById(petId)).thenReturn(Optional.of(expectedPetPost));
        Mockito.when(userRepository.findByUsername(username)).thenReturn(expectedUser);
        Mockito.when(bookmarkRepository.findByUserAndBookmarkedPost(expectedUser,expectedPetPost)).thenReturn(bookmark);
        petpostService.bookmark("bookmarkUser",petId);
        assertNull(petpostService.getBookmark(username, petId));

    }
    @Test
    public void getAllForUser(){
        Integer userId = 1;
        User expectedUser = produceUserEntity("getUser");
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));
        PetPost expectedPetPost = producePetPost();
        expectedPetPost.setSeller(expectedUser);
        Mockito.when(petpostRepository.findById(petId)).thenReturn(Optional.of(expectedPetPost));
        List<PetPost> petPosts = petpostService.getAllForUser(expectedUser.getUserId());
        assertEquals(0,petPosts.size());
    }
    }