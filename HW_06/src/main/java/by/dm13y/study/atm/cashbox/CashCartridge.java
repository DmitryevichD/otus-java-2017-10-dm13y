package by.dm13y.study.atm.cashbox;

import by.dm13y.study.atm.money.Banknote;

import java.util.List;

public interface CashCartridge {
    int countBanknote();
    void add(Banknote banknote);
    Banknote get();
    List<Banknote> get(int count);
    boolean isEmpty();
    boolean isFull();
}
