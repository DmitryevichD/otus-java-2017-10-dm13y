package by.dm13y.study.atm.money;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Money implements Comparable<Money>{
    private long rub;
    private int cops;

    public Money(){}

    public Money(long rub, int cops) {
        this.rub = rub;
        this.rub +=cops / 100;
        this.cops = cops % 100;
    }

    public Money(long rub){
        this(rub, 0);
    }

    public long getRub() {
        return rub;
    }

    public int getCops() {
        return cops;
    }

    public boolean moreThan(@NotNull Money money) {
        if (rub > money.rub) {
            return true;
        } else if (rub == money.rub && cops > money.cops) {
            return true;
        }else {
            return false;
        }

    }

    public boolean isZero(){
        return rub == 0 && cops == 0;
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

    @Override
    public int compareTo(@NotNull Money money) {
        if (rub == money.rub && cops > money.cops) return 0;
        if()
    }
}
