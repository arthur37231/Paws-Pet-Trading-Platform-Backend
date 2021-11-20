package usyd.elec5619.group42.backend.pojo;

import javax.persistence.*;

@Entity
@Table(name = "Pictures")
public class Picture {
    private Integer pictureId;
    private String url;

    public Picture() {
    }

    public Picture(Integer pictureId, String url) {
        this.pictureId = pictureId;
        this.url = url;
    }

    @Id
    @Column(name = "picture_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getPictureId() {
        return pictureId;
    }

    public void setPictureId(Integer pictureId) {
        this.pictureId = pictureId;
    }

    @Column(unique = true, length = 512, nullable = false)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "pictureId=" + pictureId +
                ", url='" + url + '\'' +
                '}';
    }

}
