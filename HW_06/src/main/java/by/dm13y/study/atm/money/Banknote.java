package by.dm13y.study.atm.money;

public enum Banknote {
    ONE_RUB(1, 0),
    TWO_RUB(2, 0),
    FIVE_RUB(5, 0),
    TEN_RUB(10, 0),
    TWENTY_RUB(20, 0),
    FIFTY_RUB(50, 0);

    private final Money nominal;

    private Banknote(long rub, int cops){
        this.nominal = new Money(rub, cops);
    }

    public Money getNominal(){
        return nominal;
    }
}
