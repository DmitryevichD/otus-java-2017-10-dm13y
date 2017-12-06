package by.dm13y.study.atm.memento;

import java.util.ArrayDeque;
import java.util.Deque;

public class CaretakerATM {
    private final Deque<MementoATM> mementoATM = new ArrayDeque<>();

    public MementoATM getFirstState(){
        return mementoATM.pop();
    }

    public MementoATM getLastState(){
        return mementoATM.pollLast();
    }

    public void setMementoATM(MementoATM state) {
        mementoATM.add(state);
    }
}
