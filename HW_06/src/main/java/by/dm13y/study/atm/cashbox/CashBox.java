package by.dm13y.study.atm.cashbox;

import by.dm13y.study.atm.cashbox.picker.BanknotePicker;
import by.dm13y.study.atm.money.Banknote;
import by.dm13y.study.atm.money.Money;

import java.util.List;

public interface CashBox {
    void addBanknote(Banknote banknote);
    List<Banknote> getBanknotes(Money countMoney);
    Money restMoney();
    boolean isSupported(Banknote banknote);
}
