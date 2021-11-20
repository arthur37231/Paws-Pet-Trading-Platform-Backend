package usyd.elec5619.group42.backend.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import usyd.elec5619.group42.backend.pojo.PetCategory;
import usyd.elec5619.group42.backend.pojo.PetPost;
import usyd.elec5619.group42.backend.pojo.User;

import java.util.List;

@Repository
public interface PetPostRepository extends JpaRepository<PetPost, Integer> {
    List<PetPost> findBySeller(User user);
    List<PetPost> findAllByCategoryInAndSoldIsFalseOrderByCreationTimeDesc(Pageable pageable, List<PetCategory> category);
    List<PetPost> findAllBySoldIsFalseOrderByCreationTimeDesc(Pageable pageable);
    List<PetPost> findAllBySoldIsFalseOrderByClickCountDesc(Pageable pageable);
}
