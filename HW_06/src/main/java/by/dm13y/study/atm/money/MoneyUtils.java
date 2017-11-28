package by.dm13y.study.atm.money;


public interface MoneyUtils {
    static Money add(Money money1, Money money2){
        long rub = money1.getRub() + money2.getRub();
        int cops = money1.getCops() + money2.getCops();
        return new Money(rub, cops);
    }

    static Money add(Money money, Banknote banknote, int countBanknote) {
        return add(money, countMoney(banknote, countBanknote));
    }

    static Money add(Money money, Banknote banknote) {
        return add(money, countMoney(banknote, 1));
    }

    static Money sub(Money reduced, Money substacted) throws UnsupportedOperationException {
        if(substacted.compareTo(reduced) > 0) throw new UnsupportedOperationException("value can not be less than zero");
        long rub = reduced.getRub() - substacted.getRub();
        int cops = reduced.getCops() - substacted.getCops();
        if(cops < 0){
            rub--;
            cops = cops + 100;
        }
        return new Money(rub, cops);
    }

    static Money sub(Money reduced, Banknote banknote, int countBanknote){
        return sub(reduced, countMoney(banknote, countBanknote));
    }

    static Money multiply(Money money, int factor) {
        return new Money(money.getRub() * factor, money.getCops() * factor);
    }

    static Money countMoney(Banknote banknote, int count){
        return multiply(banknote.getNominal(), count);
    }

    static int countBanknotes(Money money, Banknote banknote) {
        int countBanknotes = 0;

        while(money.compareTo(banknote.getNominal()) > 0) {
            countBanknotes++;
            money = sub(money, banknote.getNominal());
        }
        if (money.equals(banknote.getNominal())) {
            countBanknotes++;
        }

        return countBanknotes;
    }
}
