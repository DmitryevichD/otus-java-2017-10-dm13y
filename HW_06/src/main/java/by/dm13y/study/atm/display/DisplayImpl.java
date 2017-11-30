package by.dm13y.study.atm.display;

import java.util.List;

public class DisplayImpl implements Display{
    @Override
    public void showHeader(){
        System.out.println("WELCOME TO OUR ATM");
        System.out.println("INPUT YOUR CARD");
        System.out.println("+====================+");
    }

    @Override
    public void showInputMessage(String msg){
        System.out.print(msg + ">>> ");
    }

    @Override
    public void showInfo(String info){
        System.out.print(info +  "\n");
    }

    @Override
    public void showError(String msg){
        System.err.println(msg + "\n");
    }

    @Override
    public void showList(List<String> list){
        System.out.println(String.join("\n", list) + "\n");
    }

}
