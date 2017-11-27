package by.dm13y.study.atm.cashbox;

public interface CashCartridge {
    int countBanknote();
    void add();
    void get();
    boolean isEmpty();
    boolean isFull();
}
