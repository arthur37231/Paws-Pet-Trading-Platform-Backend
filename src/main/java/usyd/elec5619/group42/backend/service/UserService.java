package usyd.elec5619.group42.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import usyd.elec5619.group42.backend.exception.EntityNotFoundException;
import usyd.elec5619.group42.backend.exception.InvalidParameterException;
import usyd.elec5619.group42.backend.pojo.Bookmark;
import usyd.elec5619.group42.backend.pojo.PetCategory;
import usyd.elec5619.group42.backend.pojo.PetPost;
import usyd.elec5619.group42.backend.pojo.User;
import usyd.elec5619.group42.backend.repository.BookmarkRepository;
import usyd.elec5619.group42.backend.repository.PetCategoryRepository;
import usyd.elec5619.group42.backend.repository.UserRepository;

import javax.annotation.Resource;
import java.util.*;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserRepository userRepository;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private PetCategoryRepository petCategoryRepository;
    @Resource
    private BookmarkRepository bookmarkRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User get(Integer userId) throws EntityNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new EntityNotFoundException("User with UserID: " + userId);
        }
        return user.get();
    }

    public UserRepository.SimpleUser getSimpleUser(Integer userId) throws EntityNotFoundException {
        var user = userRepository.findByUserId(userId);
        if(user.isEmpty()) {
            throw new EntityNotFoundException("User with UserID: " + userId);
        }
        return user.get();
    }

    public void delete(Integer userId) {
        userRepository.deleteById(userId);
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserRepository.SimpleUser getSimpleUserByUsername(String username) throws EntityNotFoundException {
        var user = userRepository.findSimpleUserByUsername(username);
        if(user.isEmpty()) {
            throw new EntityNotFoundException("User with Username: " + username);
        }
        return user.get();
    }

    public User changePassword(String username, String oldPassword, String newPassword) throws InvalidParameterException {
        User user = userRepository.findByUsername(username);

        if(oldPassword.equals(newPassword)) {
            return user;
        }

        if(passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } else {
            throw new InvalidParameterException("Old Password");
        }
        return user;
    }

    public User update(
            String username,
            User userInfo
    ) {
        User user = userRepository.findByUsername(username);
        user.setFirstName(userInfo.getFirstName());
        user.setLastName(userInfo.getLastName());
        user.setLocation(userInfo.getLocation());
        user.setEmail(userInfo.getEmail());
        user.setPhone(userInfo.getPhone());
        user.setPortrait(userInfo.getPortrait());
        userRepository.save(user);
        return user;
    }

    public User setPreference(String username, List<Map<String, Object>> categories) {
        User user = userRepository.findByUsername(username);

        Set<PetCategory> preferenceList = new HashSet<>();
        for(Map<String, Object> petCategory : categories) {
            preferenceList.add(petCategoryRepository.getById((Integer) petCategory.get("categoryId")));
        }
        user.setFavoriteList(preferenceList);

        userRepository.save(user);
        return user;
    }

    public List<PetPost> getBookmark(int userId) {
        User user = userRepository.getById(userId);

        List<Bookmark> bookmarks = bookmarkRepository.findByUser(user);
        List<PetPost> petPosts = new ArrayList<>();
        for(Bookmark bookmark : bookmarks) {
            petPosts.add(bookmark.getBookmarkedPost());
        }

        return petPosts;
    }
}
