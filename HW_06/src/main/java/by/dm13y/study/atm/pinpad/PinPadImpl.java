package by.dm13y.study.atm.pinpad;

import java.io.InputStream;
import java.util.Scanner;

public class PinPadImpl implements Pinpad {
    InputStream is;

    public PinPadImpl(InputStream inputStream) {
        is = inputStream;
    }

    @Override
    public int enterValue(){
        return new Scanner(is).nextInt();
    }
}
