package by.dm13y.study.atm;

import java.util.Objects;

public class Money {
    private long rub;
    private int cops;

    public Money(){}

    public Money(long rub, int cops) {
        addMoney(rub, cops);
    }

    public void addMoney(long rub, int cops){
        this.rub += rub;
        this.cops +=cops;
        this.rub += cops / 100;
        this.cops = cops % 100;
    }

    public void addMoney(Money money){
        addMoney(money.rub, money.cops);
    }

    public void addMoney(Banknote banknote, int count) {
        for (int i = 0; i < count; i++) {
            addMoney(banknote.getNominal());
        }
    }

    @Override
    public boolean equals(Object object) {
        if(object == null) return false;
        if(object == this) return true;
        if(!(object instanceof Money)){
            return false;
        }
        Money money = (Money)object;
        return money.rub == this.rub && money.cops == this.cops;
    }

    @Override
    public int hashCode(){
        return Objects.hash(rub, cops);
    }
}
