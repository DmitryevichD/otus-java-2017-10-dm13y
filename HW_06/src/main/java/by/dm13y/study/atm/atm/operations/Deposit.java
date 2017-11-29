package by.dm13y.study.atm.atm.operations;

import by.dm13y.study.atm.bank.Account;
import by.dm13y.study.atm.bank.Bank;
import by.dm13y.study.atm.cashService.CashService;
import by.dm13y.study.atm.cashbox.CashBox;
import by.dm13y.study.atm.display.Display;
import by.dm13y.study.atm.money.Banknote;
import by.dm13y.study.atm.money.Money;
import by.dm13y.study.atm.money.MoneyUtils;
import by.dm13y.study.atm.pinpad.Pinpad;
import by.dm13y.study.atm.printer.Printer;

import java.util.ArrayList;
import java.util.List;

public class Deposit implements Command{
    private List<Banknote> banknotes;
    private Money totalSum;

    private void showTotalInfo(Display display){
        display.showInfo("you added: " + totalSum);
    }

    private void receiveMoney(CashService cashService, Display display){
        Banknote banknote = null;
        try{
            display.showInputMessage("enter your nominal");
            banknote = cashService.setBanknoteEvent();
        } catch (UnsupportedOperationException un_ex){
            display.showError(un_ex.getMessage());
        } catch (Exception ex) {
            display.showError("This nominal is not supported");
        }
        if(banknote != null) {
            banknotes.add(banknote);
            totalSum = MoneyUtils.add(totalSum, banknote);
        }
    }

    private boolean continueReceive(Display display, Pinpad pinpad){
        while (true) {
            try{
                showTotalInfo(display);
                display.showInputMessage("select operation:\n" +
                        "1 - add current money to deposit / exit\n" +
                        "2 - add more money\n");
                int curOperation = pinpad.enterValue();
                if(curOperation == 1){
                    return false;
                }
                if(curOperation == 2){
                    return true;
                }
                throw new Exception();
            }catch (Exception ex){
                display.showError("Unsupported operation");
            }
        }
    }

    @Override
    public void exec(Bank bank, Account account, CashBox cashBox, CashService cashService, Display display, Printer printer, Pinpad pinpad) {
        banknotes = new ArrayList<>();
        totalSum = new Money();
        display.showInfo("Deposit your money:");
        while (continueReceive(display, pinpad)) {
            receiveMoney(cashService, display);
            showTotalInfo(display);
        }
        if(!totalSum.isZero()){
            try {
                banknotes.forEach(cashBox::addBanknote);
                bank.setDeposit(account, totalSum);
                printer.print("Your current deposit is: " + bank.currentBalance(account));
            } catch (UnsupportedOperationException ex) {
                display.showError(ex.getMessage());
                cashService.giveBanknotes(banknotes);
            }

        }
    }

    @Override
    public String nameOperation() {
        return "Deposit";
    }
}
