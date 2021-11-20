package usyd.elec5619.group42.backend.service;

import org.springframework.stereotype.Service;
import usyd.elec5619.group42.backend.exception.EntityNotFoundException;
import usyd.elec5619.group42.backend.pojo.BuyRequest;
import usyd.elec5619.group42.backend.pojo.PetPost;
import usyd.elec5619.group42.backend.pojo.User;
import usyd.elec5619.group42.backend.repository.BuyRequestRepository;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class BuyRequestService {
    @Resource
    private WebSocketService webSocketService;
    @Resource
    private BuyRequestRepository buyRequestRepository;
    @Resource
    private UserService userService;
    @Resource
    private PetPostService petPostService;

    public void sendBuyRequest(String fromUsername, Integer petPostId) throws EntityNotFoundException {
        User sender = userService.getByUsername(fromUsername);
        PetPost targetPetPost = petPostService.get(petPostId);
        User receiver = targetPetPost.getSeller();
        if(receiver == null) {
            throw new EntityNotFoundException("Pet Post Seller");
        }

        BuyRequest buyRequest = new BuyRequest();
        buyRequest.setSender(sender);
        buyRequest.setReceiver(receiver);
        buyRequest.setPet(targetPetPost);
        buyRequest.setCreationTime(new Date());
        buyRequestRepository.save(buyRequest);

        try {
            webSocketService.sendMessage(receiver.getUsername(), String.format(
                    "%s sent you a buy request on %d!",
                    fromUsername,
                    buyRequest.getCreationTime().getTime()
            ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<BuyRequest> getReceivedRequestList(String username) {
        User receiver = userService.getByUsername(username);
        return buyRequestRepository.findBuyRequestsByReceiver(receiver);
    }

    public List<BuyRequest> getSentRequestList(String username) {
        User sender = userService.getByUsername(username);
        return buyRequestRepository.findBuyRequestsBySender(sender);
    }
}
