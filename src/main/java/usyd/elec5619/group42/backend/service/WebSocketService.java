package usyd.elec5619.group42.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/websocket/{username}")
@Service
public class WebSocketService {

    private static final Logger log = LoggerFactory.getLogger(WebSocketService.class);

    private static ConcurrentHashMap<String, Session> userSessionMap = new ConcurrentHashMap<>();

    private Session clientSession;
    private String username;

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        this.clientSession = session;
        this.username= username;
        userSessionMap.put(username, session);

        log.info("----------------------------------------------------------------------------");
        log.info("Username: " + username + " connected, current connection size is: " + getOnlineCount());

        try {
            sendMessage(username, "From server：connection success!");
        } catch (IOException e) {
            log.error("Username: " + username + ", network error");
        }
    }

    @OnClose
    public void onClose() {
        if(userSessionMap.containsKey(username)) {
            userSessionMap.remove(username);
        }
        log.info("----------------------------------------------------------------------------");
        log.info(username + " disconnected, current connection size is: " + getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("Message from username: " + username + ". Message: " + message);
    }

    @OnError
    public void onError(Throwable error) {
        log.error("Username: " + this.username+" error. Reason: " + error.getMessage());
        error.printStackTrace();
    }

    public void sendMessageToAll(String message) throws IOException {
        synchronized (clientSession){
            this.clientSession.getBasicRemote().sendText(message);
        }
    }

    public void sendMessage(String username, String message) throws IOException {
        if(userSessionMap.containsKey(username)) {
            userSessionMap.get(username).getBasicRemote().sendText(message);
        }
    }

    private static synchronized int getOnlineCount() {
        return userSessionMap.size();
    }
}
