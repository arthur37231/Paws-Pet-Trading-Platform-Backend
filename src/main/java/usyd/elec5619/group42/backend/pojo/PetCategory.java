package usyd.elec5619.group42.backend.pojo;

import javax.persistence.*;

@Entity
@Table(name = "PetCategories")
public class PetCategory {
    private Integer categoryId;
    private String type;
    private String breed;

    public PetCategory() {
    }

    public PetCategory(Integer categoryId, String type, String breed) {
        this.categoryId = categoryId;
        this.type = type;
        this.breed = breed;
    }

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Column(nullable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    @Override
    public String toString() {
        return "PetCategory{" +
                "categoryId=" + categoryId +
                ", type='" + type + '\'' +
                ", breed='" + breed + '\'' +
                '}';
    }
}
