package by.dm13y.study.msgsys.api;

import by.dm13y.study.msgsys.api.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public abstract class MsgRecipient {
    private final int msgSysPort = 9999;
    private final String msgSysHost;
    private Header header;
    private Socket socket;

    public MsgRecipient(String msgSysHost, RecipientType recipientType){
        this.msgSysHost = msgSysHost;
        this.header = new Header(recipientType);
    }

    public void connect(){
        buildSocket();
    }

    private void buildSocket() {
        try (ObjectOutputStream objWriter = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream objReader = new ObjectInputStream(socket.getInputStream())) {
            socket.connect(new InetSocketAddress(msgSysHost, msgSysPort), 1000);
            objWriter.writeObject(header);
            Object obj = objReader.readObject();
            if (obj instanceof Header) {
                header = ((Header) obj);
            }else {
                throw new UnsupportedOperationException("answer must be is Header.class");
            }
        }catch (Exception ex) {
            throw new UnsupportedOperationException(ex);
        }
    }

    public void sendMsg(Message msg){
        if(socket.isConnected()){
            try (ObjectOutputStream objwriter = new ObjectOutputStream(socket.getOutputStream())) {
                objwriter.writeObject(msg);
            } catch (IOException ex) {
                throw new UnsupportedOperationException(ex.getMessage());
            }
        }
    }

    public void getMsg(){
        try (ObjectInputStream objReader = new ObjectInputStream(socket.getInputStream())) {
            Message message = (Message)objReader.readObject();
            handleReceiveMsg(message);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void disconnect(){
        if(!socket.isClosed()){
            try {
                socket.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public abstract void handleReceiveMsg(Message msg);
}
