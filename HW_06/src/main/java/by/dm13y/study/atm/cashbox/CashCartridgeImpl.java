package by.dm13y.study.atm.cashbox;

import by.dm13y.study.atm.money.Banknote;

import java.util.ArrayList;
import java.util.List;

public class CashCartridgeImpl implements CashCartridge {
    private final int maxCount;
    private final Banknote nominal;
    private int count;

    public CashCartridgeImpl(Banknote nominal, int max, int count) throws UnsupportedOperationException{
        if (max < count) {
            throw new UnsupportedOperationException("current count is mach than max count");
        }
        this.maxCount = max;
        this.nominal = nominal;
        this.count = count;
    }

    @Override
    public int countBanknote() {
        return count;
    }

    private boolean isCorrect(Banknote newBanknote){
        return newBanknote == nominal;
    }

    @Override
    public void add(Banknote banknote) {
        if (!isCorrect(banknote)) {
            throw new UnsupportedOperationException("Banknote isn't correct");
        }
        if(!isFull()) {
            count++;
        }else {
            throw new UnsupportedOperationException("Cartridge is full");
        }
    }

    @Override
    public Banknote get() {
        if(!isEmpty()){
            count--;
            return nominal;
        }else {
            throw new UnsupportedOperationException("Cartridge is empty");
        }
    }

    @Override
    public List<Banknote> get(int count) {
        if (count > countBanknote()) {
            throw new UnsupportedOperationException("Banknote count too much");
        }else {
            List<Banknote> banknoteList = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                banknoteList.add(nominal);
            }
            this.count -= count;
            return banknoteList;
        }
    }

    @Override
    public boolean isEmpty() {
        return countBanknote() == 0;
    }

    @Override
    public boolean isFull() {
        return countBanknote() == maxCount;
    }
}
