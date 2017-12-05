package by.dm13y.study.atm;

import by.dm13y.study.atm.atm.ATM;
import by.dm13y.study.atm.atm.operations.Command;
import by.dm13y.study.atm.bank.Bank;
import by.dm13y.study.atm.cards.CardReader;
import by.dm13y.study.atm.cashService.CashService;
import by.dm13y.study.atm.cashbox.CashBox;
import by.dm13y.study.atm.display.Display;
import by.dm13y.study.atm.memento.MementoATM;
import by.dm13y.study.atm.money.Money;
import by.dm13y.study.atm.pinpad.Pinpad;
import by.dm13y.study.atm.printer.Printer;
import by.dm13y.study.department.Department;

import java.util.Map;
import java.util.Observable;

public class DepATM extends ATM {
    private CashBox cashBox;
    private MementoATM sourceState;

    public DepATM(Bank bank, CashBox cashBox, Display display, Pinpad pinpad, Printer printer, CardReader cardReader, CashService cashService, Map<Integer, Command> commands) {
        super(bank, cashBox, display, pinpad, printer, cardReader, cashService, commands);
        this.cashBox = cashBox;
    }

    public Money getCurrentRest(){
        return cashBox.restMoney();
    }

    public void resetATMState(Object arg){
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Observable observable, Object arg) {
        if (observable != null) {
            if (observable instanceof Department) {
                resetATMState(arg);
            }
        }
    }
}
