package by.dm13y.study.atm.atm;

import by.dm13y.study.atm.atm.operations.Command;
import by.dm13y.study.atm.bank.Account;
import by.dm13y.study.atm.bank.Bank;
import by.dm13y.study.atm.cards.Card;
import by.dm13y.study.atm.cards.CardReader;
import by.dm13y.study.atm.cashService.CashService;
import by.dm13y.study.atm.cashbox.CashBox;
import by.dm13y.study.atm.display.Display;
import by.dm13y.study.atm.pinpad.Pinpad;
import by.dm13y.study.atm.printer.Printer;

import java.util.*;

public class ATM implements Observer {
    private final int COUNT_PIN_ATTEMPTS = 3;
    private final Map<Integer, Command> commands;
    private final List<String> displayListCommands;
    private Bank bank;
    private CardReader cardReader;
    private CashService cashService;
    private CashBox cashBox;
    private Display display;
    private Pinpad pinpad;
    private Printer printer;

    public ATM(Bank bank, CashBox cashBox, Display display, Pinpad pinpad, Printer printer,
               CardReader cardReader, CashService cashService, Map<Integer, Command> commands) {
        this.bank = bank;
        this.cashBox = cashBox;
        this.display = display;
        this.pinpad = pinpad;
        this.printer = printer;
        this.cardReader = cardReader;
        this.commands = commands;
        this.cashService = cashService;

        cardReader.addATMObserver(this);
        cardReader.enable();

        displayListCommands = new ArrayList<>();
        commands.forEach((number, command) -> displayListCommands.add(number + "." + command.nameOperation()));

        display.showHeader();
    }

    public void selectOperation(Account account){
        while (true) {
            display.showList(displayListCommands);
            try {
                display.showInputMessage("select command:");
                Integer commandCode = pinpad.enterValue();
                Command cmd = commands.get(commandCode);
                if (cmd != null) {
                    if(cmd.nameOperation().equals("EXIT")) {
                        cardReader.returnCard();
                        display.showHeader();
                        return;
                    }
                    cmd.exec(bank, account, cashBox, cashService, display, printer, pinpad);
                }
            } catch (Exception ex) {
                display.showError("incorrect input, please again");
            }
        }
    }


    private void processCard(Card card) {
        Account account = bank.getAccount(card.getNumber());
        if (account == null) {
            display.showError("Bank account not found");
            cardReader.returnCard();
            display.showHeader();
        }
        selectOperation(account);
    }

    @Override
    public void update(Observable observable, Object arg) {
        if (arg == null) {return;}
        if (cardReader == null) {return;}
        if((observable instanceof CardReader) && (arg instanceof Card)){
            Card currentCard = (Card) arg;
            display.showInfo("Thanks. Your card number is " +currentCard.getNumber() + "\nCheat(pin)>>>>" + currentCard.getPin()+ "<<<<\n");
            if (isCorrectPin(currentCard)) {
                cardReader.blockReader();
                processCard(currentCard);
            }else {
                display.showInfo("INPUT YOUR CARD\n");
                cardReader.returnCard();
            }
        }
    }

    private boolean isCorrectPin(Card card) {
        for (int i = 0; i < COUNT_PIN_ATTEMPTS; i++) {
            display.showInputMessage("Input pin");
            try {
                int pin = pinpad.enterValue();
                if(pin != card.getPin()){
                    throw new Exception();
                }else {
                    return true;
                }
            }catch (Exception ex){
                display.showError("incorrect pin code, please try again");
            }
        }
        return false;
    }
}
