package by.dm13y.study.atm.cashbox.picker;

import by.dm13y.study.atm.cashbox.CashCartridge;
import by.dm13y.study.atm.cashbox.CashCartridgeImpl;
import by.dm13y.study.atm.money.Banknote;
import by.dm13y.study.atm.money.Money;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.Assert.*;

public class BanknotePickerImplTest {
    private BanknotePicker picker;

    @Before
    public void init(){
        picker = new BanknotePickerImpl((banknote1, banknote2) -> banknote1.getNominal());

    }

    @Test(expected = UnsupportedOperationException.class)
    public void pickUpMoneyException() throws Exception {
        Map<Banknote, CashCartridge> maps = new HashMap<>();
        maps.put(Banknote.ONE_RUB, new CashCartridgeImpl(Banknote.ONE_RUB, 100, 100));
        maps.put(Banknote.TWO_RUB, new CashCartridgeImpl(Banknote.TWO_RUB, 100, 100));
        maps.put(Banknote.TEN_RUB, new CashCartridgeImpl(Banknote.TEN_RUB, 100, 100));

        Money money = new Money(123, 23);
        picker.pickUpMoney(maps, money);
    }

    @Test
    public void pickUpMoney() throws Exception {
        Map<Banknote, CashCartridge> maps = new HashMap<>();
        maps.put(Banknote.ONE_RUB, new CashCartridgeImpl(Banknote.ONE_RUB, 100, 100));
        maps.put(Banknote.TWO_RUB, new CashCartridgeImpl(Banknote.TWO_RUB, 100, 0));
        maps.put(Banknote.TEN_RUB, new CashCartridgeImpl(Banknote.TEN_RUB, 100, 100));

        Money money = new Money(123);
        picker.pickUpMoney(maps, money);
        int i = 10;
    }


}