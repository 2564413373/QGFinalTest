package Controller;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@ServerEndpoint("/chat")

public class WsChat {
    static Map<String,Session> sessionMap = new ConcurrentHashMap<String, Session>();
    @OnOpen
    /////  每一个WebSocket连接对于服务器来说，都是一个session
    public void OnOpen(Session session) {
        sessionMap.put(session.getId(),session);
    }

    @OnMessage
    public void OnMessage(String text) {

    }

    @OnClose
    public void OnClose(Session session) {
        sessionMap.remove(session.getId());
    }
}
