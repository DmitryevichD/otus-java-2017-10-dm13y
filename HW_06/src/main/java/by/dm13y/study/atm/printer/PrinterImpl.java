package by.dm13y.study.atm.printer;

public class PrinterImpl implements Printer {
    @Override
    public void print(String msg){
        System.out.println("Printer: " + msg);
    }
}
