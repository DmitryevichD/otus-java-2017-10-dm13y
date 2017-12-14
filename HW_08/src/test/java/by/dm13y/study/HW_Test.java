package by.dm13y.study;

import com.google.gson.Gson;
import org.junit.Test;

import static org.junit.Assert.*;

public class HW_Test {

    @Test
    public void toJson() throws Exception {
        TestClass testClass = new TestClass();

        Gson gson = new Gson();
        String gsonString = gson.toJson(testClass);
        System.out.println(gsonString);

        JsonMaker jm = new JsonMaker();
        String hw_result = jm.toJson(testClass);
        System.out.println(hw_result);

        TestClass serializeObject = gson.fromJson(hw_result, TestClass.class);

        assertTrue(serializeObject.equals(testClass));
    }

}