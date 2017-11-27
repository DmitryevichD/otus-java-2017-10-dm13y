package by.dm13y.study.atm.cashbox;

import by.dm13y.study.atm.money.Banknote;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CashCartridgeImplTest {
    private final Banknote testNominal = Banknote.ONE_RUB;
    private CashCartridge cashCartridge;
    @Before
    public void init(){
        cashCartridge = new CashCartridgeImpl(testNominal, 200, 100);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void countBanknoteException() throws Exception {
        cashCartridge.add(Banknote.TWENTY_RUB);
    }

    @Test()
    public void countBanknote() throws Exception {
        for (int i = 0; i < 10; i++) {
            cashCartridge.add(Banknote.ONE_RUB);
        }
        assertTrue(cashCartridge.countBanknote() == 110);
    }

    @Test
    public void getOne() throws Exception {
        Banknote curBanknote = cashCartridge.get();
        assertEquals(curBanknote, testNominal);
        assertTrue(cashCartridge.countBanknote() == 99);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getManyException() throws Exception {
        cashCartridge.get(200);
    }

    @Test
    public void getMany() throws Exception {
        final int count = 55;
        List<Banknote> curBanknotes = cashCartridge.get(count);
        assertEquals(cashCartridge.countBanknote(), 45);
        assertEquals(curBanknotes.size(), 55);
        curBanknotes.forEach(val -> testNominal.equals(val));
    }

    @Test
    public void isEmpty() throws Exception {
        assertFalse(cashCartridge.isEmpty());
        cashCartridge.get(100);
        assertTrue(cashCartridge.isEmpty());
    }

    @Test
    public void isFull() throws Exception {
        assertFalse(cashCartridge.isFull());
        for (int i = 0; i < 100; i++) {
            cashCartridge.add(testNominal);
        }
        assertTrue(cashCartridge.isFull());
    }

}