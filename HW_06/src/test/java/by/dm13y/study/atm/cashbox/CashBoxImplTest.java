package by.dm13y.study.atm.cashbox;

import by.dm13y.study.atm.cashbox.picker.BanknotePicker;
import by.dm13y.study.atm.cashbox.picker.BanknotePickerImpl;
import by.dm13y.study.atm.money.Banknote;
import by.dm13y.study.atm.money.Money;
import by.dm13y.study.atm.money.MoneyUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;

public class CashBoxImplTest {
    private CashBox cashBox;
    @Before
    public void init(){
        BanknotePicker picker = new BanknotePickerImpl();
        Map<Banknote, CashCartridge> cartridgeBox = new TreeMap<>(picker.comparator());
        cartridgeBox.put(Banknote.ONE_RUB, new CashCartridgeImpl(Banknote.ONE_RUB, 100, 100));
        cartridgeBox.put(Banknote.TWO_RUB, new CashCartridgeImpl(Banknote.TWO_RUB, 100, 0));
        cartridgeBox.put(Banknote.TEN_RUB, new CashCartridgeImpl(Banknote.TEN_RUB, 100, 100));
        cashBox = new CashBoxImpl(cartridgeBox, picker);
    }


    @Test(expected = UnsupportedOperationException.class)
    public void addBanknoteToFullCartrigeExceptionTest() throws Exception {
        cashBox.addBanknote(Banknote.TEN_RUB);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void addUndefinedBanknoteExceptionTest() throws Exception {
        cashBox.addBanknote(Banknote.TWENTY_RUB);
    }

    @Test
    public void addBanknote() throws Exception {
        Money money = cashBox.restMoney();
        cashBox.addBanknote(Banknote.TWO_RUB);
        assertEquals(cashBox.restMoney(), MoneyUtils.add(money, Banknote.TWO_RUB));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getToMatchBanknoteExceptionTest() throws Exception {
        Money money = cashBox.restMoney();
        cashBox.getBanknotes(MoneyUtils.add(money, Banknote.ONE_RUB));
    }

    @Test
    public void getBanknote() throws Exception {
        Money money = cashBox.restMoney();
        Money testMoney = new Money(1000);
        cashBox.getBanknotes(testMoney);
        assertEquals(cashBox.restMoney(), MoneyUtils.sub(money, testMoney));
    }

    @Test
    public void isSupported() throws Exception {
        assertTrue(cashBox.isSupported(Banknote.ONE_RUB));
        assertTrue(cashBox.isSupported(Banknote.TWO_RUB));
        assertTrue(cashBox.isSupported(Banknote.TEN_RUB));
        assertFalse(cashBox.isSupported(Banknote.FIFTY_RUB));
    }
}