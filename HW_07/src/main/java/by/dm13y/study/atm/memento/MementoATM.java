package by.dm13y.study.atm.memento;

import by.dm13y.study.atm.DepATM;
import by.dm13y.study.atm.cashbox.CashCartridge;
import by.dm13y.study.atm.cashbox.picker.BanknotePicker;
import by.dm13y.study.atm.money.Banknote;

import java.util.Map;

public class MementoATM {
    private Map<Banknote, CashCartridge> cartridgeBox;
    private BanknotePicker banknotePicker;

    public MementoATM(DepATM depATM) {
        throw new UnsupportedOperationException();
    }

}
