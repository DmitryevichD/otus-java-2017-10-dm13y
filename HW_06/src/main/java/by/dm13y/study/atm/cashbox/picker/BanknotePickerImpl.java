package by.dm13y.study.atm.cashbox.picker;

import by.dm13y.study.atm.money.Banknote;
import by.dm13y.study.atm.money.Money;
import by.dm13y.study.atm.cashbox.CashCartridge;
import by.dm13y.study.atm.money.MoneyUtils;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class BanknotePickerImpl implements BanknotePicker {
    private Comparator<? super Banknote> cashComparator;
    public BanknotePickerImpl(Comparator<? super Banknote> cashComparator) {
        this.cashComparator = cashComparator;
    }

    private Map<Banknote, CashCartridge> sorted(Map<Banknote, CashCartridge> cartridgeBox) {
        SortedMap<Banknote, CashCartridge> newOrder = new TreeMap<Banknote, CashCartridge>(cashComparator);
        newOrder.putAll(cartridgeBox);
        return newOrder;

    }

    private void sortingCheck(Map<Banknote, CashCartridge> cartridgeBox){
        if(cartridgeBox instanceof SortedMap){
            if(((SortedMap)cartridgeBox).comparator().equals(cashComparator)){
                //already sorted
               return;
            }
        }
        cartridgeBox = sorted(cartridgeBox);
    }

    @Override
    public List<Banknote> pickUpMoney(Map<Banknote, CashCartridge> cartridges, Money money) throws UnsupportedOperationException {
        sortingCheck(cartridges);

        Map<Banknote, Integer> banknotes = isAvailable(cartridges, money);

        List<Banknote> resultList = new ArrayList<>();

        if(!banknotes.isEmpty()){
            for(Map.Entry<Banknote, Integer> banknote : banknotes.entrySet()){
                resultList.addAll(cartridges.get(banknote)   .get(banknote.getValue()));
            }
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
                money = MoneyUtils.sub(money, banknote, available);
            }
            if (money.isZero()) {return result;}
        }
        if(money.isZero()){
            return result;
        }else{
            return Collections.emptyMap();
        }
    }


}
