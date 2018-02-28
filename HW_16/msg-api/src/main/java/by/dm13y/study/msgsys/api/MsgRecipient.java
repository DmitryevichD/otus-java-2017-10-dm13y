package by.dm13y.study.msgsys.api;

import by.dm13y.study.msgsys.api.messages.Message;
import by.dm13y.study.msgsys.api.messages.RegMsg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public abstract class MsgRecipient {
    private final InetAddress msgSysAddr;
    private final int msgSysPort;
    private final Socket socket;
    private final Header header;


    public MsgRecipient(InetAddress msgSysAddr, int msgSysPort, RecipientType type){
        this.msgSysAddr = msgSysAddr;
        this.msgSysPort = msgSysPort;
        socket = initSocket();
        header = regToMsgSys(type);
    }

    private Socket initSocket(){
        receiveMsg();
    }

    private Header regToMsgSys(RecipientType type){
        if(socket.isConnected()){
            try (ObjectOutputStream objwriter = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream objReader = new ObjectInputStream(socket.getInputStream())) {
                objwriter.writeObject(new RegMsg(new Header(type)));
                Object obj = objReader.readObject();
                if(obj instanceof RegMsg)
                return (Header)
            } catch (Exception ex) {
                //todo: add logging
            }
        }else {
            //todo: add Runtime exception
        }
    }

    public void transmitMsg(Message msg){
        if(socket.isConnected()){
            try (ObjectOutputStream objwriter = new ObjectOutputStream(socket.getOutputStream())) {
                objwriter.writeObject(header);
            } catch (IOException ex) {

            }
        }
    }

    public void receiveMsg(Message msg){

    }
}
