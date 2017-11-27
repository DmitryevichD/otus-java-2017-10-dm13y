package by.dm13y.study.atm.cashbox;

import by.dm13y.study.atm.Banknote;
import by.dm13y.study.atm.Money;
import by.dm13y.study.atm.cashbox.picker.MoneyPicker;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class CashBoxImpl implements CashBox {
    private final SortedMap<Banknote, CashCartridge> cash;
    private final MoneyPicker moneyPicker;

    public CashBoxImpl(Map<Banknote, CashCartridge> cashCartridges, MoneyPicker moneyPicker) {
        cash = new TreeMap<>(cashCartridges);
        this.moneyPicker = moneyPicker;
    }

    @Override
    public void addBanknote(Banknote banknote) throws UnsupportedOperationException {
        if(!isSupported(banknote)){
            throw new UnsupportedOperationException(banknote + "is not supported");
        }
        CashCartridge cartridge = cash.get(banknote);
        if (cartridge.isFull()) {
            throw new UnsupportedOperationException("Cash cartridge is full");
        }else {
            cartridge.add();
        }
    }

    @Override
    public List<Banknote> getBanknotes(Money countMoney) throws UnsupportedOperationException {
        return moneyPicker.pickUpMoney(cash);
    }

    @Override
    public Money restMoney() {
        Money totalSum = new Money();
        cash.forEach((banknote, cartridge) -> totalSum.addMoney(banknote, cartridge.countBanknote()));
        return totalSum;
    }

    @Override
    public boolean isSupported(Banknote banknote) {
        return cash.get(banknote) != null;
    }
}
