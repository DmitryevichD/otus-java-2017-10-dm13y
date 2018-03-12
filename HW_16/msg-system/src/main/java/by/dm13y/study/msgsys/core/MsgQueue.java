package by.dm13y.study.msgsys.core;

import by.dm13y.study.msgsys.api.Sender;
import by.dm13y.study.msgsys.api.MsgSocketWrapper;
import by.dm13y.study.msgsys.api.SenderType;
import by.dm13y.study.msgsys.api.messages.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

class MsgQueue {
    private final Logger logger = LoggerFactory.getLogger(MsgQueue.class);
    private final Map<Integer, Sender> senderIdMap = new ConcurrentHashMap<>();
    private final Map<Sender, MsgSocketWrapper> socketMap = new ConcurrentHashMap<>();
    private final Map<Sender, ConcurrentLinkedQueue<Message>> inputQueue = new ConcurrentHashMap<>();
    private final Map<Sender, ConcurrentLinkedQueue<Message>> outputQueue = new ConcurrentHashMap<>();

    public synchronized void addRecipient(Sender sender, MsgSocketWrapper socket) {
        logger.info("Add recipient:" + sender);
        socketMap.put(sender, socket);
        senderIdMap.put(sender.getId(), sender);
        inputQueue.put(sender, new ConcurrentLinkedQueue<>());
        outputQueue.put(sender, new ConcurrentLinkedQueue<>());
    }

    public Map<Sender, ConcurrentLinkedQueue<Message>> getInputQueue(){
        return inputQueue;
    }

    public ConcurrentLinkedQueue<Message> getInputQueue(Sender sender){
        return inputQueue.get(sender);
    }

    public ConcurrentLinkedQueue<Message> getOutputQueue(Sender sender){
        return outputQueue.get(sender);
    }

    public List<Integer> getRecipientDBList(SenderType senderType){
        List<Integer> recipientList = socketMap.keySet()
                .stream()
                .filter(sender -> sender.getType() == senderType)
                .map(Sender::getId)
                .collect(Collectors.toList());
        logger.debug("get recipient list for type " + senderType + " " + recipientList);
        return recipientList;
    }

    public Map<Sender, MsgSocketWrapper> getSocketMap(){
        return socketMap;
    }

    public void addToOutputQueue(Sender sender, Message message) {
        logger.debug(sender + " OUTPUT ADD " + message);
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
