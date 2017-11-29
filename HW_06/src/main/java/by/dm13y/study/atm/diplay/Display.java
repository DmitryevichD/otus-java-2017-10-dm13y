package by.dm13y.study.atm.diplay;

import java.util.List;

public interface Display {
    default void showHeader(){
        System.out.println("WELCOME TO OUR ATM");
        System.out.println("INPUT YOUR CARD");
        System.out.println("+====================+");
    }

    default void getValue(String msg){
        System.out.print(msg + ">>> ");
    }
    default void showInfo(String info){
        System.out.print(info);
    }
    default void showError(String msg){
        System.err.println(msg);
    }

    default void showList(List<String> list){
        System.out.println(String.join("\n", list));
    }
}
