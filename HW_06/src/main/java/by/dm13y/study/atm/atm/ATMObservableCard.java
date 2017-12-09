package by.dm13y.study.atm.atm;

import by.dm13y.study.atm.cards.Card;

public interface ATMObservableCard {
    void addATM(ATMCardObserver atm);
    void remATM(ATMCardObserver atm);
    void update(Card card);
}