package by.dm13y.study.atm.cashbox;

import by.dm13y.study.atm.Banknote;
import by.dm13y.study.atm.Money;

import java.util.List;

public interface CashBox {
    void addBanknote(Banknote banknote);
    List<Banknote> getBanknotes(Money countMoney);
    Money restMoney();
    boolean isSupported(Banknote banknote);
}
