package by.dm13y.study;

import java.util.Arrays;
import java.util.Objects;

public class TestClass {
    String testString = "ABCDEFJ";
    Integer testInteger = 1;
    Long testLong = 1l;
    Float testFloat = 1.1f;
    Byte testByte = 1;
    Character testChar = '1';
    Double testDouble = 1.1;

    int test_Integer = 1;
    long test_Long = 1l;
    float test_Float = 1.1f;
    byte test_Byte = 1;
    char test_Char = '1';
    double test_Double = 1.1;

    int[] intArray = new int[]{1, 2, 3, 4, 5, 6};
    TestSubClass tc = new TestSubClass();

    String [] strArray = new String[]{"st1", "st2", null, null};

    Object[] objects = new Object[]{new TestSubClass(), null, null, null};

    @Override
    public boolean equals(Object object) {
        if(object == this) return true;
        if(!(object instanceof TestClass)){
            return false;
        }
        TestClass testClass = (TestClass)object;
        return (this.testString.equals(testClass.testString) &&
                this.testInteger == testClass.testInteger &&
                this.testLong == testClass.testLong &&
                this.testByte == testClass.testByte &&
                this.testChar == testClass.testChar &&
                this.test_Integer == testClass.test_Integer &&
                this.test_Long == testClass.test_Long &&
                this.test_Byte == testClass.test_Byte &&
                this.test_Char == testClass.test_Char &&
                Arrays.equals(this.intArray, testClass.intArray) &&
                this.tc.equals(testClass.tc) &&
                Arrays.equals(this.strArray, testClass.strArray));

    }

    @Override
    public int hashCode(){
        return Objects.hash(testString, testInteger, testLong, testFloat, testByte, testChar, testDouble,
                test_Integer, test_Long, test_Float, test_Byte, test_Char, test_Double, intArray, strArray,
                objects);
    }
}
