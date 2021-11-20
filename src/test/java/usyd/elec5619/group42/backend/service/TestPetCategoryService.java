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
        import usyd.elec5619.group42.backend.pojo.PetCategory;
        import usyd.elec5619.group42.backend.pojo.PetPost;
        import usyd.elec5619.group42.backend.pojo.Picture;
        import usyd.elec5619.group42.backend.pojo.User;
        import usyd.elec5619.group42.backend.repository.PetCategoryRepository;
        import usyd.elec5619.group42.backend.repository.PetPostRepository;
        import usyd.elec5619.group42.backend.repository.UserRepository;

        import javax.annotation.Resource;
        import java.math.BigDecimal;
        import java.util.*;

        import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(value = "test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestPetCategoryService {

    @Resource
    private PetCategoryService petCategoryService;

    @MockBean
    private PetCategoryRepository petCategoryRepository;


    @Test
    public void getCategoryByType(){
        PetCategory categories = new PetCategory(75,"test","test");
        List<PetCategory> petCategories = new ArrayList<>();
        petCategories.add(categories);
        Mockito.when(petCategoryRepository.findPetCategoriesByType("test")).thenReturn(petCategories);
        petCategoryService.getByName("test");
        Mockito.verify(petCategoryRepository,Mockito.times(1)).findPetCategoriesByType("test");

    }
}
