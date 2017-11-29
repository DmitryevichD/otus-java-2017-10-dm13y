package by.dm13y.study.atm.cashService;

import by.dm13y.study.atm.money.Banknote;
import by.dm13y.study.atm.money.Money;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class CashServiceImpl implements CashService {
    private List<Banknote> acceptedBanknotes;

    public CashServiceImpl(List<Banknote> acceptedBanknotes){
        setAcceptedBanknotes(acceptedBanknotes);
    }

    @Override
    public void setAcceptedBanknotes(@NotNull List<Banknote> acceptedBanknotes) {
        this.acceptedBanknotes = acceptedBanknotes;
    }

    @Override
    public boolean isAccepted(Banknote banknote) {
        return acceptedBanknotes.contains(banknote);
    }

    private Banknote findBanknote(Money money) {
        return Arrays.stream(Banknote.values())
                .filter(banknote -> money.equals(banknote.getNominal()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Banknote setBanknoteEvent() throws Exception {
        Long nominal = new Scanner(System.in).nextLong();
        Money money = new Money(nominal);
        Banknote banknote = findBanknote(money);
        if(banknote == null) throw new UnsupportedOperationException("this nominal is not supported");
        if(!isAccepted(banknote)) throw new UnsupportedOperationException("this nominal is not supported");
        return banknote;
    }

}
