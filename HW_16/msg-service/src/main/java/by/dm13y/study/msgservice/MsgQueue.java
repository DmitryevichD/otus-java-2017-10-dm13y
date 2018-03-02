package by.dm13y.study.msgservice;

import by.dm13y.study.msgsys.api.Header;
import by.dm13y.study.msgsys.api.messages.Message;

import java.net.Socket;
import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MsgQueue {
    private final Map<Header, MsgSocketWrapper> recipientMap = new ConcurrentHashMap<>();
    private final Map<Header, ConcurrentLinkedQueue<Message>> recipientQueue = new ConcurrentHashMap<>();

    public synchronized void addRecipient(Header header, MsgSocketWrapper socket) {
        recipientMap.put(header, socket);
        recipientQueue.put(header, new ConcurrentLinkedQueue<Message>());
    }

    public Map<Header, ConcurrentLinkedQueue<Message>> getRecipientQueue(){
        return recipientQueue;
    }

    public Map<Header, MsgSocketWrapper> getRecipientMap(){
        return recipientMap;
    }
}
