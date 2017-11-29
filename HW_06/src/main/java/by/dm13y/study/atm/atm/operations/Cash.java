package by.dm13y.study.atm.atm.operations;

import by.dm13y.study.atm.bank.Account;
import by.dm13y.study.atm.bank.Bank;
import by.dm13y.study.atm.cashService.CashService;
import by.dm13y.study.atm.cashbox.CashBox;
import by.dm13y.study.atm.display.Display;
import by.dm13y.study.atm.money.Banknote;
import by.dm13y.study.atm.money.Money;
import by.dm13y.study.atm.pinpad.Pinpad;
import by.dm13y.study.atm.printer.Printer;

import java.util.List;

public class Cash implements Command {
    @Override
    public void exec(Bank bank, Account account, CashBox cashBox, CashService cashService, Display display, Printer printer, Pinpad pinpad) {
        display.showInputMessage("enter count money");
        try {
            Money money = new Money(pinpad.enterValue());
            if (money.compareTo(bank.currentBalance(account)) > 0) {
                throw new UnsupportedOperationException("There are not enough funds on your bank account");
            }
            List<Banknote> banknotes = cashBox.getBanknotes(money);
            bank.reduceBalance(account, money);
            cashService.giveBanknotes(banknotes);
            printer.print(money.toString() + " are given out");
            printer.print("banknotes: " + banknotes);
            display.showInfo("Operation is done");
        } catch (UnsupportedOperationException ex) {
            display.showError(ex.getMessage());
        } catch (Exception ex) {
            display.showError("Command error");
        }
    }

    @Override
    public String nameOperation() {
        return "Cash";
    }
}
