package by.dm13y.study.atm.display;

import java.util.List;

public interface Display {
    void showHeader();
    void showInputMessage(String msg);
    void showInfo(String info);
    void showError(String msg);
    void showList(List<String> list);
}
