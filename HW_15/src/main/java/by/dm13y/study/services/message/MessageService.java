package by.dm13y.study.services.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ForkJoinPool;

@Component
public class MessageService {
    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);
    private final int POOL_SIZE = 16;
    private final long DELAY = 10;
    private final ForkJoinPool pool = new ForkJoinPool(POOL_SIZE);
    private final Map<Address, ConcurrentLinkedQueue<Message>> messageMap = new ConcurrentHashMap<>();
    private final Map<Address, MsgRecipient> addressMap = new ConcurrentHashMap<>();
    private final HashMap<Address, MessageQueueHandler> taskList = new HashMap<>();
    private Thread manager;

    public void addRecipient(MsgRecipient msgRecipient){
        addressMap.put(msgRecipient.getAddress(), msgRecipient);
        messageMap.put(msgRecipient.getAddress(), new ConcurrentLinkedQueue<>());
    }

    private void newTask(MsgRecipient recipient, ConcurrentLinkedQueue<Message> queue) {
        MessageQueueHandler mqe = new MessageQueueHandler(recipient, queue);
        pool.invoke(mqe);
        taskList.put(recipient.getAddress(), mqe);
    }

    public void sendMessage(Message message){
        messageMap.get(message.getTo()).add(message);
    }

    public void start(){
        manager = new Thread(() -> {
            logger.info("Message core is started");
            while (true) {
                for (Map.Entry<Address, ConcurrentLinkedQueue<Message>> queue : messageMap.entrySet()) {
                    Address address = queue.getKey();
                    MsgRecipient recipient = addressMap.get(address);
                    if (!queue.getValue().isEmpty()) {
                        if (taskList.get(address) == null) { //new address
                            newTask(recipient, queue.getValue());
                        } else if (taskList.get(address).isDone()) {
                            newTask(recipient, queue.getValue());
                        } else {
                            //task is not done
                        }
                    }
                }

                try {
                    Thread.sleep(DELAY);
                } catch (InterruptedException ex) {
                    logger.info("Thread is interrupted", ex);
                    return;
                }

                if (Thread.currentThread().isInterrupted()) {
                    logger.info("Thread is finishing");
                    return;
                }
            }
        });
        manager.start();
    }

    public void stop(){
        pool.shutdown();
    }

    @PreDestroy
    private void preDestroy(){
        manager.interrupt();
    }

}
