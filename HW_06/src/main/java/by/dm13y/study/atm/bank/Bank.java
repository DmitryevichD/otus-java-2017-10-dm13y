package by.dm13y.study.atm.bank;

import by.dm13y.study.atm.money.Money;
import org.jetbrains.annotations.NotNull;

import java.security.InvalidParameterException;

public interface Bank {
    Account getAccount(long cardNumber);
    Money currentBalance(Account account);
    void reduceBalance(Account account, Money money);
    void setDeposit(Account account, Money money);

}
