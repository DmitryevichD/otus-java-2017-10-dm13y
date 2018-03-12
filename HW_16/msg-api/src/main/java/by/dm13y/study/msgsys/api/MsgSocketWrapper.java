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
        Object objFromSocket;
        while ((objFromSocket = readObjectFromSocket()) != null) {
            if (objFromSocket instanceof Message) {
                Message msg = ((Message) objFromSocket);
                msgList.add(msg);
            }else {
                logger.error("Object is not instance of the message: " + objFromSocket);
            }
        }
        return msgList;
    }

    public void writeObjectToSocket(Serializable object){
        try {
            logger.debug("Socket: " + socket + " write object");
            socket.getOutputStream().write(objToByteArray(object));
        } catch (IOException e) {
            logger.error("write object to socket is failed", e);
        }
    }

    public Object readObjectFromSocket() throws IOException{
        return readObjectFromSocket(false);
    }

    public Object readObjectFromSocket(boolean waitData) throws IOException{
        byte[] header = readStreamFromSocket(4, waitData);

        if(header == null){ return null;}
        logger.debug("Socket: " + socket + " read object. Wait data is " + waitData);
        int objectSize = ByteBuffer.wrap(header).getInt();
        logger.debug("Socket: " + socket + " object size must be is " + objectSize);
        byte[] object = readStreamFromSocket(objectSize);
        return SerializationUtils.deserialize(object);
    }

    private byte[] readStreamFromSocket(int countByte) throws IOException {
        return readStreamFromSocket(countByte, false);
    }

    private byte[] readStreamFromSocket(int countByte, boolean waitData) throws IOException{
        if(!waitData && socket.getInputStream().available() == 0){
            logger.trace("socket input stream is empty");
            return null;
        }
        logger.debug("Socket: " + socket + " read stream. Count byte = " + countByte);
        int read = 0;
        byte[] buffer = new byte[countByte];
        while (read != countByte) {
            int byteSize = socket.getInputStream().read(buffer, read, countByte - read);
            read += byteSize;
            logger.debug("Reading:" + byteSize + ", Total:" + read);
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
        logger.debug("Socket: " + socket + "Write " + message);
        try {
            socket.getOutputStream().write(objToByteArray(message));
        } catch (IOException e) {
            logger.error("write to socket error", e);
        }
    }

    private byte[] objToByteArray(Serializable object){
        byte[] serObject = SerializationUtils.serialize(object);
        int sizeVal = serObject.length;
        ByteBuffer byteBuffer = ByteBuffer.allocate(4 + sizeVal);
        byteBuffer.putInt(sizeVal);
        byteBuffer.put(serObject);
        logger.debug("Socket: " + socket + "CONVERT TO BYTE[], size is " + sizeVal);
        return byteBuffer.array();
    }
}
