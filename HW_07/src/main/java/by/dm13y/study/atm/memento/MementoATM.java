package by.dm13y.study.atm.memento;

import by.dm13y.study.atm.cashbox.CashBox;
import by.dm13y.study.atm.cashbox.CashBoxImpl;

public class MementoATM {
    private final CashBox cashBox;

    public MementoATM(CashBox cashBox){
        this.cashBox = new CashBoxImpl(cashBox);
    }

    public CashBox getCashBox(){
        return cashBox;
    }
}
