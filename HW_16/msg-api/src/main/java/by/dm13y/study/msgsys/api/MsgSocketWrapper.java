package by.dm13y.study.msgsys.api;

import by.dm13y.study.msgsys.api.messages.Message;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class MsgSocketWrapper {
    private final Socket socket;

    private final static Logger logger = LoggerFactory.getLogger(MsgSocketWrapper.class);
    public MsgSocketWrapper(Socket socket) {
        this.socket = socket;
    }

    public List<Message> readMessages() throws IOException {
        List<Message> msgList = new ArrayList<>();
        Object obj = null;
        while ((obj = readObjectFromSocket()) != null) {
            if (obj instanceof Message) {
                Message msg = ((Message) obj);
                msgList.add(msg);
            }
        }
        return msgList;
    }

    public Object readObjectFromSocket() throws IOException{
        byte[] header = readStreamFromSocket(4);

        if(header == null){ return null;}

        int objectLength = ByteBuffer.wrap(header).getInt();

        byte[] object = readStreamFromSocket(objectLength);

        return SerializationUtils.deserialize(object);
    }

    private byte[] readStreamFromSocket(int countByte) throws IOException{
        if(socket.getInputStream().available() == 0){
            logger.debug("socket input stream is empty");
            return null;
        }

        int read = 0;
        byte[] buffer = new byte[countByte];
        while (read != countByte) {
            int byteSize = socket.getInputStream().read(buffer, read, countByte - read);
            read += byteSize;
        }
        return buffer;
    }

    public void destroy(){
        if(socket.isConnected()){
            try {
                socket.close();
            } catch (IOException e) {
                logger.error("socket close exception", e);
            }
        }
    }

    public void writeMessage(Message message) {
        try {
            socket.getOutputStream().write(msgToByteArray(message));
        } catch (IOException e) {
            logger.error("write to socket error", e);
        }
    }


    private byte[] msgToByteArray(Serializable object){
        byte[] serObject = SerializationUtils.serialize(object);
        ByteBuffer byteBuffer = ByteBuffer.allocate(4 + serObject.length);
        byteBuffer.putInt(serObject.length);
        byteBuffer.put(serObject);
        return byteBuffer.array();
    }
}
