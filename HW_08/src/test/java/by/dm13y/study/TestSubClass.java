package by.dm13y.study;

import java.util.Arrays;
import java.util.Objects;

public class TestSubClass {
    int subInt = 10;
    String[] subStringArray = new String[]{"12", "15"};



    @Override
    public boolean equals(Object object) {
        if(object == this) return true;
        if(!(object instanceof TestSubClass)){
            return false;
        }
        TestSubClass testSubClass = (TestSubClass)object;
        return (this.subInt == testSubClass.subInt && Arrays.equals(this.subStringArray, testSubClass.subStringArray));
    }

    @Override
    public int hashCode(){
        return Objects.hash(subInt, subStringArray);
    }
}
