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

    private long getTotalCops(){return rub * 100 + cops;}

    public Money(long rub){this(rub, 0);}

    long getRub() {return rub;}

    int getCops() {
        return cops;
    }

    public boolean isZero(){return getTotalCops() == 0;}

    @Override
    public boolean equals(@NotNull Object object) {
        if(object == this) return true;
        if(!(object instanceof Money)){
            return false;
        }
        Money money = (Money)object;
        return getTotalCops() == money.getTotalCops();
    }

    @Override
    public int hashCode(){
        return Objects.hash(rub, cops);
    }

    @Override
    public int compareTo(@NotNull Money money) {
        return (int)(getTotalCops() - money.getTotalCops());
    }

    @Override
    public String toString(){
        return (rub > 0 ? rub : "0") + " р." + (cops > 0 ? cops : "00") + " k.";
    }
}
