package by.dm13y.study.atm.bank;

import by.dm13y.study.atm.money.Money;
import by.dm13y.study.atm.money.MoneyUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BankTest {
    private Bank bank;
    private final long cardId = 777l;
    private Account account;

    @Before
    public void init(){
        bank = new BankImpl();
        account = bank.getAccount(cardId);
    }

    @Test
    public void getAccount() throws Exception{
        assertNull(bank.getAccount(3434));
        assertNotNull(bank.getAccount(cardId));
    }


    @Test
    public void currentBalance() throws Exception {
        assertEquals(bank.currentBalance(account), new Money(1000));
    }

    @Test
    public void reduceBalance() throws Exception {
        Money balance = bank.currentBalance(account);
        Money tmpMoney = new Money(500);
        bank.reduceBalance(account, tmpMoney);
        assertEquals(bank.currentBalance(account), MoneyUtils.sub(balance, tmpMoney));
    }

    @Test
    public void setDeposit() throws Exception {
        Money balance = bank.currentBalance(account);
        Money tmpMoney = new Money(500);
        bank.setDeposit(account, tmpMoney);
        assertEquals(bank.currentBalance(account), MoneyUtils.add(balance, tmpMoney));
    }

}