package usyd.elec5619.group42.backend.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import usyd.elec5619.group42.backend.exception.EntityNotFoundException;
import usyd.elec5619.group42.backend.pojo.*;
import usyd.elec5619.group42.backend.repository.BookmarkRepository;
import usyd.elec5619.group42.backend.repository.PetPostRepository;
import usyd.elec5619.group42.backend.repository.PictureRepository;
import usyd.elec5619.group42.backend.repository.UserRepository;
import usyd.elec5619.group42.backend.utils.Constants;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PetPostService {
    @Resource
    private PetPostRepository petPostRepository;
    @Resource
    private PictureRepository pictureRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private UserService userService;
    @Resource
    private BookmarkRepository bookmarkRepository;
    @Resource
    private PetCategoryService petCategoryService;

    public PetPost add(String title, String description, BigDecimal price, String location,
                       Date creationTime, User seller, Picture thumbnail, Set<Picture> pictures,
                       PetCategory category, BigDecimal weight, String gender, Integer ageYears,
                       Integer ageMonths, Boolean desexed, Boolean microchip, Boolean vaccinated, Boolean wormed) {
        PetPost petPost = new PetPost();
        petPost.setTitle(title);
        petPost.setDescription(description);
        petPost.setPrice(price);
        petPost.setLocation(location);
        petPost.setCreationTime(creationTime);
        petPost.setSeller(seller);
        petPost.setThumbnail(thumbnail);
        petPost.setPictures(pictures);
        petPost.setCategory(category);
        petPost.setWeight(weight);
        petPost.setGender(gender);
        petPost.setAgeYears(ageYears);
        petPost.setAgeMonths(ageMonths);
        petPost.setDesexed(desexed);
        petPost.setMicrochip(microchip);
        petPost.setVaccinated(vaccinated);
        petPost.setWormed(wormed);
        petPost.setClickCount(0);
        petPost.setSold(false);
        return petPostRepository.save(petPost);
    }

    private EntityNotFoundException petPostNotFound(Integer postId) {
        return new EntityNotFoundException("Pet Post with PostID: " + postId);
    }

    public PetPost get(Integer postId) throws EntityNotFoundException {
        Optional<PetPost> petPost = petPostRepository.findById(postId);
        if(petPost.isEmpty()) {
            throw petPostNotFound(postId);
        }
        return petPostRepository.getById(postId);
    }

    public List<PetPost> getAll() {
        return petPostRepository.findAll();
    }

    public void bookmark(String username, Integer postId) throws EntityNotFoundException {
        User user = userRepository.findByUsername(username);
        PetPost petPost = this.get(postId);

        Bookmark bookmark = new Bookmark();
        bookmark.setUser(user);
        bookmark.setBookmarkedPost(petPost);
        bookmarkRepository.save(bookmark);
    }

    public Bookmark getBookmark(String username, Integer postId) throws EntityNotFoundException {
        User user = userRepository.findByUsername(username);
        PetPost petPost = get(postId);
        return bookmarkRepository.findByUserAndBookmarkedPost(user, petPost);
    }

    public void unbookmark(String username, Integer postId) throws EntityNotFoundException {
        Bookmark bookmark = getBookmark(username, postId);

        bookmarkRepository.delete(bookmark);
    }

    public boolean checkIfBooked(String username, Integer postId) {
        return getBookmark(username, postId) != null;
    }

    public List<PetPost> getAllForUser(Integer id) {
        User user = userService.get(id);
        return petPostRepository.findBySeller(user);
    }

    public List<PetPost> getByCategory(String categoryName) {
        List<PetCategory> categories = petCategoryService.getByName(categoryName);
        return petPostRepository.findAllByCategoryInAndSoldIsFalseOrderByCreationTimeDesc(
                Pageable.ofSize(Constants.HOME_PAGE_CATEGORY_COLUMN_SIZE),
                categories
        );
    }

    public List<PetPost> getLatestPublished() {
        return petPostRepository.findAllBySoldIsFalseOrderByCreationTimeDesc(
                Pageable.ofSize(Constants.HOME_PAGE_LATEST_COLUMN_SIZE)
        );
    }

    public List<PetPost> getMostPopular() {
        return petPostRepository.findAllBySoldIsFalseOrderByClickCountDesc(
                Pageable.ofSize(Constants.HOME_PAGE_CATEGORY_COLUMN_SIZE)
        );
    }

    public List<Integer> getAllIDForUser(Integer id) {
        List<Integer> idList = new ArrayList<>();
        List<PetPost> petList = getAllForUser(id);
        for (PetPost petPost: petList) {
            idList.add(petPost.getPetId());
        }
        return idList;
    }

    public PetPost petPostUpdate(PetPost petPost,String username){
        Set<Picture> pictureList = new HashSet<>();
        for(Picture picture: petPost.getPictures()){
            int id = picture.getPictureId();
            Optional<Picture> optionalPicture = pictureRepository.findById(id);
            pictureList.add(optionalPicture.get());
        }
        petPost.setPictures(pictureList);
        User optionalUser = userRepository.findByUsername(username);
        petPost.setSeller(optionalUser);
        return petPostRepository.save(petPost);
    }

}
