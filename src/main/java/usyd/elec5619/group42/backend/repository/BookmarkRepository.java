package usyd.elec5619.group42.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import usyd.elec5619.group42.backend.pojo.Bookmark;
import usyd.elec5619.group42.backend.pojo.PetPost;
import usyd.elec5619.group42.backend.pojo.User;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Integer> {
    Bookmark findByUserAndBookmarkedPost(User user, PetPost bookmarkedPost);
    List<Bookmark> findByUser(User user);
}