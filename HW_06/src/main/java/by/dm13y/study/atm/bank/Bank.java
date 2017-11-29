package by.dm13y.study.atm.bank;

import by.dm13y.study.atm.money.Money;

import java.security.InvalidParameterException;

public interface Bank {
    Account getAccuant(long cardNumber) throws InvalidParameterException;
    Money getBalance(Account account);
    void updateBalance(Account account, Money money);

}
