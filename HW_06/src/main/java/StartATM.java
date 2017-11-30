import by.dm13y.study.atm.atm.ATM;
import by.dm13y.study.atm.atm.operations.*;
import by.dm13y.study.atm.bank.Bank;
import by.dm13y.study.atm.bank.BankImpl;
import by.dm13y.study.atm.cards.Card;
import by.dm13y.study.atm.cards.CardReader;
import by.dm13y.study.atm.cards.SimpleCardReader;
import by.dm13y.study.atm.cashService.CashService;
import by.dm13y.study.atm.cashService.CashServiceImpl;
import by.dm13y.study.atm.cashbox.CashBox;
import by.dm13y.study.atm.cashbox.CashBoxImpl;
import by.dm13y.study.atm.cashbox.CashCartridge;
import by.dm13y.study.atm.cashbox.CashCartridgeImpl;
import by.dm13y.study.atm.cashbox.picker.BanknotePicker;
import by.dm13y.study.atm.cashbox.picker.BanknotePickerImpl;
import by.dm13y.study.atm.display.Display;
import by.dm13y.study.atm.display.DisplayImpl;
import by.dm13y.study.atm.money.Banknote;
import by.dm13y.study.atm.pinpad.PinPadImpl;
import by.dm13y.study.atm.pinpad.Pinpad;
import by.dm13y.study.atm.printer.Printer;
import by.dm13y.study.atm.printer.PrinterImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StartATM {
    private static ATM atm;
    private static CardReader cardReader;
    private static CashService cashService;

    public static void main(String[] ars){
        Map<Banknote, CashCartridge> cashCartridgesBox = new HashMap<>();
        cashCartridgesBox.put(Banknote.ONE_RUB, new CashCartridgeImpl(Banknote.ONE_RUB, 200, 100));
        cashCartridgesBox.put(Banknote.TWO_RUB, new CashCartridgeImpl(Banknote.TWO_RUB, 200, 100));
        cashCartridgesBox.put(Banknote.FIVE_RUB, new CashCartridgeImpl(Banknote.FIVE_RUB, 200, 100));
        cashCartridgesBox.put(Banknote.TEN_RUB, new CashCartridgeImpl(Banknote.TEN_RUB, 200, 100));
        cashCartridgesBox.put(Banknote.TWENTY_RUB, new CashCartridgeImpl(Banknote.TWENTY_RUB, 200, 100));
        cashCartridgesBox.put(Banknote.FIFTY_RUB, new CashCartridgeImpl(Banknote.FIFTY_RUB, 200, 100));

        BanknotePicker bp = new BanknotePickerImpl();

        Map<Integer, Command> commands = new HashMap<>();
        commands.put(1, new Balance());
        commands.put(2, new Cash());
        commands.put(3, new Deposit());
        commands.put(99, new ReturnCard());

        CashBox cashBox = new CashBoxImpl(cashCartridgesBox, bp);

        Bank bank = new BankImpl();

        Display display = new DisplayImpl();

        Pinpad pinpad = new PinPadImpl(System.in);

        Printer printer = new PrinterImpl();

        cardReader = new SimpleCardReader();

        cashService = new CashServiceImpl(Arrays.asList(Banknote.values()));

        atm = new ATM(bank, cashBox, display, pinpad, printer,
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
