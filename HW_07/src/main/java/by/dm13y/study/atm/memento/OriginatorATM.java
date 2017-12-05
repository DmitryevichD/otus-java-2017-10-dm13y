package by.dm13y.study.atm.memento;

import java.util.ArrayList;
import java.util.List;

public class OriginatorATM {
    List<MementoATM> states = new ArrayList<>();

    public void saveToMemento(MementoATM state) {
        states.add(state);
    }

    public void restoreFromMemento()
}
