package by.dm13y.study.atm.cashService;

import by.dm13y.study.atm.money.Banknote;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class CashServiceImplTest {
    private CashService cashService;
    @Before
    public void init(){
        cashService = new CashServiceImpl(Arrays.asList(Banknote.values()));
    }
    @Test
    public void setAcceptedBanknotes() throws Exception {
        cashService.setAcceptedBanknotes(Arrays.asList(Banknote.TEN_RUB, Banknote.ONE_RUB));
        assertTrue(cashService.isAccepted(Banknote.TEN_RUB));
        assertTrue(cashService.isAccepted(Banknote.ONE_RUB));
        assertFalse(cashService.isAccepted(Banknote.TWO_RUB));
        assertFalse(cashService.isAccepted(Banknote.FIFTY_RUB));
        assertFalse(cashService.isAccepted(Banknote.FIVE_RUB));
        assertFalse(cashService.isAccepted(Banknote.TWENTY_RUB));
    }

    @Test
    public void isAccepted() throws Exception {
        Arrays.stream(Banknote.values()).forEach(banknote -> assertTrue(cashService.isAccepted(banknote)));
    }


}