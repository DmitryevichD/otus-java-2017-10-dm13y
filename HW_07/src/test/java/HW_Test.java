import by.dm13y.study.atm.DepATM;
import by.dm13y.study.atm.atm.operations.*;
import by.dm13y.study.atm.cards.CardReader;
import by.dm13y.study.atm.cards.SimpleCardReader;
import by.dm13y.study.atm.cashbox.CashBox;
import by.dm13y.study.atm.cashbox.CashBoxImpl;
import by.dm13y.study.atm.cashbox.CashCartridge;
import by.dm13y.study.atm.cashbox.CashCartridgeImpl;
import by.dm13y.study.atm.cashbox.picker.BanknotePicker;
import by.dm13y.study.atm.cashbox.picker.BanknotePickerImpl;
import by.dm13y.study.atm.display.Display;
import by.dm13y.study.atm.money.Banknote;
import by.dm13y.study.atm.money.Money;
import by.dm13y.study.atm.money.MoneyUtils;
import by.dm13y.study.department.Department;
import by.dm13y.study.department.impl.DepartmentImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class HW_Test {
    private static final int COUNT_DEP_ATM = 15;
    private static final List<DepATM> listATM = new ArrayList<>();
    private static Money initialBalance;

    private static DepATM makeATM(){
        Map<Banknote, CashCartridge> cashCartridgesBox = new HashMap<>();

        CashCartridgeImpl cartridge1 = new CashCartridgeImpl(Banknote.ONE_RUB, 200, 100);
        initialBalance = MoneyUtils.add(initialBalance, cartridge1.get(), cartridge1.countBanknote());
        cashCartridgesBox.put(Banknote.ONE_RUB, cartridge1);

        CashCartridgeImpl cartridge2 = new CashCartridgeImpl(Banknote.TWO_RUB, 200, 100);
        initialBalance = MoneyUtils.add(initialBalance, cartridge2.get(), cartridge2.countBanknote());
        cashCartridgesBox.put(Banknote.TWO_RUB, cartridge2);

        CashCartridgeImpl cartridge3 = new CashCartridgeImpl(Banknote.FIVE_RUB, 200, 100);
        initialBalance = MoneyUtils.add(initialBalance, cartridge3.get(), cartridge3.countBanknote());
        cashCartridgesBox.put(Banknote.FIVE_RUB, cartridge3);

        CashCartridgeImpl cartridge4 = new CashCartridgeImpl(Banknote.TEN_RUB, 200, 100);
        initialBalance = MoneyUtils.add(initialBalance, cartridge4.get(), cartridge4.countBanknote());
        cashCartridgesBox.put(Banknote.TEN_RUB, cartridge4);

        CashCartridgeImpl cartridge5 = new CashCartridgeImpl(Banknote.TWENTY_RUB, 200, 100);
        initialBalance = MoneyUtils.add(initialBalance, cartridge5.get(), cartridge5.countBanknote());
        cashCartridgesBox.put(Banknote.TWENTY_RUB, cartridge5);

        CashCartridgeImpl cartridge6 = new CashCartridgeImpl(Banknote.FIFTY_RUB, 200, 100);
        initialBalance = MoneyUtils.add(initialBalance, cartridge6.get(), cartridge6.countBanknote());
        cashCartridgesBox.put(Banknote.FIFTY_RUB, cartridge6);

        BanknotePicker bp = new BanknotePickerImpl();

        Map<Integer, Command> commands = Collections.emptyMap();

        CashBox cashBox = new CashBoxImpl(cashCartridgesBox, bp);

        Display display = new Display() {
            @Override public void showHeader() {}
            @Override public void showInputMessage(String msg) {}
            @Override public void showInfo(String info) {}
            @Override public void showError(String msg) {}
            @Override public void showList(List<String> list) {}
        };

        CardReader cardReader = new SimpleCardReader();

        return  new DepATM(null, cashBox, display, null, null,
                cardReader, null, commands);
    }


    @BeforeClass
    public static void genericATM(){
        initialBalance = new Money();
        for (int i = 0; i < COUNT_DEP_ATM; i++) {
            listATM.add(makeATM());
        }
    }


    @Test
    public void departmentTest(){
        Department department = new DepartmentImpl(listATM);
        Money restBefore = department.getRestOfMoney();
        assertEquals(initialBalance, restBefore);
        Money totalMoneyWithdraw = new Money();
        Integer initSum = 20;

        List<DepATM> atms = ((DepartmentImpl) department).getAtms();
        for (DepATM atm : atms) {
            Money currentMoney = new Money(initSum += 20);
            Money atmBalanceBefore = atm.getRest();

            atm.withdrawMoney(currentMoney);

            assertEquals(atm.getRest(), MoneyUtils.sub(atmBalanceBefore, currentMoney));

            totalMoneyWithdraw = MoneyUtils.add(totalMoneyWithdraw, currentMoney);
        }

        Money restAfter = department.getRestOfMoney();
        assertEquals(restAfter, MoneyUtils.sub(restBefore, totalMoneyWithdraw));

        department.resetAllATM();
        assertEquals(restBefore, department.getRestOfMoney());
    }

}
