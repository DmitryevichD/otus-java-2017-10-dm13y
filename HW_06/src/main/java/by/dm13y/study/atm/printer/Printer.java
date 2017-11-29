package by.dm13y.study.atm.printer;

public interface Printer {
    default void print(String msg){
        System.out.println("Printer: " + msg);
    }
}
