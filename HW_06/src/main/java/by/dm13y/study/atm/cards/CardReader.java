package by.dm13y.study.atm.cards;

import by.dm13y.study.atm.atm.ATM;
import by.dm13y.study.atm.atm.ATMCardObserver;
import by.dm13y.study.atm.atm.ATMObservableCard;

import java.util.ArrayList;
import java.util.List;

public abstract class CardReader implements ATMObservableCard {
    private List<ATMCardObserver> atms = new ArrayList<>();
    private boolean isEnabled = false;


    public void addATM(ATMCardObserver atm){
        atms.add(atm);
    }
    public void remATM(ATMCardObserver atm){
        atms.remove(atm);
    }

    @Override
    public void update(Card card){
        if(isEnabled)
            atms.forEach(atm -> atm.update(card));
    }

    public void enable(){
        isEnabled = true;
    }

    public void blockReader(){
        isEnabled = false;
    }

    abstract void processCard(Card card);

    public void returnCard(){
        isEnabled = true;
    }
}
