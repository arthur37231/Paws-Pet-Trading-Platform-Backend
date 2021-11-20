package usyd.elec5619.group42.backend.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "BuyRequest")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BuyRequest {
    private Integer requestId;
    private User sender;
    private User receiver;
    private PetPost pet;
    private Date creationTime;

    public BuyRequest() {
    }

    public BuyRequest(Integer requestId, User sender, User receiver, PetPost pet) {
        this.requestId = requestId;
        this.sender = sender;
        this.receiver = receiver;
        this.pet = pet;
    }

    @Id
    @Column(name = "request_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    @ManyToOne
    @JsonIgnoreProperties(value = {"posts", "favoriteList", "bookmarkedPosts"})
    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    @ManyToOne
    @JsonIgnoreProperties(value = {"posts", "favoriteList", "bookmarkedPosts"})
    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    @ManyToOne
    @JsonIgnoreProperties(value = {"seller"})
    public PetPost getPet() {
        return pet;
    }

    public void setPet(PetPost pet) {
        this.pet = pet;
    }

    @Column(name = "creation_time", nullable = false, updatable = false)
    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
