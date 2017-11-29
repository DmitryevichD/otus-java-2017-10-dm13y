package by.dm13y.study.atm.cashService;

import by.dm13y.study.atm.money.Banknote;

import java.util.List;

public class SimpleCashService extends CashService {

    public SimpleCashService(List<Banknote> acceptedBanknotes){
        setAcceptedBanknotes(acceptedBanknotes);
    }

    @Override
    void processBanknote(Banknote banknote) {
        notifyATM(banknote);
    }
}
