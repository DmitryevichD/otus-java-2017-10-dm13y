package messages;

import by.dm13y.study.msgservice.MsgService;
import by.dm13y.study.msgsys.api.MsgRecipient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import stabs.MsgRecipientMock;
import stabs.MsgServiceMock;

public class MsgStateTest {
    private static MsgService msgService;
    @BeforeClass
    public static void init() throws InterruptedException {
        msgService = MsgServiceMock.buildSimpleMsgService();
        msgService.start();
    }

    @Test
    public void MsgStateTest() throws Exception{
        while (!msgService.isStarted()) {
            Thread.sleep(100);
        }
        MsgRecipient recipient = MsgRecipientMock.buildSimpleDBRecipient();
        recipient.connect();
        recipient.getRecipientsList();
    }

    @AfterClass
    public static void stop(){
        msgService.stop();
    }
}
