package by.dm13y.study.atm.cards;

import java.util.Observable;
import java.util.Observer;

public abstract class CardReader extends Observable {
    private boolean isEnabled = false;

    public void addATMObserver(Observer observer){
        addObserver(observer);
    }
    protected void notifyATM(Card card){
        if(isEnabled) {
            setChanged();
            notifyObservers(card);
        }

    }

    public void enable(){
        isEnabled = true;
    }

    public void disable(){
        isEnabled = false;
    }


    abstract void processCard(Card card);

    public void returnCard(){
        isEnabled = true;
    }
}
