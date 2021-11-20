package usyd.elec5619.group42.backend.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PetPosts")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PetPost {
    private Integer petId;
    private String title;
    private String description;
    private BigDecimal price;
    private String location;
    private Date creationTime;
    private Integer clickCount;
    private User seller;
    private Picture thumbnail;
    private Set<Picture> pictures;  // Should be a LinkedHashSet
    private PetCategory category;
    private Boolean sold;

    private BigDecimal weight;
    private String gender;
    private Integer ageYears;
    private Integer ageMonths;
    private Boolean desexed;
    private Boolean microchip;
    private Boolean vaccinated;
    private Boolean wormed;

    private Set<Bookmark> bookmarked = new HashSet<>();

    public PetPost() {
    }

    public PetPost(Integer petId, String title, String description, BigDecimal price, String location, Date creationTime,
                   Integer clickCount, User seller, Picture thumbnail, Set<Picture> pictures, PetCategory category,
                   Boolean sold, BigDecimal weight, String gender, Integer ageYears, Integer ageMonths, Boolean desexed,
                   Boolean microchip, Boolean vaccinated, Boolean wormed, Set<Bookmark> bookmarked) {
        this.petId = petId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.location = location;
        this.creationTime = creationTime;
        this.clickCount = clickCount;
        this.seller = seller;
        this.thumbnail = thumbnail;
        this.pictures = pictures;
        this.category = category;
        this.sold = sold;
        this.weight = weight;
        this.gender = gender;
        this.ageYears = ageYears;
        this.ageMonths = ageMonths;
        this.desexed = desexed;
        this.microchip = microchip;
        this.vaccinated = vaccinated;
        this.wormed = wormed;
        this.bookmarked = bookmarked;
    }

    @Id
    @Column(name = "pet_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(length = 2048)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name = "creation_time")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    @Column(name = "click_count")
    @ColumnDefault(value = "0")
    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

    @ManyToOne
    @JsonIgnoreProperties(value = {"posts", "favoriteList", "bookmarkedPosts"})
    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    @OneToOne
    public Picture getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Picture thumbnail) {
        this.thumbnail = thumbnail;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy(value = "pictureId")
    public Set<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
    }

    @ManyToOne
    public PetCategory getCategory() {
        return category;
    }

    public void setCategory(PetCategory category) {
        this.category = category;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Column(name = "age_years")
    public Integer getAgeYears() {
        return ageYears;
    }

    public void setAgeYears(Integer ageYears) {
        this.ageYears = ageYears;
    }

    @Column(name = "age_months")
    public Integer getAgeMonths() {
        return ageMonths;
    }

    public void setAgeMonths(Integer ageMonths) {
        this.ageMonths = ageMonths;
    }

    @ColumnDefault(value = "false")
    public Boolean getDesexed() {
        return desexed;
    }

    public void setDesexed(Boolean desexed) {
        this.desexed = desexed;
    }

    @ColumnDefault(value = "false")
    public Boolean getMicrochip() {
        return microchip;
    }

    public void setMicrochip(Boolean microchip) {
        this.microchip = microchip;
    }

    @ColumnDefault(value = "false")
    public Boolean getVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(Boolean vaccinated) {
        this.vaccinated = vaccinated;
    }

    @ColumnDefault(value = "false")
    public Boolean getWormed() {
        return wormed;
    }

    public void setWormed(Boolean wormed) {
        this.wormed = wormed;
    }

    public Boolean getSold() {
        return sold;
    }

    public void setSold(Boolean sold) {
        this.sold = sold;
    }

    @OneToMany(mappedBy = "bookmarkedPost",orphanRemoval = true)
    @JsonIgnore
    public Set<Bookmark> getBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(Set<Bookmark> bookmarked) {
        this.bookmarked = bookmarked;
    }

    @Override
    public String toString() {
        return toStringShort();
    }

    private String toStringShort() {
        return "PetPost{" +
                "petId=" + petId +
                ", title='" + title + '\'' +
                ", sold=" + sold +
                '}';
    }

    private String toStringLong() {
        return "PetPost{" +
                "petId=" + petId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", location='" + location + '\'' +
                ", creationTime=" + creationTime +
                ", clickCount=" + clickCount +
                ", seller=" + seller.getUsername() +
                ", thumbnail=" + thumbnail +
                ", pictures=" + pictures +
                ", category=" + category +
                ", sold=" + sold +
                ", weight=" + weight +
                ", gender='" + gender + '\'' +
                ", ageYears=" + ageYears +
                ", ageMonths=" + ageMonths +
                ", desexed=" + desexed +
                ", microchip=" + microchip +
                ", vaccinated=" + vaccinated +
                ", wormed=" + wormed +
                '}';
    }
}
