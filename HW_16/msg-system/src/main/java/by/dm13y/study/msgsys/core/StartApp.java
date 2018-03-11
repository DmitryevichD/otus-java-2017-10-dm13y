package by.dm13y.study.msgsys.core;

public class StartApp {
    public static void main(String[] args) {
        MsgService msgService = new MsgServiceImpl();
        msgService.start();
    }
}
