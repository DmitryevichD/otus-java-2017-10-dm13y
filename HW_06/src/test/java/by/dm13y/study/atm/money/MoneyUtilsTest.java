package by.dm13y.study.atm.money;

import org.junit.Test;

import static org.junit.Assert.*;

public class MoneyUtilsTest {
    @Test
    public void addMoney() throws Exception {
        assertEquals(new Money(10, 30), MoneyUtils.add(new Money(4, 40), new Money(5, 90)));
        assertEquals(new Money(8, 80), MoneyUtils.add(new Money(4, 40), new Money(4, 40)));
        assertEquals(new Money(0, 5), MoneyUtils.add(new Money(0, 3), new Money(0, 2)));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void subMoneyException() throws Exception {
        MoneyUtils.sub(new Money(0, 0), new Money(1, 3));
    }

    @Test
    public void subMoney() throws Exception {
        assertEquals(new Money(8, 50), MoneyUtils.sub(new Money(14, 40), new Money(5, 90)));
        assertEquals(new Money(2, 30), MoneyUtils.sub(new Money(4, 40), new Money(2, 10)));
        assertEquals(new Money(0, 5), MoneyUtils.sub(new Money(0, 8), new Money(0, 3)));
    }

    @Test
    public void countBanknotes() throws Exception{
        Banknote banknote1 = Banknote.ONE_RUB;

        Banknote banknote2 = Banknote.TWENTY_RUB;

        assertEquals(MoneyUtils.countBanknotes(new Money(10, 0), banknote1), 10);
        assertEquals(MoneyUtils.countBanknotes(new Money(23, 1), banknote1), 23);
        assertEquals(MoneyUtils.countBanknotes(new Money(63, 50), banknote2), 3);
    }

    @Test
    public void multiply() throws Exception{
        assertEquals(MoneyUtils.multiply(new Money(10, 3), 3), new Money(30, 9));
        assertEquals(MoneyUtils.multiply(new Money(10, 50), 3), new Money(31, 50));
    }

}