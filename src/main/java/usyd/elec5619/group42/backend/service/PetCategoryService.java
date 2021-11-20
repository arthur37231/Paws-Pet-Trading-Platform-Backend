package usyd.elec5619.group42.backend.service;

import org.springframework.stereotype.Service;
import usyd.elec5619.group42.backend.pojo.PetCategory;
import usyd.elec5619.group42.backend.repository.PetCategoryRepository;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PetCategoryService {
    @Resource
    private PetCategoryRepository petCategoryRepository;

    public List<PetCategory> getByName(String categoryName) {
        return petCategoryRepository.findPetCategoriesByType(categoryName);
    }
}
