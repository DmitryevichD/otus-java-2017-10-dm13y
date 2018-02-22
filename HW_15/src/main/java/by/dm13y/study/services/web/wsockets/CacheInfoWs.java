package by.dm13y.study.services.web.wsockets;

import by.dm13y.study.auth.UserChecker;
import by.dm13y.study.config.ContextAware;
import by.dm13y.study.services.database.CacheInfo;
import by.dm13y.study.services.database.CacheInfoImpl;
import by.dm13y.study.services.database.MsgToDB;
import by.dm13y.study.services.message.Address;
import by.dm13y.study.services.message.Message;
import by.dm13y.study.services.message.MessageService;
import by.dm13y.study.services.message.MsgRecipient;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value= "/wscacheinfo/{userid}")
public class CacheInfoWs implements MsgRecipient, WebSocketMsgr {
    private Address address = new Address(Address.Type.WEB_SOCKET);
    private MessageService msgService;
    private MsgRecipient dbRecipient;
    private Session session;

    @OnOpen
    public void init(Session session, @PathParam("userid") String userId){
        if ((userId == null) || (UserChecker.isValid(userId) == false)) {
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            msgService = ContextAware.context.getBean(MessageService.class);
            msgService.addRecipient(this);
            dbRecipient = ContextAware.context.getBean(CacheInfoImpl.class);
            this.session = session;
        }
    }

    @Override
    public void sendMsgToClient(String msg) {
        try {
            session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void getCacheInfo(String msg, Session session){
        Message message = new MsgToDB(address, dbRecipient.getAddress()){
            @Override
            public void exec(CacheInfo cacheInfo) {
                cacheInfo.getInfoByJson(getAddress());
            }
        };
        msgService.sendMessage(message);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @OnClose
    public void destroy(){
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
