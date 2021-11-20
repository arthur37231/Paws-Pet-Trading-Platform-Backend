package usyd.elec5619.group42.backend.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import usyd.elec5619.group42.backend.exception.DuplicateUniqueException;
import usyd.elec5619.group42.backend.pojo.PetCategory;
import usyd.elec5619.group42.backend.pojo.User;
import usyd.elec5619.group42.backend.repository.UserRepository;
import usyd.elec5619.group42.backend.utils.Constants;
import usyd.elec5619.group42.backend.utils.RedisUtils;

import javax.annotation.Resource;
import java.util.Set;

@Service
public class AuthService implements UserDetailsService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found in database");
        } else {
            return org.springframework.security.core.userdetails.User
                    .withUsername(username)
                    .password(user.getPassword())
                    .authorities(Constants.AUTHENTICATED)
                    .build();
        }
    }

    public User addUser(
            String username,
            String password,
            String firstName,
            String lastName,
            String location,
            String email,
            String phone,
            String portrait,
            Set<PetCategory> favoriteList
    ) throws DuplicateUniqueException {
        try {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setLocation(location);
            user.setEmail(email);
            user.setPhone(phone);
            user.setPortrait(portrait);
            user.setFavoriteList(favoriteList);
            return userRepository.save(user);
        } catch (Exception e) {
            throw new DuplicateUniqueException("username");
        }
    }

    public void login(String token, Integer userId) {
        RedisUtils.insertToken(token, userId);
    }

    public boolean isValidToken(String token) {
        return RedisUtils.isTokenValid(token);
    }

    public void logout(String token) {
        RedisUtils.expireToken(token);
    }
}
