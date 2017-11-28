package by.dm13y.study.atm.cashbox.picker;

import by.dm13y.study.atm.cashbox.CashCartridge;
import by.dm13y.study.atm.cashbox.CashCartridgeImpl;
import by.dm13y.study.atm.money.Banknote;
import by.dm13y.study.atm.money.Money;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class BanknotePickerImplTest {
    private BanknotePicker picker;

    @Before
    public void init(){
        picker = new BanknotePickerImpl();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void pickUpMoneyException() throws Exception {
        Map<Banknote, CashCartridge> cartridgeBox = new HashMap<>();
        cartridgeBox.put(Banknote.ONE_RUB, new CashCartridgeImpl(Banknote.ONE_RUB, 100, 100));
        cartridgeBox.put(Banknote.TWO_RUB, new CashCartridgeImpl(Banknote.TWO_RUB, 100, 100));
        cartridgeBox.put(Banknote.TEN_RUB, new CashCartridgeImpl(Banknote.TEN_RUB, 100, 100));

        Money money = new Money(123, 23);
        picker.pickUpMoney(cartridgeBox, money);
    }

    @Test
    public void pickUpMoney() throws Exception {
        Map<Banknote, CashCartridge> cartridgeBox = new TreeMap<>(picker.comparator());
        cartridgeBox.put(Banknote.ONE_RUB, new CashCartridgeImpl(Banknote.ONE_RUB, 100, 100));
        cartridgeBox.put(Banknote.TWO_RUB, new CashCartridgeImpl(Banknote.TWO_RUB, 100, 0));
        cartridgeBox.put(Banknote.TEN_RUB, new CashCartridgeImpl(Banknote.TEN_RUB, 100, 100));

        Money money = new Money(123);
        List<Banknote> banknotes = picker.pickUpMoney(cartridgeBox, money);
        assertEquals(Collections.frequency(banknotes, Banknote.TEN_RUB),12);
        assertEquals(Collections.frequency(banknotes, Banknote.ONE_RUB),3);
        assertEquals(Collections.frequency(banknotes, Banknote.TWO_RUB),0);
    }


}