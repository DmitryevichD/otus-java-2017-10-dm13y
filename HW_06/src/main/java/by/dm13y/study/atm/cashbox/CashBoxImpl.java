package by.dm13y.study.atm.cashbox;

import by.dm13y.study.atm.money.Banknote;
import by.dm13y.study.atm.money.Money;
import by.dm13y.study.atm.cashbox.picker.BanknotePicker;
import by.dm13y.study.atm.money.MoneyUtils;
import java.util.*;

public class CashBoxImpl implements CashBox {
    private final Map<Banknote, CashCartridge> cartridgeBox;
    private final BanknotePicker banknotePicker;

    public CashBoxImpl(Map<Banknote, CashCartridge> cashCartridges, BanknotePicker banknotePicker) {
        this.banknotePicker = banknotePicker;
        this.cartridgeBox = new TreeMap<>(banknotePicker.comparator());
        cartridgeBox.putAll(cashCartridges);
    }

    public CashBoxImpl(CashBoxImpl cashBox) {
        banknotePicker = cashBox.banknotePicker;
        cartridgeBox = new TreeMap<>(banknotePicker.comparator());
        cashBox.cartridgeBox.forEach((banknote, cartridge) -> {
            CashCartridgeImpl newCartridge = new CashCartridgeImpl(((CashCartridgeImpl) cartridge));
            cartridgeBox.put(banknote, newCartridge);
        });
    }

    @Override
    public void addBanknote(Banknote banknote) throws UnsupportedOperationException {
        if(!isSupported(banknote)){
            throw new UnsupportedOperationException(banknote + "is not supported");
        }
        CashCartridge cartridge = cartridgeBox.get(banknote);

        if (cartridge.isFull()) {
            throw new UnsupportedOperationException("Cash cartridge is full");
        }else {
            cartridge.add(banknote);
        }
    }

    @Override
    public List<Banknote> getBanknotes(Money countMoney) throws UnsupportedOperationException {
        return banknotePicker.pickUpMoney(cartridgeBox, countMoney);
    }

    @Override
    public Money restMoney() {
        Money totalSum = new Money();
        for (Map.Entry<Banknote, CashCartridge> cartridge : cartridgeBox.entrySet()) {
            totalSum = MoneyUtils.add(totalSum, cartridge.getKey(), cartridge.getValue().countBanknote());
        }
        return totalSum;
    }

    @Override
    public boolean isSupported(Banknote banknote) {
        return cartridgeBox.get(banknote) != null;
    }
}
