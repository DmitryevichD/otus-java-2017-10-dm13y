package by.dm13y.study.atm.cashbox.picker;

import by.dm13y.study.atm.Banknote;
import by.dm13y.study.atm.cashbox.CashCartridge;

import java.util.List;
import java.util.SortedMap;

public interface MoneyPicker {
    List<Banknote> pickUpMoney(SortedMap<Banknote, CashCartridge> cartridges) throws UnsupportedOperationException;
}
