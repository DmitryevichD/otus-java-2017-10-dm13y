package by.dm13y.study.msgservice;

import by.dm13y.study.msgsys.api.messages.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MsgSocketWrapper {
    private final Socket socket;
    private final ObjectOutputStream ous;
    private final ObjectInputStream ois;
    private final static Logger logger = LoggerFactory.getLogger(MsgSocketWrapper.class);
    public MsgSocketWrapper(Socket socket) {
        this.socket = socket;
        try {
            ous = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            logger.error("Object stream is fail", e);
            throw new RuntimeException(e);
        }
    }

    public List<Message> readMessage(){
        List<Message> messages = new ArrayList<>();
        try {
            while (socket.getInputStream().available() > 0) {
                Object obj = null;
                obj = ois.readObject();
                if (obj instanceof Message) {
                    messages.add(((Message) obj));
                }
            }
        } catch (Exception ex) {
            logger.error("incorrect msg", ex);
        }
        return messages;
    }
}
