package by.dm13y.study.department.impl;

import by.dm13y.study.atm.money.Money;
import by.dm13y.study.atm.money.MoneyUtils;
import by.dm13y.study.department.Department;
import by.dm13y.study.atm.DepATM;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class DepartmentImpl extends Observable implements Department {
    private List<DepATM> atms;

    public DepartmentImpl(List<DepATM> atms) {
        atms = new ArrayList<>(atms);
        atms.forEach(this::addObserver);
    }

    @Override
    public Money getRestOfMoney() {
        Money rest = new Money();
        for (DepATM atm : atms) {
            rest = MoneyUtils.add(rest, atm.getCurrentRest());
        }
        return rest;
    }

    @Override
    public void resetAllATM() {
        notifyObservers();
    }
}
