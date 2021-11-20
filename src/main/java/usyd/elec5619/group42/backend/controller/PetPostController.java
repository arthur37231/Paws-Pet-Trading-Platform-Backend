package usyd.elec5619.group42.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import usyd.elec5619.group42.backend.exception.EntityNotFoundException;
import usyd.elec5619.group42.backend.pojo.PetPost;
import usyd.elec5619.group42.backend.pojo.Picture;
import usyd.elec5619.group42.backend.repository.PetPostRepository;
import usyd.elec5619.group42.backend.service.BuyRequestService;
import usyd.elec5619.group42.backend.service.PetPostService;
import usyd.elec5619.group42.backend.utils.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/")
public class PetPostController {
    @Autowired
    PetPostRepository petPostRepository;

    @Resource
    private PetPostService petPostService;
    @Resource
    private BuyRequestService buyRequestService;

    @GetMapping("/petPosts")
    public List<PetPost> getAllPosts() {
        return new ArrayList<>(petPostRepository.findAll());
    }

    @GetMapping("/petPosts/{id}")
    public List<PetPost> getAllPostsForUser(@PathVariable("id") int id) {
        return new ArrayList<>(petPostService.getAllForUser(id));
    }

    @GetMapping("/petPosts/id/{id}")
    public List<Integer> getAllPostsIDForUser(@PathVariable("id") int id) {
        return new ArrayList<>(petPostService.getAllIDForUser(id));
    }

    @GetMapping("/getPetPost/{id}")
    public ResponseEntity<ResponseBody> getPet(@PathVariable("id") int id) {
        Optional<PetPost> oldPetPost = petPostRepository.findById(id);
        PetPost pet = oldPetPost.get();
        return ResponseEntity.ok(
                new ResponseBody().put("petPost", pet)
        );
    }
    @PostMapping("/petPost")
    public PetPost createPet(@RequestBody PetPost petPost,HttpServletRequest request) {
        String username = (String)request.getAttribute("username");
        return petPostService.petPostUpdate(petPost,username);
    }

    @DeleteMapping("/petPost/{id}")
    public ResponseEntity<String> deletePet(@PathVariable("id") int id) {
        petPostRepository.deleteById(id);
        return new ResponseEntity<>("The pet post has been deleted!", HttpStatus.OK);
    }

    @PutMapping("/petPost/{id}")
    public ResponseEntity<String> updatePet(@PathVariable("id") int id, @RequestBody PetPost petPost) {
        Optional<PetPost> oldPetPost = petPostRepository.findById(id);
        if (oldPetPost.isPresent()) {
            PetPost newPost = oldPetPost.get();
            //update fields
            newPost.setTitle(petPost.getTitle());
            newPost.setDescription(petPost.getDescription());
            newPost.setPrice(petPost.getPrice());
            newPost.setLocation(petPost.getLocation());
            newPost.setCreationTime(petPost.getCreationTime());
            newPost.setThumbnail(petPost.getThumbnail());
            newPost.getPictures().clear();
            newPost.getPictures().addAll(petPost.getPictures());
            newPost.setCategory(petPost.getCategory());
            newPost.setWeight(petPost.getWeight());
            newPost.setGender(petPost.getGender());
            newPost.setAgeYears(petPost.getAgeYears());
            newPost.setAgeMonths(petPost.getAgeMonths());
            newPost.setDesexed(petPost.getDesexed());
            newPost.setMicrochip(petPost.getMicrochip());
            newPost.setVaccinated(petPost.getVaccinated());
            newPost.setWormed(petPost.getWormed());
            // save the change
            petPostRepository.save(newPost);
            return new ResponseEntity<>("The pet post has been updated!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The pet post can not be found!", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateCount/{id}")
    public ResponseEntity<String> updateCount(@PathVariable("id") int id, @RequestBody PetPost petPost) {
        Optional<PetPost> oldPetPost = petPostRepository.findById(id);
        if (oldPetPost.isPresent()) {
            PetPost newPost = oldPetPost.get();
            newPost.setClickCount(petPost.getClickCount() + 1);
            petPostRepository.save(newPost);
            return new ResponseEntity<>("The pet post has been clicked!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The pet post can not be found!", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<String> deactivatePost(@PathVariable("id") int id) {
        Optional<PetPost> oldPetPost = petPostRepository.findById(id);
        if (oldPetPost.isPresent()) {
            PetPost newPost = oldPetPost.get();
            newPost.setSold(true);
            petPostRepository.save(newPost);
            return new ResponseEntity<>("The pet post has been marked sold!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The pet post can not be found!", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/petPost/bookmark/{postId}")
    public ResponseEntity<ResponseBody> bookmark(@PathVariable Integer postId, HttpServletRequest request) {
        try {
            petPostService.bookmark((String) request.getAttribute("username"), postId);
            return ResponseEntity.ok(
                    new ResponseBody()
                            .put("message", "ok")
            );
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(
                    new ResponseBody(e)
            );
        }
    }

    @PostMapping("/petPost/unbookmark/{postId}")
    public ResponseEntity<ResponseBody> unbookmark(@PathVariable Integer postId, HttpServletRequest request) {
        try {
            petPostService.unbookmark((String) request.getAttribute("username"), postId);
            return ResponseEntity.ok(
                    new ResponseBody()
                            .put("message", "ok")
            );
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(
                    new ResponseBody(e)
            );
        }
    }

    @GetMapping("/petPost/ifBookmarked/{postId}")
    public boolean checkIfBooked(@PathVariable Integer postId, HttpServletRequest request) {
        return petPostService.checkIfBooked((String) request.getAttribute("username"), postId);
    }

    @PostMapping("/petPost/sendBuyRequest/{postId}")
    public ResponseEntity<ResponseBody> sendBuyRequest(@PathVariable Integer postId, HttpServletRequest request) {
        try {
            buyRequestService.sendBuyRequest((String) request.getAttribute("username"), postId);

            return ResponseEntity.ok(new ResponseBody().put("message", "ok"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(
                    new ResponseBody(e)
            );
        }
    }

    @GetMapping("/petPost/index")
    public ResponseEntity<ResponseBody> getHomePage() {
        List<PetPost> dogs = petPostService.getByCategory("Dogs");
        List<PetPost> cats = petPostService.getByCategory("Cats");
        List<PetPost> rabbits = petPostService.getByCategory("Rabbits");
        List<PetPost> latest = petPostService.getLatestPublished();
        List<PetPost> mostPopular = petPostService.getMostPopular();
        return ResponseEntity.ok(
                new ResponseBody()
                        .put("Dogs", dogs)
                        .put("Cats", cats)
                        .put("Rabbits", rabbits)
                        .put("latest", latest)
                        .put("popular", mostPopular)
        );
    }
}