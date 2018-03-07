package by.dm13y.study.msgsys.api;

import by.dm13y.study.msgsys.api.messages.Message;
import by.dm13y.study.msgsys.api.messages.MsgException;
import by.dm13y.study.msgsys.api.messages.MsgState;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.sql.Wrapper;
import java.util.List;

public abstract class MsgRecipient {
    @Getter
    private static final int msgSysPort = 9999;
    private final String msgSysHost;
    private Header header;
    private MsgSocketWrapper msgSocket;
    private final static Logger logger = LoggerFactory.getLogger(MsgRecipient.class);

    public MsgRecipient(String msgSysHost, RecipientType recipientType){
        this.msgSysHost = msgSysHost;
        this.header = new Header(recipientType);
    }

    public void connect(){
        buildSocket();
    }

    private void buildSocket(){
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(msgSysHost, msgSysPort), 1000);
            msgSocket = new MsgSocketWrapper(socket);
            registration();
        } catch (SocketTimeoutException ex) {
            logger.error("server socket is not response", ex);
        } catch (IOException e) {
            logger.error("build socket io exception", e);
        }
    }

    private void registration(){
        msgSocket.writeObjectToSocket(header);
        try {
            Object obj = msgSocket.readObjectFromSocket(true);
            if (obj instanceof Header) {
                header = ((Header) obj);
                logger.info("id is:" + header.getId());
            }else {
                logger.error("receive header exception");
                throw new IOException();
            }
        } catch (IOException e) {
            logger.error("registration is failed", e);
        }
    }

    public void getRecipientsList(){
        sendMsg(new MsgState(header));
    }

    public void sendMsg(Message msg){
        msgSocket.writeMessage(msg);
    }

    public abstract void handleStateMsg(MsgState msgState);
    public abstract void handleExceptionMsg(MsgException msgException);
    public abstract void handleReceiveMsg(Message msg);

    public void getMsg() throws IOException {
        List<Message> messages = msgSocket.readMessages();
        for (Message message : messages) {
            if (message instanceof MsgState) {
                handleStateMsg(((MsgState) message));
            }else if (message instanceof MsgException){
                handleExceptionMsg(((MsgException) message));
            } else if (message instanceof Message) {
                handleReceiveMsg(message);
            }
        }
    }
}
