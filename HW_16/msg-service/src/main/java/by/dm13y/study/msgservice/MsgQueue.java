package by.dm13y.study.msgservice;

import by.dm13y.study.msgsys.api.Header;
import by.dm13y.study.msgsys.api.MsgSocketWrapper;
import by.dm13y.study.msgsys.api.messages.Message;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MsgQueue {
    private final Map<Header, MsgSocketWrapper> socketMap = new ConcurrentHashMap<>();
    private final Map<Header, ConcurrentLinkedQueue<Message>> inputQueue = new ConcurrentHashMap<>();
    private final Map<Header, ConcurrentLinkedQueue<Message>> outputQueue = new ConcurrentHashMap<>();

    public synchronized void addRecipient(Header header, MsgSocketWrapper socket) {
        socketMap.put(header, socket);
        inputQueue.put(header, new ConcurrentLinkedQueue<Message>());
        outputQueue.put(header, new ConcurrentLinkedQueue<Message>());
    }

    public Map<Header, ConcurrentLinkedQueue<Message>> getInputQueue(){
        return inputQueue;
    }

    public Map<Header, ConcurrentLinkedQueue<Message>> getOutputQueue(){
        return outputQueue;
    }


    public Map<Header, MsgSocketWrapper> getSocketMap(){
        return socketMap;
    }
}
