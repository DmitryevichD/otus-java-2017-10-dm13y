import by.dm13y.study.atm.atm.ATMFacade;
import by.dm13y.study.atm.atm.operations.Command;
import by.dm13y.study.atm.bank.Bank;
import by.dm13y.study.atm.cards.Card;
import by.dm13y.study.atm.cards.CardReader;
import by.dm13y.study.atm.cards.SimpleCardReader;
import by.dm13y.study.atm.cashService.CashService;
import by.dm13y.study.atm.cashService.SimpleCashService;
import by.dm13y.study.atm.cashbox.CashBox;
import by.dm13y.study.atm.cashbox.CashBoxImpl;
import by.dm13y.study.atm.cashbox.CashCartridge;
import by.dm13y.study.atm.cashbox.CashCartridgeImpl;
import by.dm13y.study.atm.cashbox.picker.BanknotePicker;
import by.dm13y.study.atm.cashbox.picker.BanknotePickerImpl;
import by.dm13y.study.atm.display.Display;
import by.dm13y.study.atm.money.Banknote;
import by.dm13y.study.atm.pinpad.PinPadImpl;
import by.dm13y.study.atm.pinpad.Pinpad;
import by.dm13y.study.atm.printer.Printer;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StartATM {
    private static ATMFacade atm;
    private static CardReader cardReader;
    private static CashService cashService;

    public static void main(String[] ars){
        Map<Banknote, CashCartridge> cashCartridgesBox = new HashMap<>();
        cashCartridgesBox.put(Banknote.ONE_RUB, new CashCartridgeImpl(Banknote.ONE_RUB, 200, 100));
        cashCartridgesBox.put(Banknote.TWO_RUB, new CashCartridgeImpl(Banknote.TWO_RUB, 200, 100));
        cashCartridgesBox.put(Banknote.FIVE_RUB, new CashCartridgeImpl(Banknote.FIVE_RUB, 200, 100));
        cashCartridgesBox.put(Banknote.TEN_RUB, new CashCartridgeImpl(Banknote.TEN_RUB, 200, 100));
        cashCartridgesBox.put(Banknote.TWENTY_RUB, new CashCartridgeImpl(Banknote.TWENTY_RUB, 200, 100));

        BanknotePicker bp = new BanknotePickerImpl();

        CashBox cashBox = new CashBoxImpl(cashCartridgesBox, bp);

        Bank bank = null;

        Display display = new Display(){};

        Pinpad pinpad = new PinPadImpl(System.in);

        Printer printer = new Printer(){};

        cardReader = new SimpleCardReader();

        cashService = new SimpleCashService(Arrays.asList(Banknote.values()));

        Map<Integer, Command > commands = Collections.EMPTY_MAP;

        atm = new ATMFacade(bank, cashBox, display, pinpad, printer,
                cardReader, cashService, commands);

        Card card = new Card() {
            @Override
            public long getNumber() {
                return 777;
            }

            @Override
            public int getPin() {
                return 1476;
            }
        };
        ((SimpleCardReader) cardReader).processCard(card);
    }
}
