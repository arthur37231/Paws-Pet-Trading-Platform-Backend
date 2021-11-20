package usyd.elec5619.group42.backend.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "UsersBookmars")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Bookmark {
    private Integer bookmarkId;
    private User user;
    private PetPost bookmarkedPost;

    public Bookmark() {
    }

    public Bookmark(Integer bookmarkId, User user, PetPost bookmarkedPost) {
        this.bookmarkId = bookmarkId;
        this.user = user;
        this.bookmarkedPost = bookmarkedPost;
    }

    @Id
    @Column(name = "bookmark_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getBookmarkId() {
        return bookmarkId;
    }

    public void setBookmarkId(Integer bookmarkId) {
        this.bookmarkId = bookmarkId;
    }

    @ManyToOne
    @JsonIgnoreProperties(value = {"bookmarkedPosts"})
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JsonIgnoreProperties(value = {"seller"})
    public PetPost getBookmarkedPost() {
        return bookmarkedPost;
    }

    public void setBookmarkedPost(PetPost bookmarkedPost) {
        this.bookmarkedPost = bookmarkedPost;
    }

    @Override
    public String toString() {
        return "Bookmark{" +
                "bookmarkId=" + bookmarkId +
                ", user=" + user.getUsername() +
                ", bookmarkedPost=" + bookmarkedPost.getTitle() +
                '}';
    }
}
