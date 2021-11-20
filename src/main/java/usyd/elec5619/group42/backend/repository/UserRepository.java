package usyd.elec5619.group42.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import usyd.elec5619.group42.backend.pojo.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    interface SimpleUser {
        Integer getUserId();
        String getUsername();
        String getFirstName();
        String getLastName();
        String getLocation();
        String getEmail();
        String getPhone();
        String getPortrait();
    }

    Optional<SimpleUser> findByUserId(Integer userId);
    Optional<SimpleUser> findSimpleUserByUsername(String username);
}
