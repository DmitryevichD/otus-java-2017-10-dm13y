package by.dm13y.study.atm.cashService;

import by.dm13y.study.atm.money.Banknote;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public abstract class CashService extends Observable{
    private boolean isEnabled = false;
    private List<Banknote> acceptedBanknotes = Collections.EMPTY_LIST;

    public void setAcceptedBanknotes(@NotNull  List<Banknote> acceptedBanknotes){
        this.acceptedBanknotes = acceptedBanknotes;
    }

    protected void notifyATM(Banknote banknote){
        if((acceptedBanknotes.contains(banknote)) && isEnabled) {
            setChanged();
            notifyObservers(banknote);
        }else{
            returnBanknote();
        }
    }

    public void addATMObserver(Observer observer){
        addObserver(observer);
    }

    public void enable(){
        isEnabled = true;
    }

    public void disable(){
        isEnabled = false;
    }

    abstract void processBanknote(Banknote banknote);

    public void returnBanknote(){};
}
