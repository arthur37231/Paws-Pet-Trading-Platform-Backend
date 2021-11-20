package usyd.elec5619.group42.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import usyd.elec5619.group42.backend.pojo.PetCategory;

import java.util.List;

@Repository
public interface PetCategoryRepository extends JpaRepository<PetCategory, Integer> {
    List<PetCategory> findPetCategoriesByType(String type);
}
