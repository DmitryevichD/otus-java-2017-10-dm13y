package by.dm13y.study.msgsys.api;

import by.dm13y.study.msgsys.api.messages.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageHelper {
    private MessageHelper(){}

    private final static Logger logger = LoggerFactory.getLogger(MessageHelper.class);

    public static <T> Message buildResponse(Sender sender, Message srcMsg, T body) throws UnsupportedOperationException{
        if (srcMsg instanceof MsgSys) {
            return buildMsgSys(sender, srcMsg, body);
        }else {
            if (srcMsg.getSender().getType() == SenderType.DB_SERVICE) {
                return buildMsgToDB(sender, srcMsg, body);
            }
            if (srcMsg.getSender().getType() == SenderType.WEB_SOCKET) {
                return buildMsgToWS(sender, srcMsg, body);
            }
        }
        logger.error(sender.getType() + " is not supported");
        throw new UnsupportedOperationException();
    }

    private static <T> MsgToDB buildMsgToDB(Sender sender, Message srcMsg, T body) {
        return new MsgToDB(sender, srcMsg.getSender().getId(), body, srcMsg.getMsgMarker());
    }

    private static <T>  MsgSys buildMsgSys(Sender sender, Message srcMsg, T body) {
        return new MsgSys(sender, srcMsg.getSender().getId(), body, MsgSys.Operation.byId(srcMsg.getMsgMarker()));
    }

    private static <T> MsgToWS buildMsgToWS(Sender sender, Message srcMsg, T body) {
        return new MsgToWS(sender, srcMsg.getSender().getId(), body, srcMsg.getMsgMarker());
    }
}
