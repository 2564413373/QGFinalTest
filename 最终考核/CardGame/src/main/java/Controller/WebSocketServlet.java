package Controller;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint("/chat")
public class WebSocketServlet {
    private static Map<String,Session> sessionStringMap;
    private static AtomicInteger clientNumber;

    static {
        sessionStringMap = new ConcurrentHashMap<>();
        clientNumber = new AtomicInteger(0);
    }
    @OnOpen
    public void OnOpen(Session session) {
        sessionStringMap.put(session.getId(),session);
        /////  用户加一
        clientNumber.getAndIncrement();
        int userId = clientNumber.intValue();
        broadcastMessage("用户"+userId+"已登录");
    }

    @OnMessage
    public void OnMessage(Session session,String message) {
        broadcastMessage(message);
    }

    @OnClose
    public void OnClose(Session session) {
        sessionStringMap.remove(session.getId());
        clientNumber.getAndDecrement();
    }

    private void broadcastMessage(String message) {
        for (String id : sessionStringMap.keySet()) {
            try {
                sessionStringMap.get(id).getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
