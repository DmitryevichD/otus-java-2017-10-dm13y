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

import java.util.Map;

public class DepATM extends ATM {
    private final MementoATM originalState;

    public DepATM(Bank bank, CashBox cashBox, Display display, Pinpad pinpad, Printer printer, CardReader cardReader, CashService cashService, Map<Integer, Command> commands) {
        super(bank, cashBox, display, pinpad, printer, cardReader, cashService, commands);
        originalState = getCurrentState();
    }

    public MementoATM getCurrentState(){
        return new MementoATM(cashBox);
    } 
    
    private void restoreState(MementoATM mementoATM){
        cashBox = mementoATM.getCashBox();
    }

    public void withdrawMoney(Money money) {
        cashBox.getBanknotes(money);
    }

    public void setState(MementoATM mementoATM) {
        if (mementoATM == null) {
            restoreState(originalState);
        }else {
            restoreState(mementoATM);
        }
    }
}
