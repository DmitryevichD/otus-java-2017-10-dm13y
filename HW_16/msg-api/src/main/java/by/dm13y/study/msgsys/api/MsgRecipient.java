package by.dm13y.study.msgsys.api;

import by.dm13y.study.msgsys.api.messages.Message;
import by.dm13y.study.msgsys.api.messages.MsgSys;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;

public abstract class MsgRecipient implements Runnable {
    @Getter
    private static final int msgSysPort = 9999;
    private final String msgSysHost;
    protected Sender sender;
    private MsgSocketWrapper msgSocket;
    private final static Logger logger = LoggerFactory.getLogger(MsgRecipient.class);

    public MsgRecipient(String msgSysHost, SenderType recipientType){
        this.msgSysHost = msgSysHost;
        this.sender = new Sender(recipientType);
    }

    public void connect(){
        buildSocket();
    }

    public boolean isConnected(){
        return msgSocket != null;
    }

    private void buildSocket(){
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(msgSysHost, msgSysPort), 1000);
            if (socket.isConnected()) {
                msgSocket = new MsgSocketWrapper(socket);
                registration();
            }
        } catch (SocketTimeoutException ex) {
            logger.error("Timeout is exit");
        } catch (IOException e) {
            logger.error("Build socket error");
        }
    }

    private void registration(){
        logger.info("Handshake with msg system: start");
        msgSocket.writeObjectToSocket(sender);
        try {
            Object obj = msgSocket.readObjectFromSocket(true);
            if (obj instanceof Sender) {
                sender = ((Sender) obj);
                logger.info("Handshake with msg system: done");
            }else {
                logger.error("receive sender exception");
                throw new IOException();
            }
        } catch (IOException e) {
            logger.error("Handshake with msg system: FAILED!!!", e);
        }
    }

    public void getDBRecipientsList() {
        if (!isConnected()) {
            connect();
        }

        if (isConnected()) {
            sendMsg(new MsgSys(sender, null, "", MsgSys.Operation.DB_RECIPIENT_LIST));
            try {
                getMsg();
            } catch (IOException e) {
                logger.error("Recipient list is failed", e);
            }
        }else {
            logger.error("socket is not connected");
        }

    }

    public void sendMsg(Message msg){
        logger.debug("SEND " + msg);
        msgSocket.writeMessage(msg);
    }

    public abstract void handleSysMsg(@NotNull Message msg);
    public abstract void handleExceptionMsg(@NotNull Message msg);
    public abstract void handleReceiveMsg(@NotNull Message msg);

    public void getMsg() throws IOException {
        List<Message> messages = msgSocket.readMessages();
        if (messages.size() > 0) {
            logger.debug("GET MESSAGES. COUNT:" + messages.size());
        }
        for (Message message : messages) {
            logger.debug(message.toString());
            if (message.getSysInfo() != null) {
                logger.debug("Start sys handle");
                handleSysMsg(message);
            }
            if (message.getException() != null){
                logger.debug("Start exception handle");
                handleExceptionMsg(message);
            }
            if (message.getBody() != null){
                logger.debug("Start simple handle");
                handleReceiveMsg(message);
            }
        }
    }

    @Override
    public void run(){
        try {
            getMsg();
        } catch (IOException e) {
            logger.error("db service error", e);
        }
    }
}
