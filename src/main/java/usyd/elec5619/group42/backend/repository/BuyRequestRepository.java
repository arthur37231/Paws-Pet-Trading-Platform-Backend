package usyd.elec5619.group42.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import usyd.elec5619.group42.backend.pojo.BuyRequest;
import usyd.elec5619.group42.backend.pojo.PetPost;
import usyd.elec5619.group42.backend.pojo.User;

import java.util.Date;
import java.util.List;

@Repository
public interface BuyRequestRepository extends JpaRepository<BuyRequest, Integer> {
    List<BuyRequest> findBuyRequestsBySender(User sender);
    List<BuyRequest> findBuyRequestsByReceiver(User receiver);
    List<BuyRequest> findBuyRequestsBySenderAndReceiver(User sender, User receiver);
    List<BuyRequest> findBuyRequestsByPet(PetPost petPost);
    BuyRequest findBuyRequestByCreationTime(Date creationTime);
}
