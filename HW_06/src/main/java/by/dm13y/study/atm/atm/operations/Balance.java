package by.dm13y.study.atm.atm.operations;

import by.dm13y.study.atm.bank.Account;
import by.dm13y.study.atm.bank.Bank;
import by.dm13y.study.atm.cashService.CashService;
import by.dm13y.study.atm.cashbox.CashBox;
import by.dm13y.study.atm.display.Display;
import by.dm13y.study.atm.money.Money;
import by.dm13y.study.atm.pinpad.Pinpad;
import by.dm13y.study.atm.printer.Printer;

public class Balance implements Command {

    @Override
    public void exec(Bank bank, Account account, CashBox cashBox, CashService cashService, Display display, Printer printer, Pinpad pinpad) {
        Money money = bank.currentBalance(account);
        display.showInfo("Balance: " + money);
    }

    @Override
    public String nameOperation(){
        return "Balance";
    }
}
