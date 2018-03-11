package by.dm13y.study.msgsys.core;

import by.dm13y.study.msgsys.api.Sender;
import by.dm13y.study.msgsys.api.MsgSocketWrapper;
import by.dm13y.study.msgsys.api.messages.Message;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MsgQueue {
    private final Map<Integer, Sender> senderIdMap = new ConcurrentHashMap<>();
    private final Map<Sender, MsgSocketWrapper> socketMap = new ConcurrentHashMap<>();
    private final Map<Sender, ConcurrentLinkedQueue<Message>> inputQueue = new ConcurrentHashMap<>();
    private final Map<Sender, ConcurrentLinkedQueue<Message>> outputQueue = new ConcurrentHashMap<>();

    public synchronized void addRecipient(Sender sender, MsgSocketWrapper socket) {
        socketMap.put(sender, socket);
        senderIdMap.put(sender.getId(), sender);
        inputQueue.put(sender, new ConcurrentLinkedQueue<Message>());
        outputQueue.put(sender, new ConcurrentLinkedQueue<Message>());
    }

    public Map<Sender, ConcurrentLinkedQueue<Message>> getInputQueue(){
        return inputQueue;
    }
    public ConcurrentLinkedQueue<Message> getInputQueue(Sender sender){
        return inputQueue.get(sender);
    }

    public Map<Sender, ConcurrentLinkedQueue<Message>> getOutputQueue(){
        return outputQueue;
    }

    public ConcurrentLinkedQueue<Message> getOutputQueue(Sender sender){
        return outputQueue.get(sender);
    }

    public Set<Integer> getRecipientId(){
        return senderIdMap.keySet();
    }


    public Map<Sender, MsgSocketWrapper> getSocketMap(){
        return socketMap;
    }

    public void addToOutputQueue(Sender sender, Message message) {
        outputQueue.get(sender).add(message);
    }

    public void addToOutputQueue(Integer senderId, Message message) {
        Sender sender = senderIdMap.get(senderId);
        if(sender == null){
            throw new UnsupportedOperationException();
        }else {
            addToOutputQueue(sender, message);
        }

    }
}
