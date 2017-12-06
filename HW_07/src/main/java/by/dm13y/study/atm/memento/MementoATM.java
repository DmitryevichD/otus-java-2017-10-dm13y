package by.dm13y.study.atm.memento;

import by.dm13y.study.atm.cashbox.CashBox;
import by.dm13y.study.atm.cashbox.CashBoxImpl;

public class MementoATM {
    private final CashBox cashBox;

    public MementoATM(CashBox cashBox){
        if (cashBox instanceof CashBoxImpl) {
            this.cashBox = new CashBoxImpl(((CashBoxImpl) cashBox));
        } else {
            throw new UnsupportedOperationException("The ATM did not save its cash box");
        }
    }

    public CashBox getCashBox(){
        return cashBox;
    }
}
