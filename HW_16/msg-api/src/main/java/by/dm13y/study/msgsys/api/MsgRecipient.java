package by.dm13y.study.msgsys.api;

import by.dm13y.study.msgsys.api.messages.Message;
import by.dm13y.study.msgsys.api.messages.MsgException;
import by.dm13y.study.msgsys.api.messages.MsgSys;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;

public abstract class MsgRecipient {
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
            logger.error("server socket is not response", ex);
        } catch (IOException e) {
            logger.error("build socket io exception", e);
        }
    }

    private void registration(){
        msgSocket.writeObjectToSocket(sender);
        try {
            Object obj = msgSocket.readObjectFromSocket(true);
            if (obj instanceof Sender) {
                sender = ((Sender) obj);
                logger.info("id is:" + sender.getId());
            }else {
                logger.error("receive sender exception");
                throw new IOException();
            }
        } catch (IOException e) {
            logger.error("registration is failed", e);
        }
    }

    public void getRecipientsList(){
        sendMsg(new MsgSys(sender, null, null, MsgSys.Operation.RECIPIENT_LIST));
        try {
            getMsg();
        } catch (IOException e) {
            logger.error("get recipient list is failed", e);
        }
    }

    public void sendMsg(Message msg){
        msgSocket.writeMessage(msg);
    }

    public abstract void handleSysMsg(MsgSys msgState, MsgSys.Operation operId);
    public abstract void handleExceptionMsg(MsgException msgException);
    public abstract void handleReceiveMsg(Message msg);

    public void getMsg() throws IOException {
        List<Message> messages = msgSocket.readMessages();
        for (Message message : messages) {
            if (message instanceof MsgSys) {
                MsgSys msgSys = (MsgSys) message;
                handleSysMsg(msgSys, MsgSys.Operation.byId(msgSys.getMsgMarker()));
            }else if (message instanceof MsgException){
                handleExceptionMsg(((MsgException) message));
            } else if (message instanceof Message) {
                handleReceiveMsg(message);
            }
        }
    }
}
