package com.example.springboottest.websocket;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@ServerEndpoint("/websocket/{userId}")
public class WebSocket {
    @SuppressWarnings("unused")
	private Session session;
    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();
    private static Map<String,Session> sessionPool = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam(value="userId")String userId){
        try{
            this.session = session;
            webSockets.add(this);
            sessionPool.put(userId,session);
            log.info("【webSocket消息】有新的连接，总数为：" + webSockets.size());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(){
        try{
            webSockets.remove(this);
            log.info("【webSocket消息】连接断开，总数为：" + webSockets.size());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String message){
        log.info("【webSocket消息】收到客户端消息：" + message);
    }

    @OnError
    public void onError(Session session, Throwable error){
        log.error("用户错误，原因：" + error.getMessage());
        error.printStackTrace();
    }

    public void sendAllMessage(String message){
        log.info("【WebSocket消息】广播消息：" + message);
    }

    public void sendOneMessage(String userId, String message){
        Session session = sessionPool.get(userId);

        if(session != null && session.isOpen()){
            try{
                log.info("【WebSocket消息：】 单点消息：" + message);
                session.getAsyncRemote().sendText(message);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void sendMoreMessage(String[] userIds,String message){
        for (String userId:userIds) {
            Session session = sessionPool.get(userId);
            if(session != null && session.isOpen()){
                try{
                    log.info("【WebSocket消息：】 单点消息：" + message);
                    session.getAsyncRemote().sendText(message);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
