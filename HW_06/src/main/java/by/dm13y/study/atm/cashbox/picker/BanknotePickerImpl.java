package by.dm13y.study.atm.cashbox.picker;

import by.dm13y.study.atm.money.Banknote;
import by.dm13y.study.atm.money.Money;
import by.dm13y.study.atm.cashbox.CashCartridge;
import by.dm13y.study.atm.money.MoneyUtils;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class BanknotePickerImpl implements BanknotePicker {

    @Override
    public List<Banknote> pickUpMoney(Map<Banknote, CashCartridge> cartridges, Money money) throws UnsupportedOperationException {
        Map<Banknote, Integer> banknotes = isAvailable(cartridges, money);


        if(!banknotes.isEmpty()){
            List<Banknote> resultList = new ArrayList<>();
            banknotes.forEach((banknote, count) -> resultList.addAll(cartridges.get(banknote).get(count)));
            return resultList;
        }else {
            throw new UnsupportedOperationException("Not enough banknotes");
        }

    }

    @NotNull
    private Map<Banknote, Integer> isAvailable(@NotNull Map<Banknote, CashCartridge> cartridges, @NotNull Money money) {
        Map<Banknote, Integer> result = new HashMap<>();
        for (Map.Entry<Banknote, CashCartridge> cartridge : cartridges.entrySet()) {
            Banknote banknote = cartridge.getKey();
            int bRest = cartridge.getValue().countBanknote();
            if(bRest > 0) {
                int bCount = MoneyUtils.countBanknotes(money, banknote);
                int available = bCount > bRest ? bRest : bCount;
                result.put(banknote, available);
                try {
                    money = MoneyUtils.sub(money, banknote, available);
                } catch (UnsupportedOperationException ex) {
                    return Collections.emptyMap();
                }
            }
            if (money.isZero()) {return result;}
        }
        if(money.isZero()){
            return result;
        }else{
            return Collections.emptyMap();
        }
    }


    @Override
    public Comparator<Banknote> comparator(){
        return (o1, o2) -> o1.getNominal().compareTo(o2.getNominal()) * -1;
    }
}
