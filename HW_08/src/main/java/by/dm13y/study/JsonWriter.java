package by.dm13y.study;

import java.io.StringWriter;

public class JsonWriter extends StringWriter {
    private boolean isFirstField = false;
    private boolean isFirstElementArray = false;

    public void beginObject() {
        write("{");
        isFirstField = true;
    }
    public void endObject() {
        write("}");
    }

    public void beginArray(){
        write("[");
        isFirstElementArray = true;
    }
    public void endArray(){
        write("]");
    }

    public void name(String name){
        if (isFirstField) {
            write("\"" + name + "\":");
            isFirstField = false;
        }else {
            write(",\"" + name + "\":");
        }
    }

    public void value(Object value){
        if (value == null) {
            write("null");
        }else if (value instanceof Number) {
            write(value.toString());
        }else {
            write("\"" + value + "\"");
        }
    }
    public void arrayValue(Object value){
        if(isFirstElementArray){
            value(value);
            isFirstElementArray = false;
        }else {
            write(",");
            value(value);
        }
    }
}
