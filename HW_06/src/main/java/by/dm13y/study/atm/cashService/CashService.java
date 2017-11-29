package by.dm13y.study.atm.cashService;

import by.dm13y.study.atm.money.Banknote;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public interface CashService{
    void setAcceptedBanknotes(@NotNull  List<Banknote> acceptedBanknotes);
    boolean isAccepted(Banknote banknote);

    default Banknote processBanknote(Banknote banknote){
        if(isAccepted(banknote)){
            return banknote;
        }
        returnBanknote(banknote);
        throw new UnsupportedOperationException("Banknote is not supported");
    }

    default void returnBanknote(Banknote banknote){}

    default void giveBanknotes(List<Banknote> banknotes){}

    Banknote setBanknoteEvent() throws Exception;
}
