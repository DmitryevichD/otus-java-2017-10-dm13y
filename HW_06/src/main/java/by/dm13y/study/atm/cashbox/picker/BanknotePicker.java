package by.dm13y.study.atm.cashbox.picker;

import by.dm13y.study.atm.money.Banknote;
import by.dm13y.study.atm.money.Money;
import by.dm13y.study.atm.cashbox.CashCartridge;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public interface BanknotePicker {
    List<Banknote> pickUpMoney(Map<Banknote, CashCartridge> cartridgeBox, Money money) throws UnsupportedOperationException;
    Comparator<Banknote> comparator();
}
