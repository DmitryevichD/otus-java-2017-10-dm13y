package by.dm13y.study.department.impl;

import by.dm13y.study.atm.memento.CaretakerATM;
import by.dm13y.study.atm.money.Money;
import by.dm13y.study.atm.money.MoneyUtils;
import by.dm13y.study.department.Department;
import by.dm13y.study.atm.DepATM;
import java.util.*;

public class DepartmentImpl extends Observable implements Department {
    private final List<DepATM> atms;
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final Map<DepATM, CaretakerATM> states = new HashMap<>();

    public DepartmentImpl(List<DepATM> atms) {
        this.atms = atms;
        atms.forEach(this::addObserver);
        atms.forEach(atm -> {
            CaretakerATM caretakerATM = new CaretakerATM();
            caretakerATM.setMementoATM(atm.getCurrentState());
            states.put(atm, caretakerATM);
        });
    }

    @Override
    public Money getRestOfMoney() {
        Money rest = new Money();
        for (DepATM atm : atms) {
            rest = MoneyUtils.add(rest, atm.getRest());
        }
        return rest;
    }

    @Override
    public void resetAllATM() {
        setChanged();
        notifyObservers(null);
    }

    public List<DepATM> getAtms() {
        return atms;
    }
}
