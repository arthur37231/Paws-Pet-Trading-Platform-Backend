package usyd.elec5619.group42.backend.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "password"})
public class User {
    private Integer userId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String location;
    private String email;
    private String phone;
    private String portrait;
    private List<PetPost> posts;
    private Set<PetCategory> favoriteList;  // should be a LinkedHashSet
    private List<Bookmark> bookmarkedPosts;

    public User() {
    }

    public User(Integer userId, String username, String password, String firstName, String lastName, String location,
                String email, String phone, String portrait, Set<PetCategory> favoriteList, List<PetPost> posts,
                List<Bookmark> bookmarkedPosts) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.email = email;
        this.phone = phone;
        this.portrait = portrait;
        this.favoriteList = favoriteList;
        this.posts = posts;
        this.bookmarkedPosts = bookmarkedPosts;
    }

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(unique = true, nullable = false)
    @Size(min = 4, max = 20, message = "Username length should be between 4 and 20")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(nullable = false)
    @Size(min = 6, message = "Password length should be greater than 6")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public Set<PetCategory> getFavoriteList() {
        return favoriteList;
    }

    public void setFavoriteList(Set<PetCategory> favoriteList) {
        this.favoriteList = favoriteList;
    }

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "seller")
    @JsonIgnoreProperties(value = {"seller"})
    public List<PetPost> getPosts() {
        return posts;
    }

    public void setPosts(List<PetPost> posts) {
        this.posts = posts;
    }

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties(value = {"user"})
    public List<Bookmark> getBookmarkedPosts() {
        return bookmarkedPosts;
    }

    public void setBookmarkedPosts(List<Bookmark> bookmarkedPosts) {
        this.bookmarkedPosts = bookmarkedPosts;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", location='" + location + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", portrait='" + portrait + '\'' +
                ", favoriteList=" + favoriteList +
                ", posts=" + posts +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(location, user.location) && Objects.equals(email, user.email) && Objects.equals(phone, user.phone) && Objects.equals(portrait, user.portrait);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, firstName, lastName, location, email, phone, portrait);
    }
}
