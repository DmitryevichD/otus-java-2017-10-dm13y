package by.dm13y.study.atm.bank;

import by.dm13y.study.atm.money.Money;
import by.dm13y.study.atm.money.MoneyUtils;

import java.util.HashMap;
import java.util.Map;

public class BankImpl implements Bank {
    private Map<Long, Account> accounts = new HashMap<>();
    private Map<Account, Money> accountBalance = new HashMap<>();
    public BankImpl(){
        Account account = new Account();
        accounts.put(777l, account);
        accountBalance.put(account, new Money(1000));
    }
    @Override
    public Account getAccount(long cardNumber) {
        return accounts.get(cardNumber);
    }

    @Override
    public Money currentBalance(Account account) {
        return accountBalance.get(account);
    }

    @Override
    public void reduceBalance(Account account, Money money) {
        Money currrentBalance = currentBalance(account);
        Money rest = MoneyUtils.sub(currrentBalance, money);
        accountBalance.put(account, rest);
    }

    @Override
    public void setDeposit(Account account, Money money) {
        Money currentBalance = currentBalance(account);
        accountBalance.put(account, MoneyUtils.add(currentBalance, money));
    }
}
